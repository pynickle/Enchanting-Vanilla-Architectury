package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.api.IVillager;
import com.euphony.enc_vanilla.common.entity.DoubleHandedTemptGoal;
import com.euphony.enc_vanilla.common.entity.VillagerAttractionGoal;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.euphony.enc_vanilla.utils.LockedTradeData;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager implements IVillager {
    @Unique
    private DoubleHandedTemptGoal enc_vanilla$villagersAttractedGoal;

    @Unique
    private boolean enc_vanilla$villagersAttracted;

    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyReturnValue(method = "createAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder addAttributes(AttributeSupplier.Builder builder) {
        return builder.add(Attributes.TEMPT_RANGE, 10.0F);
    }

    @Inject(
            method = "Lnet/minecraft/world/entity/npc/Villager;<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/Holder;)V",
            at = @At(
                    value = "RETURN"
            )
    )
    private void init(EntityType<? extends Villager> entityType, Level level, Holder<VillagerType> holder, CallbackInfo ci) {
        this.enc_vanilla$villagersAttractedGoal = new VillagerAttractionGoal(this);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "RETURN"
            )
    )
    private void checkVillagersAttracted(CallbackInfo ci) {
        final boolean configEnabled = QolConfig.HANDLER.instance().enableVillagerAttraction;
        if(!this.enc_vanilla$villagersAttracted && configEnabled) {
            this.goalSelector.addGoal(0, this.enc_vanilla$villagersAttractedGoal);
            this.enc_vanilla$villagersAttracted = true;
        } else if(this.enc_vanilla$villagersAttracted && !configEnabled) {
            this.goalSelector.removeGoal(this.enc_vanilla$villagersAttractedGoal);
            this.enc_vanilla$villagersAttracted = false;
        }
    }


    @Shadow
    public abstract @NotNull VillagerData getVillagerData();

    @Unique
    private final Mutable<LockedTradeData> enc_vanilla$lockedTradeData = new MutableObject<>();

    @Unique
    private int enc_vanilla$lastKnownLevel = -1;

    @Unique
    private boolean enc_vanilla$isDirty = false;

    /**
     * 安全地执行带有LockedTradeData的操作
     */
    @Unique
    private void enc_vanilla$ifPresent(Consumer<LockedTradeData> consumer) {
        LockedTradeData data = enc_vanilla$lockedTradeData.getValue();
        if (data != null) {
            try {
                consumer.accept(data);
            } catch (Exception e) {
                // 如果出现异常，清理损坏的数据
                enc_vanilla$lockedTradeData.setValue(null);
                enc_vanilla$isDirty = true;
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void saveLockedTradeData(ValueOutput valueOutput, CallbackInfo ci) {
        enc_vanilla$ifPresent(data -> data.write(valueOutput));
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readLockedTradeData(ValueInput valueInput, CallbackInfo ci) {
        try {
            LockedTradeData data = LockedTradeData.constructOrNull(valueInput);
            enc_vanilla$lockedTradeData.setValue(data);
            enc_vanilla$isDirty = (data == null);
        } catch (Exception e) {
            // 读取失败时清理数据
            enc_vanilla$lockedTradeData.setValue(null);
            enc_vanilla$isDirty = true;
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void removeLockedTradeDataIfNoOffers(CallbackInfo ci) {
        // 如果没有交易，清理锁定数据
        if (this.offers == null || this.offers.isEmpty()) {
            if (enc_vanilla$lockedTradeData.getValue() != null) {
                enc_vanilla$lockedTradeData.setValue(null);
                enc_vanilla$isDirty = false;
            }
            return;
        }

        // 检查村民等级是否变化
        int currentLevel = getVillagerData().level();
        if (enc_vanilla$lastKnownLevel != currentLevel) {
            enc_vanilla$lastKnownLevel = currentLevel;
            enc_vanilla$isDirty = true;
        }

        // 如果数据脏了，重新生成
        if (enc_vanilla$isDirty) {
            enc_vanilla$regenerateLockedTradeData();
            enc_vanilla$isDirty = false;
        }

        // 更新锁定交易数据
        enc_vanilla$ifPresent(data -> data.tick((Villager) (Object) this, this::appendLockedOffer));
    }

    @Inject(method = "updateTrades", at = @At("HEAD"), cancellable = true)
    private void preventAdditionalTradesOnRankIncrease(CallbackInfo ci) {
        // 如果没有交易或交易为空，清理锁定数据
        if (this.offers == null || this.offers.isEmpty()) {
            enc_vanilla$lockedTradeData.setValue(null);
            enc_vanilla$isDirty = false;
            return;
        }

        // 尝试添加锁定的交易
        if (appendLockedOffer()) {
            ci.cancel();
        }
    }

    /**
     * 优化的锁定交易添加逻辑
     */
    @Unique
    private boolean appendLockedOffer() {
        if (this.offers == null) {
            return false;
        }

        AtomicBoolean result = new AtomicBoolean(false);
        enc_vanilla$ifPresent(data -> {
            MerchantOffers dismissedTrades = data.popTradeSet();
            if (dismissedTrades != null && !dismissedTrades.isEmpty()) {
                // 验证交易的有效性
                MerchantOffers validTrades = new MerchantOffers();
                for (var offer : dismissedTrades) {
                    if (offer != null && !offer.getResult().isEmpty()) {
                        validTrades.add(offer);
                    }
                }

                if (!validTrades.isEmpty()) {
                    this.offers.addAll(validTrades);
                    result.set(true);
                }
            }
        });

        return result.get();
    }

    /**
     * 重新生成锁定交易数据
     */
    @Unique
    private void enc_vanilla$regenerateLockedTradeData() {
        try {
            enc_vanilla$lockedTradeData.setValue(new LockedTradeData((Villager) (Object) this));
        } catch (Exception e) {
            // 生成失败时清理
            enc_vanilla$lockedTradeData.setValue(null);
        }
    }

    @Override
    public Optional<LockedTradeData> enc_vanilla$getLockedTradeData() {
        return Optional.ofNullable(enc_vanilla$lockedTradeData.getValue());
    }

    /**
     * 优化的等级计算，包含交易数量信息
     */
    @Override
    public int enc_vanilla$getShiftedLevel() {
        int level = getVillagerData().level();
        if (this.offers == null) {
            return level;
        }

        // 将交易数量编码到高位
        int tradeCount = this.offers.size();
        return level | (tradeCount << 8);
    }

    /**
     * 获取合并的交易列表（当前+锁定）
     */
    @Override
    public MerchantOffers enc_vanilla$getCombinedOffers() {
        MerchantOffers combinedOffers = new MerchantOffers();

        // 添加当前交易
        if (this.offers != null) {
            combinedOffers.addAll(this.offers);
        }

        // 确保锁定数据存在
        if (enc_vanilla$lockedTradeData.getValue() == null) {
            enc_vanilla$regenerateLockedTradeData();
        }

        // 添加锁定的交易
        enc_vanilla$ifPresent(data -> {
            MerchantOffers lockedOffers = data.buildLockedOffers();
            if (lockedOffers != null) {
                combinedOffers.addAll(lockedOffers);
            }
        });

        return combinedOffers;
    }

    /**
     * 检查是否有锁定的交易数据
     */
    @Unique
    public boolean enc_vanilla$hasLockedTrades() {
        return enc_vanilla$lockedTradeData.getValue() != null &&
                !enc_vanilla$lockedTradeData.getValue().hasNoOffers();
    }

    /**
     * 获取锁定交易的总数量
     */
    @Unique
    public int enc_vanilla$getLockedTradesCount() {
        return enc_vanilla$getLockedTradeData()
                .map(LockedTradeData::getTotalLockedTradesCount)
                .orElse(0);
    }
}

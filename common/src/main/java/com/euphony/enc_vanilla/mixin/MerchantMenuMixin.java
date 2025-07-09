package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.api.IClientSideMerchant;
import com.euphony.enc_vanilla.api.IMerchantMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MerchantMenu.class)
public abstract class MerchantMenuMixin implements IMerchantMenu {

    @Shadow private int merchantLevel;
    @Shadow @Final private Merchant trader;

    @Shadow public abstract MerchantOffers getOffers();

    @Unique
    private int enc_vanilla$unlockedTradeCount = 0;

    @Unique
    private static final int LEVEL_BITMASK = 0xFF; // 255

    @Unique
    private static final int TRADE_COUNT_SHIFT = 8;

    /**
     * 从传入的整数中读取解锁的交易数量
     */
    @Inject(method = "setMerchantLevel", at = @At("TAIL"))
    private void readUnlockedTradeCountFromLevel(int i, CallbackInfo ci) {
        // 解码交易数量和村民等级
        this.enc_vanilla$unlockedTradeCount = i >> TRADE_COUNT_SHIFT;
        this.merchantLevel = i & LEVEL_BITMASK;

        // 如果是客户端商人，则设置客户端解锁的交易
        if (this.trader instanceof IClientSideMerchant clientMerchant) {
            try {
                if (enc_vanilla$unlockedTradeCount > 0 && enc_vanilla$unlockedTradeCount <= getOffers().size()) {
                    List<MerchantOffer> unlockedTrades = getOffers().subList(0, enc_vanilla$unlockedTradeCount);
                    MerchantOffers offers = new MerchantOffers();
                    offers.addAll(unlockedTrades);
                    clientMerchant.enc_vanilla$setClientUnlockedTrades(offers);
                } else {
                    // 如果交易数量无效，则清空
                    clientMerchant.enc_vanilla$setClientUnlockedTrades(new MerchantOffers());
                }
            } catch (Exception e) {
                // 异常处理
                clientMerchant.enc_vanilla$setClientUnlockedTrades(new MerchantOffers());
            }
        }
    }

    /**
     * 检查是否应允许交易
     */
    @Override
    public boolean enc_vanilla$shouldAllowTrade(int i) {
        // 如果unlockedTradeCount为0，则允许所有交易（向后兼容）
        if (enc_vanilla$unlockedTradeCount == 0) {
            return true;
        }
        // 否则，只允许在解锁范围内的交易
        return i < enc_vanilla$unlockedTradeCount;
    }
}


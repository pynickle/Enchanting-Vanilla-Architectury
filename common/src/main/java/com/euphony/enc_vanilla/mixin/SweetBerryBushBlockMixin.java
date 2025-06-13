package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SweetBerryBushBlock.class)
public abstract class SweetBerryBushBlockMixin extends BushBlock implements BonemealableBlock {
    protected SweetBerryBushBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    protected void entityInsideInject(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        if(!QolConfig.HANDLER.instance().enableBushProtection) return;
        if(QolConfig.HANDLER.instance().permanentVillagersProtection && entity.getType() == EntityType.VILLAGER) {
            enc_vanilla$stuckInBlock(entity, blockState);
            ci.cancel();
        } else if(entity.getType() == EntityType.PLAYER) {
            if(QolConfig.HANDLER.instance().needLeggings) {
                Player player = (Player) entity;
                if(!player.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
                    enc_vanilla$stuckInBlock(entity, blockState);
                    ci.cancel();
                }
            } else {
                enc_vanilla$stuckInBlock(entity, blockState);
                ci.cancel();
            }
        }
    }

    @Unique
    private void enc_vanilla$stuckInBlock(Entity entity, BlockState blockState) {
        if (QolConfig.HANDLER.instance().enableSlowerSpeed) {
            entity.makeStuckInBlock(blockState, new Vec3(0.8F, 0.75F, 0.8F));
        }
    }
}

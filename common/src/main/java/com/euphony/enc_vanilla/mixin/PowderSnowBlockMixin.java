package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.utils.AABBUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin {
    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    protected void entityInsideInject(BlockState blockState, Level level, BlockPos blockPos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier, CallbackInfo ci) {
        if(entity instanceof ItemEntity item && item.getItem().is(EVItems.BIOME_CRYSTAL_ITEM.get())) {
            AABB aabb = item.getBoundingBox();
            aabb = aabb.setMaxY(aabb.maxY + 0.4D);
            aabb = aabb.inflate(0.1D, 0.0D, 0.1D);
            if(AABBUtils.contains(new AABB(blockPos), aabb)) {
                item.setItem(EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get().getDefaultInstance());
                ci.cancel();
            }
        }
    }
}

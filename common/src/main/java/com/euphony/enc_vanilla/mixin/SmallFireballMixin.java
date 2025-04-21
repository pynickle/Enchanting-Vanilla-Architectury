package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.common.init.EVItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SmallFireball.class)
public abstract class SmallFireballMixin extends Fireball {
    public SmallFireballMixin(EntityType<? extends Fireball> entityType, Level level) {
        super(entityType, level);
    }

  @Inject(method = "onHitBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isEmptyBlock(Lnet/minecraft/core/BlockPos;)Z"), cancellable = true)
  protected void onHitBlockInject(BlockHitResult blockHitResult, CallbackInfo ci, @Local BlockPos blockPos) {
        AABB checkBox = new AABB(blockPos).inflate(0.5D, 0.0D, 0.5D);
        Level level = this.level();
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, checkBox);
        for(ItemEntity item : items) {
            if (item.getItem().is(EVItems.BIOME_CRYSTAL_ITEM)) {
                item.discard();
                Containers.dropItemStack(level, blockPos.getX(), blockPos.getY() + 0.5, blockPos.getZ(), EVItems.HEATED_BIOME_CRYSTAL_ITEM.get().getDefaultInstance());
                ci.cancel();
            }
        }
    }
}

package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.utils.custom.ItemFramesUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.projectile.AbstractThrownPotion;
import net.minecraft.world.entity.projectile.ThrownSplashPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownSplashPotion.class)
public class ThrownSplashPotionMixin {
    @Inject(method = "onHitAsPotion", at = @At(value = "INVOKE",
          target = "Lnet/minecraft/world/phys/AABB;inflate(DDD)Lnet/minecraft/world/phys/AABB;",
          shift = At.Shift.AFTER,
          ordinal = 0))
    public void onHitAsPotionInject(ServerLevel serverLevel, ItemStack itemStack, HitResult hitResult, CallbackInfo ci, @Local Iterable<MobEffectInstance> iterable) {
        ItemFramesUtils.handleInvisibility((AbstractThrownPotion) (Object) this, iterable);
    }

}

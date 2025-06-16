package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.utils.custom.ItemFramesUtils;
import net.minecraft.world.entity.projectile.AbstractThrownPotion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractThrownPotion.class)
public class AbstractThrownPotionMixin {
    @Inject(method = "onHitAsWater", at = @At("HEAD"))
    private void applyWater(CallbackInfo ci) {
        ItemFramesUtils.setInvisibility((AbstractThrownPotion) (Object) this, false);
    }
}
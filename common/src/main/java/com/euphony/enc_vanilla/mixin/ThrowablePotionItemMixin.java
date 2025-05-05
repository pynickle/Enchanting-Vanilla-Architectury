package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrowablePotionItem.class)
public abstract class ThrowablePotionItemMixin extends PotionItem implements ProjectileItem {
    public ThrowablePotionItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "use",
            at = @At("RETURN")
    )
    private void useInject(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (!level.isClientSide) {
            player.getCooldowns().addCooldown(this, QolConfig.HANDLER.instance().throwablePotionCooldown);
        }
    }
}

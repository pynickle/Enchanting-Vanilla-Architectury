package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Leashable.class)
public interface LeashableMixin {
    @Inject(method = "leashTooFarBehaviour", at = @At("HEAD"))
    default void onLeashTooFarBehaviour(CallbackInfo ci) {
        if(!QolConfig.HANDLER.instance().enableBrokenLead) return;

        Entity entity = (Entity) this;
        Leashable.LeashData leashData = ((Leashable) entity).getLeashData();
        Entity leashHolder = leashData.leashHolder;
        if (leashHolder instanceof Player) {
            ((Player) leashHolder).displayClientMessage(
                    Component.translatable("message.enc_vanilla.broken_lead.message")
                            .append(Component.literal("(").withStyle(ChatFormatting.GOLD))
                            .append(entity.getName().copy().withStyle(ChatFormatting.GOLD))
                            .append(Component.literal(")").withStyle(ChatFormatting.GOLD)), true
            );
        }
    }
}

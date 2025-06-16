package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import dev.architectury.event.CompoundEventResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ThrowableFireChargeEvent {
    public static InteractionResult rightClickItem(Player player, InteractionHand interactionHand) {
        Level level = player.level();
        if(!QolConfig.HANDLER.instance().enableThrowableFireCharge || level.isClientSide) return InteractionResult.PASS;

        ItemStack itemStack = player.getItemInHand(interactionHand);
        if(itemStack.is(Items.FIRE_CHARGE)) {
            SmallFireball smallFireball = new SmallFireball(level, player, player.getLookAngle());
            smallFireball.setPos(player.getEyePosition());
            level.addFreshEntity(smallFireball);
            itemStack.consume(1, player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}

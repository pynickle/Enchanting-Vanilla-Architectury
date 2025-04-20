package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.config.categories.ToolsConfig;
import dev.architectury.event.EventResult;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

public class FrogBucketEvent {
    public static EventResult interactEntity(Player player, Entity entity, InteractionHand interactionHand) {
        if(player.level().isClientSide) return EventResult.pass();

        if(!ToolsConfig.HANDLER.instance().enableSlimeChunkDetecting) return EventResult.pass();

        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (entity.getType() == EntityType.FROG && itemstack.getItem() == Items.WATER_BUCKET && entity.isAlive()) {
            Frog frog = (Frog) entity;
            frog.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            ItemStack itemstack1 = new ItemStack(EVItems.FROG_BUCKET_ITEM.get());
            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemstack1, (p_330644_) -> {
                p_330644_.putString("variant", frog.getVariant().getRegisteredName());
                p_330644_.putFloat("Health", frog.getHealth());
            });
            ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, player, itemstack1, false);
            player.setItemInHand(interactionHand, itemstack2);

            frog.discard();
            return EventResult.interruptTrue();
        }
        return EventResult.pass();
    }
}

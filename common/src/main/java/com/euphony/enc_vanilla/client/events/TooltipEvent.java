package com.euphony.enc_vanilla.client.events;

import static com.euphony.enc_vanilla.utils.ItemUtils.createTooltip;

import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.config.categories.ToolsConfig;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientTooltipEvent;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;

public class TooltipEvent {
    public static EventResult renderPre(GuiGraphics guiGraphics, List<? extends ClientTooltipComponent> clientTooltipComponents, int i, int i1) {
        ItemStack stack = ClientTooltipEvent.additionalContexts().getItem();
        List<ClientTooltipComponent> mutable = (List<ClientTooltipComponent>) clientTooltipComponents;

        if(stack == null) return EventResult.pass();

        if (stack.is(EVItems.BIOME_CRYSTAL_ITEM)) {
            ResourceKey<Biome> biome = stack.get(EVDataComponentTypes.BIOME.get());
            if(biome == null) {
                mutable.add(createTooltip("item.enc_vanilla.biome_crystal.desc"));
            } else {
                mutable.add(createTooltip("biome." + biome.location().toLanguageKey()));
            }
        } else if(stack.is(EVItems.HEATED_BIOME_CRYSTAL_ITEM)) {
            ResourceKey<Biome> biome = stack.get(EVDataComponentTypes.BIOME.get());
            if(biome == null) {
                mutable.add(createTooltip("item.enc_vanilla.heated_biome_crystal.desc"));
            } else {
                mutable.add(createTooltip("biome." + biome.location().toLanguageKey()));
            }
        } else if(stack.is(EVItems.FROZEN_BIOME_CRYSTAL_ITEM)) {
            ResourceKey<Biome> biome = stack.get(EVDataComponentTypes.BIOME.get());
            if(biome == null) {
                mutable.add(createTooltip("item.enc_vanilla.frozen_biome_crystal.desc"));
            } else {
                mutable.add(createTooltip("biome." + biome.location().toLanguageKey()));
            }
        } else if(stack.is(EVItems.SCULK_COMPASS_ITEM.get()) || stack.is(EVItems.DAMAGED_SCULK_COMPASS_ITEM)) {
            if(!ToolsConfig.HANDLER.instance().enableSculkCompass) {
                mutable.add(createTooltip("item.enc_vanilla.sculk_compass.desc"));
            }
        }
        return EventResult.pass();
    }
}

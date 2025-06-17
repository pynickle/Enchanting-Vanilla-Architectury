package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.config.categories.tools.ToolsConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

import static com.euphony.enc_vanilla.utils.ItemUtils.createTooltip;

@Environment(EnvType.CLIENT)
public class TooltipEvent {
    public static void item(ItemStack stack, List<Component> components, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag) {
        if (stack.is(EVItems.BIOME_CRYSTAL_ITEM.get())) {
            ResourceKey<Biome> biome = stack.get(EVDataComponentTypes.BIOME.get());
            if(biome == null) {
                components.add(createTooltip("item.enc_vanilla.biome_crystal.desc"));
            } else {
                components.add(createTooltip("biome." + biome.location().toLanguageKey()));
            }
        } else if(stack.is(EVItems.HEATED_BIOME_CRYSTAL_ITEM.get())) {
            ResourceKey<Biome> biome = stack.get(EVDataComponentTypes.BIOME.get());
            if(biome == null) {
                components.add(createTooltip("item.enc_vanilla.heated_biome_crystal.desc"));
            } else {
                components.add(createTooltip("biome." + biome.location().toLanguageKey()));
            }
        } else if(stack.is(EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get())) {
            ResourceKey<Biome> biome = stack.get(EVDataComponentTypes.BIOME.get());
            if(biome == null) {
                components.add(createTooltip("item.enc_vanilla.frozen_biome_crystal.desc"));
            } else {
                components.add(createTooltip("biome." + biome.location().toLanguageKey()));
            }
        } else if(stack.is(EVItems.SCULK_COMPASS_ITEM.get()) || stack.is(EVItems.DAMAGED_SCULK_COMPASS_ITEM.get())) {
            if(!ToolsConfig.HANDLER.instance().enableSculkCompass) {
                components.add(createTooltip("item.enc_vanilla.sculk_compass.desc"));
            }
        }
    }
}

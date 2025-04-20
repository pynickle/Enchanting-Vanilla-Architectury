package com.euphony.enc_vanilla.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

public final class ItemUtils {
    private ItemUtils() {}

    public static ResourceLocation getKey(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static ClientTooltipComponent createTooltip(String key) {
        return ClientTooltipComponent.create(Component.translatable(key).withStyle(ChatFormatting.GRAY).getVisualOrderText());
    }

    public static ClientTooltipComponent createTooltip(String key, Object... args) {
        return ClientTooltipComponent.create(Component.translatable(key, args).withStyle(ChatFormatting.GRAY).getVisualOrderText());
    }
}
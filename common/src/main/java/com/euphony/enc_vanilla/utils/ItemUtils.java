package com.euphony.enc_vanilla.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class ItemUtils {
    private ItemUtils() {}

    public static ResourceLocation getKey(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static MutableComponent createTooltip(String key) {
        return Component.translatable(key).withStyle(ChatFormatting.GRAY);
    }

    public static MutableComponent createTooltip(String key, Object... args) {
        return Component.translatable(key, args).withStyle(ChatFormatting.GRAY);
    }

    public static int getItemTotalCount(Inventory inventory, ItemStack itemStack) {
        ItemStack itemStack1;
        int count = 0;

        for(int i = 0; i < inventory.items.size(); ++i) {
            itemStack1 = inventory.items.get(i);
            if (!itemStack1.isEmpty() && ItemStack.isSameItemSameComponents(itemStack, itemStack1)) {
                count += itemStack1.getCount();
            }
        }

        return count;
    }

    public static Component getWrappedItemName(ItemStack stack) {
        MutableComponent mutableComponent = Component.empty().append(stack.getHoverName());
        if (stack.has(DataComponents.CUSTOM_NAME)) {
            mutableComponent.withStyle(ChatFormatting.ITALIC);
        }

        mutableComponent.withStyle(stack.getRarity().color());

        return createTooltip("[")
                .append(mutableComponent)
                .append(createTooltip("] x"))
                .append(createTooltip(String.valueOf(stack.getCount())));
    }
}
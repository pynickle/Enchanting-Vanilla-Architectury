package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.BeehiveBlock;

import java.util.List;
import java.util.Objects;

import static com.euphony.enc_vanilla.utils.ItemUtils.createTooltip;

@Environment(EnvType.CLIENT)
public class BeeInfoEvent {
    public static void item(ItemStack stack, List<Component> components, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag) {
        if (stack != null && (stack.is(Items.BEE_NEST) || stack.is(Items.BEEHIVE))) {
            if (!ClientConfig.HANDLER.instance().enableBeeInfo) return;

            DataComponentMap dataComponents = stack.getComponents();
            BlockItemStateProperties blockItemStateProperties = dataComponents.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
            int honeyLevel = Objects.requireNonNullElse(blockItemStateProperties.get(BeehiveBlock.HONEY_LEVEL), 0);
            int bee = dataComponents.getOrDefault(DataComponents.BEES, List.of()).size();
            components.add(createTooltip("container.beehive.bees", bee, 3));
            components.add(createTooltip("container.beehive.honey", honeyLevel, 5));
        }
    }
}

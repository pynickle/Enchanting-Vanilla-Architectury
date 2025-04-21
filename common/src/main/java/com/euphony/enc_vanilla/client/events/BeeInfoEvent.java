package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.config.categories.ClientConfig;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.BeehiveBlock;

import java.util.List;
import java.util.Objects;

import static com.euphony.enc_vanilla.utils.ItemUtils.createTooltip;

public class BeeInfoEvent {
    public static EventResult renderPre(GuiGraphics guiGraphics, List<? extends ClientTooltipComponent> clientTooltipComponents, int i, int i1) {
        ItemStack stack = ClientTooltipEvent.additionalContexts().getItem();
        List<ClientTooltipComponent> mutable = (List<ClientTooltipComponent>) clientTooltipComponents;

        if (stack != null && (stack.is(Items.BEE_NEST) || stack.is(Items.BEEHIVE))) {
            if (!ClientConfig.HANDLER.instance().enableBeeInfo) return EventResult.pass();

            DataComponentMap dataComponents = stack.getComponents();
            BlockItemStateProperties blockItemStateProperties = dataComponents.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
            int honeyLevel = Objects.requireNonNullElse(blockItemStateProperties.get(BeehiveBlock.HONEY_LEVEL), 0);
            int bee = dataComponents.getOrDefault(DataComponents.BEES, List.of()).size();
            mutable.add(createTooltip("container.beehive.bees", bee, 3));
            mutable.add(createTooltip("container.beehive.honey", honeyLevel, 5));
        }
        return EventResult.pass();
    }
}

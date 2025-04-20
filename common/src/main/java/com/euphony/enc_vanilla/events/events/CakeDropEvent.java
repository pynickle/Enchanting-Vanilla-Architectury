package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import dev.architectury.event.EventResult;
import dev.architectury.utils.value.IntValue;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public class CakeDropEvent {
    public static EventResult blockBreak(Level level, BlockPos blockPos, BlockState blockState, ServerPlayer serverPlayer, @Nullable IntValue intValue) {
        if (!QolConfig.HANDLER.instance().enableCakeDrop) return EventResult.pass();

        if (serverPlayer.isCreative()) return EventResult.pass();

        if (blockState.is(BlockTags.CANDLE_CAKES)) {
            dropCake(level, blockPos);
            return EventResult.interruptTrue();
        }

        if (blockState.hasProperty(BlockStateProperties.BITES)) {
            int bites = blockState.getValue(BlockStateProperties.BITES);
            if (bites == 0) {
                dropCake(level, blockPos);
            }
        }
        return EventResult.pass();
    }

    private static void dropCake(Level level, BlockPos pos) {
        Containers.dropItemStack(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), new ItemStack(Items.CAKE));
    }
}

package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import dev.architectury.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AnvilRepairEvent {
    public static EventResult rightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        if(!QolConfig.HANDLER.instance().enableAnvilRepair) return EventResult.pass();

        if(!player.isShiftKeyDown()) return EventResult.pass();

        Level level = player.level();
        if (level.isClientSide) return EventResult.pass();

        ItemStack itemStack = player.getItemInHand(interactionHand);
        Item item = itemStack.getItem();

        if (!item.equals(QolConfig.HANDLER.instance().anvilRepairMaterial)) return EventResult.pass();

        BlockState state = level.getBlockState(blockPos);
        Block block = state.getBlock();

        BlockState newState;
        if (block.equals(Blocks.CHIPPED_ANVIL)) {
            newState = Blocks.ANVIL.defaultBlockState();
        } else if (block.equals(Blocks.DAMAGED_ANVIL)) {
            newState = Blocks.CHIPPED_ANVIL.defaultBlockState();
        } else {
            return EventResult.pass();
        }

        Direction facing = state.getValue(AnvilBlock.FACING);
        level.setBlockAndUpdate(blockPos, newState.setValue(AnvilBlock.FACING, facing));

        itemStack.consume(1, player);
        level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.5F, 1.0F);
        return EventResult.interruptTrue();
    }
}
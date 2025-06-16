package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class CeilingTorchEvent {
    private static final Map<Item, Block> placeEntries = ImmutableMap.of(
            Items.TORCH, EVBlocks.CEILING_TORCH.get(),
            Items.REDSTONE_TORCH, EVBlocks.CEILING_REDSTONE_TORCH.get(),
            Items.SOUL_TORCH, EVBlocks.CEILING_SOUL_TORCH.get()
    );

    public static InteractionResult rightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        Level level = player.level();
        if(level.isClientSide) return InteractionResult.PASS;

        if (!player.isSpectator()) {
            BlockPos placeAt = blockPos.relative(direction);

            if (direction == Direction.DOWN && (level.isEmptyBlock(placeAt) || !level.getFluidState(placeAt).isEmpty())) {
                ItemStack stack = player.getItemInHand(interactionHand);
                if(placeEntries.containsKey(stack.getItem())) {
                    placeTorch(player, interactionHand, stack, placeAt, level, placeEntries.get(stack.getItem()).defaultBlockState());
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    public static void placeTorch(Player player, InteractionHand hand, ItemStack stack, BlockPos pos, Level level, BlockState state) {
        if (state.canSurvive(level, pos)) {
            SoundType soundType;

            level.setBlockAndUpdate(pos, state);
            soundType = state.getBlock().defaultBlockState().getSoundType();
            level.playSound(player, pos.getX(), pos.getY(), pos.getZ(), soundType.getPlaceSound(), SoundSource.BLOCKS, soundType.getVolume(), soundType.getPitch());
            player.swing(hand);

            stack.consume(1, player);
        }
    }
}

package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.common.init.EVItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BiomeCrystalEvent {
    public static InteractionResult rightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        Level level = player.level();
        if (level.isClientSide) return InteractionResult.PASS;

        BlockState blockState = level.getBlockState(blockPos);
        ItemStack itemStack = player.getItemInHand(interactionHand);

        if(itemStack.is(EVItems.HEATED_BIOME_CRYSTAL_ITEM.get()) && blockState.is(Blocks.WATER_CAULDRON)) {
            LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
            itemStack.consume(1, player);
            if (player.getInventory().getFreeSlot() == -1) {
                player.drop(EVItems.BIOME_CRYSTAL_ITEM.get().getDefaultInstance(), false);
            } else {
                player.addItem(EVItems.BIOME_CRYSTAL_ITEM.get().getDefaultInstance());
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}

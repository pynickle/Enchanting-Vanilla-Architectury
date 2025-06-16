package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;

public class DoubleDoorEvent {
    public static InteractionResult rightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        Level level = player.level();
        if(level.isClientSide || !QolConfig.HANDLER.instance().enableDoubleDoor) return InteractionResult.PASS;

        if(interactionHand != InteractionHand.MAIN_HAND || player.isCrouching()) return InteractionResult.PASS;

        BlockState blockState = level.getBlockState(blockPos);
        if(blockState.getBlock() instanceof DoorBlock block) {
            if(block.type().canOpenByHand()) {
                boolean isOpen = blockState.getValue(DoorBlock.OPEN);
                DoorHingeSide side = blockState.getValue(DoorBlock.HINGE);
                Direction doorDirection = blockState.getValue(DoorBlock.FACING);

                BlockPos relativeBlockPos;
                if(side == DoorHingeSide.LEFT) {
                    relativeBlockPos = blockPos.relative(doorDirection.getClockWise());
                } else {
                    relativeBlockPos = blockPos.relative(doorDirection.getCounterClockWise());
                }

                BlockState relativeBlockState = level.getBlockState(relativeBlockPos);
                if(relativeBlockState.getBlock() instanceof DoorBlock) {
                    if(relativeBlockState.getValue(DoorBlock.OPEN) == isOpen) {
                        level.setBlock(relativeBlockPos, relativeBlockState.cycle(DoorBlock.OPEN), 10);
                    }
                }
                level.setBlock(blockPos, blockState.cycle(DoorBlock.OPEN), 10);
                level.playSound(player, blockPos, blockState.getValue(DoorBlock.OPEN) ? block.type().doorOpen() : block.type().doorClose(),
                        SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}

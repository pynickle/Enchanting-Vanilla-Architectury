package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import dev.architectury.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;

public class StickyPistonEvent {
    public static EventResult rightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        Level level = player.level();
        if(level.isClientSide || !QolConfig.HANDLER.instance().enableStickyPiston) return EventResult.pass();

        BlockState blockState = level.getBlockState(blockPos);
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if(itemStack.is(Items.SLIME_BALL)) {
            if((blockState.is(Blocks.PISTON))) {
                boolean extended = blockState.getValue(PistonBaseBlock.EXTENDED);
                Direction facing = blockState.getValue(PistonBaseBlock.FACING);
                if(!extended && facing == direction) {
                    BlockState newState = Blocks.STICKY_PISTON.defaultBlockState()
                            .setValue(PistonBaseBlock.FACING, facing)
                            .setValue(PistonBaseBlock.EXTENDED, extended);
                    level.setBlockAndUpdate(blockPos, newState);
                    itemStack.consume(1, player);
                    player.swing(interactionHand, true);
                    return EventResult.interruptTrue();
                }
            }
            if(blockState.is(Blocks.PISTON_HEAD)) {
                PistonType pistonType = blockState.getValue(PistonHeadBlock.TYPE);
                Direction facing = blockState.getValue(PistonHeadBlock.FACING);
                if(pistonType == PistonType.DEFAULT && facing == direction) {
                    BlockState newState = blockState
                            .setValue(PistonHeadBlock.TYPE, PistonType.STICKY);
                    level.setBlockAndUpdate(blockPos, newState);
                    itemStack.consume(1, player);
                    player.swing(interactionHand, true);
                    return EventResult.interruptTrue();
                }
            }
        }

        return EventResult.pass();
    }
}

package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import dev.architectury.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class StopGrowingEvent {
    public static EventResult rightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        if(!QolConfig.HANDLER.instance().enableStopGrowing) return EventResult.pass();

        Level level = player.level();
        if (level.isClientSide) return EventResult.pass();

        BlockState state = level.getBlockState(blockPos);
        ItemStack stack = player.getItemInHand(interactionHand);

        if(stack.is(Items.SHEARS)) {
            if(state.is(Blocks.SUGAR_CANE)) {
                BlockPos testPos = blockPos.above();
                BlockState testState = level.getBlockState(testPos);
                while(testState.is(Blocks.SUGAR_CANE)) {
                    testPos = testPos.above();
                    testState = level.getBlockState(testPos);
                }
                if(!level.getBlockState(testPos).is(EVBlocks.CUT_SUGAR_CANE.get())) {
                    level.setBlockAndUpdate(testPos.below(), EVBlocks.CUT_SUGAR_CANE.get().defaultBlockState());
                    stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
                    player.swing(interactionHand, true);

                    return EventResult.interruptTrue();
                }
            } else if(state.is(Blocks.BAMBOO_SAPLING)) {
                level.setBlockAndUpdate(blockPos, EVBlocks.CUT_BAMBOO_SAPLING.get().defaultBlockState());
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
                player.swing(interactionHand, true);

                return EventResult.interruptTrue();
            }
        } else if(stack.is(ItemTags.AXES) ) {
            if(state.is(Blocks.BAMBOO)) {
                BlockPos testPos = blockPos.above();
                BlockState testState = level.getBlockState(testPos);
                while(testState.is(Blocks.BAMBOO)) {
                    testPos = testPos.above();
                    testState = level.getBlockState(testPos);
                }

                if(level.getBlockState(testPos.below()).getValue(BlockStateProperties.STAGE) == 0) {
                    BlockState newState = level.getBlockState(testPos.below()).setValue(BlockStateProperties.STAGE, 1);
                    level.setBlockAndUpdate(testPos.below(), Blocks.BAMBOO.withPropertiesOf(newState));
                    stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
                    player.swing(interactionHand, true);

                    return EventResult.interruptTrue();
                }
            }
        }
        return EventResult.pass();
    }
}

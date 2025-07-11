package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RightClickHarvestEvent {
    public static InteractionResult rightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        Level level = player.level();

        if(level.isClientSide() || !QolConfig.HANDLER.instance().enableRightClickHarvest) return InteractionResult.PASS;

        if (player.isSpectator() || player.isCrouching() || interactionHand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

        if (QolConfig.HANDLER.instance().hungerCost > 0 && !player.getAbilities().instabuild && player.getFoodData().getFoodLevel() <= 0) {
            return InteractionResult.PASS;
        }

        ItemStack stack = player.getItemInHand(interactionHand);

        if (QolConfig.HANDLER.instance().requiredHoe && !stack.is(ItemTags.HOES)) return InteractionResult.PASS;

        BlockState state = level.getBlockState(blockPos);
        Block block = state.getBlock();

        if (block instanceof CocoaBlock || block instanceof CropBlock || block instanceof NetherWartBlock) {
            if (isMature(state)) {
                if (!level.isClientSide) {
                    player.swing(interactionHand);
                    dropStacks(state, (ServerLevel) level, blockPos, player, stack);
                    level.setBlockAndUpdate(blockPos, getReplantState(state));

                    player.causeFoodExhaustion((float) QolConfig.HANDLER.instance().hungerCost * 0.005f);
                    if(QolConfig.HANDLER.instance().enableHarvestXp) {
                        player.giveExperiencePoints(QolConfig.HANDLER.instance().xpAmount);
                    }
                } else {
                    player.playSound(state.getBlock() instanceof NetherWartBlock ? SoundEvents.NETHER_WART_PLANTED : SoundEvents.CROP_PLANTED, 1.0f, 1.0f);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    public static boolean isMature(BlockState state) {
        if (state.getBlock() instanceof CocoaBlock) {
            return state.getValue(CocoaBlock.AGE) >= CocoaBlock.MAX_AGE;
        } else if (state.getBlock() instanceof CropBlock cropBlock) {
            return cropBlock.isMaxAge(state);
        } else if (state.getBlock() instanceof NetherWartBlock) {
            return state.getValue(NetherWartBlock.AGE) >= NetherWartBlock.MAX_AGE;
        }

        return false;
    }

    private static BlockState getReplantState(BlockState state) {
        if (state.getBlock() instanceof CocoaBlock) {
            return state.setValue(CocoaBlock.AGE, 0);
        } else if (state.getBlock() instanceof CropBlock cropBlock) {
            return state.setValue(cropBlock.getAgeProperty(), 0);
        } else if (state.getBlock() instanceof NetherWartBlock) {
            return state.setValue(NetherWartBlock.AGE, 0);
        }

        return state;
    }

    private static void dropStacks(BlockState state, ServerLevel level, BlockPos pos, Entity entity, ItemStack itemStack) {
        Item replant = state.getBlock().asItem();

        Block.getDrops(state, level, pos, null, entity, itemStack).forEach(stack -> {
            if (stack.getItem() == replant) {
                stack.setCount(stack.getCount() - 1);
            }

            Block.popResource(level, pos, stack);
        });

        state.spawnAfterBreak(level, pos, itemStack, true);
    }
}

package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import dev.architectury.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Map;

public class CutVineEvent {
    public static EventResult rightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        if(!QolConfig.HANDLER.instance().enableCutVine) return EventResult.pass();

        Level level = player.level();
        if (level.isClientSide) return EventResult.pass();

        BlockState state = level.getBlockState(blockPos);

        ItemStack item = player.getItemInHand(interactionHand);
        if(item.is(Items.SHEARS) && state.is(Blocks.VINE)) {
            BlockState newState = EVBlocks.CUT_VINE.get().withPropertiesOf(state);
            Map<Direction, BooleanProperty> map = VineBlock.PROPERTY_BY_DIRECTION;
            for(Direction d : map.keySet()) {
                BooleanProperty prop = map.get(d);
                newState = newState.setValue(prop, state.getValue(prop));
            }

            level.setBlockAndUpdate(blockPos, newState);

            BlockPos testPos = blockPos.below();
            BlockState testState = level.getBlockState(testPos);
            while(testState.is(Blocks.VINE) || testState.is(EVBlocks.CUT_VINE.get())) {
                level.removeBlock(testPos, false);
                testPos = testPos.below();
                testState = level.getBlockState(testPos);
            }

            level.playSound(player, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
            item.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
            player.swing(interactionHand, true);

            return EventResult.interruptTrue();
        }
        return EventResult.pass();
    }
}

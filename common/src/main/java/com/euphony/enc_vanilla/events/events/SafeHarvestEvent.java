package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.euphony.enc_vanilla.keymapping.EVKeyConfig;
import dev.architectury.event.EventResult;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

import static com.euphony.enc_vanilla.events.events.RightClickHarvestEvent.isMature;

public class SafeHarvestEvent {
    public static InteractionResult leftClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        Level level = player.level();
        if(level.isClientSide || !QolConfig.HANDLER.instance().enableSafeHarvest || !EVKeyConfig.SAFE_HARVEST) return InteractionResult.PASS;

        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof CocoaBlock || block instanceof CropBlock || block instanceof NetherWartBlock) {
            if (!isMature(blockState)) {
                player.displayClientMessage(getNotMatureMessage(), true);
                return InteractionResult.SUCCESS;
            }
            if(QolConfig.HANDLER.instance().enableHarvestXp) {
                player.giveExperiencePoints(QolConfig.HANDLER.instance().xpAmount);
            }
        }
        return InteractionResult.PASS;
    }

    private static Component getNotMatureMessage() {
        MutableComponent message = Component.translatable("message.enc_vanilla.safe_harvest.mod_name").withStyle(ChatFormatting.GOLD);
        message.append(Component.translatable("message.enc_vanilla.safe_harvest.not_mature"));
        return message;
    }
}

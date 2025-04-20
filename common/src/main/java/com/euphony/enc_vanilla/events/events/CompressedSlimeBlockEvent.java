package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.events.custom.AnvilFallOnLandCallback;
import dev.architectury.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CompressedSlimeBlockEvent implements AnvilFallOnLandCallback {
    public EventResult anvilFallOnLand(Level level, BlockPos pos, FallingBlockEntity entity, float fallDistance) {
        if(level.isClientSide()) return EventResult.pass();

        final BlockPos hitBlockPos = pos.below();
        final BlockState hitBlockState = level.getBlockState(hitBlockPos);

        BlockPos belowPos = hitBlockPos.below();
        BlockState hitBelowState = level.getBlockState(belowPos);

        if(hitBlockState.is(Blocks.SLIME_BLOCK) && hitBelowState.is(Blocks.SLIME_BLOCK)) {
            level.setBlockAndUpdate(hitBlockPos, Blocks.AIR.defaultBlockState());
            level.setBlockAndUpdate(belowPos, EVBlocks.COMPRESSED_SLIME_BLOCK.get().defaultBlockState());
        }
        return EventResult.pass();
    }
}

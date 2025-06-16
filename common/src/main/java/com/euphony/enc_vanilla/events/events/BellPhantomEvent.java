package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.api.IPhantom;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.euphony.enc_vanilla.mixin.invoker.BellBlockInvoker;
import com.euphony.enc_vanilla.utils.HitUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class BellPhantomEvent {
    public static InteractionResult rightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        Level level = player.level();
        if(level.isClientSide || !QolConfig.HANDLER.instance().enableBellPhantom) return InteractionResult.PASS;

        BlockState state = level.getBlockState(blockPos);
        if(state.is(Blocks.BELL)) {
            BellBlock bell = (BellBlock) state.getBlock();
            if(!((BellBlockInvoker) bell).invokeIsProperHit(state, direction,
                    HitUtils.getPlayerBlockHitResult(level, player).getLocation().y - blockPos.getY()))
                return InteractionResult.PASS;

            int particleTicks = (int) (QolConfig.HANDLER.instance().particleDuration * 20);
            List<Phantom> phantoms = level.getEntitiesOfClass(Phantom.class, new AABB(blockPos).inflate(24));
            phantoms.forEach(phantom -> {
                phantom.animateHurt(particleTicks);
                ((IPhantom) phantom).enc_vanilla$setBellTime(particleTicks * 2);
            });
        }
        return InteractionResult.PASS;
    }
}

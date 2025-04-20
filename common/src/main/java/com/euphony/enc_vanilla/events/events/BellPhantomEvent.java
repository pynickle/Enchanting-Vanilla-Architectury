package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class BellPhantomEvent {
    public static void rightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        Level level = player.level();
        if(level.isClientSide || !QolConfig.HANDLER.instance().enableBellPhantom) return;

        if(level.getBlockState(blockPos).is(Blocks.BELL)) {
            int particleTicks = (int) (QolConfig.HANDLER.instance().particleDuration * 20);
            List<Phantom> phantoms = level.getEntitiesOfClass(Phantom.class, new AABB(blockPos).inflate(24));
            phantoms.forEach(phantom -> {
                phantom.animateHurt(particleTicks);
                // phantom.setData(EVAttachmentTypes.BELL_TIME, particleTicks);
            });
        }
    }
}

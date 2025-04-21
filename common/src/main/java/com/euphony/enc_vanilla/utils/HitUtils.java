package com.euphony.enc_vanilla.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class HitUtils {
    public static BlockHitResult getPlayerBlockHitResult(Level level, Player player) {
        Vec3 vec3 = player.getEyePosition();
        Vec3 vec32 = vec3.add(player.calculateViewVector(player.getXRot(), player.getYRot()).scale(player.blockInteractionRange()));
        return level.clip(new ClipContext(vec3, vec32, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
    }

    public static BlockHitResult getPlayerFluidHitResult(Level level, Player player, ClipContext.Fluid fluid) {
        Vec3 vec3 = player.getEyePosition();
        Vec3 vec32 = vec3.add(player.calculateViewVector(player.getXRot(), player.getYRot()).scale(player.blockInteractionRange()));
        return level.clip(new ClipContext(vec3, vec32, net.minecraft.world.level.ClipContext.Block.OUTLINE, fluid, player));
    }
}

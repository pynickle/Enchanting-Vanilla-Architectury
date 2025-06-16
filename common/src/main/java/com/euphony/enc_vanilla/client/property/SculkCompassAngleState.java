package com.euphony.enc_vanilla.client.property;


import com.euphony.enc_vanilla.common.item.SculkCompassItem;
import com.euphony.enc_vanilla.utils.CompassState;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.NeedleDirectionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class SculkCompassAngleState extends NeedleDirectionHelper {
    public static final MapCodec<SculkCompassAngleState> MAP_CODEC =
            RecordCodecBuilder.mapCodec((instance) ->
                    instance.group(
                            Codec.BOOL.optionalFieldOf("wobble", true)
                                    .forGetter(NeedleDirectionHelper::wobble))
                            .apply(instance, SculkCompassAngleState::new));
    private final NeedleDirectionHelper.Wobbler wobbler = this.newWobbler(0.8F);
    private final NeedleDirectionHelper.Wobbler noTargetWobbler = this.newWobbler(0.8F);
    private final RandomSource random = RandomSource.create();

    public SculkCompassAngleState(boolean bl) {
        super(bl);
    }

    @Override
    protected float calculate(ItemStack itemStack, ClientLevel clientLevel, int i, Entity entity) {
        SculkCompassItem compassTarget = (SculkCompassItem) itemStack.getItem();
        BlockPos blockPos = new BlockPos(compassTarget.getFoundBiomeX(itemStack), 0, compassTarget.getFoundBiomeZ(itemStack));
        long gameTime = clientLevel.getGameTime();
        return compassTarget.getState(itemStack) == CompassState.FOUND ? this.getRotationTowardsCompassTarget(entity, gameTime, blockPos) : this.getRandomlySpinningRotation(i, gameTime);
    }

    private float getRandomlySpinningRotation(int i, long l) {
        if (this.noTargetWobbler.shouldUpdate(l)) {
            this.noTargetWobbler.update(l, this.random.nextFloat());
        }

        float f = this.noTargetWobbler.rotation() + (float)hash(i) / (float)Integer.MAX_VALUE;
        return Mth.positiveModulo(f, 1.0F);
    }

    private float getRotationTowardsCompassTarget(Entity entity, long l, BlockPos blockPos) {
        float f = (float)getAngleFromEntityToPos(entity, blockPos);
        float g = getWrappedVisualRotationY(entity);
        float h;
        if (entity instanceof Player player) {
            if (player.isLocalPlayer() && player.level().tickRateManager().runsNormally()) {
                if (this.wobbler.shouldUpdate(l)) {
                    this.wobbler.update(l, 0.5F - (g - 0.25F));
                }

                h = f + this.wobbler.rotation();
                return Mth.positiveModulo(h, 1.0F);
            }
        }

        h = 0.5F - (g - 0.25F - f);
        return Mth.positiveModulo(h, 1.0F);
    }

    private static double getAngleFromEntityToPos(Entity entity, BlockPos blockPos) {
        Vec3 vec3 = Vec3.atCenterOf(blockPos);
        return Math.atan2(vec3.z() - entity.getZ(), vec3.x() - entity.getX()) / (double)((float)Math.PI * 2F);
    }

    private static float getWrappedVisualRotationY(Entity entity) {
        return Mth.positiveModulo(entity.getVisualRotationYInDegrees() / 360.0F, 1.0F);
    }

    private static int hash(int i) {
        return i * 1327217883;
    }
}


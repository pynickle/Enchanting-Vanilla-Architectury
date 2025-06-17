package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.common.item.SculkCompassItem;
import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import com.euphony.enc_vanilla.utils.CompassState;
import com.euphony.enc_vanilla.utils.Utils;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ItemPropertiesEvent {
    public static void clientSetup(Minecraft minecraft) {
        ItemPropertiesRegistry.register(Items.AXOLOTL_BUCKET, Utils.prefix("variant"), (stack, level, entity, seed) -> {
            if(!ClientConfig.HANDLER.instance().enableAxolotlBucketFix) return 0;

            int axolotlType = 0;
            CustomData customData;
            DataComponentMap components = stack.getComponents();
            if (components.has(DataComponents.BUCKET_ENTITY_DATA)) {
                customData = components.get(DataComponents.BUCKET_ENTITY_DATA);
                if (customData != null) {
                    axolotlType = customData.copyTag().getInt("Variant");
                }
            }
            return axolotlType * 0.01f + 0.0001f;
        });
        ItemPropertiesRegistry.register(EVItems.SCULK_COMPASS_ITEM.get(), Utils.prefix("angle"), new ClampedItemPropertyFunction() {
            private final CompassWobble wobble = new CompassWobble();
            private final CompassWobble wobbleRandom = new CompassWobble();

            public float unclampedCall(@NotNull ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity p_entity, int seed) {
                Entity entity = p_entity != null ? p_entity : stack.getEntityRepresentation();
                if (entity == null) {
                    return 0.0F;
                } else {
                    level = this.tryFetchLevelIfMissing(entity, level);
                    return level == null ? 0.0F : this.getCompassRotation(stack, level, seed, entity);
                }
            }

            private float getCompassRotation(ItemStack stack, ClientLevel level, int seed, Entity entity) {
                SculkCompassItem compassTarget = (SculkCompassItem) stack.getItem();
                BlockPos blockPos = new BlockPos(compassTarget.getFoundBiomeX(stack), 0, compassTarget.getFoundBiomeZ(stack));
                long i = level.getGameTime();
                return compassTarget.getState(stack) == CompassState.FOUND ? this.getRotationTowardsCompassTarget(entity, i, blockPos) : this.getRandomlySpinningRotation(seed, i);
            }

            private float getRandomlySpinningRotation(int seed, long ticks) {
                if (this.wobbleRandom.shouldUpdate(ticks)) {
                    this.wobbleRandom.update(ticks, Math.random());
                }

                double d0 = this.wobbleRandom.rotation + (double)((float)this.hash(seed) / (float)Integer.MAX_VALUE);
                return Mth.positiveModulo((float)d0, 1.0F);
            }

            private float getRotationTowardsCompassTarget(Entity entity, long ticks, BlockPos pos) {
                double d0 = this.getAngleFromEntityToPos(entity, pos);
                double d1 = this.getWrappedVisualRotationY(entity);
                if (entity instanceof Player player) {
                    if (player.isLocalPlayer() && player.level().tickRateManager().runsNormally()) {
                        if (this.wobble.shouldUpdate(ticks)) {
                            this.wobble.update(ticks, (double)0.5F - (d1 - (double)0.25F));
                        }

                        double d3 = d0 + this.wobble.rotation;
                        return Mth.positiveModulo((float)d3, 1.0F);
                    }
                }

                double d2 = (double)0.5F - (d1 - (double)0.25F - d0);
                return Mth.positiveModulo((float)d2, 1.0F);
            }

            @Nullable
            private ClientLevel tryFetchLevelIfMissing(Entity entity, @Nullable ClientLevel level) {
                return level == null && entity.level() instanceof ClientLevel ? (ClientLevel)entity.level() : level;
            }

            private double getAngleFromEntityToPos(Entity entity, BlockPos pos) {
                Vec3 vec3 = Vec3.atCenterOf(pos);
                return Math.atan2(vec3.z() - entity.getZ(), vec3.x() - entity.getX()) / (double)((float)Math.PI * 2F);
            }

            private double getWrappedVisualRotationY(Entity entity) {
                return Mth.positiveModulo(entity.getVisualRotationYInDegrees() / 360.0F, (double)1.0F);
            }

            private int hash(int value) {
                return value * 1327217883;
            }

            @Environment(value = EnvType.CLIENT)
            static class CompassWobble {
                double rotation;
                private double deltaRotation;
                private long lastUpdateTick;

                CompassWobble() {
                }

                boolean shouldUpdate(long ticks) {
                    return this.lastUpdateTick != ticks;
                }

                void update(long ticks, double rotation) {
                    this.lastUpdateTick = ticks;
                    double d0 = rotation - this.rotation;
                    d0 = Mth.positiveModulo(d0 + (double)0.5F, 1.0F) - (double)0.5F;
                    this.deltaRotation += d0 * 0.1;
                    this.deltaRotation *= 0.8;
                    this.rotation = Mth.positiveModulo(this.rotation + this.deltaRotation, 1.0F);
                }
            }
        });
    }
}

package com.euphony.enc_vanilla.common.item;

import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.common.init.EVTags;
import com.euphony.enc_vanilla.config.categories.tools.ToolsConfig;
import com.euphony.enc_vanilla.utils.CompassState;
import com.google.common.base.Stopwatch;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class SculkCompassItem extends Item {
    public static final int USE_DURATION = 40;

    public SculkCompassItem(Properties properties) {
        super(properties.stacksTo(1).component(EVDataComponentTypes.COMPASS_STATE.get(), CompassState.INACTIVE.getID()));
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if(!ToolsConfig.HANDLER.instance().enableSculkCompass)
            return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));

        if(!level.isClientSide()) {
            if(hand == InteractionHand.MAIN_HAND && player.getMainHandItem().is(EVItems.SCULK_COMPASS_ITEM.get())) {
                CompassState state = getState(player.getItemInHand(hand));
                if (state == CompassState.INACTIVE) {
                    ItemStack offhandItem = player.getOffhandItem();
                    if(offhandItem.is(EVTags.BIOME_CRYSTAL) && offhandItem.get(EVDataComponentTypes.BIOME.get()) != null) {
                        player.startUsingItem(hand);
                    }
                }
            }
        }

        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && !level.isClientSide()) {
            if(Math.random() < 0.01f) {
                level.explode(player, Explosion.getDefaultDamageSource(level, player), null,
                        player.getX(), player.getY(), player.getZ(), 3.0F, false,
                        Level.ExplosionInteraction.TRIGGER, ParticleTypes.SONIC_BOOM, ParticleTypes.SONIC_BOOM,
                        Holder.direct(SoundEvent.createVariableRangeEvent(SoundEvents.WARDEN_SONIC_BOOM.getLocation())));
                player.hurt(player.damageSources().sonicBoom(player), 10.0F);
                player.getOffhandItem().consume(1, player);
                stack = new ItemStack(EVItems.DAMAGED_SCULK_COMPASS_ITEM.get());
            } else {
                ResourceKey<Biome> biomeResourceKey = player.getOffhandItem().get(EVDataComponentTypes.BIOME.get());
                locateBiome((ServerLevel) level, player.getEyePosition(), biome -> biome.is(biomeResourceKey), stack);
            }
        }
        return stack;
    }

    private static void locateBiome(ServerLevel level, Vec3 pos, Predicate<Holder<Biome>> biome, ItemStack stack) {
        BlockPos blockpos = BlockPos.containing(pos);
        Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
        Pair<BlockPos, Holder<Biome>> pair = level.findClosestBiome3d(biome, blockpos, 6400, 32, 64);
        stopwatch.stop();
        if (pair == null) {
            setState(stack, CompassState.NOT_FOUND);
        } else {
            setFoundBiomeX(stack, pair.getFirst().getX());
            setFoundBiomeZ(stack, pair.getFirst().getZ());
            setState(stack, CompassState.FOUND);
        }
    }

    public static void setState(ItemStack stack, CompassState state) {
        if (stack.is(EVItems.SCULK_COMPASS_ITEM.get())) {
            stack.set(EVDataComponentTypes.COMPASS_STATE.get(), state.getID());
        }
    }

    public static void setFoundBiomeX(ItemStack stack, int x) {
        stack.set(EVDataComponentTypes.FOUND_X.get(), x);
    }

    public static void setFoundBiomeZ(ItemStack stack, int z) {
        stack.set(EVDataComponentTypes.FOUND_Z.get(), z);
    }

    public CompassState getState(ItemStack stack) {
        return CompassState.fromID(stack.get(EVDataComponentTypes.COMPASS_STATE.get()));
    }


    public int getFoundBiomeX(ItemStack stack) {
        if(stack.is(EVItems.SCULK_COMPASS_ITEM.get()) && stack.has(EVDataComponentTypes.FOUND_X.get())) {
            return stack.get(EVDataComponentTypes.FOUND_X.get());
        }
        return 0;
    }

    public int getFoundBiomeZ(ItemStack stack) {
        if(stack.is(EVItems.SCULK_COMPASS_ITEM.get()) && stack.has(EVDataComponentTypes.FOUND_X.get())) {
            return stack.get(EVDataComponentTypes.FOUND_Z.get());
        }
        return 0;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}

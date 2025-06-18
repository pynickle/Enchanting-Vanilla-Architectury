package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.euphony.enc_vanilla.utils.ItemUtils;
import dev.architectury.event.EventResult;
import dev.architectury.platform.Platform;
// import it.crystalnest.soul_fire_d.api.FireManager;
// import it.crystalnest.soul_fire_d.api.type.FireTyped;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.StandingAndWallBlockItem;
import org.jetbrains.annotations.Nullable;

public final class TorchHitEvent {
    public static EventResult livingHurt(LivingEntity livingEntity, DamageSource damageSource, float v) {
        if(!QolConfig.HANDLER.instance().enableTorchHit) return EventResult.pass();

        Entity entity = damageSource.getEntity();

        if (entity == null || entity.level().isClientSide) return EventResult.pass();
        Entity directEntity = damageSource.getDirectEntity();

        if (entity instanceof LivingEntity attacker && entity.equals(directEntity) && !entity.isSpectator() && canAttack(attacker, livingEntity)) {
            InteractionHand interactionHand = getInteractionHand(attacker);
            if (interactionHand != null && !livingEntity.fireImmune()) {
                ItemStack item = attacker.getItemInHand(interactionHand);
                if (interactionHand == InteractionHand.MAIN_HAND) {
                    attack(livingEntity, item);
                }
            }
        }
        return EventResult.pass();
    }

    private static void attack(Entity target, ItemStack item) {
        double seconds = getFireSeconds(item, target, QolConfig.HANDLER.instance().torchHitFireDuration);
        if (seconds > 0) {
            target.igniteForSeconds((float) seconds);
            /*
            if (Platform.isModLoaded("soul_fire_d")) {
                setOnFire(item, target, seconds);
            } else {
                target.igniteForSeconds((float) seconds);
            }

             */
        }
    }

    private static double getFireSeconds(ItemStack item, Entity target, double seconds) {
        if (Math.random() * 100 < QolConfig.HANDLER.instance().torchHitFireChance) {
            return seconds;
        }
        return 0;
    }

    @Nullable
    private static InteractionHand getInteractionHand(LivingEntity attacker) {
        if (isTorch(attacker.getMainHandItem())) {
            return InteractionHand.MAIN_HAND;
        } else if (isTorch(attacker.getOffhandItem())) {
            return InteractionHand.OFF_HAND;
        }
        return null;
    }

    private static boolean isTorch(ItemStack item) {
        return item.is(Items.TORCH) || QolConfig.HANDLER.instance().extraTorchItems.contains(ItemUtils.getKey(item.getItem()).toString()) || isSoulTorch(item);
    }

    private static boolean isSoulTorch(ItemStack item) {
        return item.is(Items.SOUL_TORCH) || QolConfig.HANDLER.instance().extraSoulTorchItems.contains(ItemUtils.getKey(item.getItem()).toString());
    }

    private static boolean canAttack(LivingEntity attacker, LivingEntity target) {
        return (attacker instanceof Player || QolConfig.HANDLER.instance().enableMobTorchHit) && attacker.canAttack(target) && (!(attacker instanceof Player attackerPlayer && target instanceof Player targetPlayer) || attackerPlayer.canHarmPlayer(targetPlayer));
    }

    /*
    public static void setOnFire(ItemStack item, Entity entity, double seconds) {
        if (item.getItem() instanceof StandingAndWallBlockItem torch && torch.getBlock() instanceof FireTyped fireTypedTorch) {
            FireManager.setOnFire(entity, (float) seconds, fireTypedTorch.getFireType());
        } else if (isSoulTorch(item)) {
            FireManager.setOnFire(entity, (float) seconds, FireManager.SOUL_FIRE_TYPE);
        } else {
            FireManager.setOnFire(entity, (float) seconds, FireManager.DEFAULT_FIRE_TYPE);
        }
    }

     */
}

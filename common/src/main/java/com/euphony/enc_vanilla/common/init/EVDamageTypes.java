package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

public class EVDamageTypes {
    public static final ResourceKey<DamageType> STONECUTTER_DAMAGE = create("stonecutter");

    public static ResourceKey<DamageType> create(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(EncVanilla.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(STONECUTTER_DAMAGE, new DamageType("enc_vanilla.stonecutter", 0.0F));
    }
}

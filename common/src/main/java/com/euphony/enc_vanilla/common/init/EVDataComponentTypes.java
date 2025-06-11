package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class EVDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(EncVanilla.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<ResourceKey<Biome>>> BIOME = DATA_COMPONENTS.register("biome", () -> DataComponentType.<ResourceKey<Biome>>builder().persistent(ResourceKey.codec(Registries.BIOME)).networkSynchronized(ResourceKey.streamCodec(Registries.BIOME)).build());
    public static final RegistrySupplier<DataComponentType<Boolean>> LOCKED = DATA_COMPONENTS.register("locked", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final RegistrySupplier<DataComponentType<Boolean>> TEMP_UNLOCKED = DATA_COMPONENTS.register("temp_unlocked", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final RegistrySupplier<DataComponentType<Integer>> COMPASS_STATE = DATA_COMPONENTS.register("compass_state", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final RegistrySupplier<DataComponentType<Integer>> FOUND_X = DATA_COMPONENTS.register("found_x", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final RegistrySupplier<DataComponentType<Integer>> FOUND_Z = DATA_COMPONENTS.register("found_z", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
}

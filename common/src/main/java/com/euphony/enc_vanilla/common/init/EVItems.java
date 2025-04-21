package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.item.FrogBucketItem;
import com.euphony.enc_vanilla.common.item.SculkCompassItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class EVItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(EncVanilla.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<FrogBucketItem> FROG_BUCKET_ITEM = register("frog_bucket",
            () -> new FrogBucketItem(new Item.Properties()));

    public static final RegistrySupplier<Item> DAMAGED_SCULK_COMPASS_ITEM = register("damaged_sculk_compass",
            new Item.Properties().stacksTo(1).fireResistant());
    public static final RegistrySupplier<SculkCompassItem> SCULK_COMPASS_ITEM = register("sculk_compass",
            () -> new SculkCompassItem(new Item.Properties()));

    public static final RegistrySupplier<Item> BIOME_CRYSTAL_ITEM = register("biome_crystal",
            new Item.Properties().stacksTo(1).fireResistant());
    public static final RegistrySupplier<Item> HEATED_BIOME_CRYSTAL_ITEM = register("heated_biome_crystal",
            new Item.Properties().stacksTo(1).fireResistant());
    public static final RegistrySupplier<Item> FROZEN_BIOME_CRYSTAL_ITEM = register("frozen_biome_crystal",
            new Item.Properties().stacksTo(1).fireResistant());

    public static <T extends Item> RegistrySupplier<T> register(String name, Supplier<? extends T> item) {
        return ITEMS.register(name, item);
    }

    public static RegistrySupplier<Item> register(String name, Item.Properties properties) {
        return ITEMS.register(name, () -> new Item(properties));
    }

    public static void registerBlockItem(String name, RegistrySupplier<? extends Block> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}

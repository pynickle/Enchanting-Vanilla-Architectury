package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.item.BiomeCrystalItem;
import com.euphony.enc_vanilla.common.item.FrogBucketItem;
import com.euphony.enc_vanilla.common.item.SculkCompassItem;
import com.euphony.enc_vanilla.utils.Utils;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;
import java.util.function.Supplier;

public class EVItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(EncVanilla.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<FrogBucketItem> FROG_BUCKET_ITEM = register("frog_bucket",
            FrogBucketItem::new);

    public static final RegistrySupplier<Item> DAMAGED_SCULK_COMPASS_ITEM = register("damaged_sculk_compass",
            new Item.Properties().stacksTo(1).fireResistant());
    public static final RegistrySupplier<SculkCompassItem> SCULK_COMPASS_ITEM = register("sculk_compass",
            SculkCompassItem::new);

    public static final RegistrySupplier<BiomeCrystalItem> BIOME_CRYSTAL_ITEM = register("biome_crystal",
            BiomeCrystalItem::new
    );
    public static final RegistrySupplier<Item> HEATED_BIOME_CRYSTAL_ITEM = register("heated_biome_crystal",
            BiomeCrystalItem::new
    );
    public static final RegistrySupplier<Item> FROZEN_BIOME_CRYSTAL_ITEM = register("frozen_biome_crystal",
            BiomeCrystalItem::new
    );

    public static <T extends Item> RegistrySupplier<T> register(String name, Function<Item.Properties, T> func, Item.Properties properties) {
        return ITEMS.register(name, () -> func.apply(properties.setId(ResourceKey.create(Registries.ITEM, Utils.prefix(name)))));
    }

    public static <T extends Item> RegistrySupplier<T> register(String name, Function<Item.Properties, T> func) {
        return ITEMS.register(name, () -> func.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Utils.prefix(name)))));
    }

    public static <T extends Item> RegistrySupplier<T> register(String name, Supplier<? extends T> item) {
        return ITEMS.register(name, item);
    }

    public static RegistrySupplier<Item> register(String name, Item.Properties properties) {
        return ITEMS.register(name, () -> new Item(properties.setId(ResourceKey.create(Registries.ITEM, Utils.prefix(name)))));
    }

    public static void registerBlockItem(String name, RegistrySupplier<? extends Block> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Utils.prefix(name))).useBlockDescriptionPrefix()));
    }
}

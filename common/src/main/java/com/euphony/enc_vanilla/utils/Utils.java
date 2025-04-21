package com.euphony.enc_vanilla.utils;

import com.euphony.enc_vanilla.EncVanilla;
import dev.architectury.platform.Platform;
import java.util.Locale;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class Utils {
    public static String getModDisplayName(String modId) {
        return Platform.getMod(modId).getName();
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(EncVanilla.MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceKey<LootTable> getReplaceLootTable(String path) {
        return key(Registries.LOOT_TABLE, path);
    }

    public static ResourceKey<LootTable> getReplaceLootTable(ResourceKey<LootTable> name) {
        return key(Registries.LOOT_TABLE, name.location().getPath());
    }

    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String path) {
        return ResourceKey.create(registry, prefix(path));
    }
}

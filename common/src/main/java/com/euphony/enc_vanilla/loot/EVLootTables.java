package com.euphony.enc_vanilla.loot;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

public class EVLootTables {
    public static final List<ResourceKey<LootTable>> HEATED_BIOME_CRYSTAL_LOOT_TABLES = List.of(
            BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY,
            BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY,
            BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY
    );
}

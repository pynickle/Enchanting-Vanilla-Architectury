package com.euphony.enc_vanilla.fabric.loot;

import com.euphony.enc_vanilla.loot.Chance;
import com.euphony.enc_vanilla.loot.EVLootTables;
import com.euphony.enc_vanilla.loot.ReplaceWithLootTableFunction;
import com.euphony.enc_vanilla.utils.Utils;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

public class LootModifiers {
    public static void onLootTableLoad(ResourceKey<LootTable> key, LootTable.Builder builder, LootTableSource source) {
        if (source.isBuiltin()) {
            if(BuiltInLootTables.RUINED_PORTAL.equals(key)) {
                builder.modifyPools(pool -> pool.apply(ReplaceWithLootTableFunction
                        .replaceWithLootTable(Utils.getReplaceLootTable(key))));
            } else if(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE.equals(key)) {
                builder.modifyPools(pool -> pool.apply(ReplaceWithLootTableFunction
                        .replaceWithLootTable(Utils.getReplaceLootTable("archaeology/biome_crystal"))
                        .when(() -> new Chance(0.1F))));
            } else if(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY.equals(key)) {
                builder.modifyPools(pool -> pool.apply(ReplaceWithLootTableFunction
                        .replaceWithLootTable(Utils.getReplaceLootTable("archaeology/frozen_biome_crystal"))
                        .when(() -> new Chance(0.1F))));
            } else if(EVLootTables.HEATED_BIOME_CRYSTAL_LOOT_TABLES.contains(key)) {
                builder.modifyPools(pool -> pool.apply(ReplaceWithLootTableFunction
                        .replaceWithLootTable(Utils.getReplaceLootTable("archaeology/heated_biome_crystal"))
                        .when(() -> new Chance(0.1F))));
            } else if(BuiltInLootTables.ANCIENT_CITY.equals(key)) {
                builder.modifyPools(pool -> pool.apply(ReplaceWithLootTableFunction
                        .replaceWithLootTable(Utils.getReplaceLootTable("chests/sculk_compass"))
                        .when(() -> new Chance(0.05F))));;
            }

        }
    }
}

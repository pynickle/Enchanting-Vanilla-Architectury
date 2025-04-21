package com.euphony.enc_vanilla.neoforge.data.loots;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVItems;
import java.util.concurrent.CompletableFuture;

import com.euphony.enc_vanilla.utils.Utils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

public class GlobalLootModifierGenerator extends GlobalLootModifierProvider {
    public GlobalLootModifierGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, EncVanilla.MOD_ID);
    }

    @Override
    protected void start() {
        this.add("sculk_compass",
                new AddTableLootModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(BuiltInLootTables.ANCIENT_CITY.location()).build(),
                        LootItemRandomChanceCondition.randomChance(0.05f).build()
                }, Utils.getReplaceLootTable("chests/sculk_compass")));
        this.add("biome_crystal",
                new AddTableLootModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE.location()).build(),
                        LootItemRandomChanceCondition.randomChance(0.1f).build()
                }, Utils.getReplaceLootTable("archaeology/biome_crystal")));
        this.add("heated_biome_crystal",
                new AddTableLootModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY.location())
                                .or(new LootTableIdCondition.Builder(BuiltInLootTables.DESERT_PYRAMID.location()))
                                .or(new LootTableIdCondition.Builder(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY.location())).build(),
                        LootItemRandomChanceCondition.randomChance(0.1f).build()
                }, Utils.getReplaceLootTable("archaeology/heated_biome_crystal")));
        this.add("frozen_biome_crystal",
                new AddTableLootModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY.location()).build(),
                        LootItemRandomChanceCondition.randomChance(0.1f).build()
                }, Utils.getReplaceLootTable("archaeology/frozen_biome_crystal")));
        this.add("lodestone",
                new AddTableLootModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(BuiltInLootTables.RUINED_PORTAL.location()).build(),
                        LootItemRandomChanceCondition.randomChance(0.005f).build()
                }, Utils.getReplaceLootTable("archaeology/biome_crystal")));
    }
}
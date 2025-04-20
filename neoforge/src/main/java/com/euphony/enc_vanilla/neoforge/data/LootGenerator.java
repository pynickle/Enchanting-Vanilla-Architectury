package com.euphony.enc_vanilla.neoforge.data;

import com.euphony.enc_vanilla.neoforge.data.loots.BlockLootTables;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class LootGenerator extends LootTableProvider {
    public LootGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK)
        ), provider);
    }

    @Override
    protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {

    }
}

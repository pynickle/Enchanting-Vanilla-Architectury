package com.euphony.enc_vanilla.neoforge.datagen;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.neoforge.datagen.loots.GlobalLootModifierGenerator;
import com.euphony.enc_vanilla.neoforge.datagen.recipes.RecipeGenerator;
import com.euphony.enc_vanilla.neoforge.datagen.tag.BlockTagGenerator;
import com.euphony.enc_vanilla.neoforge.datagen.tag.DamageTypeGenerator;
import com.euphony.enc_vanilla.neoforge.datagen.tag.ItemTagGenerator;
import com.euphony.enc_vanilla.neoforge.datagen.tag.PaintingVariantTagGenerator;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = EncVanilla.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        DatapackBuiltinEntriesProvider datapackProvider = new RegistryDataGenerator(output, event.getLookupProvider());
        CompletableFuture<HolderLookup.Provider> lookupProvider = datapackProvider.getRegistryProvider();

        generator.addProvider(true, new ModelGenerator(output));
        generator.addProvider(true, new PaintingVariantTagGenerator(output, lookupProvider));

        generator.addProvider(true, datapackProvider);

        generator.addProvider(true, new BlockTagGenerator(output, lookupProvider));
        generator.addProvider(true, new ItemTagGenerator(output, lookupProvider));
        generator.addProvider(true, new DamageTypeGenerator(output, lookupProvider));

        generator.addProvider(true, new GlobalLootModifierGenerator(output, lookupProvider));
        generator.addProvider(true, new LootGenerator(output, lookupProvider));
        generator.addProvider(true, new RecipeGenerator.Runner(output, lookupProvider));

        generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Resources for Enchanting Vanilla"),
                DetectedVersion.BUILT_IN.packVersion(PackType.CLIENT_RESOURCES),
                Optional.empty())));
    }
}

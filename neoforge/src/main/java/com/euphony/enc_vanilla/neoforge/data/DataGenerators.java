package com.euphony.enc_vanilla.neoforge.data;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.neoforge.data.loots.GlobalLootModifierGenerator;
import com.euphony.enc_vanilla.neoforge.data.models.BlockModelGenerator;
import com.euphony.enc_vanilla.neoforge.data.models.ItemModelGenerator;
import com.euphony.enc_vanilla.neoforge.data.recipes.RecipeGenerator;
import com.euphony.enc_vanilla.neoforge.data.tag.BlockTagGenerator;
import com.euphony.enc_vanilla.neoforge.data.tag.ItemTagGenerator;
import com.euphony.enc_vanilla.neoforge.data.tag.PaintingVariantTagGenerator;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
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
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = EncVanilla.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        DatapackBuiltinEntriesProvider datapackProvider = new RegistryDataGenerator(output, event.getLookupProvider());
        CompletableFuture<HolderLookup.Provider> lookupProvider = datapackProvider.getRegistryProvider();

        generator.addProvider(true, new BlockModelGenerator(output, existingFileHelper));
        generator.addProvider(true, new ItemModelGenerator(output, existingFileHelper));
        generator.addProvider(true, new PaintingVariantTagGenerator(output, lookupProvider));
        generator.addProvider(true, new BlockStateGenerator(output, existingFileHelper));

        generator.addProvider(true, datapackProvider);

        BlockTagGenerator blockTagGenerator = new BlockTagGenerator(output, lookupProvider, existingFileHelper);
        generator.addProvider(true, blockTagGenerator);
        generator.addProvider(true, new ItemTagGenerator(output, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

        generator.addProvider(true, new GlobalLootModifierGenerator(output, lookupProvider));
        generator.addProvider(true, new LootGenerator(output, lookupProvider));
        generator.addProvider(true, new RecipeGenerator(output, lookupProvider));

        generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Resources for Enchanting Vanilla"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.empty())));
    }
}

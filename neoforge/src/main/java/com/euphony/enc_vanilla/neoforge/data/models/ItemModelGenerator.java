package com.euphony.enc_vanilla.neoforge.data.models;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.utils.Utils;
import java.util.Objects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {
    ExistingFileHelper existingFileHelper;

    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EncVanilla.MOD_ID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void registerModels() {
        basicItem(Utils.prefix("frog_bucket_active"));
        predicateItem(EVItems.FROG_BUCKET_ITEM.get(), 1, "frog_bucket_active");
        for(int i = 0; i < 16; i++) {
            basicItem(Utils.prefix("sculk_compass_" + String.format("%02d", i)));
        }
        for(int i = 17; i < 32; i++) {
            basicItem(Utils.prefix("sculk_compass_" + i));
        }
        basicItem(EVItems.DAMAGED_SCULK_COMPASS_ITEM.get());
        basicItem(EVItems.BIOME_CRYSTAL_ITEM.get());
        basicItem(EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get());
        basicItem(EVItems.HEATED_BIOME_CRYSTAL_ITEM.get());

        basicItem(Utils.prefix("axolotl_bucket_wild"));
        basicItem(Utils.prefix("axolotl_bucket_gold"));
        basicItem(Utils.prefix("axolotl_bucket_cyan"));
        basicItem(Utils.prefix("axolotl_bucket_blue"));

        basicBlockItem(EVBlocks.CUT_VINE.get().asItem());
        basicBlockItem(EVBlocks.CUT_SUGAR_CANE.get().asItem());

        simpleVanillaBlockItem(EVBlocks.COMPRESSED_SLIME_BLOCK.get(), "slime_block");

        simpleBlockItem(EVBlocks.CEILING_TORCH.get());
        simpleBlockItem(EVBlocks.CEILING_REDSTONE_TORCH.get());
        simpleBlockItem(EVBlocks.CEILING_SOUL_TORCH.get());
    }

    public ItemModelBuilder basicBlockItem(Item item) {
        return basicBlockItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }

    public ItemModelBuilder basicBlockItem(ResourceLocation item) {
        return getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "block/" + item.getPath()));
    }

    public ItemModelBuilder simpleVanillaBlockItem(Block block, String path) {
        return simpleVanillaBlockItem(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)), path);
    }

    public ItemModelBuilder simpleVanillaBlockItem(ResourceLocation block, String path) {
        return this.withExistingParent(block.toString(), ResourceLocation.withDefaultNamespace("block/" + path));
    }


    protected ItemModelBuilder.OverrideBuilder predicateItem(Item item, int customModelData, String overrideModel) {
        return predicateItem(BuiltInRegistries.ITEM.getKey(item), customModelData, overrideModel);
    }

    protected ItemModelBuilder.OverrideBuilder predicateItem(ResourceLocation item, int customModelData, String overrideModel) {
        return this.getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", Utils.prefix("item/" + item.getPath()))
                .override()
                .predicate(ResourceLocation.withDefaultNamespace("custom_model_data"), customModelData)
                .model(new ModelFile.ExistingModelFile(Utils.prefix("item/" + overrideModel), existingFileHelper));
    }
}

package com.euphony.enc_vanilla.neoforge.data.models;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.client.property.AxolotlBucketVariant;
import com.euphony.enc_vanilla.client.property.FrogBucketActive;
import com.euphony.enc_vanilla.client.property.SculkCompassAngle;
import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.utils.Utils;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.WaterFluid;

import java.util.Collections;
import java.util.List;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class ModelGenerator extends ModelProvider {
    public ModelGenerator(PackOutput output) {
        super(output, EncVanilla.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generateFrogBucket(itemModels, EVItems.FROG_BUCKET_ITEM.get());
        generateSculkCompass(itemModels, EVItems.SCULK_COMPASS_ITEM.get());

        itemModel(itemModels, EVItems.DAMAGED_SCULK_COMPASS_ITEM.get());
        itemModel(itemModels, EVItems.BIOME_CRYSTAL_ITEM.get());
        itemModel(itemModels, EVItems.HEATED_BIOME_CRYSTAL_ITEM.get());
        itemModel(itemModels, EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get());

        createBambooSapling(blockModels, EVBlocks.CUT_BAMBOO_SAPLING.get());

        generateAxolotlBucket(itemModels, Items.AXOLOTL_BUCKET);

        createVine(blockModels);
        createSugarCane(blockModels, EVBlocks.CUT_SUGAR_CANE.get());

        createCompressedSlimeBlock(blockModels, EVBlocks.COMPRESSED_SLIME_BLOCK.get());

        createNormalTorch(blockModels, EVBlocks.CEILING_TORCH.get(), Blocks.TORCH);
        createNormalTorch(blockModels, EVBlocks.CEILING_SOUL_TORCH.get(), Blocks.SOUL_TORCH);
        createRedstoneTorch(blockModels, EVBlocks.CEILING_REDSTONE_TORCH.get());

        createLilyPad(blockModels);
        createAppraisalTable(blockModels, EVBlocks.APPRAISAL_TABLE.get());
    }

    public void generateFrogBucket(ItemModelGenerators itemModels, Item item) {
        ItemModel.Unbaked itemmodel$unbaked = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked itemmodel$unbaked1 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_active", ModelTemplates.FLAT_ITEM));
        itemModels.itemModelOutput.accept(item, ItemModelUtils.select(new FrogBucketActive(false), itemmodel$unbaked, new SelectItemModel.SwitchCase<>(List.of(true), itemmodel$unbaked1)));
    }

    public void generateAxolotlBucket(ItemModelGenerators itemModels, Item item) {
        ItemModel.Unbaked itemmodel$unbaked = createAxolotlBucketModel("_wild", itemModels);
        ItemModel.Unbaked itemmodel$unbaked1 = createAxolotlBucketModel("_gold", itemModels);
        ItemModel.Unbaked itemmodel$unbaked2 = createAxolotlBucketModel("_cyan", itemModels);
        ItemModel.Unbaked itemmodel$unbaked3 = createAxolotlBucketModel("_blue", itemModels);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.select(new AxolotlBucketVariant(0), itemmodel$unbaked,
                new SelectItemModel.SwitchCase<>(List.of(0.0f), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(Items.AXOLOTL_BUCKET))),
                new SelectItemModel.SwitchCase<>(List.of(0.01f), itemmodel$unbaked),
                new SelectItemModel.SwitchCase<>(List.of(0.02f), itemmodel$unbaked1),
                new SelectItemModel.SwitchCase<>(List.of(0.03f), itemmodel$unbaked2),
                new SelectItemModel.SwitchCase<>(List.of(0.04f), itemmodel$unbaked3)));
    }

    public ItemModel.Unbaked createAxolotlBucketModel(String suffix, ItemModelGenerators itemModels) {
        ResourceLocation resourceLocation = Utils.prefix("item/axolotl_bucket" + suffix);
        return ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(resourceLocation, TextureMapping.layer0(resourceLocation), itemModels.modelOutput));
    }

    public void generateSculkCompass(ItemModelGenerators itemModels, Item item) {
        List<RangeSelectItemModel.Entry> list = itemModels.createCompassModels(item);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(new SculkCompassAngle(true), 32.0F, list));
    }

    public void createBambooSapling(BlockModelGenerators blockModels, Block block) {
        blockModels.createCrossBlock(block, BlockModelGenerators.PlantType.TINTED, TextureMapping.cross(TextureMapping.getBlockTexture(Blocks.BAMBOO, "_stage0")));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(Items.BAMBOO));
    }

    public void createCompressedSlimeBlock(BlockModelGenerators blockModels, Block block) {
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(Blocks.SLIME_BLOCK);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, plainVariant(resourcelocation)));
        blockModels.registerSimpleItemModel(block, resourcelocation);
    }

    public void createAppraisalTable(BlockModelGenerators blockModels, Block block) {
        ResourceLocation resourcelocation = TextureMapping.getBlockTexture(Blocks.SPRUCE_PLANKS);
        ResourceLocation resourcelocation1 = TextureMapping.getBlockTexture(block, "_top");
        ResourceLocation resourcelocation2 = TextureMapping.getBlockTexture(block, "_side");
        TextureMapping texturemapping = (new TextureMapping()).put(TextureSlot.BOTTOM, resourcelocation).put(TextureSlot.TOP, resourcelocation1).put(TextureSlot.SIDE, resourcelocation2);
        blockModels.blockStateOutput.accept(createSimpleBlock(block, plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(EVBlocks.APPRAISAL_TABLE.get(), texturemapping, blockModels.modelOutput))));

        ResourceLocation resourcelocation3 = ModelLocationUtils.getModelLocation(EVBlocks.APPRAISAL_TABLE.get());
        blockModels.registerSimpleItemModel(block, resourcelocation3);
    }

    public void createSugarCane(BlockModelGenerators blockModels, Block block) {
        blockModels.createCrossBlock(block, BlockModelGenerators.PlantType.TINTED);
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(Items.SUGAR_CANE));
    }

    public void createLilyPad(BlockModelGenerators blockModels) {
        ResourceLocation resourcelocation = blockModels.createFlatItemModelWithBlockTexture(EVBlocks.WATERLOGGED_LILY_PAD.get().asItem(), Blocks.LILY_PAD);
        blockModels.registerSimpleTintedItemModel(EVBlocks.WATERLOGGED_LILY_PAD.get(), resourcelocation, ItemModelUtils.constantTint(-9321636));
        Variant variant = plainModel(ModelLocationUtils.getModelLocation(EVBlocks.WATERLOGGED_LILY_PAD.get()));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(EVBlocks.WATERLOGGED_LILY_PAD.get(), createRotatedVariants(variant)));
    }

    public void createVine(BlockModelGenerators blockModels) {
        blockModels.createMultifaceBlockStates(EVBlocks.CUT_VINE.get());
        ResourceLocation resourcelocation = blockModels.createFlatItemModelWithBlockTexture(EVBlocks.CUT_VINE.get().asItem(), EVBlocks.CUT_VINE.get());
        blockModels.registerSimpleTintedItemModel(EVBlocks.CUT_VINE.get(), resourcelocation, ItemModelUtils.constantTint(-12012264));
    }

    public void createNormalTorch(BlockModelGenerators blockModels, Block block, Block originalBlock) {
        TextureMapping texturemapping = TextureMapping.torch(originalBlock);
        blockModels.blockStateOutput.accept(createSimpleBlock(block, variant(plainModel(ModelTemplates.TORCH.create(block, texturemapping, blockModels.modelOutput))).with(X_ROT_180)));
        blockModels.registerSimpleFlatItemModel(block);
    }

    public void createRedstoneTorch(BlockModelGenerators blockModels, Block block) {
        TextureMapping texturemapping = TextureMapping.torch(block);
        TextureMapping texturemapping1 = TextureMapping.torch(TextureMapping.getBlockTexture(block, "_off"));
        MultiVariant multivariant = plainVariant(ModelTemplates.REDSTONE_TORCH.create(block, texturemapping, blockModels.modelOutput)).with(X_ROT_180);
        MultiVariant multivariant1 = plainVariant(ModelTemplates.TORCH_UNLIT.createWithSuffix(block, "_off", texturemapping1, blockModels.modelOutput)).with(X_ROT_180);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(createBooleanModelDispatch(BlockStateProperties.LIT, multivariant, multivariant1)));
        blockModels.registerSimpleFlatItemModel(block);
    }

    public void blockModel(BlockModelGenerators blockModels, Block block) {
        blockModels.createTrivialCube(block);
    }

    public void toolModel(ItemModelGenerators itemModels, Item item) {
        this.itemModel(itemModels, item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    public void itemModel(ItemModelGenerators itemModels, Item item) {
        this.itemModel(itemModels, item, ModelTemplates.FLAT_ITEM);
    }

    public void itemModel(ItemModelGenerators itemModels, Item item, ModelTemplate template) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(itemId.getNamespace(), "item/" + itemId.getPath());
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, textureLoc);
        itemModels.itemModelOutput.accept(item, new BlockModelWrapper.Unbaked(template.create(item, textureMapping, itemModels.modelOutput), Collections.emptyList()));
    }

    public void itemModel(ItemModelGenerators itemModels, Item item, String loc) {
        this.itemModel(itemModels, item, ModelTemplates.FLAT_ITEM, loc);
    }

    public void itemModel(ItemModelGenerators itemModels, Item item, ModelTemplate template, String loc) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(itemId.getNamespace(), "item/" + loc);
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, textureLoc);
        itemModels.itemModelOutput.accept(item, new BlockModelWrapper.Unbaked(template.create(item, textureMapping, itemModels.modelOutput), Collections.emptyList()));
    }
}

package com.euphony.enc_vanilla.neoforge.data.recipes;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.utils.Utils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends RecipeProvider {
    private static HolderLookup.RegistryLookup<Item> registry = null;
    protected RecipeGenerator(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
        registry = registries.lookupOrThrow(Registries.ITEM);
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new RecipeGenerator(registries, output);
        }

        @Override
        public String getName() {
            return "Enchanting Vanilla's Recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        addSpongeCampfireRecipes();
        addSculkCompassRecipes();
        addBetterLodestoneRecipes();
    }

    protected void addSpongeCampfireRecipes() {
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.WET_SPONGE), RecipeCategory.MISC, Items.SPONGE, 0.15F, 600)
                .unlockedBy("has_item", has(Items.WET_SPONGE))
                .save(output, createKey("wet_sponge_to_sponge"));
    }

    protected void addBetterLodestoneRecipes() {
        ShapedRecipeBuilder.shaped(registry, RecipeCategory.DECORATIONS, Blocks.LODESTONE)
                .pattern("SSS")
                .pattern("S#S")
                .pattern("SSS")
                .define('S', Items.CHISELED_STONE_BRICKS)
                .define('#', Items.IRON_INGOT)
                .unlockedBy("has_item", has(Items.IRON_INGOT))
                .save(output, createKey("lodestone"));
    }

    protected void addSculkCompassRecipes() {
        ShapedRecipeBuilder.shaped(registry, RecipeCategory.MISC, EVItems.SCULK_COMPASS_ITEM.get(), 1)
                .define('S', EVItems.DAMAGED_SCULK_COMPASS_ITEM.get())
                .define('I', Items.ECHO_SHARD)
                .define('A', Items.AMETHYST_SHARD)
                .pattern("IAI")
                .pattern("ISI")
                .pattern("III")
                .unlockedBy("has_item", has(EVItems.DAMAGED_SCULK_COMPASS_ITEM.get()))
                .save(output, createKey("sculk_compass"));

        ShapedRecipeBuilder.shaped(registry, RecipeCategory.MISC, EVBlocks.APPRAISAL_TABLE.get(), 1)
                .define('S', Items.DIAMOND)
                .define('A', ItemTags.LOGS)
                .define('I', Items.ECHO_SHARD)
                .pattern("I I")
                .pattern("SAS")
                .pattern("AAA")
                .unlockedBy("has_item", has(Items.DIAMOND))
                .save(output, createKey("appraisal_table"));
    }

    protected void shapeless(RecipeCategory recipeCategory, ItemStack resultItem, RecipeOutput recipeOutput, List<Item> items, String key, Item... additionalItems) {
        ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(registry, recipeCategory, resultItem);
        for(Item item : additionalItems) {
            shapeless
                    .requires(item)
                    .unlockedBy("has_item", has(item));
        }
        for(Item item : items) {
            shapeless
                    .requires(item)
                    .unlockedBy("has_item", has(item));
        }
        shapeless.save(recipeOutput, createKey(key));
    }

    protected void shapeless(RecipeCategory recipeCategory, ItemStack resultItem, RecipeOutput recipeOutput, List<Item> items, String key) {
        ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(registry, recipeCategory, resultItem);
        for(Item item : items) {
            shapeless
                    .requires(item)
                    .unlockedBy("has_item", has(item));
        }
        shapeless.save(recipeOutput, createKey(key));
    }

    protected void shapeless(RecipeCategory recipeCategory, ItemLike resultItem, RecipeOutput recipeOutput, List<Item> items, String key) {
        shapeless(recipeCategory, resultItem, 1, recipeOutput, items, key);
    }

    protected void shapeless(RecipeCategory recipeCategory, ItemLike resultItem, int count, RecipeOutput recipeOutput, List<Item> items, String key) {
        ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(registry, recipeCategory, resultItem, count);
        for(Item item : items) {
            shapeless
                    .requires(item)
                    .unlockedBy("has_item", has(item));
        }
        shapeless.save(recipeOutput, createKey(key));
    }

    protected ResourceLocation createLocation(String name) {
        return Utils.prefix(name);
    }

    protected ResourceKey<Recipe<?>> createKey(String name) {
        return ResourceKey.create(Registries.RECIPE, createLocation(name));
    }
}
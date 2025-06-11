package com.euphony.enc_vanilla.neoforge.data.recipes;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.utils.Utils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        addSpongeCampfireRecipes(recipeOutput);
        addSculkCompassRecipes(recipeOutput);
        addBetterLodestoneRecipes(recipeOutput);
    }

    protected void addSpongeCampfireRecipes(RecipeOutput recipeOutput) {
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.WET_SPONGE), RecipeCategory.MISC, Items.SPONGE, 0.15F, 600)
                .unlockedBy("has_item", has(Items.WET_SPONGE))
                .save(recipeOutput, createKey("wet_sponge_to_sponge"));
    }

    protected void addBetterLodestoneRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.LODESTONE)
                .pattern("SSS")
                .pattern("S#S")
                .pattern("SSS")
                .define('S', Items.CHISELED_STONE_BRICKS)
                .define('#', Items.IRON_INGOT)
                .unlockedBy("has_item", has(Items.IRON_INGOT))
                .save(recipeOutput, createKey("lodestone"));
    }

    protected void addSculkCompassRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EVItems.SCULK_COMPASS_ITEM.get(), 1)
                .define('S', EVItems.DAMAGED_SCULK_COMPASS_ITEM.get())
                .define('I', Items.ECHO_SHARD)
                .define('A', Items.AMETHYST_SHARD)
                .pattern("IAI")
                .pattern("ISI")
                .pattern("III")
                .unlockedBy("has_item", has(EVItems.DAMAGED_SCULK_COMPASS_ITEM.get()))
                .save(recipeOutput, createKey("sculk_compass"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EVBlocks.APPRAISAL_TABLE.get(), 1)
                .define('S', Items.DIAMOND)
                .define('A', ItemTags.LOGS)
                .define('I', Items.ECHO_SHARD)
                .pattern("I I")
                .pattern("SAS")
                .pattern("AAA")
                .unlockedBy("has_item", has(Items.DIAMOND))
                .save(recipeOutput, createKey("appraisal_table"));
    }

    protected void shapeless(RecipeCategory recipeCategory, ItemStack resultItem, RecipeOutput recipeOutput, List<Item> items, String key, Item... additionalItems) {
        ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(recipeCategory, resultItem);
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
        ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(recipeCategory, resultItem);
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
        ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(recipeCategory, resultItem, count);
        for(Item item : items) {
            shapeless
                    .requires(item)
                    .unlockedBy("has_item", has(item));
        }
        shapeless.save(recipeOutput, createKey(key));
    }

    protected ResourceLocation createKey(String name) {
        return Utils.prefix(name);
    }
}
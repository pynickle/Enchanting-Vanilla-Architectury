package com.euphony.enc_vanilla.fabric.mixin;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.recipes.RecipesConfig;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.SlabBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {
    private static ArrayList<ResourceLocation> id = new ArrayList<>();

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "HEAD"))
    private void apply(Map<ResourceLocation, JsonElement> map, ResourceManager arg, ProfilerFiller arg2, CallbackInfo ci) {
        id.clear();
    }

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "NEW", target = "net/minecraft/world/item/crafting/RecipeHolder"))
    private void apply(CallbackInfo ci, @Local(ordinal = 0) ImmutableMultimap.Builder<RecipeType<?>, RecipeHolder<?>> builder1, @Local(ordinal = 0) ImmutableMap.Builder<ResourceLocation, RecipeHolder<?>> builder2, @Local(ordinal = 0) ResourceLocation resourceLocation, @Local(ordinal = 0) Recipe<?> recipe) {
        if (RecipesConfig.HANDLER.instance().enableSlabsToBlocks) {
            enc_vanilla$process(builder1, builder2, resourceLocation, recipe);
        }
    }

    @Unique
    private static void enc_vanilla$process(ImmutableMultimap.Builder<RecipeType<?>, RecipeHolder<?>> builder, ImmutableMap.Builder<ResourceLocation, RecipeHolder<?>> builder1, ResourceLocation blockToSlabId, Recipe<?> recipe) {
        if (recipe instanceof ShapedRecipe shaped
                && shaped.getResultItem(null).getItem() instanceof BlockItem bi
                && bi.getBlock() instanceof SlabBlock slab
                && shaped.getResultItem(null).getCount() == 6
                && shaped.getHeight() == 1
                && shaped.getWidth() == 3
        ) {
            List<Ingredient> ingredients = shaped.getIngredients().stream().filter(i -> !i.isEmpty()).toList();
            if (ingredients.stream().allMatch(ingredients.getFirst()::equals)) {
                ItemStack[] inputs = ingredients.getFirst().getItems();
                if (inputs.length == 0) {
                    return;
                }
                ItemStack input = inputs[0];

                ResourceLocation blockRl = BuiltInRegistries.ITEM.getKey(input.getItem());
                ResourceLocation slabRl = BuiltInRegistries.BLOCK.getKey(slab);
                ResourceLocation recipeRl = ResourceLocation.fromNamespaceAndPath(EncVanilla.MOD_ID,
                        blockRl.getNamespace() + "_" + blockRl.getPath()
                                + "/" + slabRl.getNamespace()
                                + "_" + slabRl.getPath()
                );
                if(id.contains(recipeRl)) {
                    return;
                }
                id.add(recipeRl);

                RecipeHolder<ShapedRecipe> recipeHolder = new RecipeHolder<>(recipeRl, new ShapedRecipe(
                        "",
                        CraftingBookCategory.BUILDING,
                        ShapedRecipePattern.of(
                                Map.of(
                                        '#',
                                        Ingredient.of(slab)
                                ),
                                "##"
                        ),
                        input
                ));

                builder.put(RecipeType.CRAFTING, recipeHolder);
                builder1.put(recipeRl, recipeHolder);
            }
        }
    }
}

package com.euphony.enc_vanilla.fabric.mixin;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.RecipesConfig;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.SlabBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin extends SimplePreparableReloadListener<RecipeMap> implements RecipeAccess {
    private static ArrayList<ResourceLocation> id = new ArrayList<>();

    @Inject(method = "Lnet/minecraft/world/item/crafting/RecipeManager;prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/world/item/crafting/RecipeMap;", at = @At(value = "HEAD"))
    private void apply(ResourceManager arg, ProfilerFiller arg2, CallbackInfoReturnable<RecipeMap> cir) {
        id.clear();
    }

    @Inject(method = "Lnet/minecraft/world/item/crafting/RecipeManager;prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/world/item/crafting/RecipeMap;",
            at = @At(value = "INVOKE", target = "Ljava/util/SortedMap;forEach(Ljava/util/function/BiConsumer;)V"))
    private static void lambda(ResourceManager arg, ProfilerFiller arg2, CallbackInfoReturnable<RecipeMap> cir, @Local List<RecipeHolder<?>> list) {
        if(RecipesConfig.HANDLER.instance().enableSlabsToBlocks) {
            list.forEach(holder -> {
                enc_vanilla$process(list, holder.value());
            });
        }
    }

    @Unique
    private static void enc_vanilla$process(List<RecipeHolder<?>> list, Recipe<?> recipe) {
        if (recipe instanceof ShapedRecipe shaped
                && shaped.assemble(null, null).getItem() instanceof BlockItem blockItem
                && blockItem.getBlock() instanceof SlabBlock slab
                && shaped.assemble(null, null).getCount() == 6
                && shaped.getHeight() == 1
                && shaped.getWidth() == 3
        ) {
            List<Ingredient> ingredients = shaped.getIngredients().stream().filter(i -> !i.isEmpty()).map(ingredient -> ingredient.get()).toList();
            if (ingredients.stream().allMatch(ingredients.getFirst()::equals)) {
                Item inputItem = ingredients.getFirst().items().findFirst().get().value();

                ResourceLocation blockRl = BuiltInRegistries.ITEM.getKey(inputItem);
                ResourceLocation slabRl = BuiltInRegistries.BLOCK.getKey(slab);
                ResourceLocation recipeRl = ResourceLocation.fromNamespaceAndPath(EncVanilla.MOD_ID,
                        blockRl.getNamespace() + "_" + blockRl.getPath()
                                + "/" + slabRl.getNamespace()
                                + "_" + slabRl.getPath()
                );
                ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, recipeRl);
                if(id.contains(recipeRl)) {
                    return;
                }
                id.add(recipeRl);

                RecipeHolder<ShapedRecipe> recipeHolder = new RecipeHolder<>(key, new ShapedRecipe(
                        "",
                        CraftingBookCategory.BUILDING,
                        ShapedRecipePattern.of(
                                Map.of(
                                        '#',
                                        Ingredient.of(slab)
                                ),
                                "##"
                        ),
                        inputItem.getDefaultInstance()
                ));

                list.add(recipeHolder);
            }
        }
    }
}
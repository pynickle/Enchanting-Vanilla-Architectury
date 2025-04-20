package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.config.categories.RecipesConfig;
import java.util.Collection;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public class RecipeUpdateEvent {
    public static void recipeUpdate(RecipeManager recipeManager) {
        if(!RecipesConfig.HANDLER.instance().enableBetterLodestone) return;

        Collection<RecipeHolder<?>> recipeHolders = recipeManager.getRecipes();
        Collection<RecipeHolder<?>> recipesToKeep = recipeHolders.stream()
                .filter((holder) -> {
                    ResourceLocation id = holder.id();
                    return !id.equals(ResourceLocation.withDefaultNamespace("lodestone"));
                }).collect(Collectors.toList());

        recipeManager.replaceRecipes(recipesToKeep);
    }
}

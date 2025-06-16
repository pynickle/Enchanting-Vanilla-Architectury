package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.RecipesConfig;
import com.euphony.enc_vanilla.config.categories.ToolsConfig;
import com.euphony.enc_vanilla.utils.Utils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.SortedMap;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin extends SimplePreparableReloadListener<RecipeMap> {
    @Inject(
            method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/world/item/crafting/RecipeMap;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/SortedMap;forEach(Ljava/util/function/BiConsumer;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void prepareInject(ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfoReturnable<RecipeMap> cir, @Local SortedMap<ResourceLocation, Recipe<?>> sortedMap) {
        if (!RecipesConfig.HANDLER.instance().enableSpongeCampfire) {
            sortedMap.remove(Utils.prefix("wet_sponge_to_sponge"));
        }
        if(RecipesConfig.HANDLER.instance().enableBetterLodestone) {
            sortedMap.remove(Utils.prefix("lodestone"));
        }
        if(!ToolsConfig.HANDLER.instance().enableSculkCompass) {
            sortedMap.remove(Utils.prefix("sculk_compass"));
            sortedMap.remove(Utils.prefix("appraisal_table"));
        }
    }
}

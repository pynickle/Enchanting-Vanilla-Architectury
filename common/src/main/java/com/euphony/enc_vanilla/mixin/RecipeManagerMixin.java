package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.RecipesConfig;
import com.euphony.enc_vanilla.config.categories.ToolsConfig;
import com.euphony.enc_vanilla.utils.Utils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
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

import java.util.List;
import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin extends SimpleJsonResourceReloadListener {
    public RecipeManagerMixin(Gson gson, String string) {
        super(gson, string);
    }

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"))
    private void applyInject(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci) {
        if (!RecipesConfig.HANDLER.instance().enableSpongeCampfire) {
            map.remove(Utils.prefix("wet_sponge_to_sponge"));
        }
        if(RecipesConfig.HANDLER.instance().enableBetterLodestone) {
            map.remove(Utils.prefix("lodestone"));
        }
        if(!ToolsConfig.HANDLER.instance().enableSculkCompass) {
            map.remove(Utils.prefix("sculk_compass"));
        }
    }
}

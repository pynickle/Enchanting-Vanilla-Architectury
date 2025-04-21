package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Map;

@Mixin(TagLoader.class)
public class TagLoaderMixin {
    @ModifyReturnValue(method = "load", at = @At("RETURN"))
    public Map<ResourceLocation, List<TagLoader.EntryWithSource>> load(Map<ResourceLocation, List<TagLoader.EntryWithSource>> original) {
        if(!QolConfig.HANDLER.instance().enablePaintingVariants) {
            original.remove(ResourceLocation.withDefaultNamespace("painting_variant/placeable"));
        }
        return original;
    }
}

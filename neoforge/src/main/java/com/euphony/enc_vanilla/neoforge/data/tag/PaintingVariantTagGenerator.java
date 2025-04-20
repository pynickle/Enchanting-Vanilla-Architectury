package com.euphony.enc_vanilla.neoforge.data.tag;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.decoration.PaintingVariants;

public class PaintingVariantTagGenerator extends PaintingVariantTagsProvider {
    public PaintingVariantTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(PaintingVariantTags.PLACEABLE).add(PaintingVariants.WATER, PaintingVariants.WIND, PaintingVariants.FIRE, PaintingVariants.EARTH);
    }
}

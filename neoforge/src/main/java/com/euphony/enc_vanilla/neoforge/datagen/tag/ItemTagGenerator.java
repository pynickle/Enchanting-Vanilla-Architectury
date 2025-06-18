package com.euphony.enc_vanilla.neoforge.datagen.tag;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

import static com.euphony.enc_vanilla.common.tag.EVItemTags.BIOME_CRYSTAL;
import static com.euphony.enc_vanilla.common.tag.EVItemTags.FORCED_FUELS;

public class ItemTagGenerator extends ItemTagsProvider {
    public ItemTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, EncVanilla.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BIOME_CRYSTAL).add(EVItems.BIOME_CRYSTAL_ITEM.get(), EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get(), EVItems.HEATED_BIOME_CRYSTAL_ITEM.get());
        tag(FORCED_FUELS).add(Items.COAL, Items.CHARCOAL);
    }
}

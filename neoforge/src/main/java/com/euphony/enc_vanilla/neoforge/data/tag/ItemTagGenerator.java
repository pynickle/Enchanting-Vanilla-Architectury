package com.euphony.enc_vanilla.neoforge.data.tag;

import static com.euphony.enc_vanilla.common.init.EVTags.BIOME_CRYSTAL;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ItemTagGenerator extends ItemTagsProvider {
    public ItemTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, EncVanilla.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BIOME_CRYSTAL).add(EVItems.BIOME_CRYSTAL_ITEM.get(), EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get(), EVItems.HEATED_BIOME_CRYSTAL_ITEM.get());
    }
}

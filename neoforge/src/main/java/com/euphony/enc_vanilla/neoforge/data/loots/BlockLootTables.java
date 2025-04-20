package com.euphony.enc_vanilla.neoforge.data.loots;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class BlockLootTables extends BlockLootSubProvider {
    public BlockLootTables(HolderLookup.Provider registries) {
        super(ImmutableSet.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        Iterator<RegistrySupplier<Block>> blocks = EVBlocks.BLOCKS.iterator();
        List<Block> res = new ArrayList<>();
        while (blocks.hasNext()) {
            res.add(blocks.next().get());
        }
        return res;
    }

    @Override
    protected void generate() {
        dropOther(EVBlocks.WATERLOGGED_LILY_PAD.get(), Items.LILY_PAD);
        add(EVBlocks.CUT_VINE.get(), createShearsOnlyDrop(Items.VINE));
        dropOther(EVBlocks.CUT_SUGAR_CANE.get(), Items.SUGAR_CANE);
        dropOther(EVBlocks.CUT_BAMBOO_SAPLING.get(), Items.BAMBOO);

        dropOther(EVBlocks.CEILING_TORCH.get(), Items.TORCH);
        dropOther(EVBlocks.CEILING_REDSTONE_TORCH.get(), Items.REDSTONE_TORCH);
        dropOther(EVBlocks.CEILING_SOUL_TORCH.get(), Items.SOUL_TORCH);

        dropSelf(EVBlocks.COMPRESSED_SLIME_BLOCK.get());
    }
}

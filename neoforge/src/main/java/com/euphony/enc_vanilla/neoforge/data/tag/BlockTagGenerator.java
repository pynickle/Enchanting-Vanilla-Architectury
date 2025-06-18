package com.euphony.enc_vanilla.neoforge.data.tag;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.common.tag.EVBlockTags;
import com.euphony.enc_vanilla.utils.Utils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends BlockTagsProvider {
    public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, EncVanilla.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.SWORD_EFFICIENT).add(EVBlocks.CUT_VINE.get(), EVBlocks.WATERLOGGED_LILY_PAD.get(), EVBlocks.CUT_SUGAR_CANE.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(EVBlocks.CUT_VINE.get(), EVBlocks.WATERLOGGED_LILY_PAD.get(), EVBlocks.CUT_SUGAR_CANE.get(),
                EVBlocks.APPRAISAL_TABLE.get());
        tag(EVBlockTags.SWORD_INSTANTLY_MINES).add(EVBlocks.CUT_BAMBOO_SAPLING.get());

        tag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH).add(EVBlocks.CUT_VINE.get());
        tag(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH).add(EVBlocks.CUT_VINE.get());

        tag(BlockTags.REPLACEABLE).add(EVBlocks.CUT_VINE.get());
        tag(BlockTags.REPLACEABLE_BY_TREES).add(EVBlocks.CUT_VINE.get());

        tag(BlockTags.FALL_DAMAGE_RESETTING).add(EVBlocks.CUT_VINE.get());

        tag(BlockTags.ENCHANTMENT_POWER_TRANSMITTER).add(EVBlocks.CUT_VINE.get());

        tag(BlockTags.CLIMBABLE).add(EVBlocks.CUT_VINE.get());

        tag(BlockTags.BAMBOO_PLANTABLE_ON).add(EVBlocks.CUT_BAMBOO_SAPLING.get());
    }

    @SafeVarargs
    public final <T extends Block> void add(TagKey<Block> tag, T... blocks) {
        tag(tag).add(blocks);
    }
}

package com.euphony.enc_vanilla.common.tag;

import com.euphony.enc_vanilla.utils.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class EVBlockTags {
    public static final TagKey<Block> SWORD_INSTANTLY_MINES = createVanillaTag("sword_instantly_mines");

    public static TagKey<Block> create(String tagName) {
        return TagKey.create(Registries.BLOCK, Utils.prefix(tagName));
    }

    public static TagKey<Block> createVanillaTag(String tagName) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace(tagName));
    }

    public static TagKey<Block> createCTag(String tagName) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", tagName));
    }

}

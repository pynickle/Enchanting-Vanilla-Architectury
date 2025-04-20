package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.utils.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class EVTags {
    public static final TagKey<Item> BIOME_CRYSTAL = create("biome_crystal");

    public static TagKey<Item> create(String tagName) {
        return TagKey.create(Registries.ITEM, Utils.prefix(tagName));
    }
}

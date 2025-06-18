package com.euphony.enc_vanilla.common.tag;

import com.euphony.enc_vanilla.utils.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class EVItemTags {
    public static final TagKey<Item> BIOME_CRYSTAL = create("biome_crystal");
    public static final TagKey<Item> FORCED_FUELS = create("forced_fuels");

    public static TagKey<Item> create(String tagName) {
        return TagKey.create(Registries.ITEM, Utils.prefix(tagName));
    }
}

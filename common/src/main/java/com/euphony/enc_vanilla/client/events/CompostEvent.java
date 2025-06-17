package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.config.categories.recipes.RecipesConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

@Environment(EnvType.CLIENT)
public class CompostEvent {
    public static void registerCompostable(Minecraft minecraft) {
        if(!RecipesConfig.HANDLER.instance().enableMoreCompostable) return;

        addCompostable(0.1f, Items.DEAD_BUSH);

        addCompostable(0.3f, Items.DIRT);
        addCompostable(0.3f, Items.COARSE_DIRT);
        addCompostable(0.3f, Items.PODZOL);
        addCompostable(0.3f, Items.MUDDY_MANGROVE_ROOTS);

        addCompostable(0.5f, Items.ROTTEN_FLESH);
        addCompostable(0.5f, Items.GRASS_BLOCK);
        addCompostable(0.5f, Items.BAMBOO);

        addCompostable(0.65f, Items.CHORUS_FRUIT);
        addCompostable(0.65f, Items.CHORUS_PLANT);
        addCompostable(0.65f, Items.MYCELIUM);
        addCompostable(0.65f, Items.ROOTED_DIRT);

        addCompostable(0.85f, Items.POPPED_CHORUS_FRUIT);
        addCompostable(0.85f, Items.CHORUS_FLOWER);
        addCompostable(0.85f, Items.MUSHROOM_STEW);
        addCompostable(0.85f, Items.BEETROOT_SOUP);
    }

    protected static void addCompostable(float chance, ItemLike item) {
        ComposterBlock.COMPOSTABLES.putIfAbsent(item, chance);
    }
}

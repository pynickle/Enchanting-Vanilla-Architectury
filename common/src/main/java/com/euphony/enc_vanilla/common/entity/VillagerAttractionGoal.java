package com.euphony.enc_vanilla.common.entity;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class VillagerAttractionGoal extends DoubleHandedTemptGoal {

    public VillagerAttractionGoal(PathfinderMob mob) {
        super(mob, 0.5F, Ingredient.of(QolConfig.HANDLER.instance().mainHandItem), Ingredient.of(Items.BLACK_BANNER), false);

        this.offHandItems = Ingredient.of(Raid.getOminousBannerInstance(mob.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN)).getItem());
    }
}

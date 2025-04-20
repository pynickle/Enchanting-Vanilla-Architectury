package com.euphony.enc_vanilla.common.entity;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;

public class VillagerAttractionGoal extends DoubleHandedTemptGoal {

    public VillagerAttractionGoal(PathfinderMob mob, Level level) {
        super(mob, 0.5F, Ingredient.of(QolConfig.HANDLER.instance().mainHandItem), Ingredient.of(Items.BLACK_BANNER), false);
        HolderLookup.RegistryLookup<BannerPattern> bannerPattern = level.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN);

        ItemStack OMINOUS_BANNER = Items.WHITE_BANNER.getDefaultInstance();
        OMINOUS_BANNER.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        OMINOUS_BANNER.set(DataComponents.RARITY, Rarity.UNCOMMON);
        OMINOUS_BANNER.set(DataComponents.ITEM_NAME, Component.literal("{\"translate\":\"block.minecraft.ominous_banner\"}"));
        OMINOUS_BANNER.set(DataComponents.BANNER_PATTERNS, new BannerPatternLayers.Builder()
                .add(bannerPattern.getOrThrow(BannerPatterns.RHOMBUS_MIDDLE), DyeColor.CYAN)
                .add(bannerPattern.getOrThrow(BannerPatterns.STRIPE_BOTTOM), DyeColor.LIGHT_GRAY)
                .add(bannerPattern.getOrThrow(BannerPatterns.STRIPE_CENTER), DyeColor.GRAY)
                .add(bannerPattern.getOrThrow(BannerPatterns.BORDER), DyeColor.LIGHT_GRAY)
                .add(bannerPattern.getOrThrow(BannerPatterns.STRIPE_MIDDLE), DyeColor.BLACK)
                .add(bannerPattern.getOrThrow(BannerPatterns.HALF_HORIZONTAL), DyeColor.LIGHT_GRAY)
                .add(bannerPattern.getOrThrow(BannerPatterns.CIRCLE_MIDDLE), DyeColor.LIGHT_GRAY)
                .add(bannerPattern.getOrThrow(BannerPatterns.BORDER), DyeColor.BLACK)
                .build()
        );
        this.offHandItems = Ingredient.of(OMINOUS_BANNER);
    }
}

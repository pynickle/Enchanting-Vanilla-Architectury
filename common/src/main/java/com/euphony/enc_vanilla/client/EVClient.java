package com.euphony.enc_vanilla.client;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;

public class EVClient {
    public static void initialize() {
        registerBlockColor();
        registerItemColor();
    }

    public static void registerBlockColor() {
        ColorHandlerRegistry.registerBlockColors(
                (state, level, pos, tintIndex) -> 2129968,
                EVBlocks.WATERLOGGED_LILY_PAD.get());
        ColorHandlerRegistry.registerBlockColors(
                (state, level, pos, tintIndex) ->
                        level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor(),
                EVBlocks.CUT_VINE.get()
        );
        ColorHandlerRegistry.registerBlockColors(
                (state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : -1,
                EVBlocks.CUT_SUGAR_CANE.get()
        );
    }

    public static void registerItemColor() {
        ColorHandlerRegistry.registerItemColors(
                (p_92687_, p_92688_) -> FoliageColor.getDefaultColor(),
                EVBlocks.CUT_VINE.get()
        );
    }
}

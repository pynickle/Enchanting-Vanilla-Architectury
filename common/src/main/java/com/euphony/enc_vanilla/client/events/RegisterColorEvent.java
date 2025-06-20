package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;

@Environment(EnvType.CLIENT)
public class RegisterColorEvent {
    public static void registerColor(Minecraft minecraft) {
        registerBlockColor();
        registerItemColor();
    }

    public static void registerBlockColor() {
        ColorHandlerRegistry.registerBlockColors(
                (state, level, pos, tintIndex) -> 2129968,
                EVBlocks.WATERLOGGED_LILY_PAD.get());
        ColorHandlerRegistry.registerBlockColors(
                (state, level, pos, tintIndex) ->
                        level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.FOLIAGE_DEFAULT,
                EVBlocks.CUT_VINE.get()
        );
        ColorHandlerRegistry.registerBlockColors(
                (state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : -1,
                EVBlocks.CUT_SUGAR_CANE.get()
        );
    }

    public static void registerItemColor() {
        /*
        ColorHandlerRegistry.registerItemColors(
                EVBlocks.WATERLOGGED_LILY_PAD.get()
        );
        ColorHandlerRegistry.registerItemColors(
                (p_92687_, p_92688_) -> FoliageColor.FOLIAGE_DEFAULT,
                EVBlocks.CUT_VINE.get()
        );

         */
    }
}

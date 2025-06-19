package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class RenderTypeEvent {
    public static void registerRenderType(Minecraft minecraft) {
        /*
        RenderTypeRegistry.register(RenderType.cutout(), EVBlocks.CEILING_TORCH.get(),
                EVBlocks.CEILING_SOUL_TORCH.get(), EVBlocks.CEILING_REDSTONE_TORCH.get(),
                EVBlocks.CUT_VINE.get(), EVBlocks.CUT_SUGAR_CANE.get(), EVBlocks.CUT_BAMBOO_SAPLING.get(),
                EVBlocks.WATERLOGGED_LILY_PAD.get());

         */
    }
}

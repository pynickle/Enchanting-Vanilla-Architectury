package com.euphony.enc_vanilla.mixin.invoker;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerTabOverlay.class)
public interface PlayerTabOverlayInvoker {
    @Invoker("renderPingIcon")
    void invokeRenderPingIcon(GuiGraphics graphics, int width, int x, int y, PlayerInfo player);
}
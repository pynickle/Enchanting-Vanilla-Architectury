package com.euphony.enc_vanilla.screen.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class RefreshImageButton extends ImageButton {
    public RefreshImageButton(int i, int j, int k, int l, WidgetSprites widgetSprites, OnPress onPress) {
        super(i, j, k, l, widgetSprites, onPress);
    }

    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        ResourceLocation resourceLocation = this.sprites.get(this.isActive(), this.isHovered());
        guiGraphics.blitSprite(resourceLocation, this.getX(), this.getY(), this.width, this.height);
    }
}

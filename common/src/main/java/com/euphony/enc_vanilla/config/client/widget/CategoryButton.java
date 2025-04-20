package com.euphony.enc_vanilla.config.client.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CategoryButton extends Button {
    private final ItemStack icon;
    private final Component text;

    private static final int MARGIN = 8;

    public CategoryButton(int x, int y, int w, int h, Component text, ItemStack icon, OnPress onClick) {
        super(x, y, w, h, text, onClick, DEFAULT_NARRATION);
        this.icon = icon;
        this.text = text;
    }

    private static final WidgetSprites SPRITES = new WidgetSprites(ResourceLocation.withDefaultNamespace("widget/button"), ResourceLocation.withDefaultNamespace("widget/button_disabled"), ResourceLocation.withDefaultNamespace("widget/button_highlighted"));

    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        Minecraft mc = Minecraft.getInstance();
        guiGraphics.renderFakeItem(icon, getX() + 5, getY() + 2);
        
        guiGraphics.drawCenteredString(mc.font, text, getX() + width / 2 + MARGIN, getY() + (height - 8) / 2, 16777215);
    }
}

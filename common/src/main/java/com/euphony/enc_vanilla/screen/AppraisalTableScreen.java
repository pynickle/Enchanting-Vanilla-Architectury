package com.euphony.enc_vanilla.screen;

import com.euphony.enc_vanilla.screen.widget.RefreshImageButton;
import com.euphony.enc_vanilla.utils.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class AppraisalTableScreen extends AbstractContainerScreen<AppraisalTableMenu> {
    private static final String GLITCH_CHARS = "01!@#$%^&*()_+-=[]{}|;:,.<>?/";

    public static final WidgetSprites REFRESH_SPRITES = new WidgetSprites(Utils.prefix("refresh"), Utils.prefix("refresh_disabled"), Utils.prefix("refresh_highlighted"));

    private static final ResourceLocation PROGRESS_SPRITE = Utils.prefix("container/progress");
    private static final ResourceLocation TEXTURE = Utils.prefix("textures/gui/container/appraisal_table.png");

    private static final int UPDATE_INTERVAL = 3;

    private boolean isActive = false;
    public static String message = "";
    private int tickCounter = 0;
    public static StringWidget glitchWidget = new StringWidget(Component.literal(message), Minecraft.getInstance().font);
    RefreshImageButton refreshImageButton;

    public AppraisalTableScreen(AppraisalTableMenu itemCombinerMenu, Inventory inventory, Component component) {
        super(itemCombinerMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        this.refreshImageButton = new RefreshImageButton( this.leftPos + 67, this.topPos + 50, 18, 18, REFRESH_SPRITES, (button) -> {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
        });
        refreshImageButton.active = false;
        this.addRenderableWidget(
                refreshImageButton
        );
        glitchWidget = new StringWidget(this.leftPos + 96, this.topPos + 58, 30, 20, Component.literal(message), Minecraft.getInstance().font);
        glitchWidget.alignLeft();
        glitchWidget.setColor(11184810);
        this.addRenderableWidget(glitchWidget);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        this.renderTooltip(guiGraphics, i, j);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int k = this.leftPos;
        int l = this.topPos;
        guiGraphics.blit(TEXTURE, k, l, 0, 0, this.imageWidth, this.imageHeight);
        if ((this.menu).isInProgress()) {
            int m = 75;
            int n = Mth.ceil((this.menu).getProgress() * m) + 1;
            guiGraphics.blitSprite(PROGRESS_SPRITE, 74, 15, 0, 0, k + 51, l + 36, n, 15);
        }
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if(this.menu.isInProgress()) {
            if (++tickCounter >= UPDATE_INTERVAL) {
                regenerateText();
                tickCounter = 0;
            }
        } else {
            if(!glitchWidget.getMessage().getString().isEmpty()) {
                glitchWidget.setMessage(Component.literal(""));
            }
        }
        if(this.menu.getIsActive() != isActive) {
            isActive = this.menu.getIsActive();
            this.refreshImageButton.active = isActive;
        }
    }

    private void regenerateText() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 5 + random.nextInt(10);

        for (int i = 0; i < length; i++) {
            sb.append(GLITCH_CHARS.charAt(
                    random.nextInt(GLITCH_CHARS.length())
            ));
        }

        glitchWidget.setMessage(Component.literal(sb.toString()));
    }

    @Override
    public AppraisalTableMenu getMenu() {
        return super.getMenu();
    }
}

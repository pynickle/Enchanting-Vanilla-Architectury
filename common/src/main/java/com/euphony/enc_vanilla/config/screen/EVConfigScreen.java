package com.euphony.enc_vanilla.config.screen;

import com.euphony.enc_vanilla.config.categories.qol.QolScreen;
import com.euphony.enc_vanilla.config.categories.recipes.RecipesScreen;
import com.euphony.enc_vanilla.config.categories.tools.ToolsScreen;
import com.euphony.enc_vanilla.config.screen.widget.CategoryButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class EVConfigScreen extends Screen {
    private final Screen parent;
    @Nullable
    private Screen qolConfigScreen = null;
    @Nullable
    private Screen toolsConfigScreen = null;
    @Nullable
    private Screen recipesConfigScreen = null;

    public EVConfigScreen(@Nullable Screen parent) {
        super(Component.translatable("yacl3.config.enc_vanilla:config"));
        this.parent = parent;
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        assert this.minecraft != null;
        context.pose().pushMatrix();
        float scale = 2.0F;
        context.pose().translate(this.width / 2, 10);
        context.pose().scale(scale, scale);
        context.pose().translate(-this.width / 2, 0);
        context.drawCenteredString(this.minecraft.font, Component.translatable("yacl3.config.enc_vanilla:config"), this.width / 2, 0, 0xFFFFFFFF);
        context.pose().popMatrix();
    }

    @Override
    protected void init() {
        super.init();

        int categoryButtonWidth = (this.width - 80) / 3;

        var qolButton = new CategoryButton(30, 40, categoryButtonWidth, 20,
                Component.translatable("yacl3.config.enc_vanilla:config.category.qol"), Items.IRON_PICKAXE.getDefaultInstance(), (btn) -> {
            if(this.qolConfigScreen == null) {
                this.qolConfigScreen = QolScreen.makeScreen().generateScreen(this);
            }

            this.minecraft.setScreen(this.qolConfigScreen);
        });
        var toolsButton = new CategoryButton(30 + categoryButtonWidth + 10, 40, categoryButtonWidth, 20,
                Component.translatable("yacl3.config.enc_vanilla:config.category.tools"), Items.AMETHYST_SHARD.getDefaultInstance(), (btn) -> {
            if(this.toolsConfigScreen == null) {
                this.toolsConfigScreen = ToolsScreen.makeScreen().generateScreen(this);
            }

            this.minecraft.setScreen(this.toolsConfigScreen);
        });
        var recipesButton = new CategoryButton(30 + (categoryButtonWidth + 10) * 2, 40, categoryButtonWidth, 20,
                Component.translatable("yacl3.config.enc_vanilla:config.category.recipes"), Items.ANVIL.getDefaultInstance(), (btn) -> {
            if(this.recipesConfigScreen == null) {
                this.recipesConfigScreen = RecipesScreen.makeScreen().generateScreen(this);
            }

            this.minecraft.setScreen(this.recipesConfigScreen);
        });

        int doneButtonWidth = this.width - 300;
        var buttonWidget = Button.builder(CommonComponents.GUI_DONE, (btn) -> this.minecraft.setScreen(this.parent)).bounds(this.width / 2 - doneButtonWidth / 2, this.height - 30, doneButtonWidth, 20).build();

        this.addRenderableWidget(qolButton);
        this.addRenderableWidget(toolsButton);
        this.addRenderableWidget(recipesButton);
        this.addRenderableWidget(buttonWidget);
    }
}

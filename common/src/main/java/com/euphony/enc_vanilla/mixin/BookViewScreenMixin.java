package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BookViewScreen.class)
public abstract class BookViewScreenMixin extends Screen {
    @Shadow protected abstract void pageBack();

    @Shadow private int currentPage;

    @Shadow protected abstract int getNumPages();

    @Shadow protected abstract void pageForward();

    @Unique
    double enc_vanilla$progress = 0;

    protected BookViewScreenMixin(Component component) {
        super(component);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(!ClientConfig.HANDLER.instance().enableBookScroll) return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);

        double scrollDelta = verticalAmount + horizontalAmount;

        double enc_vanilla$speedFactor = 1.0;
        if (hasControlDown()) enc_vanilla$speedFactor *= ClientConfig.HANDLER.instance().ctrlSpeedMultiplier;

        enc_vanilla$progress += scrollDelta * enc_vanilla$speedFactor;

        boolean pageTurned = false;
        if (enc_vanilla$progress >= 1.0) {
            while (enc_vanilla$progress >= 1.0) {
                enc_vanilla$progress -= 1.0;
                this.pageBack();
                pageTurned = true;
            }
        } else if (enc_vanilla$progress < 0.0) {
            while (enc_vanilla$progress < 0.0) {
                enc_vanilla$progress += 1.0;
                if (this.currentPage < this.getNumPages() - 1) {
                    this.pageForward();
                }
                pageTurned = true;
            }
        }

        if (pageTurned && ClientConfig.HANDLER.instance().enablePageTurnSound) {
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
        }
        return true;
    }
}

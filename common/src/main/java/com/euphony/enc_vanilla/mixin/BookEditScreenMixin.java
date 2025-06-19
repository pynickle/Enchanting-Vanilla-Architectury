package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.api.IMultiLineEditBox;
import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookEditScreen.class)
public abstract class BookEditScreenMixin extends Screen {
    @Shadow private MultiLineEditBox page;
    @Shadow private PageButton forwardButton;
    @Shadow private PageButton backButton;

    @Shadow protected abstract void pageForward();

    @Shadow protected abstract void pageBack();

    @Shadow private int currentPage;

    @Shadow protected abstract int getNumPages();

    @Unique
    private boolean enc_vanilla$isModified;

    @Unique
    double enc_vanilla$progress = 0;

    protected BookEditScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "appendPageToBook", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/BookEditScreen;getNumPages()I",
            shift =  At.Shift.AFTER))
    private void appendPageToBookInject(CallbackInfo ci) {
        this.enc_vanilla$isModified = true;
    }

    @Override
    public void onClose() {
        if(!ClientConfig.HANDLER.instance().enableBookSaveConfirmation) {
            super.onClose();
            return;
        }

        if (this.enc_vanilla$isModified || ((IMultiLineEditBox) this.page).enc_vanilla$getIsModified()) {
            this.minecraft.setScreen(new ConfirmScreen((response) -> {
                if(response) {
                    this.enc_vanilla$isModified = false;
                    ((IMultiLineEditBox) this.page).enc_vanilla$setIsModified(false);
                    this.minecraft.setScreen(null);
                } else {
                    this.minecraft.setScreen(this);
                }
            }, Component.translatable("message.enc_vanilla.book_save.title"), Component.translatable("message.enc_vanilla.book_save.question")));
        } else {
            super.onClose();
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(!ClientConfig.HANDLER.instance().enableBookScroll) return true;

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
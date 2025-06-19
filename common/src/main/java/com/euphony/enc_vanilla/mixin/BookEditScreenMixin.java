package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.api.IMultiLineEditBox;
import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

@Mixin(BookEditScreen.class)
public class BookEditScreenMixin extends Screen {
    @Shadow private MultiLineEditBox page;
    @Unique
    private boolean enc_vanilla$isModified;

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
}
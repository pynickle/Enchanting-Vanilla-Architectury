package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.client.events.KeyMappingEvent;
import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import com.euphony.enc_vanilla.config.categories.client.ClientScreen;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(BookEditScreen.class)
public class BookEditScreenMixin extends Screen {
    @Shadow private boolean isModified;

    protected BookEditScreenMixin(Component component) {
        super(component);
    }

    @Override
    public void onClose() {
        if(!ClientConfig.HANDLER.instance().enableBookSaveConfirmation) {
            super.onClose();
            return;
        }

        if (this.isModified) {
            this.minecraft.setScreen(new ConfirmScreen((response) -> {
                if(response) {
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

package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
    @ModifyVariable(method = "tryApplyNewDataPacks", at = @At("HEAD"), argsOnly = true)
    private boolean applyNewDataPacks(boolean bl) {
        if(ClientConfig.HANDLER.instance().enableNoExperimentalWarning) {
            return false;
        }
        return bl;
    }
}

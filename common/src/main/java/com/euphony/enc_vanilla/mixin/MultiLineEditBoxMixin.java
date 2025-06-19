package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.api.IMultiLineEditBox;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.gui.components.AbstractTextAreaWidget;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MultiLineEditBox.class)
public abstract class MultiLineEditBoxMixin extends AbstractTextAreaWidget implements IMultiLineEditBox {
    @Unique
    private static boolean enc_vanilla$isModified;

    public MultiLineEditBoxMixin(int i, int j, int k, int l, Component component) {
        super(i, j, k, l, component);
    }

    @ModifyReturnValue(method = "charTyped", at = @At("RETURN"))
    private boolean charTypedInject(boolean original) {
        if(original) {
            enc_vanilla$isModified = true;
        }
        return original;
    }

    @Override
    public void enc_vanilla$setIsModified(boolean isModified) {
        enc_vanilla$isModified = isModified;
    }

    @Override
    public boolean enc_vanilla$getIsModified() {
        return enc_vanilla$isModified;
    }
}

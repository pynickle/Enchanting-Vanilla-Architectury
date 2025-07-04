package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {
    @ModifyExpressionValue(
            method = {"addMessageToDisplayQueue(Lnet/minecraft/client/GuiMessage;)V",
                    "addMessageToQueue(Lnet/minecraft/client/GuiMessage;)V",
                    "addRecentChat(Ljava/lang/String;)V"},
            at = @At(value = "CONSTANT", args = "intValue=100")
    )
    private int moreMessages(int chatMaxMessages) {
        return ClientConfig.HANDLER.instance().chatMaxMessages;
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"clearMessages(Z)V"},
            cancellable = true
    )
    public void clear(boolean clearHistory, CallbackInfo ci) {
        if (clearHistory && ClientConfig.HANDLER.instance().enableChatHistoryRetention) {
            ci.cancel();
        }
    }

    @Shadow
    @Final
    private Minecraft minecraft;

    @Unique
    private int better_client$getOffset() {
        LocalPlayer player = minecraft.player;
        if (player == null || player.isCreative() || player.isSpectator()) return 0;

        int offset = player.getArmorValue() > 0 ? 10 : 0;
        if (player.getAbsorptionAmount() > 0) offset += 10;
        return offset;
    }

    @ModifyArg(method = "render", index = 1, at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V",
            ordinal = 0
    ))
    private float offsetY(float y) {
        return y - better_client$getOffset();
    }

    @ModifyExpressionValue(
            method = "screenToChatY",
            at = @At(value = "CONSTANT", args = "doubleValue=40.0")
    )
    private double textBottomOffset(double original) {
        return original + better_client$getOffset();
    }
}

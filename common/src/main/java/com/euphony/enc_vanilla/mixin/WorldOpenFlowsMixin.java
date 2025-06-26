package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import com.mojang.serialization.Lifecycle;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldOpenFlows.class)
public class WorldOpenFlowsMixin {
    @ModifyVariable(method = "confirmWorldCreation", at = @At("HEAD"), argsOnly = true)
    private static Lifecycle alwaysStable(Lifecycle lifecycle) {
        if(ClientConfig.HANDLER.instance().enableNoExperimentalWarning) {
            return Lifecycle.stable();
        }
        return lifecycle;
    }

    @Redirect(
            method = "openWorldCheckWorldStemCompatibility",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/storage/WorldData;worldGenSettingsLifecycle()Lcom/mojang/serialization/Lifecycle;"
            )
    )
    private Lifecycle alwaysReturnStableLifecycle(WorldData worldData) {
        if(ClientConfig.HANDLER.instance().enableNoExperimentalWarning) {
            return Lifecycle.stable();
        }
        return worldData.worldGenSettingsLifecycle();
    }
}

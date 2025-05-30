package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.keymapping.EVKeyConfig;
import com.euphony.enc_vanilla.keymapping.EVKeyMappings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class KeyMappingEvent {
    private static boolean wasToggleKeyDown = false;

    public static void clientPost(Minecraft minecraft) {
        boolean isToggleKeyDown = EVKeyMappings.SAFE_HARVEST.isDown();
        if(isToggleKeyDown && !wasToggleKeyDown) {
            LocalPlayer player = minecraft.player;
            if(player != null) {
                if (EVKeyConfig.SAFE_HARVEST) {
                    EVKeyConfig.SAFE_HARVEST = false;
                    player.displayClientMessage(Component.translatable("message.enc_vanilla.key.safe_harvest.off"), true);
                } else {
                    EVKeyConfig.SAFE_HARVEST = true;
                    player.displayClientMessage(Component.translatable("message.enc_vanilla.key.safe_harvest.on"), true);
                }
            }
        }
        wasToggleKeyDown = isToggleKeyDown;
    }
}

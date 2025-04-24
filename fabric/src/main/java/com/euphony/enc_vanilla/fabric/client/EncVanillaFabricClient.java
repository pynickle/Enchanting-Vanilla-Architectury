package com.euphony.enc_vanilla.fabric.client;

import com.euphony.enc_vanilla.EncVanillaClient;
import com.euphony.enc_vanilla.client.events.BiomeTitleEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public final class EncVanillaFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EncVanillaClient.init();

        HudRenderCallback.EVENT.register(BiomeTitleEvent::renderBiomeInfo);
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new EVResourceReloadListener());
    }
}

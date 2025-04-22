package com.euphony.enc_vanilla.fabric.client;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.EncVanillaClient;
import net.fabricmc.api.ClientModInitializer;

public final class EncVanillaFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EncVanillaClient.init();
    }
}

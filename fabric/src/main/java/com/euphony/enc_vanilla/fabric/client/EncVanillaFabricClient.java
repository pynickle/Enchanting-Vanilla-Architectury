package com.euphony.enc_vanilla.fabric.client;

import com.euphony.enc_vanilla.client.EVClient;
import net.fabricmc.api.ClientModInitializer;

public final class EncVanillaFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EVClient.initialize();
    }
}

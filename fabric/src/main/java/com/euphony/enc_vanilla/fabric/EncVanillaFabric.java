package com.euphony.enc_vanilla.fabric;

import com.euphony.enc_vanilla.EncVanilla;
import net.fabricmc.api.ModInitializer;

public final class EncVanillaFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        EncVanilla.init();
    }
}

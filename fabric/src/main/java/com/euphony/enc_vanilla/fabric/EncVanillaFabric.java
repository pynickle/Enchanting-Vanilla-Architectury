package com.euphony.enc_vanilla.fabric;

import com.euphony.enc_vanilla.EncVanilla;
import net.fabricmc.api.ModInitializer;

public final class EncVanillaFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EncVanilla.init();
    }
}

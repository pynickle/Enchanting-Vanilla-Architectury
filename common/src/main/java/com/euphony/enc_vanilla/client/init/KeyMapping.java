package com.euphony.enc_vanilla.client.init;

import com.euphony.enc_vanilla.keymapping.EVKeyMappings;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class KeyMapping {
    public static void registerKeyMapping() {
        KeyMappingRegistry.register(EVKeyMappings.SAFE_HARVEST);
        KeyMappingRegistry.register(EVKeyMappings.BUNDLE_UP);
    }
}

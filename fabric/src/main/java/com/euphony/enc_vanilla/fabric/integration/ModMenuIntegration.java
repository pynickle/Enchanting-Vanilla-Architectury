package com.euphony.enc_vanilla.fabric.integration;

import com.euphony.enc_vanilla.config.screen.EVConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if(Platform.isModLoaded("yet_another_config_lib_v3")) {
            return EVConfigScreen::new;
        }
        return null;
    }
}
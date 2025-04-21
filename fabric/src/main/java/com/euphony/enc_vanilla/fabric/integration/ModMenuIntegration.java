package com.euphony.enc_vanilla.fabric.integration;

import com.euphony.enc_vanilla.config.client.EVConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return EVConfigScreen::new;
    }
}
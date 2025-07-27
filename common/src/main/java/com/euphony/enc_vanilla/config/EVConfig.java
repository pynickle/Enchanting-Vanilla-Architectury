package com.euphony.enc_vanilla.config;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.euphony.enc_vanilla.config.categories.recipes.RecipesConfig;
import com.euphony.enc_vanilla.config.categories.tools.ToolsConfig;

public final class EVConfig {
    public void load() {
        QolConfig.load();
        ToolsConfig.load();
        RecipesConfig.load();
    }
}

package com.euphony.enc_vanilla.config.categories.recipes;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.utils.Utils;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class RecipesConfig {
    public static ConfigClassHandler<RecipesConfig> HANDLER = ConfigClassHandler.createBuilder(RecipesConfig.class)
            .id(Utils.prefix("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setPath(Path.of("config", EncVanilla.MOD_ID + "/recipes.json")).build()
            )
            .build();

    public static void load() {
        HANDLER.load();
    }

    public static void save() {
        HANDLER.save();
    }

    public static final String RECIPES_CATEGORY = "recipes";
    public static final String OTHER_GROUP = "other";

    @SerialEntry public boolean enableMoreCompostable = true;
    @SerialEntry public boolean enableSlabsToBlocks = true;
    @SerialEntry public boolean enableSpongeCampfire = true;
}

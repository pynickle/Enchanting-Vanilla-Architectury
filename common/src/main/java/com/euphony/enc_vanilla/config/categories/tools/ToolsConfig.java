package com.euphony.enc_vanilla.config.categories.tools;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.utils.Utils;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class ToolsConfig {
    public static ConfigClassHandler<ToolsConfig> HANDLER = ConfigClassHandler.createBuilder(ToolsConfig.class)
            .id(Utils.prefix("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setPath(Path.of("config", EncVanilla.MOD_ID + "/tools.json")).build()
            )
            .build();

    public static void load() {
        HANDLER.load();
    }

    public static void save() {
        HANDLER.save();
    }

    public static final String TOOLS_CATEGORY = "tools";
    public static final String SCULK_COMPASS_GROUP = "sculk_compass";
    public static final String OTHER_GROUP = "other";

    @SerialEntry public boolean enableSlimeChunkDetecting = true;
    @SerialEntry public boolean enableSculkCompass = true;
}

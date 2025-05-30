package com.euphony.enc_vanilla.config.categories;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.utils.Utils;
import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

import java.nio.file.Path;
import java.util.List;

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

    private static final String TOOLS_CATEGORY = "tools";
    private static final String COMPRESSED_SLIME_BLOCK_GROUP = "compressed_slime_block";
    private static final String SCULK_COMPASS_GROUP = "sculk_compass";
    private static final String OTHER_GROUP = "other";

    @SerialEntry public boolean enableSlimeChunkDetecting = true;
    @SerialEntry public boolean enableSculkCompass = true;
    @SerialEntry public boolean enableCompressedSlimeBlock = true;

    @Environment(EnvType.CLIENT)
    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            // Compressed Slime Block
            Option<Boolean> enableCompressedSlimeBlockOpt = ConfigUtils.<Boolean>getGenericOption("enableCompressedSlimeBlock", "compressed_slime_block")
                    .binding(defaults.enableCompressedSlimeBlock,
                            () -> config.enableCompressedSlimeBlock,
                            newVal -> config.enableCompressedSlimeBlock = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            // Sculk Compass
            Option<Boolean> enableSculkCompassOpt = ConfigUtils.<Boolean>getGenericOption("enableSculkCompass", "sculk_compass")
                    .binding(defaults.enableSculkCompass,
                            () -> config.enableSculkCompass,
                            newVal -> config.enableSculkCompass = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            // Other
            Option<Boolean> enableSlimeChunkDetectingOpt = ConfigUtils.<Boolean>getGenericOption("enableSlimeChunkDetecting", "slime_chunk")
                    .binding(defaults.enableSlimeChunkDetecting,
                            () -> config.enableSlimeChunkDetecting,
                            newVal -> config.enableSlimeChunkDetecting = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(TOOLS_CATEGORY))
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(TOOLS_CATEGORY, COMPRESSED_SLIME_BLOCK_GROUP))
                                    .options(List.of(
                                            enableCompressedSlimeBlockOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(TOOLS_CATEGORY, SCULK_COMPASS_GROUP))
                                    .options(List.of(
                                            enableSculkCompassOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(TOOLS_CATEGORY, OTHER_GROUP))
                                    .options(List.of(
                                            enableSlimeChunkDetectingOpt
                                    ))
                                    .build())
                            .build())
                    .save(ToolsConfig::save);
        });
    }
}

package com.euphony.enc_vanilla.config.categories.tools;

import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

import java.util.List;

import static com.euphony.enc_vanilla.config.categories.tools.ToolsConfig.*;

@Environment(EnvType.CLIENT)
public class ToolsScreen {
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
            Option<Boolean> enableSlimeChunkDetectingOpt = ConfigUtils.<Boolean>getGenericOption("enableSlimeChunkDetecting")
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

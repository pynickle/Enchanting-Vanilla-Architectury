package com.euphony.enc_vanilla.config.categories.qol.screen;


import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.ListOption;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

import static com.euphony.enc_vanilla.config.categories.qol.QolConfig.HANDLER;

@Environment(EnvType.CLIENT)
public class ExtraForcedFuelsScreen {
    private static final String QOL_CATEGORY = "qol";

    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            ListOption<String> extraSoulTorchItemsOpt = ConfigUtils.getListGroupOption("extraSoulTorchItems")
                    .binding(defaults.extraSoulTorchItems,
                            () -> config.extraSoulTorchItems,
                            newVal -> config.extraSoulTorchItems = newVal)
                    .controller(StringControllerBuilder::create)
                    .initial("")
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(QOL_CATEGORY))
                            .group(extraSoulTorchItemsOpt)
                            .build())
                    .save(QolConfig::save);
        });
    }
}
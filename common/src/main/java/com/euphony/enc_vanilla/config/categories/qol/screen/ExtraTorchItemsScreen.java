package com.euphony.enc_vanilla.config.categories.qol.screen;

import static com.euphony.enc_vanilla.config.categories.qol.QolConfig.HANDLER;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.ListOption;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.minecraft.network.chat.Component;

public class ExtraTorchItemsScreen {
    private static final String QOL_CATEGORY = "qol";

    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            ListOption<String> extraTorchItemsOpt = ConfigUtils.getListGroupOption("extraTorchItems")
                    .binding(defaults.extraTorchItems,
                            () -> config.extraTorchItems,
                            newVal -> config.extraTorchItems = newVal)
                    .controller(StringControllerBuilder::create)
                    .initial("")
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(QOL_CATEGORY))
                            .group(extraTorchItemsOpt)
                            .build())
                    .save(QolConfig::save);
        });
    }
}

package com.euphony.enc_vanilla.config.categories.recipes;

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

import static com.euphony.enc_vanilla.config.categories.recipes.RecipesConfig.*;

@Environment(EnvType.CLIENT)
public class RecipesScreen {
    @Environment(EnvType.CLIENT)
    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            // Other
            Option<Boolean> enableMoreCompostableOpt = ConfigUtils.<Boolean>getGenericOption("enableMoreCompostable")
                    .binding(defaults.enableMoreCompostable,
                            () -> config.enableMoreCompostable,
                            newVal -> config.enableMoreCompostable = newVal)
                    .flag(ConfigUtils.RESOURCE_RELOAD)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableSlabsToBlocksOpt = ConfigUtils.<Boolean>getGenericOption("enableSlabsToBlocks", "slabs_to_blocks")
                    .binding(defaults.enableSlabsToBlocks,
                            () -> config.enableSlabsToBlocks,
                            newVal -> config.enableSlabsToBlocks = newVal)
                    .flag(ConfigUtils.RESOURCE_RELOAD)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableSpongeCampfireOpt = ConfigUtils.<Boolean>getGenericOption("enableSpongeCampfire", "sponge_campfire")
                    .binding(defaults.enableSpongeCampfire,
                            () -> config.enableSpongeCampfire,
                            newVal -> config.enableSpongeCampfire = newVal)
                    .flag(ConfigUtils.RESOURCE_RELOAD)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableBetterLodestoneOpt = ConfigUtils.<Boolean>getGenericOption("enableBetterLodestone", "better_lodestone")
                    .binding(defaults.enableBetterLodestone,
                            () -> config.enableBetterLodestone,
                            newVal -> config.enableBetterLodestone = newVal)
                    .flag(ConfigUtils.RESOURCE_RELOAD)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(RECIPES_CATEGORY))
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(RECIPES_CATEGORY, OTHER_GROUP))
                                    .options(List.of(
                                            enableMoreCompostableOpt,
                                            enableSlabsToBlocksOpt,
                                            enableSpongeCampfireOpt,
                                            enableBetterLodestoneOpt
                                    ))
                                    .build())
                            .build())
                    .save(RecipesConfig::save);
        });
    }
}

package com.euphony.enc_vanilla.config.categories.client;

import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import com.euphony.enc_vanilla.utils.config.DescComponent;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.List;

import static com.euphony.enc_vanilla.config.categories.client.ClientConfig.*;

@Environment(EnvType.CLIENT)
public class ClientScreen {
    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            // Fading Night Vision
            Option<Boolean> enableFadingNightVisionOpt = ConfigUtils.<Boolean>getGenericOption("enableFadingNightVision")
                    .binding(defaults.enableFadingNightVision,
                            () -> config.enableFadingNightVision,
                            newVal -> config.enableFadingNightVision = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Double> fadingOutDurationOpt = ConfigUtils.<Double>getGenericOption("fadingOutDuration")
                    .binding(defaults.fadingOutDuration,
                            () -> config.fadingOutDuration,
                            newVal -> config.fadingOutDuration = newVal)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                            .range(1.0, 5.0).step(0.5).formatValue(value -> Component.literal(value + "s")))
                    .build();

            // Better Ping Display
            Option<Boolean> enableBetterPingDisplayOpt = ConfigUtils.<Boolean>getGenericOption("enableBetterPingDisplay", "better_ping_display")
                    .binding(defaults.enableBetterPingDisplay,
                            () -> config.enableBetterPingDisplay,
                            newVal -> config.enableBetterPingDisplay = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableDefaultPingBarsOpt = ConfigUtils.<Boolean>getGenericOption("enableDefaultPingBars", "default_ping_bars")
                    .binding(defaults.enableDefaultPingBars,
                            () -> config.enableDefaultPingBars,
                            newVal -> config.enableDefaultPingBars = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            // Better Chat
            Option<Boolean> enableLongerChatHistoryOpt = ConfigUtils.<Boolean>getGenericOption("enableLongerChatHistory")
                    .binding(defaults.enableLongerChatHistory,
                            () -> config.enableLongerChatHistory,
                            newVal -> config.enableLongerChatHistory = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Integer> chatMaxMessagesOpt = ConfigUtils.<Integer>getGenericOption("chatMaxMessages")
                    .binding(defaults.chatMaxMessages,
                            () -> config.chatMaxMessages,
                            newVal -> config.chatMaxMessages = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(100, 32768))
                    .build();

            Option<Boolean> enableTimeStampOpt = ConfigUtils.<Boolean>getGenericOption("enableTimeStamp")
                    .binding(defaults.enableTimeStamp,
                            () -> config.enableTimeStamp,
                            newVal -> config.enableTimeStamp = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Color> timeStampColorOpt = ConfigUtils.<Color>getGenericOption("timeStampColor")
                    .binding(defaults.timeStampColor,
                            () -> config.timeStampColor,
                            newVal -> config.timeStampColor = newVal)
                    .controller(opt -> ColorControllerBuilder.create(opt).allowAlpha(false))
                    .build();

            // Biome Title
            Option<Boolean> enableBiomeTitleOpt = ConfigUtils.<Boolean>getGenericOption("enableBiomeTitle", "biome_title")
                    .binding(defaults.enableBiomeTitle,
                            () -> config.enableBiomeTitle,
                            newVal -> config.enableBiomeTitle = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> hideInF1Opt = ConfigUtils.<Boolean>getGenericOption("hideInF1")
                    .binding(defaults.hideInF1,
                            () -> config.hideInF1,
                            newVal -> config.hideInF1 = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> hideInF3Opt = ConfigUtils.<Boolean>getGenericOption("hideInF3")
                    .binding(defaults.hideInF3,
                            () -> config.hideInF3,
                            newVal -> config.hideInF3 = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Double> displayDurationOpt = ConfigUtils.<Double>getGenericOption("displayDuration")
                    .binding(defaults.displayDuration,
                            () -> config.displayDuration,
                            newVal -> config.displayDuration = newVal)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                            .range(1.0, 5.0).step(0.5).formatValue(value -> Component.literal(value + "s")))
                    .build();

            Option<Integer> fadeInTimeOpt = ConfigUtils.<Integer>getGenericOption("fadeInTime", DescComponent.TICK_EXPLANATION)
                    .binding(defaults.fadeInTime,
                            () -> config.fadeInTime,
                            newVal -> config.fadeInTime = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(0, 60).formatValue(value -> Component.literal(value + " ticks")))
                    .build();

            Option<Integer> fadeOutTimeOpt = ConfigUtils.<Integer>getGenericOption("fadeOutTime", DescComponent.TICK_EXPLANATION)
                    .binding(defaults.fadeOutTime,
                            () -> config.fadeOutTime,
                            newVal -> config.fadeOutTime = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(0, 60).formatValue(value -> Component.literal(value + " ticks")))
                    .build();

            Option<Double> scaleOpt = ConfigUtils.<Double>getGenericOption("scale")
                    .binding(defaults.scale,
                            () -> config.scale,
                            newVal -> config.scale = newVal)
                    .controller(opt -> DoubleFieldControllerBuilder.create(opt)
                            .range(0.3, 3.0))
                    .build();

            Option<Integer> yOffsetOpt = ConfigUtils.<Integer>getGenericOption("yOffset")
                    .binding(defaults.yOffset,
                            () -> config.yOffset,
                            newVal -> config.yOffset = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(-60, 60))
                    .build();

            Option<Color> colorOpt = ConfigUtils.<Color>getGenericOption("color")
                    .binding(defaults.color,
                            () -> config.color,
                            newVal -> config.color = newVal)
                    .controller(opt -> ColorControllerBuilder.create(opt).allowAlpha(false))
                    .build();

            Option<Double>  cooldownTimeOpt = ConfigUtils.<Double>getGenericOption("cooldownTime")
                    .binding(defaults.cooldownTime,
                            () -> config.cooldownTime,
                            newVal -> config.cooldownTime = newVal)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                            .range(0.0, 5.0).step(0.5).formatValue(value -> Component.literal(value + "s")))
                    .build();

            Option<Boolean> enableModNameOpt = ConfigUtils.<Boolean>getGenericOption("enableModName")
                    .binding(defaults.enableModName,
                            () -> config.enableModName,
                            newVal -> config.enableModName = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableUndergroundUpdateOpt = ConfigUtils.<Boolean>getGenericOption("enableUndergroundUpdate")
                    .binding(defaults.enableUndergroundUpdate,
                            () -> config.enableUndergroundUpdate,
                            newVal -> config.enableUndergroundUpdate = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            // Faster Climbing
            Option<Boolean> enableFasterClimbingOpt = ConfigUtils.<Boolean>getGenericOption("enableFasterClimbing")
                    .binding(defaults.enableFasterClimbing,
                            () -> config.enableFasterClimbing,
                            newVal -> config.enableFasterClimbing = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableFasterUpwardOpt = ConfigUtils.<Boolean>getGenericOption("enableFasterUpward")
                    .binding(defaults.enableFasterUpward,
                            () -> config.enableFasterUpward,
                            newVal -> config.enableFasterUpward = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableFasterDownwardOpt = ConfigUtils.<Boolean>getGenericOption("enableFasterDownward")
                    .binding(defaults.enableFasterDownward,
                            () -> config.enableFasterDownward,
                            newVal -> config.enableFasterDownward = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Double> speedMultiplierOpt = ConfigUtils.<Double>getGenericOption("speedMultiplier")
                    .binding(defaults.speedMultiplier,
                            () -> config.speedMultiplier,
                            newVal -> config.speedMultiplier = newVal)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                            .range(1.0, 10.0).step(0.5))
                    .build();

            // Other
            Option<Boolean> enableBeeInfoOpt = ConfigUtils.<Boolean>getGenericOption("enableBeeInfo", "bee_info")
                    .binding(defaults.enableBeeInfo,
                            () -> config.enableBeeInfo,
                            newVal -> config.enableBeeInfo = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();
            Option<Boolean> enableAxolotlBucketFixOpt = ConfigUtils.<Boolean>getGenericOption("enableAxolotlBucketFix", "axolotl_bucket")
                    .binding(defaults.enableAxolotlBucketFix,
                            () -> config.enableAxolotlBucketFix,
                            newVal -> config.enableAxolotlBucketFix = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableChatHistoryRetentionOpt = ConfigUtils.<Boolean>getGenericOption("enableChatHistoryRetention")
                    .binding(defaults.enableChatHistoryRetention,
                            () -> config.enableChatHistoryRetention,
                            newVal -> config.enableChatHistoryRetention = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();
            Option<Boolean> enableBookSaveConfirmationOpt = ConfigUtils.<Boolean>getGenericOption("enableBookSaveConfirmation")
                    .binding(defaults.enableBookSaveConfirmation,
                            () -> config.enableBookSaveConfirmation,
                            newVal -> config.enableBookSaveConfirmation = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(CLIENT_CATEGORY))
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, FADING_NIGHT_VISION_GROUP))
                                    .options(java.util.List.of(
                                            enableFadingNightVisionOpt,
                                            fadingOutDurationOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, BETTER_PING_DISPLAY_GROUP))
                                    .options(java.util.List.of(
                                            enableBetterPingDisplayOpt,
                                            enableDefaultPingBarsOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, BETTER_CHAT_GROUP))
                                    .options(java.util.List.of(
                                            enableLongerChatHistoryOpt,
                                            chatMaxMessagesOpt,
                                            enableTimeStampOpt,
                                            timeStampColorOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, BIOME_TITLE_GROUP))
                                    .options(java.util.List.of(
                                            enableBiomeTitleOpt,
                                            hideInF1Opt,
                                            hideInF3Opt,
                                            displayDurationOpt,
                                            fadeInTimeOpt,
                                            fadeOutTimeOpt,
                                            scaleOpt,
                                            yOffsetOpt,
                                            colorOpt,
                                            cooldownTimeOpt,
                                            enableModNameOpt,
                                            enableUndergroundUpdateOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, FASTER_CLIMBING_GROUP))
                                    .options(java.util.List.of(
                                            enableFasterClimbingOpt,
                                            enableFasterUpwardOpt,
                                            enableFasterDownwardOpt,
                                            speedMultiplierOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, OTHER_GROUP))
                                    .options(List.of(
                                            enableBeeInfoOpt,
                                            enableAxolotlBucketFixOpt,
                                            enableChatHistoryRetentionOpt,
                                            enableBookSaveConfirmationOpt
                                    ))
                                    .build())
                            .build())
                    .save(ClientConfig::save);
        });
    }
}

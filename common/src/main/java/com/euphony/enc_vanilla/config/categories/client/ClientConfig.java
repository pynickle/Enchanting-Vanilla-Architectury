package com.euphony.enc_vanilla.config.categories.client;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.utils.Utils;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.awt.*;
import java.nio.file.Path;

public class ClientConfig {
    public static ConfigClassHandler<ClientConfig> HANDLER = ConfigClassHandler.createBuilder(ClientConfig.class)
            .id(Utils.prefix("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setPath(Path.of("config", EncVanilla.MOD_ID + "/client.json")).build()
            )
            .build();

    public static void load() {
        HANDLER.load();
    }

    public static void save() {
        HANDLER.save();
    }

    public static final String CLIENT_CATEGORY = "client";
    public static final String FADING_NIGHT_VISION_GROUP = "fading_night_vision";
    public static final String BETTER_PING_DISPLAY_GROUP = "better_ping_display";
    public static final String BETTER_CHAT_GROUP = "better_chat";
    public static final String BIOME_TITLE_GROUP = "biome_title";
    public static final String FASTER_CLIMBING_GROUP = "faster_climbing";
    public static final String BOOK_SCROLL_GROUP = "book_scroll";
    public static final String MUSIC_PAUSE_GROUP = "music_pause";
    public static final String OTHER_GROUP = "other";

    @SerialEntry public boolean enableFadingNightVision = true;
    @SerialEntry public double fadingOutDuration = 3.0D;

    @SerialEntry public boolean enableBetterPingDisplay = true;
    @SerialEntry public boolean enableDefaultPingBars = false;

    @SerialEntry public boolean enableLongerChatHistory = true;
    @SerialEntry public int chatMaxMessages = 4096;
    @SerialEntry public boolean enableTimeStamp = true;
    @SerialEntry public Color timeStampColor = new Color(0xAA00AA, false);

    @SerialEntry public boolean enableFasterClimbing = true;
    @SerialEntry public boolean enableFasterUpward = true;
    @SerialEntry public boolean enableFasterDownward = true;
    @SerialEntry public double speedMultiplier = 2.0D;

    @SerialEntry public boolean enableBiomeTitle = true;
    @SerialEntry public boolean hideInF3 = true;
    @SerialEntry public boolean hideInF1 = true;
    @SerialEntry public double displayDuration = 1.5;
    @SerialEntry public int fadeInTime = 20;
    @SerialEntry public int fadeOutTime = 20;
    @SerialEntry public double scale = 2.1D;
    @SerialEntry public int yOffset = -10;
    @SerialEntry public Color color = new Color(0xffffff, false);
    @SerialEntry public double cooldownTime = 1.5D;
    @SerialEntry public boolean enableModName = false;
    @SerialEntry public boolean enableUndergroundUpdate = false;

    @SerialEntry public boolean enableBookScroll = true;
    @SerialEntry public int ctrlSpeedMultiplier = 5;
    @SerialEntry public boolean enablePageTurnSound = true;

    @SerialEntry public boolean enableMusicPause = true;
    @SerialEntry public boolean pauseUiSound = false;

    @SerialEntry public boolean enableBeeInfo = true;
    @SerialEntry public boolean enableAxolotlBucketFix = true;
    @SerialEntry public boolean enableChatHistoryRetention = true;
    @SerialEntry public boolean enableBookSaveConfirmation = true;
}

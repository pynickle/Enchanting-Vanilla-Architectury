package com.euphony.enc_vanilla.utils.config;

import com.euphony.enc_vanilla.EncVanilla;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import dev.architectury.platform.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoadConfigUtils {
    public static int loadConfigAsInt(String option, int defaultValue) {
        Path configPath = Platform.getConfigFolder().resolve(EncVanilla.MOD_ID).resolve("qol.json");

        if (Files.notExists(configPath)) {
            return defaultValue;
        }

        String json;
        try {
            json = Files.readString(configPath);
        } catch (IOException e) {
            return defaultValue;
        }

        JsonElement jsonTree;
        try {
            jsonTree = JsonParser.parseString(json);
        } catch (JsonParseException e) {
            return defaultValue;
        }
        try {
            return jsonTree.getAsJsonObject().get(option).getAsInt();
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }

    public static boolean loadConfigAsBoolean(String option, boolean defaultValue) {
        Path configPath = Platform.getConfigFolder().resolve(EncVanilla.MOD_ID).resolve("qol.json");

        if (Files.notExists(configPath)) {
            return defaultValue;
        }

        String json;
        try {
            json = Files.readString(configPath);
        } catch (IOException e) {
            return defaultValue;
        }

        JsonElement jsonTree;
        try {
            jsonTree = JsonParser.parseString(json);
        } catch (JsonParseException e) {
            return defaultValue;
        }

        try {
            return jsonTree.getAsJsonObject().get(option).getAsBoolean();
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }
}

package com.euphony.enc_vanilla.utils.config;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.utils.Utils;
import dev.isxander.yacl3.api.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.PackRepository;

import java.util.Collection;

public class ConfigUtils {
    public static final int IMG_WIDTH = 1920;
    public static final int IMG_HEIGHT = 991;

    public static final OptionFlag RESOURCE_RELOAD = (client) -> {
        if(client.hasSingleplayerServer()) {
            MinecraftServer server = client.getSingleplayerServer();
            if (server != null) {
                PackRepository packrepository = server.getPackRepository();
                Collection<String> collection = packrepository.getSelectedIds();
                server.reloadResources(collection);
            }
        }
    };

    public static ListOption.Builder<String> getListGroupOption(String name) {
        return ListOption.<String>createBuilder()
                .name(getOptionName(name))
                .description(OptionDescription.createBuilder()
                        .text(getDesc(name, null))
                        .build()
                );
    }

    public static ButtonOption.Builder getButtonOption(String name) {
        return ButtonOption.createBuilder()
                .name(getButtonOptionName(name))
                .description(OptionDescription.createBuilder()
                        .text(getDesc(name, null))
                        .build()
                );
    }

    public static <T> Option.Builder<T> getGenericOption(String name) {
        return getGenericOption(name, (DescComponent) null);
    }

    public static <T> Option.Builder<T> getGenericOption(String name, DescComponent descComponent) {
        return Option.<T>createBuilder()
                .name(getOptionName(name))
                .description(OptionDescription.createBuilder()
                        .text(getDesc(name, descComponent))
                        .build()
                );
    }

    public static <T> Option.Builder<T> getGenericOption(String name, String image) {
        return getGenericOption(name, image, null);
    }

    public static <T> Option.Builder<T> getGenericOption(String name, String image, DescComponent descComponent) {
        return Option.<T>createBuilder()
                .name(getOptionName(name))
                .description(OptionDescription.createBuilder()
                        .text(getDesc(name, descComponent))
                        .image(getImage(image), IMG_WIDTH, IMG_HEIGHT)
                        .build()
                );
    }

    public static Component getCategoryName(String category) {
        return Component.translatable(String.format("yacl3.config.%s:config.category.%s",  EncVanilla.MOD_ID, category));
    }

    public static Component getGroupName(String category, String group) {
        return Component.translatable(String.format("yacl3.config.%s:config.category.%s.group.%s", EncVanilla.MOD_ID, category, group));
    }

    private static Component getButtonOptionName(String option) {
        return Component.translatable(String.format("yacl3.config.%s:config.%s.button", EncVanilla.MOD_ID, option));
    }

    private static Component getOptionName(String option) {
        return Component.translatable(String.format("yacl3.config.%s:config.%s", EncVanilla.MOD_ID, option));
    }

    private static Component getDesc(String option, DescComponent descComponent) {
        MutableComponent component = Component.translatable(String.format("yacl3.config.%s:config.%s.desc", EncVanilla.MOD_ID, option));
        if (descComponent != null) component.append(Component.literal("\n\n").append(descComponent.getText()));
        return component;
    }

    private static ResourceLocation getImage(String name) {
        return Utils.prefix(String.format("config/%s.png", name));
    }
}

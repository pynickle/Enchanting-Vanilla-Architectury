package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import com.euphony.enc_vanilla.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.StringUtils;
import org.joml.Matrix3x2fStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BiomeTitleEvent {
    public static Biome previousBiome;
    public static ResourceKey<Biome> displayBiome;
    public static int displayTime = 0;
    public static int alpha = 0;
    public static int cooldownTime = 0;
    public static int fadeTimer = 0;
    public static boolean complete = false;
    public static boolean fadingIn = false;
    public static final Map<ResourceKey<Biome>, Component> NAME_CACHE = new HashMap<>();

    private BiomeTitleEvent() {}

    public static void clientPre(Minecraft minecraft) {
        var config = ClientConfig.HANDLER.instance();
        if (complete) {
            if (!fadingIn) {
                if (displayTime > 0) {
                    displayTime--;
                } else if (fadeTimer > 0) {
                    fadeTimer--;
                    alpha = (int) (255F / (float) config.fadeOutTime * fadeTimer);
                } else {
                    if (cooldownTime > 0) {
                        cooldownTime--;
                    }
                }
            } else {
                if(fadeTimer < config.fadeInTime) {
                    fadeTimer++;
                    alpha = (int) (255F / (float) config.fadeInTime * fadeTimer);
                } else {
                    fadeTimer = config.fadeOutTime;
                    fadingIn = false;
                    displayTime = (int) (config.displayDuration * 20);
                    alpha = 255;
                }
            }
        }
    }

    public static void renderBiomeInfo(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        var config = ClientConfig.HANDLER.instance();

        if (complete && config.enableBiomeTitle) {
            Minecraft mc = Minecraft.getInstance();

            if (hideInF1(mc, config) || hideInF3(mc, config)) return;

            Entity player = mc.getCameraEntity();

            if(player == null) return;

            BlockPos pos = player.getOnPos();

            if (mc.level != null && mc.level.isLoaded(pos)) {
                Holder<Biome> biomeHolder = mc.level.getBiome(pos);
                if (!biomeHolder.isBound()) return;

                Biome biome = biomeHolder.value();
                boolean isPlayerUnderground = mc.level.dimensionType().hasSkyLight() && !mc.level.canSeeSky(pos);
                boolean shouldUpdate = config.enableUndergroundUpdate || !isPlayerUnderground;

                if (previousBiome != biome) {
                    previousBiome = biome;
                    if(cooldownTime == 0 && shouldUpdate) {
                        biomeHolder.unwrapKey().ifPresent(key -> {
                            cooldownTime = (int) (config.cooldownTime * 20);
                            displayBiome = key;
                            displayTime = 0;
                            alpha = 0;
                            fadingIn = true;
                        });
                    }
                }
                if (alpha > 0) {
                    Font font = mc.font;
                    float scale = (float) config.scale;

                    Matrix3x2fStack pose = guiGraphics.pose();
                    pose.pushMatrix();
                    pose.translate(guiGraphics.guiWidth() / 2f, guiGraphics.guiHeight() / 2f);
                    pose.scale(scale, scale);
                    Component biomeName = getBiomeName(displayBiome, config);
                    int textWidth = font.width(biomeName);
                    int y = - font.wordWrapHeight(biomeName.getString(), 999) / 2 + config.yOffset;
                    guiGraphics.drawString(font, biomeName, (-textWidth / 2), y, 0xffffff | (alpha << 24), true);
                    pose.popMatrix();
                }
            }
        }
    }

    private static Component getBiomeName(ResourceKey<Biome> key, ClientConfig config) {
        ResourceLocation location = key.location();
        Component name = NAME_CACHE.computeIfAbsent(key, k -> {
            String translationKey = Util.makeDescriptionId("biome", location);
            MutableComponent biomeName = Component.translatable(translationKey);
            String displayedText = biomeName.getString();

            if (displayedText.equals(translationKey)) {
                String biomePath = key.location().getPath();
                String formattedBiomeName = snakeCaseToEnglish(biomePath);
                return Component.literal(formattedBiomeName);
            }
            return biomeName;
        });

        MutableComponent displayName = name.copy();

        if (config.enableModName) {
            String modName = getModName(location);
            if (modName != null)
                displayName = displayName.append(Component.literal(" (" + modName + ")"));
        }

        return displayName;
    }

    private static boolean hideInF1(Minecraft mc, ClientConfig config) {
        return mc.options.hideGui && config.hideInF1;
    }

    private static boolean hideInF3(Minecraft mc, ClientConfig config) {
        return mc.getDebugOverlay().showDebugScreen() && config.hideInF3;
    }

    private static String snakeCaseToEnglish(String biomePath) {
        return Arrays.stream(biomePath.split("_"))
                .map(StringUtils::capitalize)
                .reduce((a, b) -> a + " " + b).orElse("");
    }

    private static String getModName(ResourceLocation location) {
        String modId = location.getNamespace();

        String displayName = Utils.getModDisplayName(modId);

        return displayName == null ? snakeCaseToEnglish(modId) : displayName;
    }

    public static void clientLevelLoad(ClientLevel clientLevel) {
        complete = true;
    }
}
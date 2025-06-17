package com.euphony.enc_vanilla.fabric.client;

import com.euphony.enc_vanilla.EncVanillaClient;
import com.euphony.enc_vanilla.client.events.BiomeTitleEvent;
import com.euphony.enc_vanilla.client.property.AxolotlBucketVariant;
import com.euphony.enc_vanilla.client.property.FrogBucketActive;
import com.euphony.enc_vanilla.client.property.SculkCompassAngle;
import com.euphony.enc_vanilla.utils.Utils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import net.minecraft.server.packs.PackType;

@Environment(EnvType.CLIENT)
public final class EncVanillaFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EncVanillaClient.init();

        HudRenderCallback.EVENT.register(BiomeTitleEvent::renderBiomeInfo);
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new EVResourceReloadListener());

        SelectItemModelProperties.ID_MAPPER.put(Utils.prefix("variant"), AxolotlBucketVariant.TYPE);
        SelectItemModelProperties.ID_MAPPER.put(Utils.prefix("active"), FrogBucketActive.TYPE);
        RangeSelectItemModelProperties.ID_MAPPER.put(Utils.prefix("angle"), SculkCompassAngle.MAP_CODEC);
    }
}

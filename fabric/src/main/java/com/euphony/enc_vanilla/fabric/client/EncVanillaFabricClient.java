package com.euphony.enc_vanilla.fabric.client;

import com.euphony.enc_vanilla.EncVanillaClient;
import com.euphony.enc_vanilla.client.property.FrogBucketActive;
import com.euphony.enc_vanilla.client.property.SculkCompassAngle;
import com.euphony.enc_vanilla.utils.Utils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;

@Environment(EnvType.CLIENT)
public final class EncVanillaFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EncVanillaClient.init();

        SelectItemModelProperties.ID_MAPPER.put(Utils.prefix("active"), FrogBucketActive.TYPE);
        RangeSelectItemModelProperties.ID_MAPPER.put(Utils.prefix("angle"), SculkCompassAngle.MAP_CODEC);
    }
}

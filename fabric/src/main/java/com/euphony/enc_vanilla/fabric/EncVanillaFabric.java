package com.euphony.enc_vanilla.fabric;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.fabric.loot.LootModifiers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

public final class EncVanillaFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EncVanilla.init();
        LootTableEvents.MODIFY.register((key, builder, source, registries) -> LootModifiers.onLootTableLoad(key, builder, source));
    }
}

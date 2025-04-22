package com.euphony.enc_vanilla.neoforge;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.EncVanillaClient;
import com.euphony.enc_vanilla.config.client.EVConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = EncVanilla.MOD_ID, dist = Dist.CLIENT)
public class ENCVanillaNeoforgeClient {
    public ENCVanillaNeoforgeClient(IEventBus bus) {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, screen) -> new EVConfigScreen(screen));
        EncVanillaClient.init();
    }
}

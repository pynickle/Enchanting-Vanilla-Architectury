package com.euphony.enc_vanilla.neoforge.client;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.client.events.BiomeTitleEvent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = EncVanilla.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EVClientNeoforge {
    @SubscribeEvent
    public static void onResourceManagerReload(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((barrier, manager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor) -> CompletableFuture.runAsync(BiomeTitleEvent.NAME_CACHE::clear, backgroundExecutor).thenCompose(barrier::wait));
    }

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.TITLE, ResourceLocation.fromNamespaceAndPath(EncVanilla.MOD_ID, "overlay"), BiomeTitleEvent::renderBiomeInfo);
    }
}

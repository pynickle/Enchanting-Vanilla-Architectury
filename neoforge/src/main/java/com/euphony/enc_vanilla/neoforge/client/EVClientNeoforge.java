package com.euphony.enc_vanilla.neoforge.client;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.client.events.BiomeTitleEvent;
import com.euphony.enc_vanilla.common.init.EVMenus;
import com.euphony.enc_vanilla.screen.AppraisalTableMenu;
import com.euphony.enc_vanilla.screen.AppraisalTableScreen;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SmithingTableBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
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

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.<AppraisalTableMenu, AppraisalTableScreen>register(EVMenus.APPRAISAL_MENU.get(), AppraisalTableScreen::new);
    }
}

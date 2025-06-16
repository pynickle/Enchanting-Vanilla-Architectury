package com.euphony.enc_vanilla.neoforge.client;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.client.events.BiomeTitleEvent;
import com.euphony.enc_vanilla.client.property.AxolotlBucketVariant;
import com.euphony.enc_vanilla.client.property.FrogBucketActive;
import com.euphony.enc_vanilla.client.property.SculkCompassAngle;
import com.euphony.enc_vanilla.common.init.EVMenus;
import com.euphony.enc_vanilla.screen.AppraisalTableMenu;
import com.euphony.enc_vanilla.screen.AppraisalTableScreen;
import com.euphony.enc_vanilla.utils.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@EventBusSubscriber(modid = EncVanilla.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EVClientNeoforge {
    @SubscribeEvent
    public static void onResourceManagerReload(AddClientReloadListenersEvent event) {
        event.addListener(Utils.prefix("clear_name_cache"), (PreparableReloadListener.PreparationBarrier barrier, ResourceManager manager, Executor backgroundExecutor, Executor gameExecuter) -> CompletableFuture.runAsync(BiomeTitleEvent.NAME_CACHE::clear, backgroundExecutor).thenCompose(barrier::wait));
    }

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.TITLE, ResourceLocation.fromNamespaceAndPath(EncVanilla.MOD_ID, "overlay"), BiomeTitleEvent::renderBiomeInfo);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.<AppraisalTableMenu, AppraisalTableScreen>register(EVMenus.APPRAISAL_MENU.get(), AppraisalTableScreen::new);
    }

    @SubscribeEvent
    public static void registerSelectItemModelProperty(RegisterSelectItemModelPropertyEvent event) {
        event.register(Utils.prefix("variant"), AxolotlBucketVariant.TYPE);
        event.register(Utils.prefix("active"), FrogBucketActive.TYPE);
    }

    @SubscribeEvent
    public static void register(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(Utils.prefix("angle"), SculkCompassAngle.MAP_CODEC);
    }
}

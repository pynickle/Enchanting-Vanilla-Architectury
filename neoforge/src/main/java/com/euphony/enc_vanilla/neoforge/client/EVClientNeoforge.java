package com.euphony.enc_vanilla.neoforge.client;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.client.property.FrogBucketActive;
import com.euphony.enc_vanilla.client.property.SculkCompassAngle;
import com.euphony.enc_vanilla.common.init.EVMenus;
import com.euphony.enc_vanilla.screen.AppraisalTableMenu;
import com.euphony.enc_vanilla.screen.AppraisalTableScreen;
import com.euphony.enc_vanilla.utils.Utils;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@EventBusSubscriber(modid = EncVanilla.MOD_ID, value = Dist.CLIENT)
public class EVClientNeoforge {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.<AppraisalTableMenu, AppraisalTableScreen>register(EVMenus.APPRAISAL_MENU.get(), AppraisalTableScreen::new);
    }

    @SubscribeEvent
    public static void registerSelectItemModelProperty(RegisterSelectItemModelPropertyEvent event) {
        event.register(Utils.prefix("active"), FrogBucketActive.TYPE);
    }

    @SubscribeEvent
    public static void register(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(Utils.prefix("angle"), SculkCompassAngle.MAP_CODEC);
    }
}

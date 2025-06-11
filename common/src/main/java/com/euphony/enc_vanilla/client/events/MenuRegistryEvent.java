package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.common.init.EVMenus;
import com.euphony.enc_vanilla.screen.AppraisalTableMenu;
import com.euphony.enc_vanilla.screen.AppraisalTableScreen;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.Minecraft;

public class MenuRegistryEvent {
    public static void registerMenu(Minecraft minecraft) {
        if(Platform.isFabric()) {
            MenuRegistry.registerScreenFactory(EVMenus.APPRAISAL_MENU.get(), (MenuRegistry.ScreenFactory<AppraisalTableMenu, AppraisalTableScreen>) AppraisalTableScreen::new);
        }
    }
}

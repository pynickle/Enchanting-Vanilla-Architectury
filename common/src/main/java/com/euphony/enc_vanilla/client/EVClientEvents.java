package com.euphony.enc_vanilla.client;

import com.euphony.enc_vanilla.client.events.*;
import dev.architectury.event.events.client.ClientChatEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.event.events.common.TickEvent;

public class EVClientEvents {
    public static void init() {
        ClientTooltipEvent.ITEM.register(TooltipEvent::item);
        ClientTooltipEvent.ITEM.register(BeeInfoEvent::item);

        ClientTickEvent.CLIENT_PRE.register(BiomeTitleEvent::clientPre);
        ClientTickEvent.CLIENT_POST.register(KeyMappingEvent::clientPost);

        ClientChatEvent.RECEIVED.register(BeautifiedChatEvent::chatReceived);

        ClientLifecycleEvent.CLIENT_LEVEL_LOAD.register(BiomeTitleEvent::clientLevelLoad);
        ClientLifecycleEvent.CLIENT_SETUP.register(ItemPropertiesEvent::clientSetup);
        ClientLifecycleEvent.CLIENT_SETUP.register(RegisterColorEvent::registerColor);
        ClientLifecycleEvent.CLIENT_SETUP.register(CompostEvent::registerCompostable);
        ClientLifecycleEvent.CLIENT_SETUP.register(RenderTypeEvent::registerRenderType);
        ClientLifecycleEvent.CLIENT_SETUP.register(MenuRegistryEvent::registerMenu);

        TickEvent.PLAYER_PRE.register(FasterClimbingEvent::playerPre);
    }
}

package com.euphony.enc_vanilla.client;

import com.euphony.enc_vanilla.client.events.*;
import dev.architectury.event.events.client.*;
import dev.architectury.event.events.common.TickEvent;

public class EVClientEvents {
    public static void init() {
        ClientTooltipEvent.ITEM.register(TooltipEvent::item);
        ClientTooltipEvent.ITEM.register(BeeInfoEvent::item);

        ClientTickEvent.CLIENT_LEVEL_PRE.register(BiomeTitleEvent::clientPre);

        ClientChatEvent.RECEIVED.register(BeautifiedChatEvent::chatReceived);

        ClientGuiEvent.RENDER_PRE.register(BiomeTitleEvent::renderPre);

        ClientLifecycleEvent.CLIENT_LEVEL_LOAD.register(BiomeTitleEvent::clientLevelLoad);
        ClientLifecycleEvent.CLIENT_SETUP.register(ItemPropertiesEvent::clientSetup);
        ClientLifecycleEvent.CLIENT_SETUP.register(RegisterColorEvent::registerColor);
        ClientLifecycleEvent.CLIENT_SETUP.register(CompostEvent::registerCompostable);
        ClientLifecycleEvent.CLIENT_SETUP.register(RenderTypeEvent::registerRenderType);

        TickEvent.PLAYER_PRE.register(FasterClimbingEvent::playerPre);
    }
}

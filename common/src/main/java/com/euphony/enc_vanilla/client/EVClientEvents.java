package com.euphony.enc_vanilla.client;

import com.euphony.enc_vanilla.client.events.*;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;

public class EVClientEvents {
    public static void init() {
        ClientTooltipEvent.ITEM.register(TooltipEvent::item);

        ClientTickEvent.CLIENT_POST.register(KeyMappingEvent::clientPost);

        ClientLifecycleEvent.CLIENT_SETUP.register(RegisterColorEvent::registerColor);
        ClientLifecycleEvent.CLIENT_SETUP.register(CompostEvent::registerCompostable);
        ClientLifecycleEvent.CLIENT_SETUP.register(RenderTypeEvent::registerRenderType);
        ClientLifecycleEvent.CLIENT_SETUP.register(MenuRegistryEvent::registerMenu);
    }
}

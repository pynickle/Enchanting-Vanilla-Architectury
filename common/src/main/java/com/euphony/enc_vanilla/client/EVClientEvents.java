package com.euphony.enc_vanilla.client;

import com.euphony.enc_vanilla.client.events.*;
import dev.architectury.event.events.client.*;
import dev.architectury.event.events.common.TickEvent;

public class EVClientEvents {
    static {
        ClientTooltipEvent.RENDER_PRE.register(TooltipEvent::renderPre);
        ClientTooltipEvent.RENDER_PRE.register(BeeInfoEvent::renderPre);

        ClientTickEvent.CLIENT_LEVEL_PRE.register(BiomeTitleEvent::clientPre);

        ClientChatEvent.RECEIVED.register(BeautifiedChatEvent::chatReceived);

        ClientGuiEvent.RENDER_PRE.register(BiomeTitleEvent::renderPre);

        ClientLifecycleEvent.CLIENT_LEVEL_LOAD.register(BiomeTitleEvent::clientLevelLoad);
        ClientLifecycleEvent.CLIENT_SETUP.register(ItemPropertiesEvent::clientSetup);

        ClientRecipeUpdateEvent.EVENT.register(RecipeUpdateEvent::recipeUpdate);

        TickEvent.PLAYER_PRE.register(FasterClimbingEvent::playerPre);
    }
}

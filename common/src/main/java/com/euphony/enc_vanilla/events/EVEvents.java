package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.events.events.*;
import dev.architectury.event.events.common.*;

public class EVEvents {
    public static void init() {
        InteractionEvent.RIGHT_CLICK_BLOCK.register(AnvilRepairEvent::rightClickBlock);
        InteractionEvent.RIGHT_CLICK_BLOCK.register(BiomeCrystalEvent::rightClickBlock);
        InteractionEvent.RIGHT_CLICK_BLOCK.register(CeilingTorchEvent::rightClickBlock);
        InteractionEvent.RIGHT_CLICK_BLOCK.register(CutVineEvent::rightClickBlock);
        InteractionEvent.RIGHT_CLICK_BLOCK.register(StopGrowingEvent::rightClickBlock);
        // InteractionEvent.RIGHT_CLICK_BLOCK.register(SwitchPaintingEvent::rightClickBlock);
        InteractionEvent.RIGHT_CLICK_BLOCK.register(BellPhantomEvent::rightClickBlock);
        InteractionEvent.RIGHT_CLICK_BLOCK.register(RightClickHarvestEvent::rightClickBlock);
        InteractionEvent.RIGHT_CLICK_BLOCK.register(DoubleDoorEvent::rightClickBlock);
        InteractionEvent.RIGHT_CLICK_BLOCK.register(StickyPistonEvent::rightClickBlock);

        InteractionEvent.LEFT_CLICK_BLOCK.register(SafeHarvestEvent::leftClickBlock);

        InteractionEvent.INTERACT_ENTITY.register(FrogBucketEvent::interactEntity);
        InteractionEvent.INTERACT_ENTITY.register(PlaceChestOnBoatEvent::interactEntity);
        InteractionEvent.INTERACT_ENTITY.register(SwitchPaintingEvent::interactEntity);

        InteractionEvent.RIGHT_CLICK_ITEM.register(SpongePlaceEvent::rightClickItem);
        InteractionEvent.RIGHT_CLICK_ITEM.register(ThrowableFireChargeEvent::rightClickItem);

        BlockEvent.BREAK.register(CakeDropEvent::blockBreak);

        PlayerEvent.DROP_ITEM.register(ConcreteConversionEvent::dropItem);

        EntityEvent.LIVING_HURT.register(TorchHitEvent::livingHurt);

        TickEvent.SERVER_LEVEL_PRE.register(ConcreteConversionEvent::serverPre);
        TickEvent.PLAYER_POST.register(HealingCampfireEvent::playerPost);
    }
}

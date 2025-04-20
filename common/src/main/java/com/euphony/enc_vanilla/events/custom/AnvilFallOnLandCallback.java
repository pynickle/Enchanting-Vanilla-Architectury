package com.euphony.enc_vanilla.events.custom;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface AnvilFallOnLandCallback {
    Event<AnvilFallOnLandCallback> EVENT = EventFactory.createEventResult(AnvilFallOnLandCallback.class);

    EventResult anvilFallOnLand(Level level, BlockPos pos, FallingBlockEntity entity, float fallDistance);
}

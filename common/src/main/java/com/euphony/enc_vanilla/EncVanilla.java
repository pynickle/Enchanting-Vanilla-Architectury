package com.euphony.enc_vanilla;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.common.init.EVCreativeTabs;
import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.config.EVConfig;
import com.euphony.enc_vanilla.events.EVEvents;
import com.euphony.enc_vanilla.events.custom.AnvilFallOnLandCallback;
import com.euphony.enc_vanilla.events.events.CompressedSlimeBlockEvent;
import com.euphony.enc_vanilla.utils.BlockEntityMap;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class EncVanilla {
    public static final String MOD_ID = "enc_vanilla";

    private static final EVConfig CONFIG = new EVConfig();

    public static EVConfig getConfig() {
        return CONFIG;
    }

    public static void init() {
        EncVanilla.getConfig().load();

        BlockEntityMap.addBlockEntity(BlockEntityType.CAMPFIRE);

        EVDataComponentTypes.DATA_COMPONENTS.register();

        EVBlocks.BLOCKS.register();
        EVItems.ITEMS.register();
        EVCreativeTabs.TABS.register();

        EVEvents.init();

        AnvilFallOnLandCallback.EVENT.register(new CompressedSlimeBlockEvent());
    }
}

package com.euphony.enc_vanilla.common.item;

import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import net.minecraft.world.item.Item;

public class BiomeCrystalItem extends Item {
    public BiomeCrystalItem(Properties properties) {
        super(properties.stacksTo(1).fireResistant()
                .component(EVDataComponentTypes.LOCKED.get(), false)
                .component(EVDataComponentTypes.TEMP_UNLOCKED.get(), false));
    }
}

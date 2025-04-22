package com.euphony.enc_vanilla.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class EVKeyMappings {
    public static final KeyMapping SAFE_HARVEST = new KeyMapping(
            "key.enc_vanilla.safe_harvest",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_H,
            "category.enc_vanilla.keymapping"
    );
}

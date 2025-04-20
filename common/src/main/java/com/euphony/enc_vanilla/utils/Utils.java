package com.euphony.enc_vanilla.utils;

import com.euphony.enc_vanilla.EncVanilla;
import dev.architectury.platform.Platform;
import java.util.Locale;
import net.minecraft.resources.ResourceLocation;

public class Utils {
    public static String getModDisplayName(String modId) {
        return Platform.getMod(modId).getName();
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(EncVanilla.MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}

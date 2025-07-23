package com.euphony.enc_vanilla.neoforge;

import com.euphony.enc_vanilla.EncVanilla;
import net.minecraft.world.entity.npc.Villager;
import net.neoforged.fml.common.Mod;

@Mod(EncVanilla.MOD_ID)
public final class EncVanillaNeoforge {
    public EncVanillaNeoforge() {
        EncVanilla.init();
    }
}

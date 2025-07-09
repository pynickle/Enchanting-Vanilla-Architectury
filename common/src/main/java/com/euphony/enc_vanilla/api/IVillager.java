package com.euphony.enc_vanilla.api;

import com.euphony.enc_vanilla.utils.LockedTradeData;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.Optional;

public interface IVillager {

    static IVillager of(Villager villager) {
        return (IVillager) villager;
    }

    Optional<LockedTradeData> enc_vanilla$getLockedTradeData();

    MerchantOffers enc_vanilla$getCombinedOffers();

    int enc_vanilla$getShiftedLevel();
}

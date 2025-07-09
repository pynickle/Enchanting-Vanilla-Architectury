package com.euphony.enc_vanilla.api;

import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Unique;

public interface IClientSideMerchant {
    @Unique
    MerchantOffers enc_vanilla$getClientUnlockedTrades();

    @Unique
    void enc_vanilla$setClientUnlockedTrades(MerchantOffers offers);

}

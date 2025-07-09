package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.api.IClientSideMerchant;
import net.minecraft.world.entity.npc.ClientSideMerchant;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientSideMerchant.class)
public class ClientSideMerchantMixin implements IClientSideMerchant {
    @Unique
    private MerchantOffers enc_vanilla$clientUnlockedTrades = null;


    @Unique
    @Override
    public MerchantOffers enc_vanilla$getClientUnlockedTrades() {
        return enc_vanilla$clientUnlockedTrades;
    }

    @Unique
    @Override
    public void enc_vanilla$setClientUnlockedTrades(MerchantOffers offers) {
        this.enc_vanilla$clientUnlockedTrades = offers;
    }
}

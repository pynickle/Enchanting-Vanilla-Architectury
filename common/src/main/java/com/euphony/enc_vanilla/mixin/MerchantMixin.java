package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.api.IVillager;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Merchant.class)
public interface MerchantMixin {

    @WrapOperation(method = "openTradingScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;sendMerchantOffers(ILnet/minecraft/world/item/trading/MerchantOffers;IIZZ)V"))
    private void sendLockedOffersWithNormalOffersOnScreenOpen(Player instance, int syncId, MerchantOffers merchantOffers, int j, int k, boolean bl, boolean bl2, Operation<Void> original) {
        if(!(((Merchant)this) instanceof Villager villager) || !QolConfig.HANDLER.instance().enableVisibleTrade) original.call(instance, syncId, merchantOffers, j, k, bl, bl2);
        else {
            IVillager duck = IVillager.of(villager);
            original.call(instance, syncId, duck.enc_vanilla$getCombinedOffers(), duck.enc_vanilla$getShiftedLevel(), k, bl, bl2);
        }
    }

}
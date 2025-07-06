package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.common.tag.EVItemTags;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.euphony.enc_vanilla.utils.ItemUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceMenu.class)
public abstract class AbstractFurnaceMenuMixin extends RecipeBookMenu<SingleRecipeInput, AbstractCookingRecipe> {
    @Shadow protected abstract boolean isFuel(ItemStack arg);

    public AbstractFurnaceMenuMixin(MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Inject(
            method = "quickMoveStack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"
            ),
            cancellable = true)
    private void onBeforeCanSmelt(
            Player player, int i, CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 0) ItemStack itemStack2,
            @Local(ordinal = 0, argsOnly = true) int index
    ) {
        if(!QolConfig.HANDLER.instance().enableForcedFuels) return;

        if(index != 1 && index != 0 && index != 2) {
            if (isFuel(itemStack2) && (itemStack2.is(EVItemTags.FORCED_FUELS)
                    || QolConfig.HANDLER.instance().extraForcedFuels.contains(ItemUtils.getKey(itemStack2.getItem()).toString()))) {
                if (!this.moveItemStackTo(itemStack2, 1, 2, false)) {
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            }
        }
    }
}
package com.euphony.enc_vanilla.screen.slot;

import com.euphony.enc_vanilla.screen.AppraisalTableMenu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AppraisalCrystalSlot extends Slot {
    private final AppraisalTableMenu menu;

    public AppraisalCrystalSlot(AppraisalTableMenu arg, Container arg2, int i, int j, int k) {
        super(arg2, i, j, k);
        this.menu = arg;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return this.menu.isBiomeCrystal(itemStack);
    }
}

package com.euphony.enc_vanilla.screen;

import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import com.euphony.enc_vanilla.common.init.EVMenus;
import com.euphony.enc_vanilla.common.init.EVTags;
import com.euphony.enc_vanilla.screen.slot.AppraisalCrystalSlot;
import com.euphony.enc_vanilla.screen.slot.AppraisalRefreshSlot;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class AppraisalTableMenu extends AbstractContainerMenu {
    private final Level level;
    private final Container container;
    private final ContainerData data;

    public AppraisalTableMenu(int i, Inventory inventory) {
        this(i, inventory, new SimpleContainer(3), new SimpleContainerData(3));
    }

    public AppraisalTableMenu(int i, Inventory inventory, Container container, ContainerData containerData) {
        super(EVMenus.APPRAISAL_MENU.get(), i);
        this.level = inventory.player.level();
        this.container = container;
        this.data = containerData;
        this.addSlot(new AppraisalCrystalSlot(this, container, 0, 27, 35));
        this.addSlot(new AppraisalRefreshSlot(this, container, 1, 68, 20));
        this.addSlot(new Slot(container, 2, 133, 35) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack itemStack) {
                this.container.setItem(0, ItemStack.EMPTY);
            }
        });
        for(int j = 0; j < 3; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
        }
        this.addDataSlots(this.data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (i == 2) {
                if (!this.moveItemStackTo(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack2, itemStack);
            } else if (i != 1 && i != 0) {
                if (this.isBiomeCrystal(itemStack2)) {
                    if (!this.moveItemStackTo(itemStack2, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isRefreshMaterial(itemStack2)) {
                    if (!this.moveItemStackTo(itemStack2, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (i >= 3 && i < 30) {
                    if (!this.moveItemStackTo(itemStack2, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (i >= 30 && i < 39 && !this.moveItemStackTo(itemStack2, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
        }

        return itemStack;
    }

    public boolean isBiomeCrystal(ItemStack itemStack) {
        return itemStack.is(EVTags.BIOME_CRYSTAL);
    }

    public boolean isRefreshMaterial(ItemStack itemStack) {
        return itemStack.is(Items.DIAMOND);
    }

    @Override
    public boolean clickMenuButton(Player player, int i) {
        this.setItem(2, 2, ItemStack.EMPTY);

        ItemStack stack = this.container.getItem(1);
        stack.setCount(stack.getCount() - 1);
        this.setItem(1, 1, stack);

        ItemStack stack1 = this.container.getItem(0);
        stack1.set(EVDataComponentTypes.TEMP_UNLOCKED.get(), true);
        this.setItem(0, 0, stack1);
        return super.clickMenuButton(player, i);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    public boolean isInProgress() {
        return this.data.get(0) > 0;
    }

    public float getProgress() {
        return Mth.clamp(this.data.get(0) / 80.0F, 0.0F, 1.0F);
    }

    public boolean getIsActive() {
        return this.data.get(1) != 0;
    }

    public boolean getIsError() {
        return this.data.get(2) != 0;
    }
}

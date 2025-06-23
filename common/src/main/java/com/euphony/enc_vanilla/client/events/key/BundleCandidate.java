package com.euphony.enc_vanilla.client.events.key;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BundleCandidate implements Comparable<BundleCandidate> {
    private final Slot slot;
    private final int index;
    private final ItemStack itemStack;
    private final double efficiency;

    public BundleCandidate(Slot slot, int index, ItemStack itemStack, double efficiency) {
        this.slot = slot;
        this.index = index;
        this.itemStack = itemStack;
        this.efficiency = efficiency;
    }

    public Slot getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getIndex() {
        return index;
    }

    public double getEfficiency() {
        return efficiency;
    }

    @Override
    public int compareTo(BundleCandidate other) {
        return Double.compare(other.efficiency, this.efficiency);
    }
}
package com.euphony.enc_vanilla.client.events.key;

import com.euphony.enc_vanilla.keymapping.EVKeyMappings;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import dev.architectury.platform.Platform;
import it.unimi.dsi.fastutil.Hash;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;

import java.util.*;
import java.util.logging.Logger;

@Environment(EnvType.CLIENT)
public class BundleUpKeyMappingEvent {
    public static void bundleUp(Screen screen, int keyCode, int scanCode, int modifiers) {
        if(!EVKeyMappings.BUNDLE_UP.matches(keyCode, scanCode)) return;

        List<BundleCandidate> candidates = new ArrayList<>();

        Minecraft minecraft = Minecraft.getInstance();

        Player player = minecraft.player;
        if(player == null || screen == null) return;

        if(screen instanceof AbstractContainerScreen<?> containerScreen) {
            if(containerScreen.hoveredSlot == null) return;

            Slot hoveredSlot = containerScreen.hoveredSlot;
            ItemStack selectedItem = hoveredSlot.getItem();
            if(!selectedItem.is(ItemTags.BUNDLES)) return;

            if (containerScreen.getMenu() instanceof ChestMenu chestMenu) {
                for(int i = 0; i < chestMenu.slots.size(); i++) {
                    Slot slot = chestMenu.getSlot(i);

                    ItemStack stack = slot.getItem();
                    if (stack.isEmpty()) return;

                    Container container = slot.container;
                    Logger.getLogger("s").info(stack.toString());
                    if(container instanceof ChestBlockEntity || container instanceof EnderChestBlockEntity) {
                        Logger.getLogger("s").info("1");
                        double efficiency = (double) stack.getMaxStackSize() / stack.getCount();
                        candidates.add(new BundleCandidate(slot, i, stack, efficiency));
                    } else if(container instanceof Inventory) {
                        Logger.getLogger("s").info("2");
                        if(slot.slot >= 9) {
                            double efficiency = (double) stack.getMaxStackSize() / stack.getCount();
                            candidates.add(new BundleCandidate(slot, i, stack, efficiency));
                        }
                    }
                }
            } else if(containerScreen instanceof InventoryScreen
                    || containerScreen instanceof CreativeModeInventoryScreen) {
                var menu = containerScreen.getMenu();
                for(int i = 0; i < menu.slots.size(); i++) {
                    Slot slot = menu.getSlot(i);
                    ItemStack stack = slot.getItem();
                    if (stack.isEmpty()) return;

                    Container container = slot.container;
                    if(container instanceof Inventory) {
                        if(slot.slot >= 9) {
                            double efficiency = (double) stack.getMaxStackSize() / stack.getCount();
                            candidates.add(new BundleCandidate(slot, i, stack, efficiency));
                        }
                    }
                }
            }
            candidates.sort(null);

            BundleItem bundleItem = (BundleItem) selectedItem.getItem();
            // screen.mouseClicked(hoveredSlot.x, hoveredSlot.y, 0);
            for(BundleCandidate candidate : candidates) {
                // screen.mouseClicked(candidate.getSlot().x, candidate.getSlot().y, 1);
                performSlotSwap(containerScreen.getMenu(), minecraft.gameMode, containerScreen.getMenu().containerId, containerScreen.getMenu().slots.indexOf(hoveredSlot), candidate.getIndex(), player);
                // bundleItem.overrideStackedOnOther(selectedItem, candidate.getSlot(), ClickAction.PRIMARY, player);
            }
        }

        /*
        if (screen instanceof AbstractContainerScreen<?> abstractContainerScreen) {
            Inventory inventory = player.getInventory();

            if(abstractContainerScreen.hoveredSlot == null) return;

            ItemStack selectedItem = abstractContainerScreen.hoveredSlot.getItem();
            if(!selectedItem.is(ItemTags.BUNDLES)) return;

            for(int slot = 9; slot < inventory.items.size(); slot++) {
                ItemStack stack = inventory.getItem(slot);
                if (stack.isEmpty()) continue;

                double efficiency = (double) stack.getMaxStackSize() / stack.getCount();
                candidates.add(new BundleCandidate(slot, stack, efficiency));
            }

            candidates.sort(null);

            BundleItem bundleItem = (BundleItem) selectedItem.getItem();
            for(BundleCandidate candidate : candidates) {
                if(screen instanceof CreativeModeInventoryScreen
                        || screen instanceof InventoryScreen) {
                    Slot slot = abstractContainerScreen.getMenu().getSlot(candidate.getSlot());
                    bundleItem.overrideStackedOnOther(selectedItem, slot, ClickAction.PRIMARY, player);
                } else if(abstractContainerScreen instanceof ContainerScreen containerScreen) {
                    int size = containerScreen.getMenu().slots.size();
                    Logger.getLogger("a").info(String.valueOf(size));
                    Slot slot = abstractContainerScreen.getMenu().getSlot(candidate.getSlot() + (size - inventory.items.size()));
                    bundleItem.overrideStackedOnOther(selectedItem, slot, ClickAction.PRIMARY, player);
                }
            }

        }

         */
    }

    public static void performSlotSwap(AbstractContainerMenu menu, MultiPlayerGameMode gameMode, int containerId, int bundleSlot, int targetSlot, Player player) {
        // 使用正确的点击类型来处理捆绑袋的交互
        // 1. 右键点击 (QUICK_MOVE) 捆绑袋，将物品拿到手上
        gameMode.handleInventoryMouseClick(containerId, bundleSlot, 1, ClickType.PICKUP, player);

        // 2. 左键点击目标物品，将其放入捆绑袋
        gameMode.handleInventoryMouseClick(containerId, targetSlot, 0, ClickType.PICKUP, player);

        // 3. 如果手上还有物品（捆绑袋没有完全吸收），则放回原处
        if (!player.containerMenu.getCarried().isEmpty()) {
            gameMode.handleInventoryMouseClick(containerId, bundleSlot, 0, ClickType.PICKUP, player);
        }
    }

    private static void pickUpItem(AbstractContainerMenu menu, MultiPlayerGameMode gameMode, int containerId, int slot, Player player) {
        var cursorStack = player.containerMenu.getCarried();
        var itemSlot = menu.getSlot(slot);
        var mouseButton = 0;

        gameMode.handleInventoryMouseClick(containerId, slot, mouseButton, ClickType.PICKUP, player);
    }
}

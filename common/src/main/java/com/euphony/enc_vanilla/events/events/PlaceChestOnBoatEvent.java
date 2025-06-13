package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import dev.architectury.event.EventResult;
import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tschipp.carryon.common.carry.CarryOnData;
import tschipp.carryon.common.carry.CarryOnDataManager;

public class PlaceChestOnBoatEvent {
    public static EventResult interactEntity(Player player, Entity entity, InteractionHand interactionHand) {
        if (!QolConfig.HANDLER.instance().enablePlaceChestOnBoat) return EventResult.pass();

        Level level = player.level();
        if (level.isClientSide) return EventResult.pass();

        BlockPos pos = entity.getOnPos();
        ItemStack stack = player.getItemInHand(interactionHand);

        if (!(entity instanceof Boat boat)) return EventResult.pass();

        if (player.isShiftKeyDown() && stack.is(Items.CHEST)) {
            ChestBoat newBoat = createChestBoatFromBoat(level, pos, boat);
            boat.discard();
            stack.consume(1, player);
            level.addFreshEntity(newBoat);
            return EventResult.interruptTrue();
        }

        if (!player.isShiftKeyDown() && Platform.isModLoaded("carryon")) {
            CarryOnData carry = CarryOnDataManager.getCarryData(player);
            if (carry.isCarrying() && carry.isCarrying(CarryOnData.CarryType.BLOCK)) {
                BlockState state = carry.getBlock();
                if (state.is(Blocks.CHEST)) {
                    ChestBoat newBoat = createChestBoatFromBoat(level, pos, boat);
                    ChestBlockEntity blockEntity = (ChestBlockEntity) carry.getBlockEntity(pos, level.registryAccess());
                    if (blockEntity == null) return EventResult.pass();
                    for (int i = 0; i < blockEntity.getContainerSize(); i++) {
                        ItemStack itemStack = blockEntity.getItem(i);
                        if (!itemStack.isEmpty()) {
                            newBoat.setItem(i, itemStack);
                        }
                    }
                    boat.discard();
                    carry.clear();
                    CarryOnDataManager.setCarryData(player, carry);
                    player.playSound(state.getSoundType().getPlaceSound(), 1.0f, 0.5f);
                    level.playSound(null, pos, state.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 0.5f);
                    player.swing(InteractionHand.MAIN_HAND, true);
                    level.addFreshEntity(newBoat);
                    return EventResult.interruptTrue();
                }
            }
        }
        return EventResult.pass();
    }

    private static ChestBoat createChestBoatFromBoat(Level level, BlockPos pos, Boat boat) {
        ChestBoat newBoat = new ChestBoat(level, pos.getX(), pos.getY(), pos.getZ());
        newBoat.setXRot(boat.getXRot());
        newBoat.setYRot(boat.getYRot());
        newBoat.setPos(boat.position());
        return newBoat;
    }
}

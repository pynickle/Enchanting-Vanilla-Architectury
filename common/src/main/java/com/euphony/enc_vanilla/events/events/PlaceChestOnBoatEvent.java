package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import dev.architectury.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractChestBoat;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Optional;

public class PlaceChestOnBoatEvent {
    private static final Map<Item, EntityType<? extends AbstractChestBoat>> ITEM_ENTITY_MAP = Map.of(
            Items.ACACIA_BOAT, EntityType.ACACIA_CHEST_BOAT,
            Items.BIRCH_BOAT, EntityType.BIRCH_CHEST_BOAT,
            Items.DARK_OAK_BOAT, EntityType.DARK_OAK_CHEST_BOAT,
            Items.CHERRY_BOAT, EntityType.CHERRY_CHEST_BOAT,
            Items.OAK_BOAT, EntityType.OAK_CHEST_BOAT,
            Items.JUNGLE_BOAT, EntityType.JUNGLE_CHEST_BOAT,
            Items.SPRUCE_BOAT, EntityType.SPRUCE_CHEST_BOAT,
            Items.MANGROVE_BOAT, EntityType.MANGROVE_CHEST_BOAT,
            Items.PALE_OAK_BOAT, EntityType.PALE_OAK_CHEST_BOAT,
            Items.BAMBOO_RAFT, EntityType.BAMBOO_CHEST_RAFT
    );

    public static EventResult interactEntity(Player player, Entity entity, InteractionHand interactionHand) {
        if (!QolConfig.HANDLER.instance().enablePlaceChestOnBoat) return EventResult.pass();

        Level level = player.level();
        if (level.isClientSide) return EventResult.pass();

        BlockPos pos = entity.getOnPos();
        ItemStack stack = player.getItemInHand(interactionHand);

        if (!(entity instanceof Boat boat)) return EventResult.pass();

        if (player.isShiftKeyDown() && stack.is(Items.CHEST)) {
            AbstractChestBoat newBoat = createChestBoatFromBoat(level, boat, player);
            if(newBoat != null) {
                boat.discard();
                stack.consume(1, player);
                level.addFreshEntity(newBoat);
                return EventResult.interruptTrue();
            }
        }
/*
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

 */
        return EventResult.pass();
    }

    private static AbstractChestBoat createChestBoatFromBoat(Level level, Boat boat, Player player) {
        Optional<EntityType<? extends AbstractChestBoat>> type = Optional.ofNullable(ITEM_ENTITY_MAP.get(boat.getPickResult()));
        if (type.isEmpty()) {
            return null;
        }
        AbstractChestBoat newBoat = type.get().create(level, EntitySpawnReason.SPAWN_ITEM_USE);
        EntityType.createDefaultStackConfig(level, boat.getPickResult(), player).accept(newBoat);
        newBoat.setXRot(boat.getXRot());
        newBoat.setYRot(boat.getYRot());
        newBoat.setPos(boat.position());
        return newBoat;
    }
}

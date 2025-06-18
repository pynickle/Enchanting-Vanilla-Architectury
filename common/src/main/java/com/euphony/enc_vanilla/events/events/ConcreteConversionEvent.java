package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.api.IConcretePowderBlock;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import dev.architectury.event.EventResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;

import java.util.ArrayList;
import java.util.List;

public class ConcreteConversionEvent {
    public static EventResult dropItem(Player player, ItemEntity itemEntity) {
        if (!itemEntity.level().isClientSide()) {
            addPlayerThrownItemEntity(itemEntity);
        }
        return EventResult.pass();
    }

    public static void serverPre(ServerLevel serverLevel) {
        entities.forEach(entity -> {
            if (entity.isAlive() && entity.isInWater()) {
                convertItemEntity(entity);
            }
        });
    }

    private static final List<ItemEntity> entities = new ArrayList<>();

    private static void addPlayerThrownItemEntity(ItemEntity entity) {
        if (QolConfig.HANDLER.instance().enableWaterConversion && (isConcretePowder(entity) || (QolConfig.HANDLER.instance().enableMudConversion && isConvertableToMud(entity))))
            if(!entities.contains(entity)) entities.add(entity);
    }

    private static boolean isConcretePowder(ItemEntity entity) {
        return Block.byItem(entity.getItem().getItem()) instanceof ConcretePowderBlock;
    }

    private static boolean isConvertableToMud(ItemEntity entity) {
        return Block.byItem(entity.getItem().getItem()).defaultBlockState().is(BlockTags.CONVERTABLE_TO_MUD);
    }

    private static void convertItemEntity(ItemEntity entity) {
        ItemStack stack = entity.getItem();
        Block block = null;
        if (isConcretePowder(entity))
            block = ((IConcretePowderBlock)(Block.byItem(stack.getItem()))).enc_vanilla$getConcrete();
        else if (QolConfig.HANDLER.instance().enableMudConversion && isConvertableToMud(entity))
            block = Blocks.MUD;

        if (block != null)
            entity.setItem(new ItemStack(block, stack.getCount()));
    }
}
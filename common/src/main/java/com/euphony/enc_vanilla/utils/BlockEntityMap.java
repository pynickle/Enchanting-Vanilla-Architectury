package com.euphony.enc_vanilla.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityMap {
    public static HashMap<BlockEntityType<?>, WeakHashMap<Level, ArrayList<BlockEntity>>> cachedBlockEntities = new HashMap<>();

    public static void addBlockEntity(BlockEntityType<?> blockEntityType) {
        if (!cachedBlockEntities.containsKey(blockEntityType)) {
            cachedBlockEntities.put(blockEntityType, new WeakHashMap<>());
        }
    }
}

package com.euphony.enc_vanilla.common.item;

import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FrogBucketItem extends MobBucketItem {
    public FrogBucketItem(Properties properties) {
        super(EntityType.FROG, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.component(EVDataComponentTypes.ACTIVE.get(), false));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull ServerLevel level, Entity entity, @Nullable EquipmentSlot equipmentSlot) {
        Vec3 pos = entity.position();
        int x = Mth.floor(pos.x);
        int z = Mth.floor(pos.z);
        boolean isActive = stack.get(EVDataComponentTypes.ACTIVE.get()).booleanValue();
        if(isActive != isSlimeChunk(level, x, z))
            stack.set(EVDataComponentTypes.ACTIVE.get(), !isActive);
    }

    public static boolean isSlimeChunk(ServerLevel world, int x, int z) {
        ChunkPos chunkpos = new ChunkPos(new BlockPos(x, 0, z));
        return WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, world.getSeed(), 987234911L).nextInt(10) == 0;
    }
}

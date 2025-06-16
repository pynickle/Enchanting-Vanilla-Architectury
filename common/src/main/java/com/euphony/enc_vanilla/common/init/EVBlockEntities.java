package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.entity.block.AppraisalTableBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public class EVBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(EncVanilla.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<AppraisalTableBlockEntity>> APPRAISAL = BLOCK_ENTITIES.register("appraisal_table_be", () -> new BlockEntityType<>(AppraisalTableBlockEntity::new, Set.of(EVBlocks.APPRAISAL_TABLE.get())));
}

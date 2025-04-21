package com.euphony.enc_vanilla.loot;

import com.euphony.enc_vanilla.EncVanilla;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class EVLootFunctions {
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPES = DeferredRegister.create(EncVanilla.MOD_ID, Registries.LOOT_FUNCTION_TYPE);

    public static final RegistrySupplier<LootItemFunctionType<ReplaceWithLootTableFunction>> REPLACE_WITH_LOOT_TABLE = LOOT_FUNCTION_TYPES.register("replace_with_loot_table", () -> new LootItemFunctionType<>(ReplaceWithLootTableFunction.CODEC));

}

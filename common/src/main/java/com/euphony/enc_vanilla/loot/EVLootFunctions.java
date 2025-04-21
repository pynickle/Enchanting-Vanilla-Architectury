package com.euphony.enc_vanilla.loot;

import architectury_inject_moonlightmulti_common_43ee0fa2602c49a882d592f1d0a77010_6a2e5bd3833926c4db6303cf1f71ba99632924592c3191517ab9e0b1e2834fd2moonlight12121810devjar.PlatformMethods;
import com.euphony.enc_vanilla.EncVanilla;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import it.crystalnest.cobweb.api.registry.Register;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class EVLootFunctions {
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPES = DeferredRegister.create(EncVanilla.MOD_ID, Registries.LOOT_FUNCTION_TYPE);

    public static final RegistrySupplier<LootItemFunctionType<ReplaceWithLootTableFunction>> REPLACE_WITH_LOOT_TABLE = LOOT_FUNCTION_TYPES.register("replace_with_loot_table", () -> new LootItemFunctionType<>(ReplaceWithLootTableFunction.CODEC));

}

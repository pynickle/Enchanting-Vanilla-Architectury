package com.euphony.enc_vanilla.loot;

import com.euphony.enc_vanilla.EncVanilla;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class EVLootConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(EncVanilla.MOD_ID, Registries.LOOT_CONDITION_TYPE);

    public static final Holder<LootItemConditionType> CHANCE = register("config_value_chance", Chance.CODEC);

    private static Holder<LootItemConditionType> register(String name, MapCodec<? extends LootItemCondition> codec) {
        return LOOT_CONDITION_TYPES.register(name, () -> new LootItemConditionType(codec));
    }
}
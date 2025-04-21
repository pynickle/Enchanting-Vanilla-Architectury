package com.euphony.enc_vanilla.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

public record Chance(float value) implements LootItemCondition {

    public static final MapCodec<Chance> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(Codec.FLOAT.fieldOf("config").forGetter(Chance::value))
                    .apply(instance, Chance::new)
    );

    @Override
    public @NotNull LootItemConditionType getType() {
        return EVLootConditions.CHANCE.value();
    }

    @Override
    public boolean test(LootContext context) {
        return context.getRandom().nextDouble() < value;
    }

    /*
    private enum ChanceConfig implements StringRepresentable {
        ARCHAEOLOGY("archaeology", Artifacts.CONFIG.general.archaeologyChance),
        ENTITY_EQUIPMENT("entity_equipment", Artifacts.CONFIG.general.entityEquipmentChance),
        EVERLASTING_BEEF("everlasting_beef", Artifacts.CONFIG.items.everlastingBeefDropRate);

        private static final Codec<ChanceConfig> CODEC = StringRepresentable.fromEnum(ChanceConfig::values);

        final String name;
        final Supplier<Double> value;

        ChanceConfig(String name, Supplier<Double> value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

     */
}

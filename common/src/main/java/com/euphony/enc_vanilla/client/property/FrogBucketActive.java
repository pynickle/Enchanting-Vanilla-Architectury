package com.euphony.enc_vanilla.client.property;

import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import com.euphony.enc_vanilla.common.item.FrogBucketItem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record FrogBucketActive(boolean active) implements SelectItemModelProperty<Boolean> {
    public static final Codec<Boolean> VALUE_CODEC = Codec.BOOL;
    public static final SelectItemModelProperty.Type<FrogBucketActive, Boolean> TYPE = SelectItemModelProperty.Type.create(
            RecordCodecBuilder.mapCodec((instance) -> instance.group(Codec.BOOL.optionalFieldOf("active", false).forGetter(FrogBucketActive::active)).apply(instance, FrogBucketActive::new)), VALUE_CODEC
    );

    @Override
    public @Nullable Boolean get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        return itemStack.getOrDefault(EVDataComponentTypes.ACTIVE.get(), false);
    }

    @Override
    public Codec<Boolean> valueCodec() {
        return VALUE_CODEC;
    }

    @Override
    public Type type() {
        return TYPE;
    }
}

package com.euphony.enc_vanilla.client.property;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SculkCompassAngle implements RangeSelectItemModelProperty {
    public static final MapCodec<SculkCompassAngle> MAP_CODEC;
    private final SculkCompassAngleState state;

    public SculkCompassAngle(boolean bl) {
        this(new SculkCompassAngleState(bl));
    }

    private SculkCompassAngle(SculkCompassAngleState compassAngleState) {
        this.state = compassAngleState;
    }

    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        return this.state.get(itemStack, clientLevel, livingEntity, i);
    }

    public MapCodec<SculkCompassAngle> type() {
        return MAP_CODEC;
    }

    static {
        MAP_CODEC = SculkCompassAngleState.MAP_CODEC.xmap(SculkCompassAngle::new, (compassAngle) -> compassAngle.state);
    }
}


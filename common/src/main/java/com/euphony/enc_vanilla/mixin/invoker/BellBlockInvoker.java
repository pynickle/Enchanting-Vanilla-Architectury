package com.euphony.enc_vanilla.mixin.invoker;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BellBlock.class)
public interface BellBlockInvoker {
    @Invoker("isProperHit")
    boolean invokeIsProperHit(BlockState arg, Direction arg2, double d);
}

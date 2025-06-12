package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.utils.config.LoadConfigUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;


@Mixin(Blocks.class)
public class BlocksMixin {
    @ModifyArg(method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/TorchflowerCropBlock;<init>(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V")
    )
    private static BlockBehaviour.Properties torchflowerCropBlockLightLevel(BlockBehaviour.Properties properties) {
        if(LoadConfigUtils.loadConfigAsBoolean("enableGlowingTorchFlower", true)) {
            return properties.lightLevel(blockState -> switch (blockState.getValue(TorchflowerCropBlock.AGE)) {
                case 0 -> LoadConfigUtils.loadConfigAsInt("torchFlowerStage1LightLevel", 3);
                case 1 -> LoadConfigUtils.loadConfigAsInt("torchFlowerStage2LightLevel", 7);
                default -> LoadConfigUtils.loadConfigAsInt("torchFlowerLightLevel", 12);
            });
        }
        return properties;
    }

    @ModifyArg(method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/FlowerBlock;<init>(Lnet/minecraft/core/Holder;FLnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V",
                    ordinal = 0),
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=torchflower")))
    private static BlockBehaviour.Properties torchflowerLightLevel(BlockBehaviour.Properties properties) {
        if(LoadConfigUtils.loadConfigAsBoolean("enableGlowingTorchFlower", true)) {
            return properties.lightLevel(blockState -> LoadConfigUtils.loadConfigAsInt("torchFlowerLightLevel", 12));
        }
        return properties;
    }

    @ModifyArg(
            method = "flowerPot",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/FlowerPotBlock;<init>(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V")
    )
    private static BlockBehaviour.Properties torchflowerPotLightLevel(Block block, BlockBehaviour.Properties properties) {
        if(LoadConfigUtils.loadConfigAsBoolean("enableGlowingTorchFlower", true)) {
            return block.equals(Blocks.TORCHFLOWER) ? properties.lightLevel(blockState -> LoadConfigUtils.loadConfigAsInt("pottedTorchFlowerLightLevel", 14)) : properties;
        }
        return properties;
    }
}

package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.block.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EVBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(EncVanilla.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<WaterloggedLilyPadBlock> WATERLOGGED_LILY_PAD = BLOCKS.register("waterlogged_lily_pad",
            () -> new WaterloggedLilyPadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD)));

    public static final RegistrySupplier<CutVineBlock> CUT_VINE = registerWithItem("cut_vine",
            () -> new CutVineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)));

    public static final RegistrySupplier<CutSugarCaneBlock> CUT_SUGAR_CANE = registerWithItem("cut_sugar_cane",
            () -> new CutSugarCaneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SUGAR_CANE)));

    public static final RegistrySupplier<CutBambooSaplingBlock> CUT_BAMBOO_SAPLING = registerWithItem("cut_bamboo_sapling",
            () -> new CutBambooSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_SAPLING)));

    public static final RegistrySupplier<CompressedSlimeBlock> COMPRESSED_SLIME_BLOCK = registerWithItem("compressed_slime_block",
            () -> new CompressedSlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));

    public static final RegistrySupplier<CeilingTorchBlock> CEILING_TORCH = registerWithItem("torch",
            () -> new CeilingTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH), ParticleTypes.FLAME, Blocks.TORCH));
    public static final RegistrySupplier<RedstoneCeilingTorchBlock> CEILING_REDSTONE_TORCH = registerWithItem("redstone_torch",
            () -> new RedstoneCeilingTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_TORCH), Blocks.REDSTONE_TORCH));
    public static final RegistrySupplier<CeilingTorchBlock> CEILING_SOUL_TORCH = registerWithItem("soul_torch",
            () -> new CeilingTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_TORCH), ParticleTypes.SOUL_FIRE_FLAME, Blocks.SOUL_TORCH));


    public static <T extends Block> RegistrySupplier<T> register(String name, Supplier<? extends T> supplier) {
        return BLOCKS.register(name, supplier);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<? extends T> supplier) {
        RegistrySupplier<T> ret = BLOCKS.register(name, supplier);
        EVItems.registerBlockItem(name, ret);
        return ret;
    }

    public static RegistrySupplier<Block> register(String name, Block block) {
        return register(name, () -> new Block(BlockBehaviour.Properties.ofFullCopy(block)));
    }

    public static RegistrySupplier<Block> registerWithItem(String name, Block block) {
        RegistrySupplier<Block> ret = BLOCKS.register(name, () -> new Block(BlockBehaviour.Properties.ofFullCopy(block)));
        EVItems.registerBlockItem(name, ret);
        return ret;
    }
}

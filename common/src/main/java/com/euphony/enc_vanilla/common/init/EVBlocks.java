package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.block.*;
import com.euphony.enc_vanilla.utils.Utils;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;

public class EVBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(EncVanilla.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<WaterloggedLilyPadBlock> WATERLOGGED_LILY_PAD = registerWithItem("waterlogged_lily_pad",
            WaterloggedLilyPadBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD));

    public static final RegistrySupplier<CutVineBlock> CUT_VINE = registerWithItem("cut_vine",
            CutVineBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.VINE));

    public static final RegistrySupplier<CutSugarCaneBlock> CUT_SUGAR_CANE = registerWithItem("cut_sugar_cane",
            CutSugarCaneBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SUGAR_CANE));

    public static final RegistrySupplier<CutBambooSaplingBlock> CUT_BAMBOO_SAPLING = registerWithItem("cut_bamboo_sapling",
            CutBambooSaplingBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_SAPLING).instabreak());

    public static final RegistrySupplier<CeilingTorchBlock> CEILING_TORCH = registerWithItem("torch",
            (p) -> new CeilingTorchBlock(p, ParticleTypes.FLAME, Blocks.TORCH),
            BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH));
    public static final RegistrySupplier<RedstoneCeilingTorchBlock> CEILING_REDSTONE_TORCH = registerWithItem("redstone_torch",
            (p) -> new RedstoneCeilingTorchBlock(p, Blocks.REDSTONE_TORCH),
            BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_TORCH));
    public static final RegistrySupplier<CeilingTorchBlock> CEILING_SOUL_TORCH = registerWithItem("soul_torch",
            (p) -> new CeilingTorchBlock(p, ParticleTypes.SOUL_FIRE_FLAME, Blocks.SOUL_TORCH),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_TORCH));

    public static final RegistrySupplier<AppraisalTableBlock> APPRAISAL_TABLE = registerWithItem("appraisal_table",
            AppraisalTableBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE));

    public static <T extends Block> RegistrySupplier<T> register(String name, Supplier<? extends T> supplier) {
        return BLOCKS.register(name, supplier);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<? extends T> supplier) {
        RegistrySupplier<T> ret = register(name, supplier);
        EVItems.registerBlockItem(name, ret);
        return ret;
    }

    public static <T extends Block> RegistrySupplier<T> register(String name, Function<BlockBehaviour.Properties, T> func, BlockBehaviour.Properties properties) {
        return BLOCKS.register(name, () -> func.apply(properties.setId(ResourceKey.create(Registries.BLOCK, Utils.prefix(name)))));
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Function<BlockBehaviour.Properties, T> func, BlockBehaviour.Properties properties) {
        RegistrySupplier<T> ret = register(name, func, properties);
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

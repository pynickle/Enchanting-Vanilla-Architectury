package com.euphony.enc_vanilla.common.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RedstoneCeilingTorchBlock extends RedstoneTorchBlock {
    private final Block originalBlock;

    public RedstoneCeilingTorchBlock(Properties properties, Block originalBlock) {
        super(properties);

        this.originalBlock = originalBlock;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return CeilingTorchBlock.CEILING_SHAPE;
    }

    @Override
    protected List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return List.of(new ItemStack(originalBlock));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        return direction == Direction.UP && !canSurvive(state, levelReader, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canSupportCenter(world, pos.above(), Direction.DOWN);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter access, BlockPos pos, Direction side) {
        return state.getValue(LIT) && Direction.DOWN != side ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter access, BlockPos pos, Direction side) {
        return side == Direction.UP ? state.getSignal(access, pos, side) : 0;
    }

    @Override
    protected boolean hasNeighborSignal(Level world, BlockPos pos, BlockState state) {
        return world.hasSignal(pos.above(), Direction.UP);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource rand) {
        if (state.getValue(LIT)) {
            double x = pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            double y = pos.getY() + 0.45D + (rand.nextDouble() - 0.5D) * 0.2D;
            double z = pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;

            world.addParticle(DustParticleOptions.REDSTONE, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return new ItemStack(originalBlock);
    }
}
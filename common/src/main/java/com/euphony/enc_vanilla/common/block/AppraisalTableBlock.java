package com.euphony.enc_vanilla.common.block;

import com.euphony.enc_vanilla.common.entity.block.AppraisalTableBlockEntity;
import com.euphony.enc_vanilla.common.init.EVBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;


public class AppraisalTableBlock extends BaseEntityBlock {
    private static final Component CONTAINER_TITLE = Component.translatable("container.appraisal");

    public static final MapCodec<AppraisalTableBlock> CODEC = simpleCodec(AppraisalTableBlock::new);

    public AppraisalTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createAppraisalTicker(level, blockEntityType, EVBlockEntities.APPRAISAL.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createAppraisalTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends AppraisalTableBlockEntity> blockEntityType2) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, blockEntityType2, AppraisalTableBlockEntity::serverTick);
    }

    protected void openContainer(Level level, BlockPos blockPos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof AppraisalTableBlockEntity) {
            player.openMenu((MenuProvider) blockEntity);
        }
    }

    protected InteractionResult useWithoutItem(BlockState arg, Level arg2, BlockPos arg3, Player arg4, BlockHitResult arg5) {
        if (arg2.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            this.openContainer(arg2, arg3, arg4);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected void onRemove(BlockState arg, Level level, BlockPos arg3, BlockState arg4, boolean bl) {
        if (!arg.is(arg4.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(arg3);
            if (blockEntity instanceof AppraisalTableBlockEntity) {
                if (level instanceof ServerLevel) {
                    drop(level, arg3, (AppraisalTableBlockEntity) blockEntity, 0);
                    drop(level, arg3, (AppraisalTableBlockEntity) blockEntity, 1);
                }

                super.onRemove(arg, level, arg3, arg4, bl);
                level.updateNeighbourForOutputSignal(arg3, this);
            } else {
                super.onRemove(arg, level, arg3, arg4, bl);
            }

        }
    }

    public void drop(Level level, BlockPos arg3, AppraisalTableBlockEntity blockEntity, int index) {
        Containers.dropItemStack(level, arg3.getX(), arg3.getY(), arg3.getZ(), blockEntity.getItem(index));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AppraisalTableBlockEntity(blockPos, blockState);
    }
}

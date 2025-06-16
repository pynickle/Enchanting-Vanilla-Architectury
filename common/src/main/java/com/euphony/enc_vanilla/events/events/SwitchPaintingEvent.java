package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import dev.architectury.event.EventResult;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SwitchPaintingEvent {
    private static @NotNull BlockPos getBlockPos(Painting painting1, Direction dir) {
        AABB bb = painting1.getBoundingBox();
        return switch (dir) {
            case SOUTH -> BlockPos.containing(bb.minX, bb.maxY - 0.5, bb.maxZ);
            case WEST -> BlockPos.containing(bb.minX, bb.maxY - 0.5, bb.minZ);
            case EAST -> BlockPos.containing(bb.maxX, bb.maxY - 0.5, bb.maxZ - 0.5);
            default -> BlockPos.containing(bb.maxX - 0.5, bb.maxY - 0.5, bb.minZ);
        };
    }

    private static int variantArea(Holder<PaintingVariant> variant) {
        return variant.value().area();
    }

    public static EventResult interactEntity(Player player, Entity entity, InteractionHand interactionHand) {
        if(!QolConfig.HANDLER.instance().enablePaintingSwitching) return EventResult.pass();

        Level level = player.level();
        if (level.isClientSide || interactionHand != InteractionHand.OFF_HAND) return EventResult.pass();

        if (!player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty()) return EventResult.pass();

        if(entity.getType() == EntityType.PAINTING) {
            Painting painting = (Painting) entity;
            List<Holder<PaintingVariant>> list = new ArrayList<>();
            level.registryAccess().lookupOrThrow(Registries.PAINTING_VARIANT).getTagOrEmpty(PaintingVariantTags.PLACEABLE).forEach(list::add);
            if (!list.isEmpty()) {
                list.removeIf(p_344343_ -> {
                    painting.setVariant(p_344343_);
                    return !painting.survives();
                });
                list.remove(painting.getVariant());
                if (!list.isEmpty()) {
                    int i = list.stream().mapToInt(SwitchPaintingEvent::variantArea).max().orElse(0);
                    list.removeIf(p_218883_ -> variantArea(p_218883_) < i);
                    Optional<Holder<PaintingVariant>> optional = Util.getRandomSafe(list, painting.getRandom());
                    if(optional.isPresent()) {
                        painting.setVariant(optional.get());
                        player.swing(interactionHand);
                        return EventResult.interruptTrue();
                    }
                }
            }
        }
        return EventResult.pass();
    }
/*
    public static EventResult rightClickBlock(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        Level level = player.level();
        if (level.isClientSide || !Platform.isModLoaded("fastpaintings")) return EventResult.pass();

        if(interactionHand != InteractionHand.OFF_HAND) return EventResult.pass();

        if (!player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty()) return EventResult.pass();

        BlockEntity blockEntity = level.getBlockEntity(blockPos);

        if(blockEntity instanceof PaintingBlockEntity painting) {
            CompoundTag tag = painting.getUpdateTag(level.registryAccess());

            String rawVariantResourceLocation = tag.getString("variant");

            ResourceLocation variantResourceLocation;
            try {
                variantResourceLocation = ResourceLocation.parse(rawVariantResourceLocation);
            }
            catch (ResourceLocationException ex) {
                return EventResult.pass();
            }

            Registry<PaintingVariant> paintingVariantRegistry = level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT);
            Optional<PaintingVariant> optionalPaintingVariant = paintingVariantRegistry.getOptional(variantResourceLocation);
            if (optionalPaintingVariant.isPresent()) {
                Holder<PaintingVariant> paintingVariantHolder = paintingVariantRegistry.wrapAsHolder(optionalPaintingVariant.get());
                List<Holder<PaintingVariant>> list = new ArrayList<>();
                level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getTagOrEmpty(PaintingVariantTags.PLACEABLE).forEach(list::add);
                if (!list.isEmpty()) {
                    list.remove(paintingVariantHolder);
                    if (!list.isEmpty()) {
                        int width = paintingVariantHolder.value().width();
                        int height = paintingVariantHolder.value().height();
                        list.removeIf(p_218883_ -> p_218883_.value().width() != width || p_218883_.value().height() != height);
                        Optional<Holder<PaintingVariant>> optional = Util.getRandomSafe(list, RandomSource.create());
                        optional.ifPresent(painting::setVariant);
                        level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        Direction dir = painting.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
                        for(int x = 0; x < width; x++) {
                            for(int y = 0; y < height; y++) {
                                BlockPos newPos = blockPos.below(y).relative(dir.getCounterClockWise(), x);
                                if(optional.isPresent()) {
                                    Painting painting1 = new Painting(level, newPos, dir, optional.get());
                                    BlockPos pos = getBlockPos(painting1, dir);
                                    if (pos.getX() == blockPos.getX() && pos.getZ() == blockPos.getZ() && pos.getY() == blockPos.getY()) {
                                        level.addFreshEntity(painting1);
                                        player.swing(interactionHand);
                                        return EventResult.interruptTrue();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return EventResult.pass();
    }

 */
}

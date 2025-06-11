package com.euphony.enc_vanilla.common.entity.block;

import com.euphony.enc_vanilla.common.init.EVBlockEntities;
import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.screen.AppraisalTableMenu;
import com.euphony.enc_vanilla.utils.WeightedRandomSampler;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.LevelStem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.util.Mth.EPSILON;

public class AppraisalTableBlockEntity extends BaseContainerBlockEntity {
    protected final ContainerData dataAccess;
    int progress = 0;
    boolean isActive = false;
    WeightedRandomSampler<Holder<Biome>> weightedRandomSampler;
    ItemStack originalStack = ItemStack.EMPTY;

    protected NonNullList<ItemStack> items;

    public AppraisalTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EVBlockEntities.APPRAISAL.get(), blockPos, blockState);
        this.items = NonNullList.withSize(3, ItemStack.EMPTY);
        this.weightedRandomSampler = new WeightedRandomSampler<>();
        this.dataAccess = new ContainerData() {
            public int get(int i) {
                switch (i) {
                    case 0:
                        return progress;
                    case 1:
                        return isActive ? 1 : 0;
                    default:
                        return 0;
                }
            }

            public void set(int i, int j) {
                switch (i) {
                    case 0 -> AppraisalTableBlockEntity.this.progress = j;
                    case 1 -> AppraisalTableBlockEntity.this.isActive = j != 0;
                }

            }

            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.appraisal");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    public void setItem(int i, ItemStack arg) {
        ItemStack itemstack = this.items.get(i);
        boolean flag = !arg.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, arg);
        this.items.set(i, arg);
        arg.limitSize(this.getMaxStackSize(arg));
        if (i == 0 && !flag) {
            this.progress = 0;
            this.setChanged();
        }

    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new AppraisalTableMenu(i, inventory, this, this.dataAccess);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.putInt("Progress", this.progress);
        compoundTag.putBoolean("IsActive", this.isActive);
        ContainerHelper.saveAllItems(compoundTag, this.items, provider);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items, provider);
        this.progress = compoundTag.getInt("Progress");
        this.isActive = compoundTag.getBoolean("IsActive");
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, AppraisalTableBlockEntity arg4) {
        ItemStack originalInput = arg4.getItem(0);
        if (!originalInput.isEmpty()) {
            if(arg4.items.get(2).isEmpty()) {
                if (arg4.dataAccess.get(0) < 80) {
                    boolean isLocked = originalInput.getOrDefault(EVDataComponentTypes.LOCKED.get(), false);
                    boolean isTempUnlocked = originalInput.getOrDefault(EVDataComponentTypes.TEMP_UNLOCKED.get(), false);

                    if (!isLocked || isTempUnlocked) {
                        arg4.progress++;
                    }
                } else {
                    ItemStack input = originalInput.copy();

                    if(!arg4.originalStack.is(input.getItem())) {
                        level.players().getFirst().sendSystemMessage(Component.literal("1"));
                        float min, max;
                        if (input.is(EVItems.BIOME_CRYSTAL_ITEM.get())) {
                            min = 0.0f;
                            max = 1.0f;
                        } else if (input.is(EVItems.FROZEN_BIOME_CRYSTAL_ITEM)) {
                            min = -1.0f;
                            max = 0.0f;
                        } else {
                            min = 1.0f;
                            max = 2.0f;
                        }

                        Registry<Biome> biomeRegistry = level.registryAccess().registryOrThrow(Registries.BIOME);

                        ResourceLocation levelDimension = level.dimension().location();
                        List<Holder<Biome>> biomes = new ArrayList<>();
                        if (levelDimension.getNamespace().equals("minecraft")) {
                            TagKey<Biome> biomeTagKey;
                            if (levelDimension.equals(BuiltinDimensionTypes.OVERWORLD.location())) {
                                biomeTagKey = BiomeTags.IS_OVERWORLD;
                            } else if (levelDimension.equals(BuiltinDimensionTypes.NETHER.location())) {
                                biomeTagKey = BiomeTags.IS_NETHER;
                            } else {
                                biomeTagKey = BiomeTags.IS_END;
                            }
                            biomes = biomeRegistry.stream().map(biomeRegistry::wrapAsHolder).filter(biomeHolder ->
                                            biomeHolder.is(biomeTagKey))
                                    .filter(biomeHolder -> biomeTemperatureInRange(biomeHolder.value().getBaseTemperature(), min, max)).toList();
                        } else {
                            ResourceKey<Level> dimensionKey = level.dimension();

                            for (Holder.Reference<Biome> holder : biomeRegistry.holders().toList()) {
                                Registry<LevelStem> dimensionRegistry = level.registryAccess().registryOrThrow(Registries.LEVEL_STEM);
                                ResourceKey<LevelStem> stemKey = ResourceKey.create(
                                        Registries.LEVEL_STEM,
                                        dimensionKey.location()
                                );
                                LevelStem stem = dimensionRegistry.get(stemKey);
                                ChunkGenerator chunkGenerator = stem.generator();
                                BiomeSource biomeSource = chunkGenerator.getBiomeSource();
                                if (biomeSource.possibleBiomes().stream()
                                        .anyMatch(holder1 -> holder1.is(holder.unwrapKey().orElse(null)))) {
                                    if (biomeTemperatureInRange(holder.value().getBaseTemperature(), min, max)) {
                                        biomes.add(holder);
                                    }
                                }
                            }
                        }

                        arg4.originalStack = input;
                        arg4.weightedRandomSampler.init(biomes);
                    }
                    Holder<Biome> biome = arg4.weightedRandomSampler.sample();

                    if(!input.getOrDefault(EVDataComponentTypes.LOCKED.get(), false)) {
                        input.set(EVDataComponentTypes.LOCKED.get(), true);
                    }
                    input.set(EVDataComponentTypes.TEMP_UNLOCKED.get(), false);
                    arg4.setItem(0, input);

                    ItemStack input1 = input.copy();
                    input1.set(EVDataComponentTypes.BIOME.get(), biome.unwrapKey().get());

                    arg4.setItem(2, input1);
                    arg4.progress = 0;
                }
            }
        } else {
            arg4.progress = 0;
            arg4.items.set(2, ItemStack.EMPTY);
        }
        if((!arg4.items.get(1).isEmpty() && (!arg4.getItem(2).isEmpty() || arg4.getItem(0).has(EVDataComponentTypes.LOCKED.get()))) != arg4.isActive) {
            arg4.isActive = !arg4.isActive;
        }
    }

    public static boolean biomeTemperatureInRange(float value, float min, float max) {
        value = Mth.clamp(value, -1.0f, 2.0f);
        return (value - min) >= -EPSILON && (max - value) >= -EPSILON;
    }
}

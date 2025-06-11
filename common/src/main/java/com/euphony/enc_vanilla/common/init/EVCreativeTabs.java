package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EVCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(EncVanilla.MOD_ID, Registries.CREATIVE_MODE_TAB);

    static {
    TABS.register(
        "items",
        () ->
            CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1)
                .title(Component.translatable("itemGroup.enc_vanilla.items"))
                .icon(() -> new ItemStack(EVItems.BIOME_CRYSTAL_ITEM.get()))
                .displayItems(
                    (parameters, output) -> {
                      output.accept(EVItems.DAMAGED_SCULK_COMPASS_ITEM.get());
                      output.accept(EVItems.SCULK_COMPASS_ITEM.get());
                      output.accept(EVItems.BIOME_CRYSTAL_ITEM.get());
                      output.accept(EVItems.HEATED_BIOME_CRYSTAL_ITEM.get());
                      output.accept(EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get());
                      output.accept(EVBlocks.COMPRESSED_SLIME_BLOCK.get());
                      output.accept(EVBlocks.APPRAISAL_TABLE.get());
                      output.accept(EVItems.FROG_BUCKET_ITEM.get());
                    })
                .build());
    }
}

package com.euphony.enc_vanilla.neoforge.data.models;

import com.euphony.enc_vanilla.EncVanilla;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockModelGenerator extends BlockModelProvider {
    public BlockModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EncVanilla.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}

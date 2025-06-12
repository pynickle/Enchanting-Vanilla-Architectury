package com.euphony.enc_vanilla.neoforge.data.tag;

import com.euphony.enc_vanilla.common.init.EVDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class DamageTypeGenerator extends DamageTypeTagsProvider {
    public DamageTypeGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(arg, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        tag(DamageTypeTags.NO_KNOCKBACK).add(EVDamageTypes.STONECUTTER_DAMAGE);
    }
}

package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.common.init.EVDamageTypes;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.StonecutterBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(StonecutterBlock.class)
public abstract class StonecutterBlockMixin extends Block {
    public StonecutterBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if(!QolConfig.HANDLER.instance().enableStonecutterDamage) return;

        if (entity instanceof LivingEntity) {
            Registry<DamageType> damageTypes = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
            entity.hurt(new DamageSource(damageTypes.getHolderOrThrow(EVDamageTypes.STONECUTTER_DAMAGE)), 1.0F);
        }
    }
}

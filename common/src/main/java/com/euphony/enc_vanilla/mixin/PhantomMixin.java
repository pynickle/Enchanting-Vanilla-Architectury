package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.api.IPhantom;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Phantom.class)
public abstract class PhantomMixin extends FlyingMob implements Enemy, IPhantom {
    @Unique
    private static final EntityDataAccessor<Integer> BELL_TIME = SynchedEntityData.defineId(Phantom.class, EntityDataSerializers.INT);

    protected PhantomMixin(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedDataInject(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(BELL_TIME, 0);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveDataInject(CompoundTag compoundTag, CallbackInfo ci) {
        this.enc_vanilla$setBellTime(compoundTag.getInt("BellTime").get());
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveDataInject(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.putInt("BellTime", this.enc_vanilla$getBellTime());
    }


    @Override
    public void enc_vanilla$setBellTime(int i) {
        this.entityData.set(BELL_TIME, i, true);
    }

    @Unique
    public int enc_vanilla$getBellTime() {
        return this.entityData.get(BELL_TIME);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", ordinal = 0))
    private void tickRedirect(Level instance, ParticleOptions arg, double d, double e, double f, double g, double h, double i) {
        int bellTime = enc_vanilla$getBellTime();
        if(bellTime > 0) {
            enc_vanilla$setBellTime(bellTime - 1);
            this.level().addParticle(ParticleTypes.PORTAL, d, e, f, g, h, i);
        } else {
            this.level().addParticle(ParticleTypes.MYCELIUM, d, e, f, g, h, i);
        }
    }
}

package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.common.entity.DoubleHandedTemptGoal;
import com.euphony.enc_vanilla.common.entity.VillagerAttractionGoal;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    @Unique
    private DoubleHandedTemptGoal enc_vanilla$villagersAttractedGoal;

    @Unique
    private boolean enc_vanilla$villagersAttracted;

    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/npc/VillagerType;)V",
            at = @At(
                    value = "RETURN"
            )
    )
    private void init(EntityType<? extends Villager> entityType, Level level, VillagerType villagerType, CallbackInfo ci) {
        this.enc_vanilla$villagersAttractedGoal = new VillagerAttractionGoal(this, level);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "RETURN"
            )
    )
    private void checkVillagersAttracted(CallbackInfo ci) {
        final boolean configEnabled = QolConfig.HANDLER.instance().enableVillagerAttraction;
        if(!this.enc_vanilla$villagersAttracted && configEnabled) {
            this.goalSelector.addGoal(0, this.enc_vanilla$villagersAttractedGoal);
            this.enc_vanilla$villagersAttracted = true;
        } else if(this.enc_vanilla$villagersAttracted && !configEnabled) {
            this.goalSelector.removeGoal(this.enc_vanilla$villagersAttractedGoal);
            this.enc_vanilla$villagersAttracted = false;
        }
    }
}

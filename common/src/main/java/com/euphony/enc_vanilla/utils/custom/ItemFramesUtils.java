package com.euphony.enc_vanilla.utils.custom;

import com.euphony.enc_vanilla.api.ICustomItemFrame;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.projectile.AbstractThrownPotion;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ItemFramesUtils {
    public static void handleInvisibility(AbstractThrownPotion thrownPotion, Iterable<MobEffectInstance> effects) {
        if(!QolConfig.HANDLER.instance().enableInvisibleItemFrame) return;

        effects.forEach(
                effect -> {
                    if (effect.is(MobEffects.INVISIBILITY)) {
                        setInvisibility(thrownPotion, true);
                    }
                });
    }

    public static void setInvisibility(AbstractThrownPotion thrownPotion, boolean invisibility) {
        AABB checkBox = thrownPotion.getBoundingBox().inflate(2.0D, 1.0D, 2.0D);
        List<ItemFrame> itemFrames = thrownPotion.level().getEntitiesOfClass(ItemFrame.class, checkBox);
        for (ItemFrame frame : itemFrames) {
            ICustomItemFrame itemFrame = (ICustomItemFrame) frame;
            if (invisibility != itemFrame.enc_vanilla$getIsInvisible())
                itemFrame.enc_vanilla$setIsInvisible(invisibility);
        }
    }
}

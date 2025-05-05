package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PotionItem.class)
public class PotionItemMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Item.Properties modifyStackSize(Item.Properties properties) {
        return properties.stacksTo(QolConfig.HANDLER.instance().enableStackablePotion ? 16 : 1);
    }
}

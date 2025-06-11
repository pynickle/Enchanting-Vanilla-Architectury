package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.screen.AppraisalTableMenu;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class EVMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(EncVanilla.MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<AppraisalTableMenu>> APPRAISAL_MENU =
            MENUS.register("appraisal_menu", () -> new MenuType<>(AppraisalTableMenu::new, FeatureFlags.REGISTRY.allFlags()));
}

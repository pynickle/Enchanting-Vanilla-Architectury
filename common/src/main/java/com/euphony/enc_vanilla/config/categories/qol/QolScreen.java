package com.euphony.enc_vanilla.config.categories.qol;

import com.euphony.enc_vanilla.config.categories.qol.screen.ExtraForcedFuelsScreen;
import com.euphony.enc_vanilla.config.categories.qol.screen.ExtraSoulTorchItemsScreen;
import com.euphony.enc_vanilla.config.categories.qol.screen.ExtraTorchItemsScreen;
import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import com.euphony.enc_vanilla.utils.config.DescComponent;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.gui.YACLScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.BiConsumer;

import static com.euphony.enc_vanilla.config.categories.qol.QolConfig.*;

@Environment(EnvType.CLIENT)
public class QolScreen {
    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            // Villager Attraction
            Option<Boolean> enableVillagerAttractionOpt = ConfigUtils.<Boolean>getGenericOption("enableVillagerAttraction")
                    .binding(defaults.enableVillagerAttraction,
                            () -> config.enableVillagerAttraction,
                            newVal -> config.enableVillagerAttraction = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Item> mainHandItemOpt = ConfigUtils.<Item>getGenericOption("mainHandItem")
                    .binding(defaults.mainHandItem,
                            () -> config.mainHandItem,
                            newVal -> config.mainHandItem = newVal)
                    .controller(ItemControllerBuilder::create)
                    .build();

            // Item Frame
            Option<Boolean> enableInvisibleItemFrameOpt = ConfigUtils.<Boolean>getGenericOption("enableInvisibleItemFrame")
                    .binding(defaults.enableInvisibleItemFrame,
                            () -> config.enableInvisibleItemFrame,
                            newVal -> config.enableInvisibleItemFrame = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            // Torch Hit
            Option<Boolean> enableTorchHitOpt = ConfigUtils.<Boolean>getGenericOption("enableTorchHit")
                    .binding(defaults.enableTorchHit,
                            () -> config.enableTorchHit,
                            newVal -> config.enableTorchHit = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableMobTorchHitOpt = ConfigUtils.<Boolean>getGenericOption("enableMobTorchHit")
                    .binding(defaults.enableMobTorchHit,
                            () -> config.enableMobTorchHit,
                            newVal -> config.enableMobTorchHit = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Integer> torchHitFireChanceOpt = ConfigUtils.<Integer>getGenericOption("torchHitFireChance")
                    .binding(defaults.torchHitFireChance,
                            () -> config.torchHitFireChance,
                            newVal -> config.torchHitFireChance = newVal)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 100).step(1).formatValue(value -> Component.literal(value + "%")))
                    .build();

            Option<Double> torchHitFireDurationOpt = ConfigUtils.<Double>getGenericOption("torchHitFireDuration")
                    .binding(defaults.torchHitFireDuration,
                            () -> config.torchHitFireDuration,
                            newVal -> config.torchHitFireDuration = newVal)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                            .range(3.0, 10.0).step(0.5).formatValue(value -> Component.literal(value + "s")))
                    .build();

            Option<BiConsumer<YACLScreen, ButtonOption>> extraTorchItemsOpt = ConfigUtils.getButtonOption("extraTorchItems")
                    .action(((yaclScreen, buttonOption) -> Minecraft.getInstance().setScreen(ExtraTorchItemsScreen.makeScreen().generateScreen(yaclScreen))))
                    .build();

            Option<BiConsumer<YACLScreen, ButtonOption>> extraSoulTorchItemsOpt = ConfigUtils.getButtonOption("extraSoulTorchItems")
                    .action(((yaclScreen, buttonOption) -> Minecraft.getInstance().setScreen(ExtraSoulTorchItemsScreen.makeScreen().generateScreen(yaclScreen))))
                    .build();

            // Farmland Trampling Prevention
            Option<Boolean> enableFarmlandTramplingPreventionOpt = ConfigUtils.<Boolean>getGenericOption("enableFarmlandTramplingPrevention")
                    .binding(defaults.enableFarmlandTramplingPrevention,
                            () -> config.enableFarmlandTramplingPrevention,
                            newVal -> config.enableFarmlandTramplingPrevention = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            // Anvil Repair
            Option<Boolean> enableAnvilRepairOpt = ConfigUtils.<Boolean>getGenericOption("enableAnvilRepair")
                    .binding(defaults.enableAnvilRepair,
                            () -> config.enableAnvilRepair,
                            newVal -> config.enableAnvilRepair = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Item> anvilRepairMaterialOpt = ConfigUtils.<Item>getGenericOption("anvilRepairMaterial")
                    .binding(defaults.anvilRepairMaterial,
                            () -> config.anvilRepairMaterial,
                            newVal -> config.anvilRepairMaterial = newVal)
                    .controller(ItemControllerBuilder::create)
                    .build();

            // Water Conversion
            Option<Boolean> enableWaterConversionOpt = ConfigUtils.<Boolean>getGenericOption("enableWaterConversion")
                    .binding(defaults.enableWaterConversion,
                            () -> config.enableWaterConversion,
                            newVal -> config.enableWaterConversion = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableMudConversionOpt = ConfigUtils.<Boolean>getGenericOption("enableMudConversion")
                    .binding(defaults.enableMudConversion,
                            () -> config.enableMudConversion,
                            newVal -> config.enableMudConversion = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            // Bell Phantom
            Option<Boolean> enableBellPhantomOpt = ConfigUtils.<Boolean>getGenericOption("enableBellPhantom")
                    .binding(defaults.enableBellPhantom,
                            () -> config.enableBellPhantom,
                            newVal -> config.enableBellPhantom = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Double> particleDurationOpt = ConfigUtils.<Double>getGenericOption("particleDuration")
                    .binding(defaults.particleDuration,
                            () -> config.particleDuration,
                            newVal -> config.particleDuration = newVal)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                            .range(0.0, 5.0).step(0.5).formatValue(value -> Component.literal(value + "s")))
                    .build();

            // Highlight Mobs
            Option<Boolean> enableHighlightMobsOpt = ConfigUtils.<Boolean>getGenericOption("enableHighlightMobs", "highlight_mobs")
                    .binding(defaults.enableHighlightMobs,
                            () -> config.enableHighlightMobs,
                            newVal -> config.enableHighlightMobs = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Double> highlightDurationOpt = ConfigUtils.<Double>getGenericOption("highlightDuration")
                    .binding(defaults.highlightDuration,
                            () -> config.highlightDuration,
                            newVal -> config.highlightDuration = newVal)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                            .range(1.0, 5.0).step(0.5).formatValue(value -> Component.literal(value + "s")))
                    .build();

            // Sponge Placing
            Option<Boolean> enableSpongePlacingOpt = ConfigUtils.<Boolean>getGenericOption("enableSpongePlacing")
                    .binding(defaults.enableSpongePlacing,
                            () -> config.enableSpongePlacing,
                            newVal -> config.enableSpongePlacing = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableSpongePlacingSneakingOpt = ConfigUtils.<Boolean>getGenericOption("enableSpongePlacingSneaking")
                    .binding(defaults.enableSpongePlacingSneaking,
                            () -> config.enableSpongePlacingSneaking,
                            newVal -> config.enableSpongePlacingSneaking = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            // Healing Campfire
            Option<Boolean> enableHealingCampfireOpt = ConfigUtils.<Boolean>getGenericOption("enableHealingCampfire")
                    .binding(defaults.enableHealingCampfire,
                            () -> config.enableHealingCampfire,
                            newVal -> config.enableHealingCampfire = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Integer> checkEveryTickOpt = ConfigUtils.<Integer>getGenericOption("checkEveryTick", DescComponent.TICK_EXPLANATION)
                    .binding(defaults.checkEveryTick,
                            () -> config.checkEveryTick,
                            newVal -> config.checkEveryTick = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(1, 60).formatValue(value -> Component.literal(value + " ticks")))
                    .build();

            Option<Integer> healingRadiusOpt = ConfigUtils.<Integer>getGenericOption("healingRadius")
                    .binding(defaults.healingRadius,
                            () -> config.healingRadius,
                            newVal -> config.healingRadius = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(0, 60))
                    .build();

            Option<Double> effectDurationOpt = ConfigUtils.<Double>getGenericOption("effectDuration")
                    .binding(defaults.effectDuration,
                            () -> config.effectDuration,
                            newVal -> config.effectDuration = newVal)
                    .controller(opt -> DoubleFieldControllerBuilder.create(opt)
                            .range(1.0, 5.0).formatValue(value -> Component.literal(value + "s")))
                    .build();

            Option<Integer> effectLevelOpt = ConfigUtils.<Integer>getGenericOption("effectLevel")
                    .binding(defaults.effectLevel,
                            () -> config.effectLevel,
                            newVal -> config.effectLevel = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(0, 60))
                    .build();

            // Right Click Harvest
            Option<Boolean> enableRightClickHarvestOpt = ConfigUtils.<Boolean>getGenericOption("enableRightClickHarvest")
                    .binding(defaults.enableRightClickHarvest,
                            () -> config.enableRightClickHarvest,
                            newVal -> config.enableRightClickHarvest = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> requiredHoeOpt = ConfigUtils.<Boolean>getGenericOption("requiredHoe")
                    .binding(defaults.requiredHoe,
                            () -> config.requiredHoe,
                            newVal -> config.requiredHoe = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Double> hungerCostOpt = ConfigUtils.<Double>getGenericOption("hungerCost")
                    .binding(defaults.hungerCost,
                            () -> config.hungerCost,
                            newVal -> config.hungerCost = newVal)
                    .controller(opt -> DoubleFieldControllerBuilder.create(opt)
                            .range(0.0, 5.0))
                    .build();

            // Harvest Xp
            Option<Boolean> enableHarvestXpOpt = ConfigUtils.<Boolean>getGenericOption("enableHarvestXp")
                    .binding(defaults.enableHarvestXp,
                            () -> config.enableHarvestXp,
                            newVal -> config.enableHarvestXp = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Integer> xpAmountOpt = ConfigUtils.<Integer>getGenericOption("xpAmount")
                    .binding(defaults.xpAmount,
                            () -> config.xpAmount,
                            newVal -> config.xpAmount = newVal)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(1, 5).step(1))
                    .build();

            // Stackable Potion
            Option<Boolean> enableStackablePotionOpt = ConfigUtils.<Boolean>getGenericOption("enableStackablePotion")
                    .binding(defaults.enableStackablePotion,
                            () -> config.enableStackablePotion,
                            newVal -> config.enableStackablePotion = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Integer> throwablePotionCooldownOpt = ConfigUtils.<Integer>getGenericOption("throwablePotionCooldown", DescComponent.TICK_EXPLANATION)
                    .binding(defaults.throwablePotionCooldown,
                            () -> config.throwablePotionCooldown,
                            newVal -> config.throwablePotionCooldown = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(0, 100))
                    .build();

            // Glowing Torchflower
            Option<Boolean> enableGlowingTorchFlowerOpt = ConfigUtils.<Boolean>getGenericOption("enableGlowingTorchFlower")
                    .binding(defaults.enableGlowingTorchFlower,
                            () -> config.enableGlowingTorchFlower,
                            newVal -> config.enableGlowingTorchFlower = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Integer> torchFlowerLightLevelOpt = ConfigUtils.<Integer>getGenericOption("torchFlowerLightLevel")
                    .binding(defaults.torchFlowerLightLevel,
                            () -> config.torchFlowerLightLevel,
                            newVal -> config.torchFlowerLightLevel = newVal)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 15).step(1))
                    .build();
            Option<Integer> pottedTorchFlowerLightLevelOpt = ConfigUtils.<Integer>getGenericOption("pottedTorchFlowerLightLevel")
                    .binding(defaults.pottedTorchFlowerLightLevel,
                            () -> config.pottedTorchFlowerLightLevel,
                            newVal -> config.pottedTorchFlowerLightLevel = newVal)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 15).step(1))
                    .build();
            Option<Integer> torchFlowerStage1LightLevelOpt = ConfigUtils.<Integer>getGenericOption("torchFlowerStage1LightLevel")
                    .binding(defaults.torchFlowerStage1LightLevel,
                            () -> config.torchFlowerStage1LightLevel,
                            newVal -> config.torchFlowerStage1LightLevel = newVal)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 15).step(1))
                    .build();
            Option<Integer> torchFlowerStage2LightLevelOpt = ConfigUtils.<Integer>getGenericOption("torchFlowerStage2LightLevel")
                    .binding(defaults.torchFlowerStage2LightLevel,
                            () -> config.torchFlowerStage2LightLevel,
                            newVal -> config.torchFlowerStage2LightLevel = newVal)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 15).step(1))
                    .build();

            // Bush Protection
            Option<Boolean> enableBushProtectionOpt = ConfigUtils.<Boolean>getGenericOption("enableBushProtection")
                    .binding(defaults.enableBushProtection,
                            () -> config.enableBushProtection,
                            newVal -> config.enableBushProtection = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();
            Option<Boolean> needLeggingsOpt = ConfigUtils.<Boolean>getGenericOption("needLeggings")
                    .binding(defaults.needLeggings,
                            () -> config.needLeggings,
                            newVal -> config.needLeggings = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();
            Option<Boolean> permanentVillagersProtectionOpt = ConfigUtils.<Boolean>getGenericOption("permanentVillagersProtection")
                    .binding(defaults.permanentVillagersProtection,
                            () -> config.permanentVillagersProtection,
                            newVal -> config.permanentVillagersProtection = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();
            Option<Boolean> enableSlowerSpeedOpt = ConfigUtils.<Boolean>getGenericOption("enableSlowerSpeed")
                    .binding(defaults.enableSlowerSpeed,
                            () -> config.enableSlowerSpeed,
                            newVal -> config.enableSlowerSpeed = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            // Stonecutter Damage
            Option<Boolean> enableStonecutterDamageOpt = ConfigUtils.<Boolean>getGenericOption("enableStonecutterDamage")
                    .binding(defaults.enableStonecutterDamage,
                            () -> config.enableStonecutterDamage,
                            newVal -> config.enableStonecutterDamage = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();
            Option<Boolean> villagerImmunityOpt = ConfigUtils.<Boolean>getGenericOption("villagerImmunity")
                    .binding(defaults.villagerImmunity,
                            () -> config.villagerImmunity,
                            newVal -> config.villagerImmunity = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            // Forced Fuels
            Option<Boolean> enableForcedFuelsOpt = ConfigUtils.<Boolean>getGenericOption("enableForcedFuels")
                    .binding(defaults.enableForcedFuels,
                            () -> config.enableForcedFuels,
                            newVal -> config.enableForcedFuels = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();
            Option<BiConsumer<YACLScreen, ButtonOption>> extraForcedFuelsOpt = ConfigUtils.getButtonOption("extraForcedFuels")
                    .action(((yaclScreen, buttonOption) -> Minecraft.getInstance().setScreen(ExtraForcedFuelsScreen.makeScreen().generateScreen(yaclScreen))))
                    .build();

            // Other
            Option<Boolean> enableBlocksOnLilyPadOpt = ConfigUtils.<Boolean>getGenericOption("enableBlocksOnLilyPad", "blocks_on_lily_pad")
                    .binding(defaults.enableBlocksOnLilyPad,
                            () -> config.enableBlocksOnLilyPad,
                            newVal -> config.enableBlocksOnLilyPad = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enablePaintingSwitchingOpt = ConfigUtils.<Boolean>getGenericOption("enablePaintingSwitching")
                    .binding(defaults.enablePaintingSwitching,
                            () -> config.enablePaintingSwitching,
                            newVal -> config.enablePaintingSwitching = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableCutVineOpt = ConfigUtils.<Boolean>getGenericOption("enableCutVine", "cut_vine")
                    .binding(defaults.enableCutVine,
                            () -> config.enableCutVine,
                            newVal -> config.enableCutVine = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableStopGrowingOpt = ConfigUtils.<Boolean>getGenericOption("enableStopGrowing", "stop_growing")
                    .binding(defaults.enableStopGrowing,
                            () -> config.enableStopGrowing,
                            newVal -> config.enableStopGrowing = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableShutupNameTagOpt = ConfigUtils.<Boolean>getGenericOption("enableShutupNameTag")
                    .binding(defaults.enableShutupNameTag,
                            () -> config.enableShutupNameTag,
                            newVal -> config.enableShutupNameTag = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableJukeboxLoopOpt = ConfigUtils.<Boolean>getGenericOption("enableJukeboxLoop")
                    .binding(defaults.enableJukeboxLoop,
                            () -> config.enableJukeboxLoop,
                            newVal -> config.enableJukeboxLoop = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableCakeDropOpt = ConfigUtils.<Boolean>getGenericOption("enableCakeDrop")
                    .binding(defaults.enableCakeDrop,
                            () -> config.enableCakeDrop,
                            newVal -> config.enableCakeDrop = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableCeilingTorchOpt = ConfigUtils.<Boolean>getGenericOption("enableCeilingTorch", "ceiling_torch")
                    .binding(defaults.enableCeilingTorch,
                            () -> config.enableCeilingTorch,
                            newVal -> config.enableCeilingTorch = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableSafeLavaBucketOpt = ConfigUtils.<Boolean>getGenericOption("enableSafeLavaBucket")
                    .binding(defaults.enableSafeLavaBucket,
                            () -> config.enableSafeLavaBucket,
                            newVal -> config.enableSafeLavaBucket = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enablePlaceChestOnBoatOpt = ConfigUtils.<Boolean>getGenericOption("enablePlaceChestOnBoat")
                    .binding(defaults.enablePlaceChestOnBoat,
                            () -> config.enablePlaceChestOnBoat,
                            newVal -> config.enablePlaceChestOnBoat = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableNameTagDespawnOpt = ConfigUtils.<Boolean>getGenericOption("enableNameTagDespawn")
                    .binding(defaults.enableNameTagDespawn,
                            () -> config.enableNameTagDespawn,
                            newVal -> config.enableNameTagDespawn = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableSafeHarvestOpt = ConfigUtils.<Boolean>getGenericOption("enableSafeHarvest")
                    .binding(defaults.enableSafeHarvest,
                            () -> config.enableSafeHarvest,
                            newVal -> config.enableSafeHarvest = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableDoubleDoorOpt = ConfigUtils.<Boolean>getGenericOption("enableDoubleDoor")
                    .binding(defaults.enableDoubleDoor,
                            () -> config.enableDoubleDoor,
                            newVal -> config.enableDoubleDoor = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();
            Option<Boolean> enableBrokenLeadOpt = ConfigUtils.<Boolean>getGenericOption("enableBrokenLead")
                    .binding(defaults.enableBrokenLead,
                            () -> config.enableBrokenLead,
                            newVal -> config.enableBrokenLead = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();
            Option<Boolean> enableStickyPistonOpt = ConfigUtils.<Boolean>getGenericOption("enableStickyPiston")
                    .binding(defaults.enableStickyPiston,
                            () -> config.enableStickyPiston,
                            newVal -> config.enableStickyPiston = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();
            Option<Boolean> enableThrowableFireChargeOpt = ConfigUtils.<Boolean>getGenericOption("enableThrowableFireCharge")
                    .binding(defaults.enableThrowableFireCharge,
                            () -> config.enableThrowableFireCharge,
                            newVal -> config.enableThrowableFireCharge = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(QOL_CATEGORY))
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, VILLAGER_ATTRACTION_GROUP))
                                    .options(List.of(
                                            enableVillagerAttractionOpt,
                                            mainHandItemOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, ITEM_FRAME_GROUP))
                                    .options(List.of(
                                            enableInvisibleItemFrameOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, TORCH_HIT_GROUP))
                                    .options(List.of(
                                            enableTorchHitOpt,
                                            enableMobTorchHitOpt,
                                            torchHitFireChanceOpt,
                                            torchHitFireDurationOpt,
                                            extraTorchItemsOpt,
                                            extraSoulTorchItemsOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, TRAMPLING_PREVENTION_GROUP))
                                    .options(List.of(
                                            enableFarmlandTramplingPreventionOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, ANVIL_REPAIR_GROUP))
                                    .options(List.of(
                                            enableAnvilRepairOpt,
                                            anvilRepairMaterialOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, WATER_CONVERSION_GROUP))
                                    .options(List.of(
                                            enableWaterConversionOpt,
                                            enableMudConversionOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, BELL_PHANTOM_GROUP))
                                    .options(List.of(
                                            enableBellPhantomOpt,
                                            particleDurationOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, HIGHLIGHT_MOBS_GROUP))
                                    .options(List.of(
                                            enableHighlightMobsOpt,
                                            highlightDurationOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, SPONGE_PLACING_GROUP))
                                    .options(List.of(
                                            enableSpongePlacingOpt,
                                            enableSpongePlacingSneakingOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, HEALING_CAMPFIRE_GROUP))
                                    .options(List.of(
                                            enableHealingCampfireOpt,
                                            checkEveryTickOpt,
                                            healingRadiusOpt,
                                            effectDurationOpt,
                                            effectLevelOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, RIGHT_CLICK_HARVEST_GROUP))
                                    .options(List.of(
                                            enableRightClickHarvestOpt,
                                            requiredHoeOpt,
                                            hungerCostOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, HARVEST_XP_GROUP))
                                    .options(List.of(
                                            enableHarvestXpOpt,
                                            xpAmountOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, STACKABLE_POTION_GROUP))
                                    .options(List.of(
                                            enableStackablePotionOpt,
                                            throwablePotionCooldownOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, GLOWING_TORCHFLOWER_GROUP))
                                    .options(List.of(
                                            enableGlowingTorchFlowerOpt,
                                            torchFlowerLightLevelOpt,
                                            pottedTorchFlowerLightLevelOpt,
                                            torchFlowerStage1LightLevelOpt,
                                            torchFlowerStage2LightLevelOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, BUSH_PROTECTION_GROUP))
                                    .options(List.of(
                                            enableBushProtectionOpt,
                                            needLeggingsOpt,
                                            permanentVillagersProtectionOpt,
                                            enableSlowerSpeedOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, STONECUTTER_DAMAGE_GROUP))
                                    .options(List.of(
                                            enableStonecutterDamageOpt,
                                            villagerImmunityOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, FORCED_FUELS_GROUP))
                                    .options(List.of(
                                            enableForcedFuelsOpt,
                                            extraForcedFuelsOpt
                                    ))
                                    .build())
                            .build())
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(OTHER_CATEGORY))
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(OTHER_CATEGORY, OTHER_GROUP))
                                    .options(List.of(
                                            enableBlocksOnLilyPadOpt,
                                            enablePaintingSwitchingOpt,
                                            enableCutVineOpt,
                                            enableStopGrowingOpt,
                                            enableShutupNameTagOpt,
                                            enableJukeboxLoopOpt,
                                            enableCakeDropOpt,
                                            enableCeilingTorchOpt,
                                            enableWaterConversionOpt,
                                            enableSafeLavaBucketOpt,
                                            enablePlaceChestOnBoatOpt,
                                            enableNameTagDespawnOpt,
                                            enableSafeHarvestOpt,
                                            enableDoubleDoorOpt,
                                            enableBrokenLeadOpt,
                                            enableStickyPistonOpt,
                                            enableThrowableFireChargeOpt
                                    ))
                                    .build())
                            .build())
                    .save(QolConfig::save);
        });
    }
}

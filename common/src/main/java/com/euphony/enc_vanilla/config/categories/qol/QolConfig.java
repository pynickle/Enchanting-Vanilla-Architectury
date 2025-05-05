package com.euphony.enc_vanilla.config.categories.qol;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.qol.screen.ExtraSoulTorchItemsScreen;
import com.euphony.enc_vanilla.config.categories.qol.screen.ExtraTorchItemsScreen;
import com.euphony.enc_vanilla.utils.Utils;
import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import com.euphony.enc_vanilla.utils.config.DescComponent;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.YACLScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;

public final class QolConfig {
    public static ConfigClassHandler<QolConfig> HANDLER = ConfigClassHandler.createBuilder(QolConfig.class)
            .id(Utils.prefix("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setPath(Path.of("config", EncVanilla.MOD_ID + "/qol.json")).build()
            )
            .build();

    public static void load() {
        HANDLER.load();
    }

    public static void save() {
        HANDLER.save();
    }

    private static final String QOL_CATEGORY = "qol";
    private static final String VILLAGER_ATTRACTION_GROUP = "villager_attraction";
    private static final String ITEM_FRAME_GROUP = "item_frame";
    private static final String TORCH_HIT_GROUP = "torch_hit";
    private static final String TRAMPLING_PREVENTION_GROUP = "trampling_prevention";
    private static final String ANVIL_REPAIR_GROUP = "anvil_repair";
    private static final String WATER_CONVERSION_GROUP = "water_conversion";
    private static final String BELL_PHANTOM_GROUP = "bell_phantom";
    private static final String HIGHLIGHT_MOBS_GROUP = "highlight_mobs";
    private static final String SPONGE_PLACING_GROUP = "sponge_placing";
    private static final String HEALING_CAMPFIRE_GROUP = "healing_campfire";
    private static final String RIGHT_CLICK_HARVEST_GROUP = "right_click_harvest";
    private static final String HARVEST_XP_GROUP = "harvest_xp";
    private static final String STACKABLE_POTION_GROUP = "stackable_potion";
    private static final String OTHER_GROUP = "other";

    @SerialEntry public boolean enableVillagerAttraction = true;
    @SerialEntry public Item mainHandItem = Items.EMERALD;

    @SerialEntry public boolean enableInvisibleItemFrame = true;

    @SerialEntry public boolean enableTorchHit = true;
    @SerialEntry public boolean enableMobTorchHit = true;
    @SerialEntry public int torchHitFireChance = 50;
    @SerialEntry public double torchHitFireDuration = 3.0;
    @SerialEntry public List<String> extraTorchItems = List.of(
            "bonetorch:bonetorch",
            "torchmaster:megatorch",
            "hardcore_torches:lit_torch",
            "magnumtorch:diamond_magnum_torch",
            "magnumtorch:emerald_magnum_torch",
            "magnumtorch:amethyst_magnum_torch",
            "magical_torches:mega_torch",
            "magical_torches:grand_torch",
            "magical_torches:medium_torch",
            "magical_torches:small_torch",
            "pgwbandedtorches:banded_torch_white",
            "pgwbandedtorches:banded_torch_orange",
            "pgwbandedtorches:banded_torch_magenta",
            "pgwbandedtorches:banded_torch_light_blue",
            "pgwbandedtorches:banded_torch_yellow",
            "pgwbandedtorches:banded_torch_lime",
            "pgwbandedtorches:banded_torch_pink",
            "pgwbandedtorches:banded_torch_gray",
            "pgwbandedtorches:banded_torch_light_gray",
            "pgwbandedtorches:banded_torch_cyan",
            "pgwbandedtorches:banded_torch_purple",
            "pgwbandedtorches:banded_torch_blue",
            "pgwbandedtorches:banded_torch_brown",
            "pgwbandedtorches:banded_torch_green",
            "pgwbandedtorches:banded_torch_red",
            "pgwbandedtorches:banded_torch_black"
    );
    @SerialEntry public List<String> extraSoulTorchItems = List.of();

    @SerialEntry public boolean enableFarmlandTramplingPrevention = true;

    @SerialEntry public boolean enableAnvilRepair = true;
    @SerialEntry public Item anvilRepairMaterial = Items.IRON_INGOT;

    @SerialEntry public boolean enableWaterConversion = true;
    @SerialEntry public boolean enableMudConversion = false;

    @SerialEntry public boolean enableBellPhantom = true;
    @SerialEntry public double particleDuration = 2.0D;

    @SerialEntry public boolean enableHighlightMobs = true;
    @SerialEntry public double highlightDuration = 3.0;

    @SerialEntry public boolean enableSpongePlacing = true;
    @SerialEntry public boolean enableSpongePlacingSneaking = false;

    @SerialEntry public boolean enableHealingCampfire = true;
    @SerialEntry public int checkEveryTick = 60;
    @SerialEntry public int healingRadius = 16;
    @SerialEntry public double effectDuration = 5;
    @SerialEntry public int effectLevel = 1;

    @SerialEntry public boolean enableRightClickHarvest = true;
    @SerialEntry public boolean requiredHoe = false;
    @SerialEntry public double hungerCost = 1.0;

    @SerialEntry public boolean enableHarvestXp = true;
    @SerialEntry public int xpAmount = 1;

    @SerialEntry public boolean enableStackablePotion = true;
    @SerialEntry public int throwablePotionCooldown = 20;

    @SerialEntry public boolean enableBlocksOnLilyPad = true;
    @SerialEntry public boolean enablePaintingSwitching = true;
    @SerialEntry public boolean enableCutVine = true;
    @SerialEntry public boolean enableStopGrowing = true;
    @SerialEntry public boolean enableShutupNameTag = true;
    @SerialEntry public boolean enableJukeboxLoop = true;
    @SerialEntry public boolean enableCakeDrop = true;
    @SerialEntry public boolean enableCeilingTorch = true;
    @SerialEntry public boolean enableSafeLavaBucket = false;
    @SerialEntry public boolean enablePlaceChestOnBoat = true;
    @SerialEntry public boolean enableNameTagDespawn = true;
    @SerialEntry public boolean enableSafeHarvest = true;

    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            // Villager Attraction
            Option<Boolean> enableVillagerAttractionOpt = ConfigUtils.<Boolean>getGenericOption("enableVillagerAttraction", "villager_attraction")
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
            Option<Boolean> enableInvisibleItemFrameOpt = ConfigUtils.<Boolean>getGenericOption("enableInvisibleItemFrame", "item_frame")
                    .binding(defaults.enableInvisibleItemFrame,
                            () -> config.enableInvisibleItemFrame,
                            newVal -> config.enableInvisibleItemFrame = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            // Torch Hit
            Option<Boolean> enableTorchHitOpt = ConfigUtils.<Boolean>getGenericOption("enableTorchHit", "torch_hit")
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
            Option<Boolean> enableWaterConversionOpt = ConfigUtils.<Boolean>getGenericOption("enableWaterConversion", "water_conversion")
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
            Option<Boolean> enableSpongePlacingOpt = ConfigUtils.<Boolean>getGenericOption("enableSpongePlacing", "sponge_placing")
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
            Option<Boolean> enableStackablePotionOpt = ConfigUtils.<Boolean>getGenericOption("enableStackablePotion", "stackable_potion")
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

            Option<Boolean> enableShutupNameTagOpt = ConfigUtils.<Boolean>getGenericOption("enableShutupNameTag", "shutup_name_tag")
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

            Option<Boolean> enableCakeDropOpt = ConfigUtils.<Boolean>getGenericOption("enableCakeDrop", "cake_drop")
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
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, OTHER_GROUP))
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
                                            enableSafeHarvestOpt
                                    ))
                                    .build())
                            .build())
                    .save(QolConfig::save);
        });
    }

}

package com.euphony.enc_vanilla.config.categories.qol;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.utils.Utils;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.nio.file.Path;
import java.util.List;

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

    public static final String QOL_CATEGORY = "qol";
    public static final String OTHER_CATEGORY = "other";

    public static final String VILLAGER_ATTRACTION_GROUP = "villager_attraction";
    public static final String ITEM_FRAME_GROUP = "item_frame";
    public static final String TORCH_HIT_GROUP = "torch_hit";
    public static final String TRAMPLING_PREVENTION_GROUP = "trampling_prevention";
    public static final String ANVIL_REPAIR_GROUP = "anvil_repair";
    public static final String WATER_CONVERSION_GROUP = "water_conversion";
    public static final String BELL_PHANTOM_GROUP = "bell_phantom";
    public static final String HIGHLIGHT_MOBS_GROUP = "highlight_mobs";
    public static final String SPONGE_PLACING_GROUP = "sponge_placing";
    public static final String HEALING_CAMPFIRE_GROUP = "healing_campfire";
    public static final String RIGHT_CLICK_HARVEST_GROUP = "right_click_harvest";
    public static final String HARVEST_XP_GROUP = "harvest_xp";
    public static final String STACKABLE_POTION_GROUP = "stackable_potion";
    public static final String GLOWING_TORCHFLOWER_GROUP = "glowing_torchflower";
    public static final String BUSH_PROTECTION_GROUP = "bush_protection";
    public static final String STONECUTTER_DAMAGE_GROUP = "stonecutter_damage";
    public static final String FORCED_FUELS_GROUP = "forced_fuels";
    public static final String OTHER_GROUP = "other";

    @SerialEntry public boolean enableVillagerAttraction = true;
    @SerialEntry public Item mainHandItem = Items.EMERALD;
    @SerialEntry public boolean enableOffHandItem = true;

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

    @SerialEntry public boolean enableGlowingTorchFlower = true;
    @SerialEntry public int torchFlowerLightLevel = 12;
    @SerialEntry public int pottedTorchFlowerLightLevel = 14;
    @SerialEntry public int torchFlowerStage1LightLevel = 3;
    @SerialEntry public int torchFlowerStage2LightLevel = 7;

    @SerialEntry public boolean enableBushProtection = true;
    @SerialEntry public boolean needLeggings = true;
    @SerialEntry public boolean permanentVillagersProtection = true;
    @SerialEntry public boolean enableSlowerSpeed = true;

    @SerialEntry public boolean enableStonecutterDamage = true;
    @SerialEntry public boolean villagerImmunity = true;

    @SerialEntry public boolean enableForcedFuels = true;
    @SerialEntry public List<String> extraForcedFuels = List.of();

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
    @SerialEntry public boolean enableDoubleDoor = true;
    @SerialEntry public boolean enableBrokenLead = true;
    @SerialEntry public boolean enableStickyPiston = true;
    @SerialEntry public boolean enableThrowableFireCharge = true;
}

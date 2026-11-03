package com.naturareimagined.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class NRConfig {

    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.DoubleValue FUSEWOOD_EXPLOSION_POWER;
    public static final ModConfigSpec.BooleanValue FUSEWOOD_BREAKS_BLOCKS;
    public static final ModConfigSpec.BooleanValue GENERATE_BERRY_BUSHES;
    public static final ModConfigSpec.IntValue BERRY_BUSH_RARITY;
    public static final ModConfigSpec.BooleanValue GENERATE_BLUEBELLS;
    public static final ModConfigSpec.IntValue BLUEBELL_RARITY;
    public static final ModConfigSpec.BooleanValue GRASS_DROPS_SEEDS;
    public static final ModConfigSpec.DoubleValue GRASS_SEED_CHANCE;
    public static final ModConfigSpec.BooleanValue ENABLE_WHEAT_FLOUR;
    public static final ModConfigSpec.BooleanValue GENERATE_CLOUDS;
    public static final ModConfigSpec.IntValue CLOUD_RARITY;
    public static final ModConfigSpec.DoubleValue SULFUR_CLOUD_EXPLOSION;
    public static final ModConfigSpec.BooleanValue GENERATE_SAGUARO;
    public static final ModConfigSpec.IntValue SAGUARO_RARITY;
    public static final ModConfigSpec.BooleanValue GENERATE_GLOWSHROOMS;
    public static final ModConfigSpec.IntValue GLOWSHROOM_RARITY;
    public static final ModConfigSpec.BooleanValue GENERATE_THORNVINES;
    public static final ModConfigSpec.BooleanValue GENERATE_TAINTED_SOIL;
    public static final ModConfigSpec.BooleanValue GENERATE_HEAT_SAND;
    public static final ModConfigSpec.BooleanValue GENERATE_REDWOOD;
    public static final ModConfigSpec.IntValue REDWOOD_MIN_HEIGHT;
    public static final ModConfigSpec.IntValue REDWOOD_MAX_HEIGHT;
    public static final ModConfigSpec.DoubleValue BLAZE_RAIL_SPEED;
    public static final ModConfigSpec.IntValue FLINT_AND_BLAZE_DURABILITY;
    public static final ModConfigSpec.BooleanValue SPAWN_IMP;
    public static final ModConfigSpec.BooleanValue SPAWN_NITRO;
    public static final ModConfigSpec.BooleanValue SPAWN_HEATSCAR;
    public static final ModConfigSpec.IntValue HEATSCAR_BABY_MIN;
    public static final ModConfigSpec.IntValue HEATSCAR_BABY_MAX;
    public static final ModConfigSpec.BooleanValue ADD_OVERWORLD_CHEST_LOOT;
    public static final ModConfigSpec.BooleanValue ADD_NETHER_CHEST_LOOT;
    public static final ModConfigSpec.BooleanValue FURNACE_COOKS_FROM_FLAME;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("mechanics");
        FUSEWOOD_EXPLOSION_POWER = builder
                .comment("Explosion power when a player breaks a fusewood log. Peaceful difficulty never explodes.")
                .defineInRange("fusewoodExplosionPower", 2.0D, 0.0D, 16.0D);
        FUSEWOOD_BREAKS_BLOCKS = builder
                .comment("If true, a fusewood explosion breaks blocks. Default keeps the blast as a hazard only.")
                .define("fusewoodBreaksBlocks", false);
        builder.pop();
        builder.push("plants");
        GENERATE_BERRY_BUSHES = builder
                .comment("Place berry bushes in matching biomes.")
                .define("generateBerryBushes", true);
        BERRY_BUSH_RARITY = builder
                .comment("A bush tries to place about once every this many chunks in its biomes. Lower is more common.")
                .defineInRange("berryBushRarity", 6, 1, 1000);
        GENERATE_BLUEBELLS = builder
                .comment("Place bluebell patches in forests.")
                .define("generateBluebells", true);
        BLUEBELL_RARITY = builder
                .comment("A bluebell patch tries to place about once every this many forest chunks.")
                .defineInRange("bluebellRarity", 12, 1, 1000);
        GRASS_DROPS_SEEDS = builder
                .comment("Breaking short or tall grass can drop barley seeds and cotton seeds.")
                .define("grassDropsCropSeeds", true);
        GRASS_SEED_CHANCE = builder
                .comment("Chance, rolled separately for barley and cotton, when grass is broken without shears.")
                .defineInRange("grassCropSeedChance", 0.0625, 0.0, 1.0);
        ENABLE_WHEAT_FLOUR = builder
                .comment("Allow crafting wheat into wheat flour. The flour still smelts into bread.")
                .define("enableWheatFlourRecipe", true);
        builder.pop();
        builder.push("world");
        GENERATE_CLOUDS = builder.comment("Place clouds in the Nether, between y 32 and 120.").define("generateClouds", true);
        CLOUD_RARITY = builder.defineInRange("cloudRarity", 40, 1, 1000);
        SULFUR_CLOUD_EXPLOSION = builder.defineInRange("sulfurCloudExplosion", 3.0, 0.0, 16.0);
        GENERATE_SAGUARO = builder.define("generateSaguaro", true);
        SAGUARO_RARITY = builder.defineInRange("saguaroRarity", 5, 1, 1000);
        GENERATE_GLOWSHROOMS = builder.define("generateGlowshrooms", true);
        GLOWSHROOM_RARITY = builder.defineInRange("glowshroomRarity", 16, 1, 1000);
        GENERATE_THORNVINES = builder.define("generateThornvines", true);
        GENERATE_TAINTED_SOIL = builder.define("generateTaintedSoil", true);
        GENERATE_HEAT_SAND = builder.define("generateHeatSand", true);
        GENERATE_REDWOOD = builder.comment("Natural redwood placement. Saplings still grow when this is off.").define("generateRedwood", false);
        REDWOOD_MIN_HEIGHT = builder.defineInRange("redwoodMinHeight", 24, 8, 48);
        REDWOOD_MAX_HEIGHT = builder.defineInRange("redwoodMaxHeight", 40, 8, 64);
        BLAZE_RAIL_SPEED = builder.comment("Minecart top speed on blaze rails. Vanilla rails use 0.4.").defineInRange("blazeRailSpeed", 0.8, 0.1, 2.0);
        FLINT_AND_BLAZE_DURABILITY = builder.defineInRange("flintAndBlazeDurability", 64, 1, 10000);
        FURNACE_COOKS_FROM_FLAME = builder
                .comment("If true, a netherrack furnace cooks without fuel while fire or soul fire is directly below it. Fuel still works when no flame is there. Set this to false for a vanilla furnace made of netherrack.")
                .define("netherrackFurnaceCooksFromFlame", true);
        builder.pop();
        builder.push("mobs");
        SPAWN_IMP = builder.define("spawnImp", true);
        SPAWN_NITRO = builder.define("spawnNitroCreeper", true);
        SPAWN_HEATSCAR = builder.comment("Heatscar spiders are off by default because they leave babies behind.").define("spawnHeatscar", false);
        HEATSCAR_BABY_MIN = builder.defineInRange("heatscarBabyMin", 2, 0, 8);
        HEATSCAR_BABY_MAX = builder.defineInRange("heatscarBabyMax", 4, 0, 8);
        builder.pop();
        builder.push("loot");
        ADD_OVERWORLD_CHEST_LOOT = builder.define("addOverworldChestLoot", true);
        ADD_NETHER_CHEST_LOOT = builder.define("addNetherChestLoot", true);
        builder.pop();
        SPEC = builder.build();
    }

    private NRConfig() {}
}

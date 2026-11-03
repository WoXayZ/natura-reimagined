package com.naturareimagined.world;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRWoods;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class NRBiomes {

    public static final ResourceKey<Biome> REDWOOD = biome("redwood_forest");
    public static final ResourceKey<Biome> EUCALYPTUS = biome("eucalyptus_grove");
    public static final ResourceKey<Biome> HOPSEED = biome("hopseed_woodland");
    public static final ResourceKey<Biome> SAKURA = biome("sakura_grove");
    public static final ResourceKey<Biome> MAPLE = biome("maple_woods");
    public static final ResourceKey<Biome> SILVERBELL = biome("silverbell_forest");
    public static final ResourceKey<Biome> AMARANTH = biome("amaranth_canopy");
    public static final ResourceKey<Biome> TIGER = biome("tigerwood_jungle");
    public static final ResourceKey<Biome> WILLOW = biome("willow_marsh");
    public static final ResourceKey<Biome> GHOSTWOOD = biome("ghostwood_forest");
    public static final ResourceKey<Biome> BLOODWOOD = biome("bloodwood_wilds");
    public static final ResourceKey<Biome> DARKWOOD = biome("darkwood_thicket");
    public static final ResourceKey<Biome> FUSEWOOD = biome("fusewood_wastes");
    public static final ResourceKey<Biome> FUNGAL = biome("fungal_depths");

    public static final ResourceKey<PlacedFeature> REDWOOD_TREES = placed("redwood_forest_trees");
    public static final ResourceKey<PlacedFeature> REDWOOD_MEGA = placed("redwood_forest_mega");
    public static final ResourceKey<PlacedFeature> REDWOOD_WILD = placed("redwood_forest_wild");
    public static final ResourceKey<PlacedFeature> EUCALYPTUS_TREES = placed("eucalyptus_grove_trees");
    public static final ResourceKey<PlacedFeature> HOPSEED_TREES = placed("hopseed_woodland_trees");
    public static final ResourceKey<PlacedFeature> SAKURA_TREES = placed("sakura_grove_trees");
    public static final ResourceKey<PlacedFeature> MAPLE_TREES = placed("maple_woods_trees");
    public static final ResourceKey<PlacedFeature> SILVERBELL_TREES = placed("silverbell_forest_trees");
    public static final ResourceKey<PlacedFeature> AMARANTH_TREES = placed("amaranth_canopy_trees");
    public static final ResourceKey<PlacedFeature> TIGER_TREES = placed("tigerwood_jungle_trees");
    public static final ResourceKey<PlacedFeature> WILLOW_TREES = placed("willow_marsh_trees");
    public static final ResourceKey<PlacedFeature> GHOSTWOOD_TREES = placed("ghostwood_forest_trees");
    public static final ResourceKey<PlacedFeature> BLOODWOOD_TREES = placed("bloodwood_wilds_trees");
    public static final ResourceKey<PlacedFeature> DARKWOOD_TREES = placed("darkwood_thicket_trees");
    public static final ResourceKey<PlacedFeature> FUSEWOOD_TREES = placed("fusewood_wastes_trees");
    public static final ResourceKey<PlacedFeature> HOPSEED_RASPBERRY = placed("hopseed_raspberry");
    public static final ResourceKey<PlacedFeature> HOPSEED_BLACKBERRY = placed("hopseed_blackberry");
    public static final ResourceKey<PlacedFeature> MAPLE_BLACKBERRY = placed("maple_blackberry");
    public static final ResourceKey<PlacedFeature> SILVERBELL_BLUEBELLS = placed("silverbell_bluebells");
    public static final ResourceKey<PlacedFeature> WILLOW_MALOBERRY = placed("willow_maloberry");
    public static final ResourceKey<PlacedFeature> FUNGAL_GLOWSHROOMS = placed("fungal_glowshrooms");
    public static final ResourceKey<PlacedFeature> PYREFLY = placed("pyrefly_bush");
    public static final ResourceKey<PlacedFeature> PYREFLY_RARE = placed("pyrefly_bush_rare");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PYREFLY_CONFIGURED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id("pyrefly_bush"));

    private static final int WATER = 4159204;
    private static final int WATER_FOG = 329011;
    private static final int FOG = 12638463;

    private NRBiomes() {}

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(REDWOOD, redwood(placed, carvers));
        context.register(EUCALYPTUS, eucalyptus(placed, carvers));
        context.register(HOPSEED, hopseed(placed, carvers));
        context.register(SAKURA, sakura(placed, carvers));
        context.register(MAPLE, maple(placed, carvers));
        context.register(SILVERBELL, silverbell(placed, carvers));
        context.register(AMARANTH, amaranth(placed, carvers));
        context.register(TIGER, tiger(placed, carvers));
        context.register(WILLOW, willow(placed, carvers));
        context.register(GHOSTWOOD, ghostwood(placed, carvers));
        context.register(BLOODWOOD, bloodwood(placed, carvers));
        context.register(DARKWOOD, darkwood(placed, carvers));
        context.register(FUSEWOOD, fusewood(placed, carvers));
        context.register(FUNGAL, fungal(placed, carvers));
    }

    private static Biome redwood(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = overworldMobs();
        mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4));
        mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
        mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
        BiomeGenerationSettings.Builder world = base(placed, carvers);
        BiomeDefaultFeatures.addMossyStoneBlock(world);
        BiomeDefaultFeatures.addFerns(world);
        BiomeDefaultFeatures.addDefaultOres(world);
        BiomeDefaultFeatures.addDefaultSoftDisks(world);
        feature(world, REDWOOD_TREES);
        feature(world, REDWOOD_MEGA);
        feature(world, REDWOOD_WILD);
        BiomeDefaultFeatures.addDefaultFlowers(world);
        BiomeDefaultFeatures.addGiantTaigaVegetation(world);
        BiomeDefaultFeatures.addDefaultMushrooms(world);
        BiomeDefaultFeatures.addDefaultExtraVegetation(world);
        return overworldBiome(true, 0.3F, 0.85F, null, 0x2F5D32, WATER, WATER_FOG, FOG, mobs.build(), world.build());
    }

    private static Biome eucalyptus(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder world = ground(placed, carvers);
        feature(world, EUCALYPTUS_TREES);
        BiomeDefaultFeatures.addDefaultFlowers(world);
        BiomeDefaultFeatures.addForestGrass(world);
        BiomeDefaultFeatures.addDefaultMushrooms(world);
        BiomeDefaultFeatures.addDefaultExtraVegetation(world);
        return overworldBiome(true, 0.95F, 0.4F, 0x8FB4D6, 0x7FA36A, WATER, WATER_FOG, FOG, overworldMobs().build(), world.build());
    }

    private static Biome hopseed(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder world = base(placed, carvers);
        BiomeDefaultFeatures.addForestFlowers(world);
        BiomeDefaultFeatures.addDefaultOres(world);
        BiomeDefaultFeatures.addDefaultSoftDisks(world);
        feature(world, HOPSEED_TREES);
        BiomeDefaultFeatures.addDefaultFlowers(world);
        BiomeDefaultFeatures.addForestGrass(world);
        BiomeDefaultFeatures.addDefaultMushrooms(world);
        BiomeDefaultFeatures.addDefaultExtraVegetation(world);
        feature(world, HOPSEED_RASPBERRY);
        feature(world, HOPSEED_BLACKBERRY);
        return overworldBiome(true, 0.7F, 0.8F, null, null, WATER, WATER_FOG, FOG, overworldMobs().build(), world.build());
    }

    private static Biome sakura(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = overworldMobs();
        mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(NRContent.SAKURA_FOX.get(), 10, 2, 4));
        BiomeGenerationSettings.Builder world = base(placed, carvers);
        BiomeDefaultFeatures.addPlainGrass(world);
        BiomeDefaultFeatures.addDefaultOres(world);
        BiomeDefaultFeatures.addDefaultSoftDisks(world);
        world.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        world.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_CHERRY);
        feature(world, SAKURA_TREES);
        return overworldBiome(true, 0.5F, 0.8F, 0xE7C2D0, 0xE7B6C8, 0xF3C1D8, 0xC48AA8, FOG, mobs.build(), world.build());
    }

    private static Biome maple(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder world = base(placed, carvers);
        BiomeDefaultFeatures.addForestFlowers(world);
        BiomeDefaultFeatures.addDefaultOres(world);
        BiomeDefaultFeatures.addDefaultSoftDisks(world);
        feature(world, MAPLE_TREES);
        BiomeDefaultFeatures.addDefaultFlowers(world);
        BiomeDefaultFeatures.addForestGrass(world);
        BiomeDefaultFeatures.addDefaultMushrooms(world);
        BiomeDefaultFeatures.addDefaultExtraVegetation(world);
        feature(world, MAPLE_BLACKBERRY);
        return overworldBiome(true, 0.6F, 0.7F, 0xC4782A, 0xCC5718, WATER, WATER_FOG, FOG, overworldMobs().build(), world.build());
    }

    private static Biome silverbell(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = overworldMobs();
        mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 6, 2, 4));
        mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 6, 2, 3));
        BiomeGenerationSettings.Builder world = base(placed, carvers);
        BiomeDefaultFeatures.addFerns(world);
        BiomeDefaultFeatures.addDefaultOres(world);
        BiomeDefaultFeatures.addDefaultSoftDisks(world);
        feature(world, SILVERBELL_TREES);
        BiomeDefaultFeatures.addDefaultFlowers(world);
        BiomeDefaultFeatures.addTaigaGrass(world);
        BiomeDefaultFeatures.addDefaultExtraVegetation(world);
        feature(world, SILVERBELL_BLUEBELLS);
        return overworldBiome(true, 0.25F, 0.65F, 0xC5D6C8, 0xD7E4DC, WATER, WATER_FOG, 0xE7EEF4, mobs.build(), world.build());
    }

    private static Biome amaranth(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobs);
        mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(NRContent.AMARANTH_BEE.get(), 8, 2, 3));
        BiomeGenerationSettings.Builder world = base(placed, carvers);
        world.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_FOREST_FLOWERS);
        BiomeDefaultFeatures.addDefaultOres(world);
        BiomeDefaultFeatures.addDefaultSoftDisks(world);
        feature(world, AMARANTH_TREES);
        world.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_FLOWER_FOREST);
        BiomeDefaultFeatures.addDefaultGrass(world);
        BiomeDefaultFeatures.addDefaultMushrooms(world);
        BiomeDefaultFeatures.addDefaultExtraVegetation(world);
        return overworldBiome(true, 0.9F, 0.95F, 0x4F7A3A, 0x3F6B32, WATER, WATER_FOG, FOG, mobs.build(), world.build());
    }

    private static Biome tiger(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = overworldMobs();
        mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 8, 1, 2));
        mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 10, 1, 2));
        BiomeGenerationSettings.Builder world = ground(placed, carvers);
        BiomeDefaultFeatures.addLightBambooVegetation(world);
        feature(world, TIGER_TREES);
        BiomeDefaultFeatures.addWarmFlowers(world);
        BiomeDefaultFeatures.addJungleGrass(world);
        BiomeDefaultFeatures.addDefaultMushrooms(world);
        BiomeDefaultFeatures.addDefaultExtraVegetation(world);
        BiomeDefaultFeatures.addJungleVines(world);
        BiomeDefaultFeatures.addJungleMelons(world);
        return overworldBiome(true, 0.95F, 0.9F, 0x30B04A, 0x1F8A32, WATER, WATER_FOG, FOG, mobs.build(), world.build());
    }

    private static Biome willow(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = overworldMobs();
        mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));
        mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(NRContent.WILLOW_FROG.get(), 10, 2, 5));
        mobs.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1, 1));
        BiomeGenerationSettings.Builder world = new BiomeGenerationSettings.Builder(placed, carvers);
        BiomeDefaultFeatures.addFossilDecoration(world);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(world);
        BiomeDefaultFeatures.addDefaultCrystalFormations(world);
        BiomeDefaultFeatures.addDefaultMonsterRoom(world);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(world);
        BiomeDefaultFeatures.addDefaultSprings(world);
        BiomeDefaultFeatures.addSurfaceFreezing(world);
        BiomeDefaultFeatures.addDefaultOres(world);
        BiomeDefaultFeatures.addSwampClayDisk(world);
        feature(world, WILLOW_TREES);
        world.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_SWAMP);
        world.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
        world.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
        world.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
        world.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_SWAMP);
        world.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_SWAMP);
        BiomeDefaultFeatures.addDefaultMushrooms(world);
        BiomeDefaultFeatures.addSwampExtraVegetation(world);
        world.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
        feature(world, WILLOW_MALOBERRY);
        return overworldBiome(true, 0.8F, 0.9F, 0x6A7039, 0x6A7039, 0x4C7631, 0x2A4A22, FOG, mobs.build(), world.build());
    }

    private static Biome ghostwood(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings mobs = netherMobs()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 40, 2, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 4, 2, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HOGLIN, 4, 2, 3))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(NRContent.GHOST_BAT.get(), 36, 4, 8))
                .build();
        BiomeGenerationSettings.Builder world = nether(placed, carvers);
        feature(world, GHOSTWOOD_TREES);
        return netherBiome(0.4F, 0xC8D0C0, SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP, SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY, mobs, world.build());
    }

    private static Biome bloodwood(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings mobs = netherMobs()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 15, 3, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HOGLIN, 12, 2, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(NRContent.CINDERLIN.get(), 36, 2, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(NRContent.ASHEN_SKELETON.get(), 28, 2, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(NRContent.HELLHOUND.get(), 24, 2, 4))
                .build();
        BiomeGenerationSettings.Builder world = nether(placed, carvers);
        feature(world, BLOODWOOD_TREES);
        return netherBiome(2.0F, 0x330303, SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP, SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, SoundEvents.MUSIC_BIOME_CRIMSON_FOREST, mobs, world.build());
    }

    private static Biome darkwood(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings mobs = netherMobs()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 4, 2, 3))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(NRContent.ASHEN_SKELETON.get(), 36, 2, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(NRContent.HELLHOUND.get(), 24, 2, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 2, 1, 4))
                .build();
        BiomeGenerationSettings.Builder world = nether(placed, carvers);
        feature(world, DARKWOOD_TREES);
        return netherBiome(2.0F, 0x0C0A0A, SoundEvents.AMBIENT_WARPED_FOREST_LOOP, SoundEvents.AMBIENT_WARPED_FOREST_MOOD, SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS, SoundEvents.MUSIC_BIOME_WARPED_FOREST, mobs, world.build());
    }

    private static Biome fusewood(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings mobs = netherMobs()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 20, 2, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(NRContent.ASHEN_SKELETON.get(), 28, 2, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(NRContent.HELLHOUND.get(), 20, 2, 4))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(NRContent.EMBER_BAT.get(), 24, 3, 6))
                .build();
        BiomeGenerationSettings.Builder world = nether(placed, carvers);
        feature(world, FUSEWOOD_TREES);
        return netherBiome(2.0F, 0x5A4030, SoundEvents.AMBIENT_BASALT_DELTAS_LOOP, SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, SoundEvents.MUSIC_BIOME_BASALT_DELTAS, mobs, world.build());
    }

    private static Biome fungal(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings mobs = netherMobs()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 4, 2, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HOGLIN, 2, 1, 2))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(NRContent.GHOST_BAT.get(), 24, 3, 6))
                .build();
        BiomeGenerationSettings.Builder world = nether(placed, carvers);
        feature(world, FUNGAL_GLOWSHROOMS);
        return netherBiome(2.0F, 0x1A1030, SoundEvents.AMBIENT_NETHER_WASTES_LOOP, SoundEvents.AMBIENT_NETHER_WASTES_MOOD, SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, SoundEvents.MUSIC_BIOME_NETHER_WASTES, mobs, world.build());
    }

    private static MobSpawnSettings.Builder netherMobs() {
        return new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 40, 1, 2));
    }

    private static BiomeGenerationSettings.Builder nether(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placed, carvers);
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, net.minecraft.data.worldgen.placement.MiscOverworldPlacements.SPRING_LAVA);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NETHER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.RED_MUSHROOM_NETHER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED);
        BiomeDefaultFeatures.addNetherDefaultOres(builder);
        return builder;
    }

    private static Biome netherBiome(float temperature, int fog, Holder<net.minecraft.sounds.SoundEvent> loop, Holder<net.minecraft.sounds.SoundEvent> mood, Holder<net.minecraft.sounds.SoundEvent> additions, Holder<net.minecraft.sounds.SoundEvent> music, MobSpawnSettings spawns, BiomeGenerationSettings generation) {
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(temperature)
                .downfall(0.0F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(WATER)
                        .waterFogColor(WATER_FOG)
                        .fogColor(fog)
                        .skyColor(sky(temperature))
                        .ambientLoopSound(loop)
                        .ambientMoodSound(new AmbientMoodSettings(mood, 6000, 8, 2.0))
                        .ambientAdditionsSound(new AmbientAdditionsSettings(additions, 0.0111))
                        .backgroundMusic(Musics.createGameMusic(music))
                        .build())
                .mobSpawnSettings(spawns)
                .generationSettings(generation)
                .build();
    }

    private static Biome overworldBiome(boolean rain, float temperature, float downfall, Integer grass, Integer foliage, int water, int waterFog, int fog, MobSpawnSettings spawns, BiomeGenerationSettings generation) {
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .waterColor(water)
                .waterFogColor(waterFog)
                .fogColor(fog)
                .skyColor(sky(temperature))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
        if (grass != null) {
            effects.grassColorOverride(grass);
        }
        if (foliage != null) {
            effects.foliageColorOverride(foliage);
        }
        return new Biome.BiomeBuilder()
                .hasPrecipitation(rain)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects.build())
                .mobSpawnSettings(spawns)
                .generationSettings(generation)
                .build();
    }

    private static MobSpawnSettings.Builder overworldMobs() {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(mobs);
        BiomeDefaultFeatures.commonSpawns(mobs);
        return mobs;
    }

    private static BiomeGenerationSettings.Builder base(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placed, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        return builder;
    }

    private static BiomeGenerationSettings.Builder ground(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder builder = base(placed, carvers);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);
        return builder;
    }

    private static void feature(BiomeGenerationSettings.Builder builder, ResourceKey<PlacedFeature> feature) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, feature);
    }

    private static int sky(float temperature) {
        float value = Mth.clamp(temperature / 3.0F, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - value * 0.05F, 0.5F + value * 0.1F, 1.0F);
    }

    private static ResourceKey<Biome> biome(String name) {
        return ResourceKey.create(Registries.BIOME, NaturaConstants.id(name));
    }

    private static ResourceKey<PlacedFeature> placed(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NaturaConstants.id(name));
    }

    public static ResourceKey<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> configuredTree(String family) {
        return NRWoods.named(family).treeFeature;
    }
}

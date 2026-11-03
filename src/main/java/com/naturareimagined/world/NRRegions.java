package com.naturareimagined.world;

import com.mojang.datafixers.util.Pair;
import com.naturareimagined.NaturaConstants;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRPlants;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import terrablender.api.ModifiedVanillaOverworldBuilder;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

public final class NRRegions {

    private NRRegions() {}

    public static void register() {
        Regions.register(new Overworld(NaturaConstants.id("overworld"), 4));
        Regions.register(new Nether(NaturaConstants.id("nether"), 3));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, NaturaConstants.MOD_ID, overworldSurface());
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.NETHER, NaturaConstants.MOD_ID, netherSurface());
    }

    private static SurfaceRules.RuleSource overworldSurface() {
        SurfaceRules.RuleSource redwood = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PATCH, 0.15), SurfaceRules.state(Blocks.PODZOL.defaultBlockState())),
                SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState()));
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(NRBiomes.REDWOOD), floor(redwood)),
                carpet(NRBiomes.MAPLE, NRPlants.AUTUMNAL.block().get()),
                carpet(NRBiomes.SILVERBELL, NRPlants.BLUEGRASS.block().get()),
                carpet(NRBiomes.EUCALYPTUS, NRPlants.BLUEGRASS.block().get()),
                carpet(NRBiomes.AMARANTH, NRPlants.TOPIARY.block().get()));
    }

    private static SurfaceRules.RuleSource netherSurface() {
        SurfaceRules.ConditionSource belowRoof = SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(108), 0));
        return SurfaceRules.ifTrue(belowRoof, SurfaceRules.sequence(
                nylium(NRBiomes.GHOSTWOOD, NRContent.GHOST_NYLIUM.get().defaultBlockState()),
                nylium(NRBiomes.BLOODWOOD, NRContent.BLOOD_NYLIUM.get().defaultBlockState()),
                nylium(NRBiomes.DARKWOOD, NRContent.DARK_NYLIUM.get().defaultBlockState()),
                nylium(NRBiomes.FUSEWOOD, NRContent.FUSE_NYLIUM.get().defaultBlockState()),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(NRBiomes.FUNGAL),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PATCH, -0.15),
                                        SurfaceRules.state(NRContent.TAINTED_SOIL.get().defaultBlockState()))))));
    }

    private static SurfaceRules.RuleSource carpet(ResourceKey<Biome> biome, Block block) {
        return SurfaceRules.ifTrue(SurfaceRules.isBiome(biome), floor(SurfaceRules.state(block.defaultBlockState())));
    }

    private static SurfaceRules.RuleSource nylium(ResourceKey<Biome> biome, net.minecraft.world.level.block.state.BlockState state) {
        return SurfaceRules.ifTrue(SurfaceRules.isBiome(biome),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(state)));
    }

    private static SurfaceRules.RuleSource floor(SurfaceRules.RuleSource state) {
        return SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), state));
    }

    public static final class Overworld extends Region {
        public Overworld(net.minecraft.resources.ResourceLocation name, int weight) {
            super(name, RegionType.OVERWORLD, weight);
        }

        @Override
        public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
            this.addModifiedVanillaOverworldBiomes(mapper, NRRegions::replaceOverworld);
        }
    }

    private static void replaceOverworld(ModifiedVanillaOverworldBuilder builder) {
        builder.replaceBiome(Biomes.FOREST, NRBiomes.HOPSEED);
        builder.replaceBiome(Biomes.BIRCH_FOREST, NRBiomes.MAPLE);
        builder.replaceBiome(Biomes.CHERRY_GROVE, NRBiomes.SAKURA);
        builder.replaceBiome(Biomes.TAIGA, NRBiomes.SILVERBELL);
        builder.replaceBiome(Biomes.SWAMP, NRBiomes.WILLOW);
        builder.replaceBiome(Biomes.SAVANNA, NRBiomes.EUCALYPTUS);
        builder.replaceBiome(Biomes.OLD_GROWTH_PINE_TAIGA, NRBiomes.REDWOOD);
        builder.replaceBiome(Biomes.JUNGLE, NRBiomes.TIGER);
        builder.replaceBiome(Biomes.DARK_FOREST, NRBiomes.AMARANTH);
    }

    public static final class Nether extends Region {
        public Nether(net.minecraft.resources.ResourceLocation name, int weight) {
            super(name, RegionType.NETHER, weight);
        }

        @Override
        public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
            this.addBiome(mapper, point(0.0F), point(0.0F), point(0.0F), point(0.0F), point(0.0F), point(0.0F), 0.0F, Biomes.NETHER_WASTES);
            this.addBiome(mapper, point(0.0F), point(-0.5F), point(0.0F), point(0.0F), point(0.0F), point(0.0F), 0.0F, NRBiomes.GHOSTWOOD);
            this.addBiome(mapper, point(0.4F), point(0.0F), point(0.0F), point(0.0F), point(0.0F), point(0.0F), 0.0F, NRBiomes.BLOODWOOD);
            this.addBiome(mapper, point(0.0F), point(0.5F), point(0.0F), point(0.0F), point(0.0F), point(0.0F), 0.375F, NRBiomes.DARKWOOD);
            this.addBiome(mapper, point(-0.5F), point(0.0F), point(0.0F), point(0.0F), point(0.0F), point(0.0F), 0.175F, NRBiomes.FUSEWOOD);
            this.addBiome(mapper, Climate.Parameter.span(0.1F, 0.3F), Climate.Parameter.span(0.15F, 0.35F), point(0.0F), point(0.0F), point(0.0F), point(0.0F), 0.0F, NRBiomes.FUNGAL);
        }
    }

    private static Climate.Parameter point(float value) {
        return Climate.Parameter.point(value);
    }
}

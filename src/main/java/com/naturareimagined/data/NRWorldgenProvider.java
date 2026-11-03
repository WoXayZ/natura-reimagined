package com.naturareimagined.data;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.content.world.SpecialTreeFeature;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRFeatures;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRWoods;
import com.naturareimagined.world.NRBiomes;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import com.naturareimagined.content.world.ClearTrunkPlacement;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public final class NRWorldgenProvider extends DatapackBuiltinEntriesProvider {

    public static final net.minecraft.core.RegistrySetBuilder BUILDER = new net.minecraft.core.RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, NRWorldgenProvider::configured)
            .add(Registries.PLACED_FEATURE, NRWorldgenProvider::placed)
            .add(Registries.BIOME, NRBiomes::bootstrap);

    public NRWorldgenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(NaturaConstants.MOD_ID));
    }

    private static void configured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        for (WoodFamily family : NRWoods.ALL) {
            if (isSpecial(family.name)) {
                net.minecraft.world.level.block.state.BlockState leaves = family.leaves.get().defaultBlockState();
                net.minecraft.world.level.block.state.BlockState flowering = family.floweringLeaves == null ? leaves : family.floweringLeaves.get().defaultBlockState();
                net.minecraft.world.level.block.state.BlockState fruit = family.fruitLeaves == null ? leaves : family.fruitLeaves.get().defaultBlockState();
                context.register(family.treeFeature, new ConfiguredFeature<>(NRFeatures.SPECIAL_TREE.get(),
                        new SpecialTreeFeature.Config(family.name, family.log.get().defaultBlockState(), leaves, flowering, fruit, false)));
                if (family.megaTree != null) {
                    context.register(family.megaTree, new ConfiguredFeature<>(NRFeatures.SPECIAL_TREE.get(),
                            new SpecialTreeFeature.Config(family.name + "_mega", family.log.get().defaultBlockState(), leaves, flowering, fruit, false)));
                }
                if ("redwood".equals(family.name)) {
                    context.register(NRContent.REDWOOD_WILD, new ConfiguredFeature<>(NRFeatures.SPECIAL_TREE.get(),
                            new SpecialTreeFeature.Config(family.name, family.log.get().defaultBlockState(), leaves, flowering, fruit, true)));
                }
                continue;
            }
            context.register(family.treeFeature, new ConfiguredFeature<>(Feature.TREE, tree(family)));
        }
        for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
            context.register(bush.configured(), new ConfiguredFeature<>(NRFeatures.STACKED_BUSH.get(),
                    new SimpleBlockConfiguration(BlockStateProvider.simple(bush.block().get().defaultBlockState()))));
        }
        context.register(NRPlants.BLUEBELL_CONFIGURED, new ConfiguredFeature<>(NRFeatures.FLOWER_PATCH.get(),
                new SimpleBlockConfiguration(BlockStateProvider.simple(NRPlants.BLUEBELLS.get().defaultBlockState()))));
        scatter(context, NRContent.SAGUARO_CONFIGURED, NRContent.SAGUARO.get());
        scatter(context, NRContent.GLOWSHROOM_CONFIGURED, NRContent.GREEN_GLOWSHROOM.get());
        scatter(context, NRContent.CLOUD_CONFIGURED, NRContent.WHITE_CLOUD.get());
        scatter(context, NRContent.THORN_CONFIGURED, NRContent.THORNVINE.get());
        scatter(context, NRContent.TAINTED_CONFIGURED, NRContent.TAINTED_SOIL.get());
        scatter(context, NRContent.HEAT_SAND_CONFIGURED, NRContent.HEAT_SAND.get());
        context.register(NRBiomes.PYREFLY_CONFIGURED, new ConfiguredFeature<>(NRFeatures.GROUND_PLANT.get(),
                new SimpleBlockConfiguration(BlockStateProvider.simple(NRContent.PYREFLY_BUSH.get()))));
    }

    private static void scatter(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, net.minecraft.world.level.block.Block block) {
        context.register(key, new ConfiguredFeature<>(NRFeatures.SCATTER.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(block))));
    }

    private static TreeConfiguration tree(WoodFamily family) {
        BlockStateProvider dirt = BlockStateProvider.simple(family.nether ? Blocks.NETHERRACK : Blocks.DIRT);
        TrunkPlacer trunk;
        FoliagePlacer foliage;
        switch (family.name) {
            case "sakura" -> {
                trunk = new CherryTrunkPlacer(7, 1, 0,
                        new WeightedListInt(SimpleWeightedRandomList.<net.minecraft.util.valueproviders.IntProvider>builder()
                                .add(ConstantInt.of(1), 1).add(ConstantInt.of(2), 1).add(ConstantInt.of(3), 1).build()),
                        UniformInt.of(2, 4),
                        UniformInt.of(-4, -3),
                        UniformInt.of(-1, 0));
                foliage = new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.25F, 0.25F, 0.16666667F, 0.33333334F);
            }
            case "maple" -> {
                trunk = new StraightTrunkPlacer(6, 3, 0);
                foliage = new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4);
            }
            case "amaranth" -> {
                trunk = new StraightTrunkPlacer(8, 2, 0);
                foliage = new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3);
            }
            case "tiger" -> {
                trunk = new ForkingTrunkPlacer(5, 2, 1);
                foliage = new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0));
            }
            case "silverbell" -> {
                trunk = new StraightTrunkPlacer(6, 2, 0);
                foliage = new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 4);
            }
            default -> {
                trunk = new StraightTrunkPlacer(5, 2, 0);
                foliage = new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3);
            }
        }
        TreeConfiguration.TreeConfigurationBuilder builder = new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(family.log.get()),
                trunk,
                BlockStateProvider.simple(family.leaves.get()),
                foliage,
                new TwoLayersFeatureSize(1, 0, family.name.equals("sakura") ? 2 : 1))
                .dirt(dirt)
                .ignoreVines();
        if ("amaranth".equals(family.name)) {
            builder.decorators(java.util.List.of(new BeehiveDecorator(0.05F)));
        }
        return builder.build();
    }

    private static boolean isSpecial(String name) {
        return name.equals("willow") || name.equals("eucalyptus") || name.equals("hopseed") || name.equals("redwood")
                || name.equals("bloodwood") || name.equals("ghostwood") || name.equals("fusewood") || name.equals("darkwood");
    }

    private static void placed(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configured = context.lookup(Registries.CONFIGURED_FEATURE);
        for (WoodFamily family : NRWoods.ALL) {
            int rarity = switch (family.name) {
                case "silverbell" -> 70;
                case "tiger" -> 30;
                case "eucalyptus" -> 25;
                case "redwood" -> 150;
                case "amaranth" -> 2;
                default -> 10;
            };
            var feature = "redwood".equals(family.name) ? NRContent.REDWOOD_WILD : family.treeFeature;
            context.register(placedKey(family), new PlacedFeature(
                    configured.getOrThrow(feature),
                    List.of(
                            RarityFilter.onAverageOnceEvery(rarity),
                            InSquarePlacement.spread(),
                            PlacementUtils.HEIGHTMAP,
                            BiomeFilter.biome())));
        }
        for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
            List<PlacementModifier> modifiers = new ArrayList<>();
            modifiers.add(InSquarePlacement.spread());
            if (bush.nether()) {
                modifiers.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(110)));
            } else {
                modifiers.add(PlacementUtils.HEIGHTMAP);
            }
            modifiers.add(BiomeFilter.biome());
            context.register(bush.placed(), new PlacedFeature(configured.getOrThrow(bush.configured()), modifiers));
        }
        context.register(NRPlants.BLUEBELL_PLACED, new PlacedFeature(
                configured.getOrThrow(NRPlants.BLUEBELL_CONFIGURED),
                List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
        placeScatter(context, configured, NRContent.SAGUARO_PLACED, NRContent.SAGUARO_CONFIGURED, false);
        placeScatter(context, configured, NRContent.GLOWSHROOM_PLACED, NRContent.GLOWSHROOM_CONFIGURED, true);
        placeScatter(context, configured, NRContent.CLOUD_PLACED, NRContent.CLOUD_CONFIGURED, true);
        placeScatter(context, configured, NRContent.THORN_PLACED, NRContent.THORN_CONFIGURED, true);
        placeScatter(context, configured, NRContent.TAINTED_PLACED, NRContent.TAINTED_CONFIGURED, true);
        placeScatter(context, configured, NRContent.HEAT_SAND_PLACED, NRContent.HEAT_SAND_CONFIGURED, true);
        counted(context, configured, NRBiomes.REDWOOD_TREES, NRWoods.REDWOOD.treeFeature, 2, false, 28, 7);
        counted(context, configured, NRBiomes.REDWOOD_MEGA, NRWoods.REDWOOD.megaTree, 1, false, 36, 9);
        counted(context, configured, NRBiomes.REDWOOD_WILD, NRContent.REDWOOD_WILD, 1, false, 28, 7);
        counted(context, configured, NRBiomes.EUCALYPTUS_TREES, NRWoods.EUCALYPTUS.treeFeature, 4, false, 16, 6);
        counted(context, configured, NRBiomes.HOPSEED_TREES, NRWoods.HOPSEED.treeFeature, 5, false, 12, 5);
        counted(context, configured, NRBiomes.SAKURA_TREES, NRWoods.SAKURA.treeFeature, 4, false, 14, 6);
        counted(context, configured, NRBiomes.MAPLE_TREES, NRWoods.MAPLE.treeFeature, 5, false, 14, 6);
        counted(context, configured, NRBiomes.SILVERBELL_TREES, NRWoods.SILVERBELL.treeFeature, 4, false, 12, 5);
        counted(context, configured, NRBiomes.AMARANTH_TREES, NRWoods.AMARANTH.treeFeature, 6, false, 14, 6);
        counted(context, configured, NRBiomes.TIGER_TREES, NRWoods.TIGER.treeFeature, 5, false, 12, 6);
        counted(context, configured, NRBiomes.WILLOW_TREES, NRWoods.WILLOW.treeFeature, 3, false, 12, 5);
        counted(context, configured, NRBiomes.GHOSTWOOD_TREES, NRWoods.GHOSTWOOD.treeFeature, 4, true, 16, 5);
        counted(context, configured, NRBiomes.BLOODWOOD_TREES, NRWoods.BLOODWOOD.treeFeature, 3, true, 16, 5);
        counted(context, configured, NRBiomes.DARKWOOD_TREES, NRWoods.DARKWOOD.treeFeature, 5, true, 12, 5);
        counted(context, configured, NRBiomes.FUSEWOOD_TREES, NRWoods.FUSEWOOD.treeFeature, 2, true, 10, 5);
        counted(context, configured, NRBiomes.HOPSEED_RASPBERRY, bushConfigured("raspberry"), 4);
        counted(context, configured, NRBiomes.HOPSEED_BLACKBERRY, bushConfigured("blackberry"), 3);
        counted(context, configured, NRBiomes.MAPLE_BLACKBERRY, bushConfigured("blackberry"), 3);
        counted(context, configured, NRBiomes.SILVERBELL_BLUEBELLS, NRPlants.BLUEBELL_CONFIGURED, 5);
        counted(context, configured, NRBiomes.WILLOW_MALOBERRY, bushConfigured("maloberry"), 4);
        counted(context, configured, NRBiomes.FUNGAL_GLOWSHROOMS, NRContent.GLOWSHROOM_CONFIGURED, 28, true, 0, 0);
        context.register(NRBiomes.PYREFLY, new PlacedFeature(configured.getOrThrow(NRBiomes.PYREFLY_CONFIGURED), List.of(
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(96)),
                BiomeFilter.biome())));
        context.register(NRBiomes.PYREFLY_RARE, new PlacedFeature(configured.getOrThrow(NRBiomes.PYREFLY_CONFIGURED), List.of(
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(96)),
                BiomeFilter.biome())));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> bushConfigured(String blockName) {
        for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
            if (bush.blockName().equals(blockName)) {
                return bush.configured();
            }
        }
        throw new IllegalStateException(blockName);
    }

    private static void counted(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configured,
                                ResourceKey<PlacedFeature> placed, ResourceKey<ConfiguredFeature<?, ?>> feature, int count) {
        counted(context, configured, placed, feature, count, false, 0, 0);
    }

    private static void counted(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configured,
                                ResourceKey<PlacedFeature> placed, ResourceKey<ConfiguredFeature<?, ?>> feature, int count,
                                boolean nether, int clearHeight, int clearRadius) {
        List<PlacementModifier> modifiers = new ArrayList<>();
        if (nether) {
            modifiers.add(CountOnEveryLayerPlacement.of(count));
        } else {
            modifiers.add(CountPlacement.of(count));
            modifiers.add(InSquarePlacement.spread());
            modifiers.add(PlacementUtils.HEIGHTMAP);
        }
        if (clearHeight > 0) {
            modifiers.add(new ClearTrunkPlacement(clearHeight, clearRadius));
        }
        modifiers.add(BiomeFilter.biome());
        context.register(placed, new PlacedFeature(configured.getOrThrow(feature), modifiers));
    }

    private static void placeScatter(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configured,
                                     ResourceKey<PlacedFeature> placed, ResourceKey<ConfiguredFeature<?, ?>> feature, boolean nether) {
        List<PlacementModifier> modifiers = new ArrayList<>();
        modifiers.add(InSquarePlacement.spread());
        if (nether) {
            modifiers.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(120)));
        } else {
            modifiers.add(PlacementUtils.HEIGHTMAP);
        }
        modifiers.add(BiomeFilter.biome());
        context.register(placed, new PlacedFeature(configured.getOrThrow(feature), modifiers));
    }

    public static ResourceKey<PlacedFeature> placedKey(WoodFamily family) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NaturaConstants.id(family.name + "_tree"));
    }
}

package com.naturareimagined.content.world;

import com.naturareimagined.config.NRConfig;
import com.naturareimagined.registry.NRContent;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class ScatterFeature extends Feature<SimpleBlockConfiguration> {

    public ScatterFeature() {
        super(SimpleBlockConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        BlockState state = context.config().toPlace().getState(context.random(), context.origin());
        Block block = state.getBlock();
        if (block == NRContent.SAGUARO.get()) {
            return NRConfig.GENERATE_SAGUARO.get() && context.random().nextInt(NRConfig.SAGUARO_RARITY.get()) == 0 && saguaro(context);
        }
        if (block instanceof CloudBlock) {
            return NRConfig.GENERATE_CLOUDS.get() && context.random().nextInt(NRConfig.CLOUD_RARITY.get()) == 0 && cloud(context, state);
        }
        if (block instanceof com.naturareimagined.content.plant.GlowshroomBlock glow) {
            return NRConfig.GENERATE_GLOWSHROOMS.get() && context.random().nextInt(NRConfig.GLOWSHROOM_RARITY.get()) == 0 && glow(context, glow);
        }
        if (block instanceof ThornVineBlock) {
            return NRConfig.GENERATE_THORNVINES.get() && context.random().nextInt(12) == 0 && thorn(context, state);
        }
        if (block == NRContent.TAINTED_SOIL.get()) {
            return NRConfig.GENERATE_TAINTED_SOIL.get() && patch(context, state, Blocks.NETHERRACK);
        }
        if (block == NRContent.HEAT_SAND.get()) {
            return NRConfig.GENERATE_HEAT_SAND.get() && patch(context, state, Blocks.NETHERRACK);
        }
        return false;
    }

    private boolean saguaro(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos floor = context.origin().below();
        if (!saguaroGround(level.getBlockState(floor))) {
            return false;
        }
        int height = 2 + context.random().nextInt(2);
        for (int y = 0; y < height; y++) {
            if (!level.getBlockState(context.origin().above(y)).isAir()) {
                return false;
            }
        }
        for (int y = 0; y < height; y++) {
            level.setBlock(context.origin().above(y), NRContent.SAGUARO.get().defaultBlockState(), Block.UPDATE_CLIENTS);
        }
        if (context.random().nextBoolean()) {
            Direction side = Direction.Plane.HORIZONTAL.getRandomDirection(context.random());
            BlockPos fruit = context.origin().above(height - 1).relative(side);
            if (level.getBlockState(fruit).isAir()) {
                level.setBlock(fruit, NRContent.SAGUARO_FRUIT_BLOCK.get().defaultBlockState()
                        .setValue(net.minecraft.world.level.block.CocoaBlock.AGE, context.random().nextInt(3))
                        .setValue(net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING, side.getOpposite()), Block.UPDATE_CLIENTS);
            }
        }
        return true;
    }

    private static boolean saguaroGround(BlockState state) {
        return state.is(BlockTags.SAND) || state.is(Blocks.NETHERRACK) || state.is(BlockTags.NYLIUM)
                || state.is(Blocks.SOUL_SAND) || state.is(Blocks.SOUL_SOIL) || state.is(NRContent.HEAT_SAND.get());
    }

    private boolean cloud(FeaturePlaceContext<SimpleBlockConfiguration> context, BlockState state) {
        WorldGenLevel level = context.level();
        var random = context.random();
        BlockState cloud = switch (random.nextInt(4)) {
            case 1 -> NRContent.DARK_CLOUD.get().defaultBlockState();
            case 2 -> NRContent.ASH_CLOUD.get().defaultBlockState();
            case 3 -> NRContent.SULFUR_CLOUD.get().defaultBlockState();
            default -> NRContent.WHITE_CLOUD.get().defaultBlockState();
        };
        int radiusX;
        int radiusY;
        int radiusZ;
        switch (random.nextInt(4)) {
            case 1 -> {
                radiusX = 5;
                radiusY = 1;
                radiusZ = 3;
            }
            case 2 -> {
                radiusX = 6;
                radiusY = 1;
                radiusZ = 2;
            }
            case 3 -> {
                radiusX = 3;
                radiusY = 2;
                radiusZ = 3;
            }
            default -> {
                radiusX = 2;
                radiusY = 1;
                radiusZ = 2;
            }
        }
        if (random.nextBoolean()) {
            int swap = radiusX;
            radiusX = radiusZ;
            radiusZ = swap;
        }
        BlockPos origin = context.origin();
        if (this.cloudNearby(level, origin, radiusX + 3, radiusY + 2, radiusZ + 3)) {
            return false;
        }
        ArrayList<BlockPos> spots = new ArrayList<>();
        for (int x = -radiusX; x <= radiusX; x++) {
            for (int y = -radiusY; y <= radiusY; y++) {
                for (int z = -radiusZ; z <= radiusZ; z++) {
                    double nx = x / (double) Math.max(radiusX, 1);
                    double ny = y / (double) Math.max(radiusY, 1);
                    double nz = z / (double) Math.max(radiusZ, 1);
                    if (nx * nx + ny * ny + nz * nz > 1.0D) {
                        continue;
                    }
                    BlockPos pos = origin.offset(x, y, z);
                    if (level.getBlockState(pos).isAir()) {
                        spots.add(pos);
                    }
                }
            }
        }
        if (spots.size() < 8) {
            return false;
        }
        for (BlockPos pos : spots) {
            level.setBlock(pos, cloud, Block.UPDATE_CLIENTS);
        }
        return true;
    }

    private boolean cloudNearby(WorldGenLevel level, BlockPos origin, int radiusX, int radiusY, int radiusZ) {
        for (int x = -radiusX; x <= radiusX; x++) {
            for (int y = -radiusY; y <= radiusY; y++) {
                for (int z = -radiusZ; z <= radiusZ; z++) {
                    if (level.getBlockState(origin.offset(x, y, z)).getBlock() instanceof CloudBlock) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean glow(FeaturePlaceContext<SimpleBlockConfiguration> context, com.naturareimagined.content.plant.GlowshroomBlock glow) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        if (!level.getBlockState(pos).isAir() || !glow.defaultBlockState().canSurvive(level, pos)) {
            return false;
        }
        com.naturareimagined.content.plant.GlowshroomBlock chosen = switch (context.random().nextInt(3)) {
            case 1 -> (com.naturareimagined.content.plant.GlowshroomBlock) NRContent.BLUE_GLOWSHROOM.get();
            case 2 -> (com.naturareimagined.content.plant.GlowshroomBlock) NRContent.PURPLE_GLOWSHROOM.get();
            default -> (com.naturareimagined.content.plant.GlowshroomBlock) NRContent.GREEN_GLOWSHROOM.get();
        };
        if (context.random().nextInt(5) == 0 && com.naturareimagined.content.plant.HugeGlowshroom.grow(level, pos, chosen.color(), context.random())) {
            return true;
        }
        level.setBlock(pos, chosen.defaultBlockState(), Block.UPDATE_CLIENTS);
        return true;
    }

    private boolean thorn(FeaturePlaceContext<SimpleBlockConfiguration> context, BlockState state) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        if (!level.getBlockState(pos).isAir()) {
            return false;
        }
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos wall = pos.relative(direction);
            if (level.getBlockState(wall).isFaceSturdy(level, wall, direction.getOpposite())) {
                level.setBlock(pos, state.setValue(VineBlock.PROPERTY_BY_DIRECTION.get(direction), true), Block.UPDATE_CLIENTS);
                return true;
            }
        }
        return false;
    }

    private boolean patch(FeaturePlaceContext<SimpleBlockConfiguration> context, BlockState state, Block floor) {
        WorldGenLevel level = context.level();
        boolean placed = false;
        for (int i = 0; i < 24; i++) {
            BlockPos pos = context.origin().offset(context.random().nextInt(7) - 3, context.random().nextInt(3) - 1, context.random().nextInt(7) - 3);
            if (level.getBlockState(pos).is(floor)) {
                level.setBlock(pos, state, Block.UPDATE_CLIENTS);
                placed = true;
            }
        }
        return placed;
    }
}

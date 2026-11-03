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
            return heatSand(context, state);
        }
        return false;
    }

    private boolean saguaro(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos floor = context.origin().below();
        if (!saguaroGround(level.getBlockState(floor)) || NetherSpace.nearBedrock(level, context.origin(), 16)) {
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
        BlockPos origin = context.origin();
        if (NetherSpace.nearBedrock(level, origin, 10)) {
            return false;
        }
        BlockState cloud = switch (random.nextInt(4)) {
            case 1 -> NRContent.DARK_CLOUD.get().defaultBlockState();
            case 2 -> NRContent.ASH_CLOUD.get().defaultBlockState();
            case 3 -> NRContent.SULFUR_CLOUD.get().defaultBlockState();
            default -> NRContent.WHITE_CLOUD.get().defaultBlockState();
        };
        int lumps = 3 + random.nextInt(3);
        double[] centerX = new double[lumps];
        double[] centerY = new double[lumps];
        double[] centerZ = new double[lumps];
        double[] radiusX = new double[lumps];
        double[] radiusY = new double[lumps];
        double[] radiusZ = new double[lumps];
        centerX[0] = 0;
        centerY[0] = 0;
        centerZ[0] = 0;
        radiusX[0] = 3.5 + random.nextDouble() * 2.5;
        radiusY[0] = 1.2 + random.nextDouble() * 1.1;
        radiusZ[0] = 3.0 + random.nextDouble() * 2.5;
        for (int lump = 1; lump < lumps; lump++) {
            centerX[lump] = random.nextInt(7) - 3;
            centerY[lump] = random.nextInt(3) - 1;
            centerZ[lump] = random.nextInt(7) - 3;
            radiusX[lump] = 1.6 + random.nextDouble() * 2.2;
            radiusY[lump] = 0.8 + random.nextDouble() * 1.0;
            radiusZ[lump] = 1.6 + random.nextDouble() * 2.2;
        }
        if (this.cloudNearby(level, origin, 8, 4, 8)) {
            return false;
        }
        ArrayList<BlockPos> spots = new ArrayList<>();
        for (int x = -7; x <= 7; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -7; z <= 7; z++) {
                    double field = this.cloudEdge(origin.getX() + x, origin.getY() + y, origin.getZ() + z) * 0.22;
                    for (int lump = 0; lump < lumps; lump++) {
                        double dx = (x - centerX[lump]) / radiusX[lump];
                        double dy = (y - centerY[lump]) / radiusY[lump];
                        double dz = (z - centerZ[lump]) / radiusZ[lump];
                        double distance = dx * dx + dy * dy + dz * dz;
                        if (distance < 1.0) {
                            field += (1.0 - distance) * (lump == 0 ? 1.0 : 0.75);
                        }
                    }
                    if (field < 0.62) {
                        continue;
                    }
                    BlockPos pos = origin.offset(x, y, z);
                    if (level.getBlockState(pos).isAir() && !NetherSpace.nearBedrock(level, pos, 6)) {
                        spots.add(pos);
                    }
                }
            }
        }
        if (spots.size() < 10) {
            return false;
        }
        for (BlockPos pos : spots) {
            level.setBlock(pos, cloud, Block.UPDATE_CLIENTS);
        }
        return true;
    }

    private double cloudEdge(int x, int y, int z) {
        int n = x * 73428767 ^ y * 912839 ^ z * 4534567;
        n = (n << 13) ^ n;
        return 1.0 - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0;
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
        if (!level.getBlockState(pos).isAir() || !glow.defaultBlockState().canSurvive(level, pos)
                || NetherSpace.nearBedrock(level, pos, 16)) {
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
        if (!level.getBlockState(pos).isAir() || NetherSpace.nearBedrock(level, pos, 16)) {
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

    private boolean heatSand(FeaturePlaceContext<SimpleBlockConfiguration> context, BlockState sand) {
        if (!NRConfig.GENERATE_HEAT_SAND.get()) {
            return false;
        }
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(origin.getX(), 96, origin.getZ());
        BlockPos surface = null;
        for (int y = 96; y >= 8; y--) {
            cursor.setY(y);
            if (heatFloor(level, cursor)) {
                surface = cursor.immutable();
                break;
            }
        }
        if (surface == null) {
            return false;
        }
        int radius = 6 + context.random().nextInt(4);
        boolean placed = false;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x * x + z * z > radius * radius) {
                    continue;
                }
                BlockPos at = heatSurface(level, surface.offset(x, 0, z));
                if (at == null) {
                    continue;
                }
                level.setBlock(at, sand, Block.UPDATE_CLIENTS);
                placed = true;
            }
        }
        return placed;
    }

    private static BlockPos heatSurface(WorldGenLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int y = 2; y >= -2; y--) {
            cursor.set(pos.getX(), pos.getY() + y, pos.getZ());
            if (heatFloor(level, cursor)) {
                return cursor.immutable();
            }
        }
        return null;
    }

    private static boolean heatFloor(WorldGenLevel level, BlockPos pos) {
        if (!netherGround(level.getBlockState(pos))) {
            return false;
        }
        BlockPos below = pos.below();
        BlockState support = level.getBlockState(below);
        if (support.is(Blocks.BEDROCK) || !support.isFaceSturdy(level, below, Direction.UP)) {
            return false;
        }
        return NetherSpace.openColumn(level, pos, 6);
    }

    private static boolean netherGround(BlockState state) {
        return state.is(Blocks.NETHERRACK) || state.is(BlockTags.NYLIUM) || state.is(BlockTags.BASE_STONE_NETHER)
                || state.is(Blocks.SOUL_SAND) || state.is(Blocks.SOUL_SOIL) || state.is(Blocks.GRAVEL)
                || state.is(Blocks.BLACKSTONE) || state.is(Blocks.BASALT) || state.is(Blocks.SMOOTH_BASALT)
                || state.is(NRContent.GHOST_NYLIUM.get()) || state.is(NRContent.BLOOD_NYLIUM.get())
                || state.is(NRContent.DARK_NYLIUM.get()) || state.is(NRContent.FUSE_NYLIUM.get());
    }

    private boolean patch(FeaturePlaceContext<SimpleBlockConfiguration> context, BlockState state, Block floor) {
        WorldGenLevel level = context.level();
        boolean placed = false;
        for (int i = 0; i < 24; i++) {
            BlockPos pos = context.origin().offset(context.random().nextInt(7) - 3, context.random().nextInt(3) - 1, context.random().nextInt(7) - 3);
            if (level.getBlockState(pos).is(floor) && !NetherSpace.nearBedrock(level, pos, 16)) {
                level.setBlock(pos, state, Block.UPDATE_CLIENTS);
                placed = true;
            }
        }
        return placed;
    }
}

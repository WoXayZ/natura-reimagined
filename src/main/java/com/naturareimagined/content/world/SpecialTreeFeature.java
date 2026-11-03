package com.naturareimagined.content.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naturareimagined.config.NRConfig;
import com.naturareimagined.gameplay.NRAdvancements;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class SpecialTreeFeature extends Feature<SpecialTreeFeature.Config> {

    public SpecialTreeFeature() {
        super(Config.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> context) {
        Config config = context.config();
        if (config.naturalOnly() && !NRConfig.GENERATE_REDWOOD.get()) {
            return false;
        }
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        boolean placed = switch (config.kind()) {
            case "willow" -> willow(level, origin, random, config);
            case "eucalyptus" -> eucalyptus(level, origin, random, config);
            case "hopseed" -> hopseed(level, origin, random, config, false);
            case "hopseed_mega" -> hopseed(level, origin, random, config, true);
            case "redwood" -> redwood(level, origin, random, config, false);
            case "redwood_mega" -> redwood(level, origin, random, config, true);
            case "bloodwood" -> bloodwood(level, origin, random, config);
            case "ghostwood" -> ghostwood(level, origin, random, config);
            case "fusewood" -> blob(level, origin, random, config, 4, 3);
            case "darkwood" -> darkwood(level, origin, random, config);
            default -> false;
        };
        if (placed && "redwood".equals(config.kind()) && level instanceof ServerLevel server) {
            ServerPlayer player = (ServerPlayer) server.getNearestPlayer(origin.getX(), origin.getY(), origin.getZ(), 24, false);
            if (player != null) {
                NRAdvancements.grant(player, "husbandry/grow_redwood");
            }
        }
        return placed;
    }

    private boolean willow(WorldGenLevel level, BlockPos origin, RandomSource random, Config config) {
        if (!blob(level, origin, random, config, 7, 2)) {
            return false;
        }
        roots(level, origin, random, config.log());
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                if (Math.abs(x) + Math.abs(z) < 2) {
                    continue;
                }
                cursor.set(origin.getX() + x, origin.getY() + 6, origin.getZ() + z);
                if (!(level.getBlockState(cursor).getBlock() instanceof LeavesBlock)) {
                    continue;
                }
                int hang = 3 + random.nextInt(3);
                for (int y = 1; y <= hang; y++) {
                    cursor.setY(origin.getY() + 6 - y);
                    BlockState at = level.getBlockState(cursor);
                    if (at.isAir() || at.is(Blocks.WATER)) {
                        level.setBlock(cursor, leaf(config.leaves()), Block.UPDATE_CLIENTS);
                    } else {
                        break;
                    }
                }
            }
        }
        return true;
    }

    private boolean eucalyptus(WorldGenLevel level, BlockPos origin, RandomSource random, Config config) {
        int height = 10 + random.nextInt(3);
        if (!trunk(level, origin, height, config.log())) {
            return false;
        }
        Direction[] directions = Direction.Plane.HORIZONTAL.stream().toArray(Direction[]::new);
        int[] heights = {4, 7};
        for (int branchHeight : heights) {
            for (Direction direction : directions) {
                BlockPos start = origin.above(branchHeight);
                for (int length = 1; length <= 2; length++) {
                    set(level, start.relative(direction, length), config.log());
                }
                BlockPos end = start.relative(direction, 2);
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = 0; dy <= 1; dy++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            setIfAir(level, end.offset(dx, dy, dz), leaf(config.leaves()));
                        }
                    }
                }
            }
        }
        canopy(level, origin.above(height - 1), 2, leaf(config.leaves()));
        return true;
    }

    private boolean hopseed(WorldGenLevel level, BlockPos origin, RandomSource random, Config config, boolean mega) {
        if (!mega) {
            return blob(level, origin, random, config, 6, 2);
        }
        BlockPos[] columns = {origin, origin.east(), origin.south(), origin.east().south()};
        for (BlockPos column : columns) {
            if (!level.getBlockState(column.below()).isSolidRender(level, column.below())) {
                return false;
            }
        }
        int height = 6 + random.nextInt(2);
        for (BlockPos column : columns) {
            for (int y = 0; y < height; y++) {
                if (!replaceable(level, column.above(y))) {
                    return false;
                }
            }
        }
        for (BlockPos column : columns) {
            for (int y = 0; y < height; y++) {
                level.setBlock(column.above(y), config.log(), Block.UPDATE_CLIENTS);
            }
        }
        canopy(level, origin.above(height - 1).south().east(), 3, leaf(config.leaves()));
        return true;
    }

    private boolean redwood(WorldGenLevel level, BlockPos origin, RandomSource random, Config config, boolean mega) {
        BlockPos[] columns = mega
                ? new BlockPos[] {origin, origin.east(), origin.south(), origin.east().south()}
                : new BlockPos[] {origin};
        int min = NRConfig.REDWOOD_MIN_HEIGHT.get() + (mega ? 12 : 0);
        int max = Math.max(min, NRConfig.REDWOOD_MAX_HEIGHT.get() + (mega ? 16 : 0));
        int height = min + random.nextInt(max - min + 1);
        for (BlockPos column : columns) {
            if (!level.getBlockState(column.below()).isSolidRender(level, column.below())) {
                return false;
            }
            for (int y = 0; y < height; y++) {
                if (!replaceable(level, column.above(y))) {
                    return false;
                }
            }
        }
        for (BlockPos column : columns) {
            for (int y = 0; y < height; y++) {
                level.setBlock(column.above(y), config.log(), Block.UPDATE_CLIENTS);
            }
        }
        BlockPos center = mega ? origin.south().east() : origin;
        BlockState leaves = leaf(config.leaves());
        int canopyStart = height / 5;
        for (int y = canopyStart; y < height; y++) {
            int radius = Math.max(2, (height - y) / 3);
            if (y >= height - 2) {
                radius = y == height - 1 ? 1 : 2;
            }
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (Math.abs(x) + Math.abs(z) > radius + 1) {
                        continue;
                    }
                    setIfAir(level, center.offset(x, y, z), leaves);
                }
            }
        }
        return true;
    }

    private void roots(WorldGenLevel level, BlockPos origin, RandomSource random, BlockState log) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos at = origin.relative(direction);
            int depth = 2 + random.nextInt(3);
            for (int i = 0; i < depth; i++) {
                BlockPos pos = at.below(i);
                BlockState state = level.getBlockState(pos);
                if (!(state.isAir() || state.is(Blocks.WATER) || state.is(Blocks.DIRT) || state.is(Blocks.MUD)
                        || state.is(Blocks.GRASS_BLOCK) || state.canBeReplaced())) {
                    break;
                }
                level.setBlock(pos, log, Block.UPDATE_CLIENTS);
            }
        }
        for (int i = 1; i <= 2; i++) {
            BlockPos pos = origin.below(i);
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.DIRT) || state.is(Blocks.MUD) || state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.ROOTED_DIRT)) {
                level.setBlock(pos, log, Block.UPDATE_CLIENTS);
            }
        }
    }

    private boolean ghostwood(WorldGenLevel level, BlockPos origin, RandomSource random, Config config) {
        int height = 8 + random.nextInt(2);
        if (!trunk(level, origin, height, config.log())) {
            return false;
        }
        BlockState leaves = leaf(config.leaves());
        canopy(level, origin.above(height - 2), 4, leaves);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                if (Math.abs(x) + Math.abs(z) < 3) {
                    continue;
                }
                cursor.set(origin.getX() + x, origin.getY() + height - 2, origin.getZ() + z);
                if (!(level.getBlockState(cursor).getBlock() instanceof LeavesBlock)) {
                    continue;
                }
                int hang = 2 + random.nextInt(3);
                for (int y = 1; y <= hang; y++) {
                    cursor.setY(origin.getY() + height - 2 - y);
                    if (!replaceable(level, cursor)) {
                        break;
                    }
                    level.setBlock(cursor, leaves, Block.UPDATE_CLIENTS);
                }
            }
        }
        return true;
    }

    private boolean bloodwood(WorldGenLevel level, BlockPos origin, RandomSource random, Config config) {
        BlockPos.MutableBlockPos cursor = origin.mutable();
        BlockPos ceiling = null;
        for (int y = 0; y < 32; y++) {
            cursor.set(origin.getX(), origin.getY() + y, origin.getZ());
            if (level.getBlockState(cursor).isSolidRender(level, cursor) && replaceable(level, cursor.below())) {
                ceiling = cursor.below().immutable();
                break;
            }
        }
        if (ceiling == null) {
            return false;
        }
        int height = 8 + random.nextInt(2);
        for (int y = 0; y < height; y++) {
            if (!replaceable(level, ceiling.below(y))) {
                return false;
            }
        }
        for (int y = 0; y < height; y++) {
            level.setBlock(ceiling.below(y), config.log(), Block.UPDATE_CLIENTS);
        }
        BlockState leaves = leaf(config.leaves());
        BlockPos canopy = ceiling.below(height - 2);
        canopy(level, canopy, 4, leaves);
        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                if (Math.abs(x) + Math.abs(z) < 3) {
                    continue;
                }
                cursor.set(canopy.getX() + x, canopy.getY(), canopy.getZ() + z);
                if (!(level.getBlockState(cursor).getBlock() instanceof LeavesBlock)) {
                    continue;
                }
                int hang = 2 + random.nextInt(3);
                for (int y = 1; y <= hang; y++) {
                    cursor.setY(canopy.getY() + y);
                    if (!replaceable(level, cursor)) {
                        break;
                    }
                    level.setBlock(cursor, leaves, Block.UPDATE_CLIENTS);
                }
            }
        }
        return true;
    }

    private boolean darkwood(WorldGenLevel level, BlockPos origin, RandomSource random, Config config) {
        if (!blob(level, origin, random, config, 5, 2)) {
            return false;
        }
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = -2; x <= 2; x++) {
            for (int y = 3; y <= 6; y++) {
                for (int z = -2; z <= 2; z++) {
                    cursor.set(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
                    if (!(level.getBlockState(cursor).getBlock() instanceof LeavesBlock) || random.nextInt(3) != 0) {
                        continue;
                    }
                    BlockState choice = random.nextBoolean() ? config.flowering() : config.fruit();
                    if (!choice.isAir()) {
                        level.setBlock(cursor, leaf(choice), Block.UPDATE_CLIENTS);
                    }
                }
            }
        }
        return true;
    }

    private boolean blob(WorldGenLevel level, BlockPos origin, RandomSource random, Config config, int height, int radius) {
        if (!trunk(level, origin, height, config.log())) {
            return false;
        }
        canopy(level, origin.above(height - 2), radius, leaf(config.leaves()));
        return true;
    }

    private boolean trunk(WorldGenLevel level, BlockPos origin, int height, BlockState log) {
        if (!level.getBlockState(origin.below()).isSolidRender(level, origin.below())) {
            return false;
        }
        for (int y = 0; y < height; y++) {
            if (!replaceable(level, origin.above(y))) {
                return false;
            }
        }
        for (int y = 0; y < height; y++) {
            level.setBlock(origin.above(y), log, Block.UPDATE_CLIENTS);
        }
        return true;
    }

    private void canopy(WorldGenLevel level, BlockPos center, int radius, BlockState leaves) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -1; y <= 2; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (Math.abs(x) + Math.abs(z) > radius + 1) {
                        continue;
                    }
                    setIfAir(level, center.offset(x, y, z), leaves);
                }
            }
        }
    }

    private static void set(WorldGenLevel level, BlockPos pos, BlockState state) {
        if (replaceable(level, pos)) {
            level.setBlock(pos, state, Block.UPDATE_CLIENTS);
        }
    }

    private static void setIfAir(WorldGenLevel level, BlockPos pos, BlockState state) {
        BlockState at = level.getBlockState(pos);
        if (at.isAir() || at.is(Blocks.WATER) || at.canBeReplaced()) {
            level.setBlock(pos, state, Block.UPDATE_CLIENTS);
        }
    }

    private static boolean replaceable(LevelAccessor level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.isAir() || state.is(Blocks.WATER) || state.canBeReplaced()
                || state.getBlock() instanceof LeavesBlock || state.getBlock() instanceof SaplingBlock;
    }

    private static BlockState leaf(BlockState state) {
        if (state.hasProperty(LeavesBlock.PERSISTENT)) {
            return state.setValue(LeavesBlock.PERSISTENT, false).setValue(LeavesBlock.DISTANCE, 1);
        }
        return state;
    }

    public record Config(String kind, BlockState log, BlockState leaves, BlockState flowering, BlockState fruit, boolean naturalOnly) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("kind").forGetter(Config::kind),
                BlockState.CODEC.fieldOf("log").forGetter(Config::log),
                BlockState.CODEC.fieldOf("leaves").forGetter(Config::leaves),
                BlockState.CODEC.fieldOf("flowering").forGetter(Config::flowering),
                BlockState.CODEC.fieldOf("fruit").forGetter(Config::fruit),
                Codec.BOOL.fieldOf("natural").forGetter(Config::naturalOnly)
        ).apply(instance, Config::new));
    }
}

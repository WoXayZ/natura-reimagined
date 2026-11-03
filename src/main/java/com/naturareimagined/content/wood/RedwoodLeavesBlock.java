package com.naturareimagined.content.wood;

import com.mojang.serialization.MapCodec;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RedwoodLeavesBlock extends LeavesBlock {

    public static final MapCodec<RedwoodLeavesBlock> CODEC = simpleCodec(RedwoodLeavesBlock::new);
    public static final int SUPPORT_DISTANCE = 16;

    public RedwoodLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends LeavesBlock> codec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(PERSISTENT) || state.getValue(DISTANCE) != DECAY_DISTANCE) {
            return;
        }
        if (supported(level, pos)) {
            return;
        }
        dropResources(state, level, pos);
        level.removeBlock(pos, false);
    }

    private static boolean supported(ServerLevel level, BlockPos origin) {
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        ArrayDeque<Integer> distance = new ArrayDeque<>();
        Set<BlockPos> seen = new HashSet<>();
        queue.add(origin);
        distance.add(0);
        seen.add(origin);
        while (!queue.isEmpty()) {
            BlockPos current = queue.removeFirst();
            int steps = distance.removeFirst();
            if (steps >= SUPPORT_DISTANCE) {
                continue;
            }
            for (Direction direction : Direction.values()) {
                BlockPos next = current.relative(direction);
                if (!seen.add(next)) {
                    continue;
                }
                BlockState state = level.getBlockState(next);
                if (state.is(BlockTags.LOGS)) {
                    return true;
                }
                if (state.getBlock() instanceof LeavesBlock && steps + 1 < SUPPORT_DISTANCE) {
                    queue.add(next);
                    distance.add(steps + 1);
                }
            }
        }
        return false;
    }
}

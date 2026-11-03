package com.naturareimagined.content.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class NetherSpace {

    private NetherSpace() {}

    public static boolean nearBedrock(WorldGenLevel level, BlockPos pos, int reach) {
        int min = Math.max(level.getMinBuildHeight(), pos.getY() - 2);
        int max = Math.min(level.getMaxBuildHeight() - 1, pos.getY() + reach);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int y = min; y <= max; y++) {
            cursor.set(pos.getX(), y, pos.getZ());
            if (level.getBlockState(cursor).is(Blocks.BEDROCK)) {
                return true;
            }
        }
        return false;
    }

    public static boolean openColumn(WorldGenLevel level, BlockPos floor, int air) {
        for (int y = 1; y <= air; y++) {
            BlockState above = level.getBlockState(floor.above(y));
            if (!above.isAir()) {
                return false;
            }
        }
        return !nearBedrock(level, floor, air + 12);
    }
}

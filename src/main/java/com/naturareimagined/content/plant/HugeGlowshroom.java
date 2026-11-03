package com.naturareimagined.content.plant;

import com.naturareimagined.registry.NRContent;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class HugeGlowshroom {

    private HugeGlowshroom() {}

    public static boolean grow(LevelAccessor level, BlockPos origin, String color, RandomSource random) {
        if (!level.getBlockState(origin.below()).isSolidRender(level, origin.below())) {
            return false;
        }
        int height = switch (color) {
            case "blue" -> 7 + random.nextInt(2);
            case "purple" -> 6 + random.nextInt(2);
            default -> 5 + random.nextInt(2);
        };
        if (!room(level, origin, height, color)) {
            return false;
        }
        BlockState stem = NRContent.stem(color).defaultBlockState();
        BlockState cap = NRContent.cap(color).defaultBlockState();
        for (int y = 0; y < height; y++) {
            level.setBlock(origin.above(y), stem, Block.UPDATE_ALL);
        }
        switch (color) {
            case "purple" -> dome(level, origin.above(height), cap, stem);
            case "blue" -> brownCap(level, origin.above(height), cap, stem, 4);
            default -> brownCap(level, origin.above(height), cap, stem, 3);
        }
        return true;
    }

    private static boolean room(LevelAccessor level, BlockPos origin, int height, String color) {
        int radius = "blue".equals(color) ? 4 : 3;
        for (int y = 0; y <= height + 1; y++) {
            int capRadius = y >= height - 1 ? radius : 0;
            for (int x = -capRadius; x <= capRadius; x++) {
                for (int z = -capRadius; z <= capRadius; z++) {
                    BlockPos at = origin.offset(x, y, z);
                    if (at.equals(origin)) {
                        continue;
                    }
                    if (!level.getBlockState(at).isAir()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void brownCap(LevelAccessor level, BlockPos top, BlockState cap, BlockState stem, int radius) {
        disc(level, top, cap, radius - 1, false);
        disc(level, top.below(), cap, radius, true);
        level.setBlock(top.below(), stem, Block.UPDATE_ALL);
    }

    private static void dome(LevelAccessor level, BlockPos top, BlockState cap, BlockState stem) {
        disc(level, top, cap, 1, false);
        disc(level, top.below(), cap, 2, false);
        disc(level, top.below(2), cap, 3, true);
        level.setBlock(top.below(), stem, Block.UPDATE_ALL);
        level.setBlock(top.below(2), stem, Block.UPDATE_ALL);
    }

    private static void disc(LevelAccessor level, BlockPos center, BlockState cap, int radius, boolean skipCorners) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (skipCorners && Math.abs(x) == radius && Math.abs(z) == radius) {
                    continue;
                }
                BlockPos at = center.offset(x, 0, z);
                if (level.getBlockState(at).isAir() || level.getBlockState(at).getBlock() instanceof GlowshroomBlock) {
                    level.setBlock(at, cap, Block.UPDATE_ALL);
                }
            }
        }
    }
}

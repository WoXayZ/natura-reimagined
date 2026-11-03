package com.naturareimagined.content.plant;

import com.naturareimagined.content.world.TaintedFarmlandBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

final class HeatedCrops {

    private HeatedCrops() {}

    static boolean tryGrow(CropBlock crop, BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockState floor = level.getBlockState(pos.below());
        if (!(floor.getBlock() instanceof TaintedFarmlandBlock)) {
            return false;
        }
        if (!floor.getValue(TaintedFarmlandBlock.HEATED) || crop.getAge(state) >= crop.getMaxAge()) {
            return true;
        }
        if (random.nextInt(3) == 0) {
            level.setBlock(pos, crop.getStateForAge(crop.getAge(state) + 1), Block.UPDATE_CLIENTS);
        }
        return true;
    }
}

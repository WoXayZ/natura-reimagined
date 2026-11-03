package com.naturareimagined.content.world;

import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class GroundPlantFeature extends Feature<SimpleBlockConfiguration> {

    public GroundPlantFeature() {
        super(SimpleBlockConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockState state = context.config().toPlace().getState(context.random(), context.origin());
        BlockPos spot = this.findFloor(level, context.origin(), state);
        if (spot == null) {
            return false;
        }
        level.setBlock(spot, state, 2);
        return true;
    }

    private BlockPos findFloor(WorldGenLevel level, BlockPos origin, BlockState state) {
        int top = Math.min(origin.getY(), 96);
        for (int y = top; y >= 8; y--) {
            BlockPos pos = new BlockPos(origin.getX(), y, origin.getZ());
            if (level.getBlockState(pos).isAir() && state.canSurvive(level, pos) && !NetherSpace.nearBedrock(level, pos, 16)) {
                return pos;
            }
        }
        return null;
    }
}

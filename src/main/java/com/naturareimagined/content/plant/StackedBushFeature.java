package com.naturareimagined.content.plant;

import com.naturareimagined.config.NRConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class StackedBushFeature extends Feature<SimpleBlockConfiguration> {

    public StackedBushFeature() {
        super(SimpleBlockConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        if (!NRConfig.GENERATE_BERRY_BUSHES.get() || context.random().nextInt(NRConfig.BERRY_BUSH_RARITY.get()) != 0) {
            return false;
        }
        WorldGenLevel level = context.level();
        BlockState state = context.config().toPlace().getState(context.random(), context.origin());
        if (!(state.getBlock() instanceof BerryBushBlock bush)) {
            return false;
        }
        BlockPos floor = this.findAirAboveFloor(level, context.origin());
        if (floor == null || !state.canSurvive(level, floor)) {
            return false;
        }
        int height = 1 + context.random().nextInt(3);
        boolean placed = false;
        for (int i = 0; i < height; i++) {
            BlockPos at = floor.above(i);
            if (!level.getBlockState(at).isAir() || !state.canSurvive(level, at)) {
                break;
            }
            int age = context.random().nextInt(BerryBushBlock.MAX_AGE + 1);
            level.setBlock(at, state.setValue(BerryBushBlock.AGE, age), Block.UPDATE_CLIENTS);
            placed = true;
        }
        return placed;
    }

    public static BlockPos findAirAboveFloor(WorldGenLevel level, BlockPos origin) {
        BlockPos.MutableBlockPos cursor = origin.mutable();
        int climbed = 0;
        while (!level.getBlockState(cursor).isAir() && climbed < 16 && cursor.getY() < level.getMaxBuildHeight() - 1) {
            cursor.move(Direction.UP);
            climbed++;
        }
        if (!level.getBlockState(cursor).isAir()) {
            return null;
        }
        int dropped = 0;
        while (level.getBlockState(cursor.below()).isAir() && dropped < 24 && cursor.getY() > level.getMinBuildHeight() + 1) {
            cursor.move(Direction.DOWN);
            dropped++;
        }
        return level.getBlockState(cursor).isAir() ? cursor.immutable() : null;
    }
}

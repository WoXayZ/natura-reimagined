package com.naturareimagined.content.plant;

import com.naturareimagined.config.NRConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class FlowerPatchFeature extends Feature<SimpleBlockConfiguration> {

    public FlowerPatchFeature() {
        super(SimpleBlockConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        if (!NRConfig.GENERATE_BLUEBELLS.get() || context.random().nextInt(NRConfig.BLUEBELL_RARITY.get()) != 0) {
            return false;
        }
        WorldGenLevel level = context.level();
        BlockState state = context.config().toPlace().getState(context.random(), context.origin());
        BlockPos origin = StackedBushFeature.findAirAboveFloor(level, context.origin());
        if (origin == null) {
            return false;
        }
        boolean placed = false;
        for (int i = 0; i < 24; i++) {
            BlockPos pos = origin.offset(
                    context.random().nextInt(8) - context.random().nextInt(8),
                    context.random().nextInt(3) - context.random().nextInt(3),
                    context.random().nextInt(8) - context.random().nextInt(8));
            BlockPos spot = StackedBushFeature.findAirAboveFloor(level, pos);
            if (spot != null && level.getBlockState(spot).isAir() && state.canSurvive(level, spot)) {
                level.setBlock(spot, state, Block.UPDATE_CLIENTS);
                placed = true;
            }
        }
        return placed;
    }
}

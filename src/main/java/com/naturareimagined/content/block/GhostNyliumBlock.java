package com.naturareimagined.content.block;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GhostNyliumBlock extends NyliumBlock {

    public static final MapCodec<GhostNyliumBlock> CODEC = simpleCodec(GhostNyliumBlock::new);

    public GhostNyliumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<NyliumBlock> codec() {
        return CODEC.flatXmap(
                DataResult::success,
                block -> block instanceof GhostNyliumBlock nylium
                        ? DataResult.success(nylium)
                        : DataResult.error(() -> "Expected ghost nylium"));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return false;
    }
}

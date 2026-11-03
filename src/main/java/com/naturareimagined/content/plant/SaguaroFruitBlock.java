package com.naturareimagined.content.plant;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.naturareimagined.registry.NRContent;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SaguaroFruitBlock extends CocoaBlock {

    public static final MapCodec<SaguaroFruitBlock> CODEC = simpleCodec(SaguaroFruitBlock::new);

    public SaguaroFruitBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<CocoaBlock> codec() {
        return CODEC.flatXmap(
                DataResult::success,
                block -> block instanceof SaguaroFruitBlock fruit
                        ? DataResult.success(fruit)
                        : DataResult.error(() -> "Expected a saguaro fruit"));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos support = pos.relative(state.getValue(FACING));
        return level.getBlockState(support).is(NRContent.SAGUARO.get());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clicked = context.getClickedFace();
        if (clicked.getAxis().isHorizontal()) {
            BlockState attached = this.defaultBlockState().setValue(FACING, clicked.getOpposite());
            if (attached.canSurvive(context.getLevel(), context.getClickedPos())) {
                return attached;
            }
        }
        return super.getStateForPlacement(context);
    }
}

package com.naturareimagined.content.world;

import com.naturareimagined.registry.NRContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class TaintedFarmlandBlock extends FarmBlock {

    public static final BooleanProperty HEATED = BooleanProperty.create("heated");

    public TaintedFarmlandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HEATED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HEATED);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighbor, net.minecraft.world.level.LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.canSurvive(level, pos)) {
            return NRContent.TAINTED_SOIL.get().defaultBlockState();
        }
        return state;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        return above.isAir() || !above.isFaceSturdy(level, pos.above(), Direction.DOWN);
    }

    @Override
    public void fallOn(net.minecraft.world.level.Level level, BlockState state, BlockPos pos, net.minecraft.world.entity.Entity entity, float fallDistance) {
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int moisture = state.getValue(MOISTURE);
        boolean nearWater = false;
        for (BlockPos near : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (level.getFluidState(near).is(FluidTags.WATER)) {
                nearWater = true;
                break;
            }
        }
        boolean raining = level.isRainingAt(pos.above());
        BlockState next = state;
        if ((nearWater || raining) && moisture < MAX_MOISTURE) {
            next = next.setValue(MOISTURE, MAX_MOISTURE);
        } else if (!nearWater && !raining && moisture > 0) {
            next = next.setValue(MOISTURE, moisture - 1);
        }
        boolean heated = false;
        for (BlockPos near : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            BlockState neighbor = level.getBlockState(near);
            if (neighbor.is(Blocks.LAVA) || neighbor.is(Blocks.FIRE) || neighbor.is(Blocks.MAGMA_BLOCK)) {
                heated = true;
                break;
            }
        }
        if (next.getValue(HEATED) != heated) {
            next = next.setValue(HEATED, heated);
        }
        if (next != state) {
            level.setBlock(pos, next, Block.UPDATE_CLIENTS);
        }
    }
}

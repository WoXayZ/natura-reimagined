package com.naturareimagined.content.plant;

import com.mojang.serialization.MapCodec;
import com.naturareimagined.registry.NRContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SaguaroBlock extends BushBlock {

    public static final MapCodec<SaguaroBlock> CODEC = simpleCodec(SaguaroBlock::new);
    private static final VoxelShape OUTLINE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private static final VoxelShape COLLISION = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public SaguaroBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<SaguaroBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(Blocks.SAND) || state.is(Blocks.RED_SAND) || state.is(this)
                || state.is(Blocks.NETHERRACK) || state.is(BlockTags.NYLIUM)
                || state.is(Blocks.SOUL_SAND) || state.is(Blocks.SOUL_SOIL)
                || state.is(NRContent.HEAT_SAND.get());
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isEmptyBlock(pos.above()) && this.height(level, pos) < 3 && random.nextInt(8) == 0) {
            level.setBlock(pos.above(), this.defaultBlockState(), Block.UPDATE_ALL);
        }
    }

    private int height(Level level, BlockPos pos) {
        int height = 0;
        BlockPos cursor = pos;
        while (level.getBlockState(cursor).is(this) && height < 6) {
            height++;
            cursor = cursor.below();
        }
        return height;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return OUTLINE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighbor = level.getBlockState(neighborPos);
            if (neighbor.is(NRContent.SAGUARO_FRUIT_BLOCK.get())) {
                continue;
            }
            if (neighbor.isSolid() || level.getFluidState(neighborPos).is(FluidTags.LAVA)) {
                return false;
            }
        }
        BlockPos below = pos.below();
        return this.mayPlaceOn(level.getBlockState(below), level, below);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighbor, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }
        return state;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof ItemEntity item && item.getItem().is(NRContent.SAGUARO_FRUIT.get())) {
            return;
        }
        entity.hurt(level.damageSources().cactus(), 1.0F);
    }
}

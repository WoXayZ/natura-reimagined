package com.naturareimagined.content.plant;

import com.mojang.serialization.MapCodec;
import com.naturareimagined.registry.NRContent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlowshroomBlock extends BushBlock implements BonemealableBlock {

    public static final MapCodec<GlowshroomBlock> CODEC = simpleCodec(properties -> new GlowshroomBlock(properties));
    private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);

    private final String color;

    public GlowshroomBlock(String color, Properties properties) {
        super(properties);
        this.color = color;
    }

    public GlowshroomBlock(Properties properties) {
        this("green", properties);
    }

    @Override
    public MapCodec<GlowshroomBlock> codec() {
        return CODEC;
    }

    public String color() {
        return this.color;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.NYLIUM) || state.is(Blocks.NETHERRACK) || state.is(Blocks.SOUL_SOIL) || state.is(NRContent.TAINTED_SOIL.get());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextFloat() < 0.4F;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        HugeGlowshroom.grow(level, pos, this.color, random);
    }
}

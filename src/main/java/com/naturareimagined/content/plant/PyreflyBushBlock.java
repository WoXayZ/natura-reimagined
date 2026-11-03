package com.naturareimagined.content.plant;

import com.mojang.serialization.MapCodec;
import com.naturareimagined.registry.NRContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PyreflyBushBlock extends BushBlock {

    public static final MapCodec<PyreflyBushBlock> CODEC = simpleCodec(PyreflyBushBlock::new);

    public PyreflyBushBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.NYLIUM) || state.is(Blocks.NETHERRACK) || state.is(Blocks.SOUL_SAND) || state.is(Blocks.SOUL_SOIL);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        SimpleParticleType particle = NRContent.PYREFLY_PARTICLE.get();
        int count = 4 + random.nextInt(3);
        for (int i = 0; i < count; i++) {
            level.addParticle(particle,
                    pos.getX() + 0.15 + random.nextDouble() * 0.7,
                    pos.getY() + 0.35 + random.nextDouble() * 0.7,
                    pos.getZ() + 0.15 + random.nextDouble() * 0.7,
                    (random.nextDouble() - 0.5) * 0.05,
                    0.012 + random.nextDouble() * 0.03,
                    (random.nextDouble() - 0.5) * 0.05);
        }
    }
}

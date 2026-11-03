package com.naturareimagined.content.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NetherGlassBlock extends TransparentBlock {

    private final boolean burns;
    private final boolean slows;

    public NetherGlassBlock(boolean burns, boolean slows, Properties properties) {
        super(properties);
        this.burns = burns;
        this.slows = slows;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (this.burns && entity instanceof LivingEntity living) {
            living.hurt(level.damageSources().hotFloor(), 1.0F);
        }
        if (this.slows && entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
        }
    }
}

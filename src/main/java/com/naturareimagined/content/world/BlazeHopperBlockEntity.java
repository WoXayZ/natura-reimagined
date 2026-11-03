package com.naturareimagined.content.world;

import com.naturareimagined.registry.NRContent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class BlazeHopperBlockEntity extends HopperBlockEntity {

    private static final AABB SUCK = Block.box(-4.0D, 11.0D, -4.0D, 20.0D, 32.0D, 20.0D).toAabbs().getFirst();

    public BlazeHopperBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return NRContent.BLAZE_HOPPER_BE.get();
    }

    @Override
    public AABB getSuckAabb() {
        return SUCK;
    }
}

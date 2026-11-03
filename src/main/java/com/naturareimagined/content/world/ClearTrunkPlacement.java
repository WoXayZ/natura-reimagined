package com.naturareimagined.content.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naturareimagined.registry.NRFeatures;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ClearTrunkPlacement extends PlacementModifier {

    public static final MapCodec<ClearTrunkPlacement> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("height").forGetter(placement -> placement.height),
            Codec.INT.fieldOf("radius").forGetter(placement -> placement.radius)
    ).apply(instance, ClearTrunkPlacement::new));

    private final int height;
    private final int radius;

    public ClearTrunkPlacement(int height, int radius) {
        this.height = height;
        this.radius = radius;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        BlockPos ground = pos.below();
        BlockState below = context.getBlockState(ground);
        if (wood(below) || !below.isSolidRender(context.getLevel(), ground)) {
            return Stream.of();
        }
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = -this.radius; x <= this.radius; x++) {
            for (int z = -this.radius; z <= this.radius; z++) {
                for (int y = -this.height; y < this.height; y++) {
                    cursor.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                    if (wood(context.getBlockState(cursor))) {
                        return Stream.of();
                    }
                }
            }
        }
        return Stream.of(pos);
    }

    private static boolean wood(BlockState state) {
        return state.is(BlockTags.LOGS) || state.is(BlockTags.LEAVES) || state.getBlock() instanceof net.minecraft.world.level.block.LeavesBlock;
    }

    @Override
    public PlacementModifierType<?> type() {
        return NRFeatures.CLEAR_TRUNK.get();
    }
}

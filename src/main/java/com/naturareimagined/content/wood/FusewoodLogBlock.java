package com.naturareimagined.content.wood;

import com.naturareimagined.config.NRConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FusewoodLogBlock extends RotatedPillarBlock {

    public FusewoodLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockState result = super.playerWillDestroy(level, pos, state, player);
        if (!level.isClientSide && level.getDifficulty() != Difficulty.PEACEFUL && !player.isCreative()) {
            float power = NRConfig.FUSEWOOD_EXPLOSION_POWER.get().floatValue();
            if (power > 0.0F) {
                Level.ExplosionInteraction interaction = NRConfig.FUSEWOOD_BREAKS_BLOCKS.get()
                        ? Level.ExplosionInteraction.BLOCK
                        : Level.ExplosionInteraction.NONE;
                level.explode(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, power, interaction);
            }
        }
        return result;
    }
}

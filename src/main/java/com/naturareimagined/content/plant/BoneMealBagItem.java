package com.naturareimagined.content.plant;

import com.naturareimagined.registry.NRPlants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BoneMealBagItem extends Item {

    public BoneMealBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos center = context.getClickedPos();
        Player player = context.getPlayer();
        boolean grew = false;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (this.grow(level, center.offset(dx, 0, dz))) {
                    grew = true;
                }
            }
        }
        if (!grew) {
            return InteractionResult.PASS;
        }
        if (player == null || !player.getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
            if (player != null) {
                ItemStack empty = new ItemStack(NRPlants.EMPTY_SEED_BAG.get());
                if (!player.getInventory().add(empty)) {
                    player.drop(empty, false);
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private boolean grow(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof BonemealableBlock growable) || !growable.isValidBonemealTarget(level, pos, state)) {
            return false;
        }
        if (level instanceof ServerLevel server && growable.isBonemealSuccess(level, level.random, pos, state)) {
            growable.performBonemeal(server, level.random, pos, state);
        }
        level.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
        if (level instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 8, 0.4, 0.3, 0.4, 0);
        }
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return new ItemStack(NRPlants.EMPTY_SEED_BAG.get());
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }
}

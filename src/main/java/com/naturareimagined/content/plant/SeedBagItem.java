package com.naturareimagined.content.plant;

import com.naturareimagined.registry.NRPlants;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SeedBagItem extends Item {

    private final Supplier<BlockState> plant;
    private final Block ground;

    public SeedBagItem(Properties properties, Supplier<BlockState> plant, Block ground) {
        super(properties);
        this.plant = plant;
        this.ground = ground;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos center = context.getClickedPos();
        Player player = context.getPlayer();
        boolean planted = false;
        BlockState sapling = this.plant.get();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos groundPos = center.offset(dx, 0, dz);
                BlockPos above = groundPos.above();
                if (!level.getBlockState(groundPos).is(this.ground) || !level.getBlockState(above).canBeReplaced()) {
                    continue;
                }
                if (player != null && !player.mayUseItemAt(above, net.minecraft.core.Direction.UP, context.getItemInHand())) {
                    continue;
                }
                level.setBlock(above, sapling, Block.UPDATE_ALL);
                level.playSound(player, above, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F);
                planted = true;
            }
        }
        if (!planted) {
            return InteractionResult.PASS;
        }
        if (player == null || !player.getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
            this.returnEmptyBag(player);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return new ItemStack(NRPlants.EMPTY_SEED_BAG.get());
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    private void returnEmptyBag(Player player) {
        if (player == null) {
            return;
        }
        ItemStack empty = new ItemStack(NRPlants.EMPTY_SEED_BAG.get());
        if (!player.getInventory().add(empty)) {
            player.drop(empty, false);
        }
    }
}

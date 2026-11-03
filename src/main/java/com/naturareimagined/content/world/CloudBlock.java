package com.naturareimagined.content.world;

import com.naturareimagined.config.NRConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ItemInteractionResult;

public class CloudBlock extends Block {

    private final boolean explodes;

    public CloudBlock(boolean explodes, Properties properties) {
        super(properties);
        this.explodes = explodes;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, net.minecraft.world.level.BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getShape(BlockState state, net.minecraft.world.level.BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        var motion = entity.getDeltaMovement();
        if (motion.y < 0) {
            entity.setDeltaMovement(motion.x, motion.y * 0.4, motion.z);
            entity.resetFallDistance();
        }
        if (this.explodes && entity instanceof AbstractArrow arrow && arrow.isOnFire()) {
            this.detonate(level, pos);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(net.minecraft.world.item.ItemStack stack, BlockState state, Level level, BlockPos pos, net.minecraft.world.entity.player.Player player, net.minecraft.world.InteractionHand hand, BlockHitResult hit) {
        if (this.explodes && (stack.getItem() instanceof FlintAndSteelItem || stack.is(com.naturareimagined.registry.NRContent.FLINT_AND_BLAZE.get()))) {
            if (!level.isClientSide) {
                this.detonate(level, pos);
                stack.hurtAndBreak(1, player, hand == net.minecraft.world.InteractionHand.MAIN_HAND
                        ? net.minecraft.world.entity.EquipmentSlot.MAINHAND
                        : net.minecraft.world.entity.EquipmentSlot.OFFHAND);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (this.explodes && projectile instanceof AbstractArrow arrow && arrow.isOnFire()) {
            this.detonate(level, hit.getBlockPos());
        }
    }

    private void detonate(Level level, BlockPos pos) {
        if (level.isClientSide) {
            return;
        }
        level.removeBlock(pos, false);
        level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                NRConfig.SULFUR_CLOUD_EXPLOSION.get().floatValue(), Level.ExplosionInteraction.NONE);
    }

    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacent, Direction side) {
        return adjacent.getBlock() instanceof CloudBlock || super.skipRendering(state, adjacent, side);
    }
}

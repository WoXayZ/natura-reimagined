package com.naturareimagined.content.item;

import com.naturareimagined.config.NRConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

public class FlintAndBlazeItem extends Item {

    public FlintAndBlazeItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return NRConfig.FLINT_AND_BLAZE_DURABILITY.get();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var player = context.getPlayer();
        Direction face = context.getClickedFace();
        BlockPos origin = context.getClickedPos().relative(face);
        Direction across = face.getAxis() == Direction.Axis.Y ? Direction.EAST : Direction.UP;
        Direction along = face.getAxis() == Direction.Axis.Y ? Direction.SOUTH : face.getClockWise();
        boolean lit = false;
        for (int du = -1; du <= 1; du++) {
            for (int dv = -1; dv <= 1; dv++) {
                BlockPos place = origin.relative(across, du).relative(along, dv);
                BlockPos support = place.relative(face.getOpposite());
                BlockState supportState = level.getBlockState(support);
                if (supportState.getBlock() instanceof CampfireBlock && supportState.hasProperty(BlockStateProperties.LIT)
                        && !supportState.getValue(BlockStateProperties.LIT)) {
                    level.setBlock(support, supportState.setValue(BlockStateProperties.LIT, true), 11);
                    lit = true;
                    continue;
                }
                if (BaseFireBlock.canBePlacedAt(level, place, face)) {
                    level.setBlock(place, BaseFireBlock.getState(level, place), 11);
                    lit = true;
                }
            }
        }
        if (!lit) {
            return InteractionResult.PASS;
        }
        level.playSound(player, origin, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F);
        level.gameEvent(player, GameEvent.BLOCK_PLACE, origin);
        if (player == null || !player.getAbilities().instabuild) {
            context.getItemInHand().hurtAndBreak(1, player, context.getHand() == net.minecraft.world.InteractionHand.MAIN_HAND
                    ? EquipmentSlot.MAINHAND
                    : EquipmentSlot.OFFHAND);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}

package com.naturareimagined.content.machine;

import com.naturareimagined.config.NRConfig;
import com.naturareimagined.registry.NRContent;
import java.lang.reflect.Field;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NetherrackFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    private static final Field LIT_TIME = field("litTime");
    private static final Field LIT_DURATION = field("litDuration");

    public NetherrackFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(NRContent.FURNACE_BE.get(), pos, state, RecipeType.SMELTING);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new FurnaceMenu(id, inventory, this, this.dataAccess);
    }

    @Override
    protected int getBurnDuration(ItemStack fuel) {
        return super.getBurnDuration(fuel) * 2;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, NetherrackFurnaceBlockEntity furnace) {
        if (NRConfig.FURNACE_COOKS_FROM_FLAME.get() && flameBelow(level, pos)) {
            try {
                LIT_TIME.setInt(furnace, 200);
                LIT_DURATION.setInt(furnace, 200);
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException(exception);
            }
        }
        serverTick(level, pos, state, furnace);
        boolean burning = lit(furnace);
        BlockState now = level.getBlockState(pos);
        if (now.getBlock() instanceof NetherrackFurnaceBlock && now.getValue(AbstractFurnaceBlock.LIT) != burning) {
            level.setBlock(pos, now.setValue(AbstractFurnaceBlock.LIT, burning), Block.UPDATE_ALL);
        }
    }

    private static boolean lit(NetherrackFurnaceBlockEntity furnace) {
        try {
            return LIT_TIME.getInt(furnace) > 0;
        } catch (IllegalAccessException exception) {
            throw new IllegalStateException(exception);
        }
    }

    private static boolean flameBelow(Level level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return below.is(Blocks.FIRE) || below.is(Blocks.SOUL_FIRE);
    }

    private static Field field(String name) {
        try {
            Field found = AbstractFurnaceBlockEntity.class.getDeclaredField(name);
            found.setAccessible(true);
            return found;
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException(exception);
        }
    }
}

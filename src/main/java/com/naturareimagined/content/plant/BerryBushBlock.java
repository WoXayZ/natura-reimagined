package com.naturareimagined.content.plant;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.tags.BlockTags;

public class BerryBushBlock extends BushBlock implements BonemealableBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final int MAX_AGE = 3;
    private static final int MAX_STACK = 3;
    private static final VoxelShape SMALL = Block.box(4, 0, 4, 12, 8, 12);
    private static final VoxelShape MEDIUM = Block.box(2, 0, 2, 14, 12, 14);

    private final boolean nether;
    private final Supplier<Item> berries;
    private final MapCodec<BerryBushBlock> codec;

    public BerryBushBlock(boolean nether, Supplier<Item> berries, Properties properties) {
        super(properties);
        this.nether = nether;
        this.berries = berries;
        this.codec = simpleCodec(props -> new BerryBushBlock(nether, berries, props));
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public MapCodec<BerryBushBlock> codec() {
        return this.codec;
    }

    public Item berries() {
        return this.berries.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AGE)) {
            case 0 -> SMALL;
            case 1 -> MEDIUM;
            default -> Shapes.block();
        };
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(AGE) == 0 ? Shapes.empty() : this.getShape(state, level, pos, context);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.is(this)) {
            return this.columnHeight(level, pos) < MAX_STACK;
        }
        if (this.nether) {
            return state.is(BlockTags.NYLIUM) || state.is(Blocks.NETHERRACK) || state.is(Blocks.SOUL_SOIL) || state.is(Blocks.SOUL_SAND);
        }
        return state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND) || state.is(Blocks.MOSS_BLOCK) || state.is(Blocks.MUD);
    }

    private int columnHeight(BlockGetter level, BlockPos pos) {
        int height = 0;
        BlockPos cursor = pos;
        while (height < MAX_STACK && level.getBlockState(cursor).is(this)) {
            height++;
            cursor = cursor.below();
        }
        return height;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        if (random.nextInt(5) != 0) {
            return;
        }
        int light = level.getRawBrightness(pos, 0);
        boolean canGrow = this.nether ? light < 8 : light >= 9;
        if (!canGrow) {
            return;
        }
        if (age < MAX_AGE) {
            level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
        } else {
            this.growUp(level, pos);
        }
    }

    private void growUp(ServerLevel level, BlockPos pos) {
        if (this.columnHeight(level, pos) >= MAX_STACK || !level.isEmptyBlock(pos.above())) {
            return;
        }
        level.setBlock(pos.above(), this.defaultBlockState(), Block.UPDATE_ALL);
    }

    private BlockPos top(BlockGetter level, BlockPos pos) {
        BlockPos cursor = pos;
        while (level.getBlockState(cursor.above()).is(this) && cursor.getY() < pos.getY() + MAX_STACK) {
            cursor = cursor.above();
        }
        return cursor;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, net.minecraft.world.entity.player.Player player, BlockHitResult hit) {
        if (state.getValue(AGE) < MAX_AGE) {
            return InteractionResult.PASS;
        }
        int count = 1 + level.random.nextInt(2);
        popResource(level, pos, new ItemStack(this.berries.get(), count));
        level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
        BlockState younger = state.setValue(AGE, 1);
        level.setBlock(pos, younger, Block.UPDATE_CLIENTS);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, younger));
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity instanceof Fox || entity instanceof Bee) {
            return;
        }
        entity.makeStuckInBlock(state, new Vec3(0.8, 0.75, 0.8));
        if (level.isClientSide || state.getValue(AGE) == 0) {
            return;
        }
        if (entity.xOld != entity.getX() || entity.zOld != entity.getZ()) {
            double movedX = Math.abs(entity.getX() - entity.xOld);
            double movedZ = Math.abs(entity.getZ() - entity.zOld);
            if (movedX >= 0.003 || movedZ >= 0.003) {
                entity.hurt(level.damageSources().sweetBerryBush(), 1.0F);
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        BlockPos top = this.top(level, pos);
        BlockState topState = level.getBlockState(top);
        if (topState.getValue(AGE) < MAX_AGE) {
            return true;
        }
        return this.columnHeight(level, top) < MAX_STACK && level.getBlockState(top.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos top = this.top(level, pos);
        BlockState topState = level.getBlockState(top);
        int age = topState.getValue(AGE);
        if (age < MAX_AGE) {
            level.setBlock(top, topState.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
            return;
        }
        this.growUp(level, top);
    }
}

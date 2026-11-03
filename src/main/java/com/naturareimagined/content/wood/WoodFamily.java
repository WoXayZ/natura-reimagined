package com.naturareimagined.content.wood;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.registry.NRBlocks;
import com.naturareimagined.registry.NRItems;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public final class WoodFamily {

    public final String name;
    public final String englishName;
    public final boolean nether;
    public final int leavesColor;
    private final MapColor color;
    public final BlockSetType blockSet;
    public final WoodType woodType;
    public final TreeGrower treeGrower;
    public final ResourceKey<ConfiguredFeature<?, ?>> treeFeature;
    public final ResourceKey<ConfiguredFeature<?, ?>> megaTree;
    public final TagKey<Block> logTag;
    public final TagKey<Item> logItemTag;

    public final DeferredBlock<RotatedPillarBlock> log;
    public final DeferredBlock<RotatedPillarBlock> wood;
    public final DeferredBlock<RotatedPillarBlock> strippedLog;
    public final DeferredBlock<RotatedPillarBlock> strippedWood;
    public final DeferredBlock<Block> planks;
    public final DeferredBlock<StairBlock> stairs;
    public final DeferredBlock<SlabBlock> slab;
    public final DeferredBlock<FenceBlock> fence;
    public final DeferredBlock<FenceGateBlock> fenceGate;
    public final DeferredBlock<DoorBlock> door;
    public final DeferredBlock<TrapDoorBlock> trapdoor;
    public final DeferredBlock<ButtonBlock> button;
    public final DeferredBlock<PressurePlateBlock> pressurePlate;
    public final DeferredBlock<StandingSignBlock> sign;
    public final DeferredBlock<WallSignBlock> wallSign;
    public final DeferredBlock<CeilingHangingSignBlock> hangingSign;
    public final DeferredBlock<WallHangingSignBlock> wallHangingSign;
    public final DeferredBlock<Block> bookshelf;
    public final DeferredBlock<LeavesBlock> leaves;
    public final DeferredBlock<LeavesBlock> floweringLeaves;
    public final DeferredBlock<LeavesBlock> fruitLeaves;
    public final DeferredBlock<SaplingBlock> sapling;
    public final DeferredItem<BoatItem> boat;
    public final DeferredItem<BoatItem> chestBoat;

    public final List<DeferredItem<?>> items = new ArrayList<>();

    public WoodFamily(String name, String englishName, MapColor color, boolean nether, int leavesColor) {
        this.name = name;
        this.englishName = englishName;
        this.nether = nether;
        this.leavesColor = leavesColor;
        this.color = color;
        String id = NaturaConstants.MOD_ID + ":" + name;
        this.blockSet = BlockSetType.register(new BlockSetType(id));
        this.woodType = WoodType.register(new WoodType(NaturaConstants.MOD_ID + "_" + name, this.blockSet));
        this.treeFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id(name + "_tree"));
        this.megaTree = "hopseed".equals(name) || "redwood".equals(name)
                ? ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id(name + "_mega"))
                : null;
        this.treeGrower = this.megaTree == null
                ? new TreeGrower(name, Optional.empty(), Optional.of(this.treeFeature), Optional.empty())
                : new TreeGrower(name, Optional.of(this.megaTree), Optional.of(this.treeFeature), Optional.empty());
        this.logTag = TagKey.create(Registries.BLOCK, NaturaConstants.id(name + "_logs"));
        this.logItemTag = TagKey.create(Registries.ITEM, NaturaConstants.id(name + "_logs"));

        this.log = log(name + "_log", "fusewood".equals(name), woodProperties());
        this.wood = pillar(name + "_wood", woodProperties());
        this.strippedLog = pillar("stripped_" + name + "_log", woodProperties());
        this.strippedWood = pillar("stripped_" + name + "_wood", woodProperties());
        this.planks = block(name + "_planks", () -> new Block(woodProperties()));
        this.stairs = block(name + "_stairs", () -> new StairBlock(this.planks.get().defaultBlockState(), woodProperties()));
        this.slab = block(name + "_slab", () -> new SlabBlock(woodProperties()));
        this.fence = block(name + "_fence", () -> new FenceBlock(woodProperties()));
        this.fenceGate = block(name + "_fence_gate", () -> new FenceGateBlock(this.woodType, woodProperties()));
        this.door = block(name + "_door", () -> new DoorBlock(this.blockSet, woodProperties().noOcclusion()));
        this.trapdoor = block(name + "_trapdoor", () -> new TrapDoorBlock(this.blockSet, woodProperties().noOcclusion()));
        this.button = block(name + "_button", () -> new ButtonBlock(this.blockSet, 30, BlockBehaviour.Properties.of()
                .noCollission()
                .strength(0.5F)
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.WOOD)));
        this.pressurePlate = block(name + "_pressure_plate", () -> new PressurePlateBlock(this.blockSet, BlockBehaviour.Properties.of()
                .mapColor(color)
                .noCollission()
                .strength(0.5F)
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.WOOD)));

        this.sign = NRBlocks.BLOCKS.register(name + "_sign", () -> new StandingSignBlock(this.woodType, signProperties()));
        this.wallSign = NRBlocks.BLOCKS.register(name + "_wall_sign", () -> new WallSignBlock(this.woodType, signProperties().dropsLike(this.sign.get())));
        this.hangingSign = NRBlocks.BLOCKS.register(name + "_hanging_sign", () -> new CeilingHangingSignBlock(this.woodType, signProperties()));
        this.wallHangingSign = NRBlocks.BLOCKS.register(name + "_wall_hanging_sign", () -> new WallHangingSignBlock(this.woodType, signProperties().dropsLike(this.hangingSign.get())));

        this.bookshelf = block(name + "_bookshelf", () -> new Block(shelfProperties()));
        this.leaves = block(name + "_leaves", () -> "redwood".equals(name)
                ? new RedwoodLeavesBlock(leavesProperties())
                : new LeavesBlock(leavesProperties()));
        if ("darkwood".equals(name)) {
            this.floweringLeaves = block(name + "_flowering_leaves", () -> new LeavesBlock(leavesProperties()));
            this.fruitLeaves = block(name + "_fruit_leaves", () -> new LeavesBlock(leavesProperties()));
        } else {
            this.floweringLeaves = null;
            this.fruitLeaves = null;
        }
        BlockBehaviour.Properties saplingProperties = BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY);
        if ("bloodwood".equals(name)) {
            this.sapling = block(name + "_sapling", () -> new BloodwoodSaplingBlock(this.treeGrower, saplingProperties));
        } else if (this.nether) {
            this.sapling = block(name + "_sapling", () -> new NetherSaplingBlock(this.treeGrower, saplingProperties));
        } else {
            this.sapling = block(name + "_sapling", () -> new SaplingBlock(this.treeGrower, saplingProperties));
        }

        item(this.log);
        item(this.wood);
        item(this.strippedLog);
        item(this.strippedWood);
        item(this.planks);
        item(this.stairs);
        item(this.slab);
        item(this.fence);
        item(this.fenceGate);
        item(this.door);
        item(this.trapdoor);
        item(this.button);
        item(this.pressurePlate);
        item(name + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16), this.sign.get(), this.wallSign.get()));
        item(name + "_hanging_sign", () -> new HangingSignItem(this.hangingSign.get(), this.wallHangingSign.get(), new Item.Properties().stacksTo(16)));
        item(this.bookshelf);
        item(this.leaves);
        if (this.floweringLeaves != null) {
            item(this.floweringLeaves);
            item(this.fruitLeaves);
        }
        item(this.sapling);
        if (this.nether) {
            this.boat = null;
            this.chestBoat = null;
        } else {
            Boat.Type type = NRBoatTypes.type(name);
            this.boat = item(name + "_boat", () -> new BoatItem(false, type, new Item.Properties().stacksTo(1)));
            this.chestBoat = item(name + "_chest_boat", () -> new BoatItem(true, type, new Item.Properties().stacksTo(1)));
        }
    }

    private BlockBehaviour.Properties woodProperties() {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .mapColor(this.color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD);
        return this.nether ? properties : properties.ignitedByLava();
    }

    private BlockBehaviour.Properties signProperties() {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .mapColor(this.color)
                .noCollission()
                .strength(1.0F)
                .sound(SoundType.WOOD);
        return this.nether ? properties : properties.ignitedByLava();
    }

    private BlockBehaviour.Properties shelfProperties() {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .mapColor(this.color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(1.5F)
                .sound(SoundType.WOOD);
        return this.nether ? properties : properties.ignitedByLava();
    }

    private BlockBehaviour.Properties leavesProperties() {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
                .pushReaction(PushReaction.DESTROY);
        return this.nether ? properties : properties.ignitedByLava();
    }

    private DeferredBlock<RotatedPillarBlock> log(String id, boolean fuse, BlockBehaviour.Properties properties) {
        return NRBlocks.BLOCKS.register(id, () -> fuse ? new FusewoodLogBlock(properties) : new RotatedPillarBlock(properties));
    }

    private DeferredBlock<RotatedPillarBlock> pillar(String id, BlockBehaviour.Properties properties) {
        return NRBlocks.BLOCKS.register(id, () -> new RotatedPillarBlock(properties));
    }

    private <B extends Block> DeferredBlock<B> block(String id, Supplier<B> supplier) {
        return NRBlocks.BLOCKS.register(id, supplier);
    }

    private <B extends Block> DeferredBlock<B> block(String id, BlockBehaviour.Properties properties, java.util.function.Function<BlockBehaviour.Properties, B> factory) {
        return NRBlocks.BLOCKS.register(id, () -> factory.apply(properties));
    }

    private void item(DeferredBlock<? extends Block> block) {
        item(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private <I extends Item> DeferredItem<I> item(String id, Supplier<I> supplier) {
        DeferredItem<I> registered = NRItems.ITEMS.register(id, supplier);
        this.items.add(registered);
        return registered;
    }
}

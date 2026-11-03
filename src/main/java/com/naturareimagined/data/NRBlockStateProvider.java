package com.naturareimagined.data;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.plant.BarleyCropBlock;
import com.naturareimagined.content.plant.BerryBushBlock;
import com.naturareimagined.content.plant.ColoredGrassBlock;
import com.naturareimagined.content.plant.CottonCropBlock;
import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRWoods;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class NRBlockStateProvider extends BlockStateProvider {

    public NRBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, NaturaConstants.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (WoodFamily family : NRWoods.ALL) {
            logBlock(family.log.get());
            blockItem(family.log.get());
            logBlock(family.strippedLog.get());
            blockItem(family.strippedLog.get());
            axisBlock(family.wood.get(), blockTexture(family.log.get()), blockTexture(family.log.get()));
            blockItem(family.wood.get());
            axisBlock(family.strippedWood.get(), blockTexture(family.strippedLog.get()), blockTexture(family.strippedLog.get()));
            blockItem(family.strippedWood.get());
            simpleBlockWithItem(family.planks.get(), cubeAll(family.planks.get()));
            stairsBlock(family.stairs.get(), blockTexture(family.planks.get()));
            blockItem(family.stairs.get());
            slabBlock(family.slab.get(), blockTexture(family.planks.get()), blockTexture(family.planks.get()));
            blockItem(family.slab.get());
            fenceBlock(family.fence.get(), blockTexture(family.planks.get()));
            fenceGateBlock(family.fenceGate.get(), blockTexture(family.planks.get()));
            blockItem(family.fenceGate.get());
            doorBlockWithRenderType(family.door.get(),
                    blockTexture(family.name + "_door_bottom"),
                    blockTexture(family.name + "_door_top"),
                    "cutout");
            trapdoor(family.trapdoor.get(), family.name);
            blockItem(family.trapdoor.get(), family.name + "_trapdoor_bottom");
            buttonBlock(family.button.get(), blockTexture(family.planks.get()));
            pressurePlateBlock(family.pressurePlate.get(), blockTexture(family.planks.get()));
            blockItem(family.pressurePlate.get());
            signBlock(family.sign.get(), family.wallSign.get(), blockTexture(family.planks.get()));
            hangingSignBlock(family.hangingSign.get(), family.wallHangingSign.get(), blockTexture(family.strippedLog.get()));
            simpleBlockWithItem(family.bookshelf.get(), models().cubeColumn(family.name + "_bookshelf",
                    blockTexture(family.bookshelf.get()), blockTexture(family.planks.get())));
            leaves(family.leaves.get(), family.name + "_leaves");
            if (family.floweringLeaves != null) {
                leaves(family.floweringLeaves.get(), family.name + "_flowering_leaves");
                leaves(family.fruitLeaves.get(), family.name + "_fruit_leaves");
            }
            if ("bloodwood".equals(family.name)) {
                hangingSapling(family.sapling.get(), family.name);
            } else {
                simpleBlock(family.sapling.get(), models().cross(family.name + "_sapling", blockTexture(family.sapling.get()))
                        .renderType("cutout"));
            }
        }
        for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
            bush(bush.block().get(), bush.blockName());
        }
        crop(NRPlants.BARLEY_CROP.get(), BarleyCropBlock.AGE, BarleyCropBlock.MAX_AGE, "barley");
        crop(NRPlants.COTTON_CROP.get(), CottonCropBlock.AGE, CottonCropBlock.MAX_AGE, "cotton");
        simpleBlock(NRPlants.BLUEBELLS.get(), models().cross("bluebells", blockTexture("bluebells_flowers")).renderType("cutout"));
        for (NRPlants.GrassSet grass : NRPlants.GRASSES) {
            coloredGrass(grass);
        }
        contentBlocks();
    }

    private void contentBlocks() {
        simpleBlockWithItem(NRContent.GHOST_NYLIUM.get(), models().cubeBottomTop("ghost_nylium",
                blockTexture("ghost_nylium_side"), mcLoc("block/netherrack"), blockTexture("ghost_nylium")));
        simpleBlockWithItem(NRContent.BLOOD_NYLIUM.get(), models().cubeBottomTop("blood_nylium",
                blockTexture("blood_nylium_side"), mcLoc("block/netherrack"), blockTexture("blood_nylium")));
        simpleBlockWithItem(NRContent.DARK_NYLIUM.get(), models().cubeBottomTop("dark_nylium",
                blockTexture("dark_nylium_side"), mcLoc("block/netherrack"), blockTexture("dark_nylium")));
        simpleBlockWithItem(NRContent.FUSE_NYLIUM.get(), models().cubeBottomTop("fuse_nylium",
                blockTexture("fuse_nylium_side"), mcLoc("block/netherrack"), blockTexture("fuse_nylium")));
        simpleBlockWithItem(NRContent.HEAT_SAND.get(), cubeAll(NRContent.HEAT_SAND.get()));
        simpleBlockWithItem(NRContent.TAINTED_SOIL.get(), cubeAll(NRContent.TAINTED_SOIL.get()));
        ModelFile dryFarm = models().withExistingParent("tainted_farmland_dry", mcLoc("block/template_farmland"))
                .texture("dirt", blockTexture("tainted_soil"))
                .texture("top", blockTexture("tainted_farmland_heated"));
        ModelFile wetFarm = models().withExistingParent("tainted_farmland_wet", mcLoc("block/template_farmland"))
                .texture("dirt", blockTexture("tainted_soil"))
                .texture("top", blockTexture("tainted_farmland_dry"));
        getVariantBuilder(NRContent.TAINTED_FARMLAND.get()).forAllStates(state -> {
            boolean moist = state.getValue(net.minecraft.world.level.block.FarmBlock.MOISTURE) == net.minecraft.world.level.block.FarmBlock.MAX_MOISTURE
                    && !state.getValue(com.naturareimagined.content.world.TaintedFarmlandBlock.HEATED);
            return ConfiguredModel.builder().modelFile(moist ? wetFarm : dryFarm).build();
        });
        simpleBlockItem(NRContent.TAINTED_FARMLAND.get(), dryFarm);
        thornVines();
        simpleBlockWithItem(NRContent.SOUL_GLASS.get(), models().cubeAll("soul_glass", blockTexture("soul_glass")).renderType("cutout"));
        simpleBlockWithItem(NRContent.HEAT_GLASS.get(), models().cubeAll("heat_glass", blockTexture("heat_glass")).renderType("cutout"));
        cloud(NRContent.WHITE_CLOUD.get(), "white_cloud");
        cloud(NRContent.DARK_CLOUD.get(), "dark_cloud");
        cloud(NRContent.ASH_CLOUD.get(), "ash_cloud");
        cloud(NRContent.SULFUR_CLOUD.get(), "sulfur_cloud");
        saguaro();
        ModelFile stage0 = models().withExistingParent("saguaro_stage0", mcLoc("block/cocoa_stage0"))
                .texture("particle", blockTexture("saguaro_stage0")).texture("cocoa", blockTexture("saguaro_stage0")).renderType("cutout");
        ModelFile stage1 = models().withExistingParent("saguaro_stage1", mcLoc("block/cocoa_stage1"))
                .texture("particle", blockTexture("saguaro_stage1")).texture("cocoa", blockTexture("saguaro_stage1")).renderType("cutout");
        ModelFile stage2 = saguaroStage2();
        getVariantBuilder(NRContent.SAGUARO_FRUIT_BLOCK.get()).forAllStates(state -> {
            ModelFile model = switch (state.getValue(net.minecraft.world.level.block.CocoaBlock.AGE)) {
                case 1 -> stage1;
                case 2 -> stage2;
                default -> stage0;
            };
            int y = switch (state.getValue(net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING)) {
                case EAST -> 270;
                case NORTH -> 180;
                case WEST -> 90;
                default -> 0;
            };
            return ConfiguredModel.builder().modelFile(model).rotationY(y).build();
        });
        glow(NRContent.GREEN_GLOWSHROOM.get(), "green");
        glow(NRContent.BLUE_GLOWSHROOM.get(), "blue");
        glow(NRContent.PURPLE_GLOWSHROOM.get(), "purple");
        glowStem(NRContent.GREEN_GLOWSHROOM_STEM.get(), "green");
        glowStem(NRContent.BLUE_GLOWSHROOM_STEM.get(), "blue");
        glowStem(NRContent.PURPLE_GLOWSHROOM_STEM.get(), "purple");
        cap(NRContent.GREEN_GLOWSHROOM_CAP.get(), "green");
        cap(NRContent.BLUE_GLOWSHROOM_CAP.get(), "blue");
        cap(NRContent.PURPLE_GLOWSHROOM_CAP.get(), "purple");
        furnace();
        hopper();
        rail(NRContent.BLAZE_RAIL.get(), "blaze_rail", "blaze_rail_corner", false);
        rail(NRContent.BLAZE_POWERED_RAIL.get(), "blaze_powered_rail", "blaze_powered_rail", true);
        rail(NRContent.BLAZE_DETECTOR_RAIL.get(), "blaze_detector_rail", "blaze_detector_rail", true);
        rail(NRContent.BLAZE_ACTIVATOR_RAIL.get(), "blaze_activator_rail", "blaze_activator_rail", true);
        lever();
    }

    private void cloud(Block block, String name) {
        simpleBlockWithItem(block, models().cubeAll(name, blockTexture(name)).renderType("translucent"));
    }

    private void glow(Block block, String color) {
        ModelFile model = models().cross(color + "_glowshroom", blockTexture(color + "_glowshroom"))
                .texture("particle", blockTexture(color + "_glowshroom_skin"))
                .renderType("translucent");
        simpleBlock(block, model);
        itemModels().getBuilder(color + "_glowshroom").parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", blockTexture(color + "_glowshroom"));
    }

    private void cap(Block block, String color) {
        ModelFile model = models().cube(color + "_glowshroom_cap",
                blockTexture(color + "_glowshroom_inside"),
                blockTexture(color + "_glowshroom_skin"),
                blockTexture(color + "_glowshroom_skin"),
                blockTexture(color + "_glowshroom_skin"),
                blockTexture(color + "_glowshroom_skin"),
                blockTexture(color + "_glowshroom_skin")).texture("particle", blockTexture(color + "_glowshroom_skin")).renderType("translucent");
        simpleBlockWithItem(block, model);
    }

    private void furnace() {
        ModelFile off = models().orientable("netherrack_furnace", blockTexture("netherrack_furnace_side"), blockTexture("netherrack_furnace_off"), blockTexture("netherrack_furnace_top"));
        ModelFile on = models().orientable("netherrack_furnace_on", blockTexture("netherrack_furnace_side"), blockTexture("netherrack_furnace_on"), blockTexture("netherrack_furnace_top"));
        horizontalBlock(NRContent.NETHERRACK_FURNACE.get(), state -> state.getValue(net.minecraft.world.level.block.AbstractFurnaceBlock.LIT) ? on : off);
        simpleBlockItem(NRContent.NETHERRACK_FURNACE.get(), off);
    }

    private void hopper() {
        ModelFile down = hopperModel("blaze_hopper", "minecraft:block/hopper");
        ModelFile side = hopperModel("blaze_hopper_side", "minecraft:block/hopper_side");
        getVariantBuilder(NRContent.BLAZE_HOPPER.get()).forAllStates(state -> {
            net.minecraft.core.Direction facing = state.getValue(net.minecraft.world.level.block.HopperBlock.FACING);
            int y = switch (facing) {
                case EAST -> 90;
                case SOUTH -> 180;
                case WEST -> 270;
                default -> 0;
            };
            return ConfiguredModel.builder().modelFile(facing == net.minecraft.core.Direction.DOWN ? down : side).rotationY(y).build();
        });
        flatItem("blaze_hopper", modLoc("item/blaze_hopper"));
    }

    private ModelFile hopperModel(String name, String parent) {
        return models().withExistingParent(name, parent)
                .texture("particle", blockTexture("blaze_hopper_outside"))
                .texture("top", blockTexture("blaze_hopper_top"))
                .texture("side", blockTexture("blaze_hopper_outside"))
                .texture("inside", blockTexture("blaze_hopper_inside"));
    }

    private void rail(Block block, String texture, String corner, boolean powered) {
        ModelFile flat = railModel(texture + "_flat", "minecraft:block/rail_flat", texture);
        ModelFile raisedNe = railModel(texture + "_raised_ne", "minecraft:block/rail_raised_ne", texture);
        ModelFile raisedSw = railModel(texture + "_raised_sw", "minecraft:block/rail_raised_sw", texture);
        ModelFile cornerModel = railModel(texture + "_corner", "minecraft:block/rail_curved", corner);
        ModelFile on = powered ? railModel(texture + "_on", "minecraft:block/rail_flat", texture + "_on") : flat;
        ModelFile raisedNeOn = powered ? railModel(texture + "_raised_ne_on", "minecraft:block/rail_raised_ne", texture + "_on") : raisedNe;
        ModelFile raisedSwOn = powered ? railModel(texture + "_raised_sw_on", "minecraft:block/rail_raised_sw", texture + "_on") : raisedSw;
        getVariantBuilder(block).forAllStates(state -> {
            net.minecraft.world.level.block.state.properties.RailShape shape = state.getValue(powered
                    ? net.minecraft.world.level.block.PoweredRailBlock.SHAPE
                    : net.minecraft.world.level.block.RailBlock.SHAPE);
            boolean lit = powered && state.getValue(net.minecraft.world.level.block.PoweredRailBlock.POWERED);
            ModelFile model = switch (shape) {
                case ASCENDING_EAST, ASCENDING_NORTH -> lit ? raisedNeOn : raisedNe;
                case ASCENDING_WEST, ASCENDING_SOUTH -> lit ? raisedSwOn : raisedSw;
                case SOUTH_EAST, SOUTH_WEST, NORTH_WEST, NORTH_EAST -> cornerModel;
                default -> lit ? on : flat;
            };
            int y = switch (shape) {
                case EAST_WEST, ASCENDING_EAST, ASCENDING_WEST, SOUTH_WEST -> 90;
                case NORTH_WEST -> 180;
                case NORTH_EAST -> 270;
                default -> 0;
            };
            return ConfiguredModel.builder().modelFile(model).rotationY(y).build();
        });
        flatItem(texture, blockTexture(texture));
    }

    private void lever() {
        ModelFile off = models().withExistingParent("nether_lever", "minecraft:block/lever")
                .texture("particle", blockTexture("nether_lever"))
                .texture("lever", blockTexture("nether_lever"))
                .texture("base", mcLoc("block/netherrack"));
        ModelFile on = models().withExistingParent("nether_lever_on", "minecraft:block/lever_on")
                .texture("particle", blockTexture("nether_lever"))
                .texture("lever", blockTexture("nether_lever"))
                .texture("base", mcLoc("block/netherrack"));
        getVariantBuilder(NRContent.NETHER_LEVER.get()).forAllStates(state -> {
            net.minecraft.world.level.block.state.properties.AttachFace face = state.getValue(net.minecraft.world.level.block.LeverBlock.FACE);
            net.minecraft.core.Direction facing = state.getValue(net.minecraft.world.level.block.LeverBlock.FACING);
            int y = switch (facing) {
                case EAST -> 90;
                case SOUTH -> 180;
                case WEST -> 270;
                default -> 0;
            };
            int x = 0;
            if (face == net.minecraft.world.level.block.state.properties.AttachFace.CEILING) {
                x = 180;
                y = (y + 180) % 360;
            } else if (face == net.minecraft.world.level.block.state.properties.AttachFace.WALL) {
                x = 90;
            }
            return ConfiguredModel.builder().modelFile(state.getValue(net.minecraft.world.level.block.LeverBlock.POWERED) ? on : off).rotationX(x).rotationY(y).build();
        });
        itemModels().getBuilder("nether_lever").parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", blockTexture("nether_lever"));
    }

    private ModelFile saguaroStage2() {
        BlockModelBuilder model = models().getBuilder("saguaro_stage2")
                .texture("particle", blockTexture("saguaro_stage2"))
                .texture("cocoa", blockTexture("saguaro_stage2"))
                .renderType("cutout");
        var pod = model.element().from(4, 3, 7).to(12, 12, 15);
        pod.face(Direction.UP).texture("#cocoa").uvs(0, 0, 7, 7).end();
        pod.face(Direction.DOWN).texture("#cocoa").uvs(0, 0, 7, 7).end();
        pod.face(Direction.NORTH).texture("#cocoa").uvs(8, 4, 16, 13).end();
        pod.face(Direction.SOUTH).texture("#cocoa").uvs(8, 4, 16, 13).end();
        pod.face(Direction.WEST).texture("#cocoa").uvs(8, 4, 16, 13).end();
        pod.face(Direction.EAST).texture("#cocoa").uvs(8, 4, 16, 13).end();
        pod.end();
        var stem = model.element().from(8, 12, 12).to(8, 16, 16);
        stem.face(Direction.EAST).texture("#cocoa").uvs(16, 0, 12, 4).end();
        stem.face(Direction.WEST).texture("#cocoa").uvs(12, 0, 16, 4).end();
        stem.end();
        return model;
    }

    private void bush(BerryBushBlock block, String name) {
        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(BerryBushBlock.AGE);
            String texture = age == BerryBushBlock.MAX_AGE ? name + "_ripe" : name;
            return ConfiguredModel.builder().modelFile(bushModel(name + "_age_" + age, texture, age)).build();
        });
        simpleBlockItem(block, models().getExistingFile(modLoc("block/" + name + "_age_" + BerryBushBlock.MAX_AGE)));
    }

    private ModelFile bushModel(String modelName, String texture, int age) {
        int from = age == 0 ? 4 : age == 1 ? 2 : 0;
        int to = age == 0 ? 12 : age == 1 ? 14 : 16;
        int height = age == 0 ? 8 : age == 1 ? 12 : 16;
        int v0 = age == 0 ? 8 : age == 1 ? 4 : 0;
        BlockModelBuilder model = models().withExistingParent(modelName, mcLoc("block/block"))
                .texture("particle", blockTexture(texture))
                .texture("all", blockTexture(texture))
                .renderType("cutout");
        var element = model.element().from(from, 0, from).to(to, height, to).shade(false);
        for (Direction direction : Direction.values()) {
            element.face(direction).texture("#all").uvs(from, v0, to, 16).end();
        }
        return element.end();
    }

    private void crop(Block block, net.minecraft.world.level.block.state.properties.IntegerProperty age, int max, String prefix) {
        var builder = getVariantBuilder(block);
        for (int stage = 0; stage <= max; stage++) {
            ModelFile model = models().crop(prefix + "_stage_" + stage, blockTexture(prefix + "_stage_" + stage)).renderType("cutout");
            builder.partialState().with(age, stage).modelForState().modelFile(model).addModel();
        }
    }

    private void coloredGrass(NRPlants.GrassSet grass) {
        ModelFile normal = models().withExistingParent(grass.name(), mcLoc("block/grass_block")).renderType("cutout_mipped");
        ModelFile snowy = models().withExistingParent(grass.name() + "_snowy", mcLoc("block/grass_block_snow")).renderType("cutout_mipped");
        getVariantBuilder(grass.block().get())
                .partialState().with(ColoredGrassBlock.SNOWY, false).modelForState().modelFile(normal).addModel()
                .partialState().with(ColoredGrassBlock.SNOWY, true).modelForState().modelFile(snowy).addModel();
        simpleBlockItem(grass.block().get(), normal);
    }

    private ModelFile railModel(String name, String parent, String texture) {
        return models().withExistingParent(name, parent).texture("rail", blockTexture(texture)).renderType("cutout");
    }

    private void saguaro() {
        BlockModelBuilder model = models().withExistingParent("saguaro", mcLoc("block/block"))
                .texture("particle", blockTexture("saguaro_side"))
                .texture("side", blockTexture("saguaro_side"))
                .texture("top", blockTexture("saguaro_top"))
                .texture("bottom", blockTexture("saguaro_bottom"))
                .renderType("cutout");
        model.element().from(1, 0, 1).to(15, 16, 15)
                .face(Direction.DOWN).uvs(1, 1, 15, 15).texture("#bottom").end()
                .face(Direction.UP).uvs(1, 1, 15, 15).texture("#top").end()
                .end();
        model.element().from(0, 0, 1).to(16, 16, 15)
                .face(Direction.NORTH).texture("#side").end()
                .face(Direction.SOUTH).texture("#side").end()
                .end();
        model.element().from(1, 0, 0).to(15, 16, 16)
                .face(Direction.WEST).texture("#side").end()
                .face(Direction.EAST).texture("#side").end()
                .end();
        simpleBlockWithItem(NRContent.SAGUARO.get(), model);
    }

    private void hangingSapling(Block block, String name) {
        BlockModelBuilder model = models().getBuilder(name + "_sapling")
                .texture("particle", blockTexture(name + "_sapling"))
                .texture("cross", blockTexture(name + "_sapling"))
                .renderType("cutout")
                .ao(false);
        model.element().from(0.8F, 0, 8).to(15.2F, 16, 8)
                .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(Direction.NORTH).uvs(0, 16, 16, 0).texture("#cross").end()
                .face(Direction.SOUTH).uvs(0, 16, 16, 0).texture("#cross").end()
                .end();
        model.element().from(8, 0, 0.8F).to(8, 16, 15.2F)
                .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45).rescale(true).end()
                .shade(false)
                .face(Direction.WEST).uvs(0, 16, 16, 0).texture("#cross").end()
                .face(Direction.EAST).uvs(0, 16, 16, 0).texture("#cross").end()
                .end();
        simpleBlock(block, model);
    }

    private void glowStem(Block block, String color) {
        ModelFile model = models().cubeColumn(color + "_glowshroom_stem", blockTexture(color + "_glowshroom_stem"), blockTexture(color + "_glowshroom_stem"))
                .texture("particle", blockTexture(color + "_glowshroom_stem")).renderType("translucent");
        axisBlock((net.minecraft.world.level.block.RotatedPillarBlock) block, model, model);
        simpleBlockItem(block, model);
    }

    private void thornVines() {
        ModelFile vine = models().withExistingParent("thorn_vines", mcLoc("block/vine"))
                .texture("vine", blockTexture("thorn_vines"))
                .texture("particle", blockTexture("thorn_vines"))
                .renderType("cutout");
        var multipart = getMultipartBuilder(NRContent.THORNVINE.get());
        face(multipart, vine, 0, 0, false, VineBlock.NORTH);
        face(multipart, vine, 90, 0, true, VineBlock.EAST);
        face(multipart, vine, 180, 0, true, VineBlock.SOUTH);
        face(multipart, vine, 270, 0, true, VineBlock.WEST);
        multipart.part().modelFile(vine).rotationX(270).uvLock(true).addModel().condition(VineBlock.UP, true);
        multipart.part().modelFile(vine).rotationX(270).uvLock(true).addModel()
                .condition(VineBlock.NORTH, false).condition(VineBlock.EAST, false)
                .condition(VineBlock.SOUTH, false).condition(VineBlock.WEST, false).condition(VineBlock.UP, false);
        flatItem("thorn_vines", blockTexture("thorn_vines"));
    }

    private void face(net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder multipart, ModelFile vine,
                      int y, int x, boolean lock, net.minecraft.world.level.block.state.properties.BooleanProperty property) {
        multipart.part().modelFile(vine).rotationY(y).rotationX(x).uvLock(lock).addModel().condition(property, true);
        multipart.part().modelFile(vine).rotationY(y).rotationX(x).uvLock(lock).addModel()
                .condition(VineBlock.NORTH, false).condition(VineBlock.EAST, false)
                .condition(VineBlock.SOUTH, false).condition(VineBlock.WEST, false).condition(VineBlock.UP, false);
    }

    private void trapdoor(Block block, String name) {
        ResourceLocation texture = blockTexture(name + "_trapdoor");
        ModelFile bottom = trapdoorFlat(name + "_trapdoor_bottom", texture, 0);
        ModelFile top = trapdoorFlat(name + "_trapdoor_top", texture, 13);
        ModelFile open = trapdoorOpen(name + "_trapdoor_open", texture);
        getVariantBuilder(block).forAllStatesExcept(state -> {
            boolean opened = state.getValue(TrapDoorBlock.OPEN);
            ModelFile model = opened ? open : state.getValue(TrapDoorBlock.HALF) == Half.TOP ? top : bottom;
            int y = 0;
            if (opened) {
                y = switch (state.getValue(TrapDoorBlock.FACING)) {
                    case EAST -> 90;
                    case SOUTH -> 180;
                    case WEST -> 270;
                    default -> 0;
                };
            }
            return ConfiguredModel.builder().modelFile(model).rotationY(y).build();
        }, BlockStateProperties.POWERED, BlockStateProperties.WATERLOGGED);
    }

    private ModelFile trapdoorFlat(String modelName, ResourceLocation texture, int y) {
        BlockModelBuilder model = models().withExistingParent(modelName, mcLoc("block/thin_block"))
                .texture("texture", texture).texture("particle", texture).renderType("cutout");
        doorSlice(model, y + 2, true, false, 0);
        doorSlice(model, y + 1, false, false, 1);
        doorSlice(model, y, false, true, 0);
        return model;
    }

    private ModelFile trapdoorOpen(String modelName, ResourceLocation texture) {
        BlockModelBuilder model = models().withExistingParent(modelName, mcLoc("block/thin_block"))
                .texture("texture", texture).texture("particle", texture).renderType("cutout");
        openSlice(model, 13, 0, true, false);
        openSlice(model, 14, 1, false, false);
        openSlice(model, 15, 0, false, true);
        return model;
    }

    private void doorSlice(BlockModelBuilder model, int y, boolean up, boolean down, int row) {
        var element = model.element().from(0, y, 0).to(16, y + 1, 16);
        if (up) {
            var face = element.face(Direction.UP).texture("#texture").uvs(0, 0, 16, 16);
            if (y + 1 == 16) {
                face.cullface(Direction.UP);
            }
            face.end();
        }
        if (down) {
            var face = element.face(Direction.DOWN).texture("#texture").uvs(0, 0, 16, 16);
            if (y == 0) {
                face.cullface(Direction.DOWN);
            }
            face.end();
        }
        int v = row == 0 ? 0 : 1;
        element.face(Direction.NORTH).texture("#texture").uvs(0, v, 16, v + 1).cullface(Direction.NORTH).end();
        element.face(Direction.SOUTH).texture("#texture").uvs(0, v, 16, v + 1).cullface(Direction.SOUTH).end();
        element.face(Direction.WEST).texture("#texture").uvs(0, v, 16, v + 1).cullface(Direction.WEST).end();
        element.face(Direction.EAST).texture("#texture").uvs(0, v, 16, v + 1).cullface(Direction.EAST).end();
        element.end();
    }

    private void openSlice(BlockModelBuilder model, int z, int row, boolean front, boolean back) {
        var element = model.element().from(0, 0, z).to(16, 16, z + 1);
        if (front) {
            element.face(Direction.NORTH).texture("#texture").uvs(0, 0, 16, 16)
                    .rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).end();
        }
        if (back) {
            element.face(Direction.SOUTH).texture("#texture").uvs(0, 0, 16, 16)
                    .rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).cullface(Direction.SOUTH).end();
        }
        int v = row == 0 ? 0 : 1;
        element.face(Direction.UP).texture("#texture").uvs(0, v, 16, v + 1).cullface(Direction.UP).end();
        element.face(Direction.DOWN).texture("#texture").uvs(0, v, 16, v + 1).cullface(Direction.DOWN).end();
        element.face(Direction.WEST).texture("#texture").uvs(0, v, 16, v + 1)
                .rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).cullface(Direction.WEST).end();
        element.face(Direction.EAST).texture("#texture").uvs(0, v, 16, v + 1)
                .rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).cullface(Direction.EAST).end();
        element.end();
    }

    private void flatItem(String name, ResourceLocation texture) {
        itemModels().getBuilder(name).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", texture);
    }

    private void blockItem(Block block) {
        blockItem(block, blockTexture(block).getPath().substring("block/".length()));
    }

    private void blockItem(Block block, String model) {
        itemModels().withExistingParent(blockTexture(block).getPath().substring("block/".length()), modLoc("block/" + model));
    }

    private void leaves(Block block, String texture) {
        ModelFile model = models().withExistingParent(texture, "minecraft:block/leaves")
                .texture("all", blockTexture(texture));
        simpleBlockWithItem(block, model);
    }

    private ResourceLocation blockTexture(String name) {
        return modLoc("block/" + name);
    }
}

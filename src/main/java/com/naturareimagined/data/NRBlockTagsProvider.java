package com.naturareimagined.data;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRTags;
import com.naturareimagined.registry.NRWoods;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class NRBlockTagsProvider extends BlockTagsProvider {

    public NRBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper helper) {
        super(output, lookup, NaturaConstants.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (WoodFamily family : NRWoods.ALL) {
            tag(family.logTag).add(family.log.get(), family.wood.get(), family.strippedLog.get(), family.strippedWood.get());
            tag(BlockTags.LOGS).addTag(family.logTag);
            tag(BlockTags.PLANKS).add(family.planks.get());
            tag(BlockTags.WOODEN_STAIRS).add(family.stairs.get());
            tag(BlockTags.WOODEN_SLABS).add(family.slab.get());
            tag(BlockTags.WOODEN_FENCES).add(family.fence.get());
            tag(BlockTags.FENCE_GATES).add(family.fenceGate.get());
            tag(BlockTags.WOODEN_DOORS).add(family.door.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(family.trapdoor.get());
            tag(BlockTags.WOODEN_BUTTONS).add(family.button.get());
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(family.pressurePlate.get());
            tag(BlockTags.STANDING_SIGNS).add(family.sign.get());
            tag(BlockTags.WALL_SIGNS).add(family.wallSign.get());
            tag(BlockTags.CEILING_HANGING_SIGNS).add(family.hangingSign.get());
            tag(BlockTags.WALL_HANGING_SIGNS).add(family.wallHangingSign.get());
            tag(BlockTags.ENCHANTMENT_POWER_PROVIDER).add(family.bookshelf.get());
            tag(Tags.Blocks.STRIPPED_LOGS).add(family.strippedLog.get());
            tag(Tags.Blocks.STRIPPED_WOODS).add(family.strippedWood.get());
            tag(Tags.Blocks.BOOKSHELVES).add(family.bookshelf.get());
            tag(Tags.Blocks.FENCE_GATES_WOODEN).add(family.fenceGate.get());
            tag(BlockTags.LEAVES).add(family.leaves.get());
            tag(BlockTags.MINEABLE_WITH_HOE).add(family.leaves.get());
            tag(BlockTags.SAPLINGS).add(family.sapling.get());
            tag(BlockTags.MINEABLE_WITH_AXE).add(
                    family.log.get(), family.wood.get(), family.strippedLog.get(), family.strippedWood.get(),
                    family.planks.get(), family.stairs.get(), family.slab.get(), family.fence.get(),
                    family.fenceGate.get(), family.door.get(), family.trapdoor.get(), family.button.get(),
                    family.pressurePlate.get(), family.sign.get(), family.wallSign.get(),
                    family.hangingSign.get(), family.wallHangingSign.get(), family.bookshelf.get());
            if (!family.nether) {
                tag(BlockTags.LOGS_THAT_BURN).addTag(family.logTag);
            }
            if (family.floweringLeaves != null) {
                tag(BlockTags.LEAVES).add(family.floweringLeaves.get(), family.fruitLeaves.get());
                tag(BlockTags.MINEABLE_WITH_HOE).add(family.floweringLeaves.get(), family.fruitLeaves.get());
            }
        }
        tag(BlockTags.CROPS).add(NRPlants.BARLEY_CROP.get(), NRPlants.COTTON_CROP.get());
        tag(BlockTags.MAINTAINS_FARMLAND).add(NRPlants.BARLEY_CROP.get(), NRPlants.COTTON_CROP.get());
        tag(BlockTags.BEE_GROWABLES).add(NRPlants.BARLEY_CROP.get(), NRPlants.COTTON_CROP.get());
        tag(BlockTags.SMALL_FLOWERS).add(NRPlants.BLUEBELLS.get());
        for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
            tag(BlockTags.SWORD_EFFICIENT).add(bush.block().get());
        }
        for (NRPlants.GrassSet grass : NRPlants.GRASSES) {
            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(grass.block().get());
        }
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(NRContent.HEAT_SAND.get(), NRContent.TAINTED_SOIL.get(), NRContent.TAINTED_FARMLAND.get());
        tag(BlockTags.SAND).add(NRContent.HEAT_SAND.get());
        tag(BlockTags.INFINIBURN_NETHER).add(NRContent.TAINTED_SOIL.get());
        tag(BlockTags.CLIMBABLE).add(NRContent.THORNVINE.get());
        tag(BlockTags.SWORD_EFFICIENT).add(NRContent.THORNVINE.get(), NRContent.SAGUARO.get(), NRContent.SAGUARO_FRUIT_BLOCK.get());
        tag(BlockTags.NYLIUM).add(NRContent.GHOST_NYLIUM.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                NRContent.GHOST_NYLIUM.get(),
                NRContent.SOUL_GLASS.get(), NRContent.HEAT_GLASS.get(),
                NRContent.NETHERRACK_FURNACE.get(), NRContent.BLAZE_HOPPER.get(),
                NRContent.BLAZE_RAIL.get(), NRContent.BLAZE_POWERED_RAIL.get(),
                NRContent.BLAZE_DETECTOR_RAIL.get(), NRContent.BLAZE_ACTIVATOR_RAIL.get());
        tag(BlockTags.RAILS).add(NRContent.BLAZE_RAIL.get(), NRContent.BLAZE_POWERED_RAIL.get(),
                NRContent.BLAZE_DETECTOR_RAIL.get(), NRContent.BLAZE_ACTIVATOR_RAIL.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(NRContent.NETHER_LEVER.get());
        tag(NRTags.MINEABLE_HATCHET).addTag(BlockTags.MINEABLE_WITH_AXE);
    }
}

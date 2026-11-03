package com.naturareimagined.data;

import com.naturareimagined.content.plant.BarleyCropBlock;
import com.naturareimagined.content.plant.BerryBushBlock;
import com.naturareimagined.content.plant.CottonCropBlock;
import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRWoods;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class NRBlockLoot extends BlockLootSubProvider {

    public NRBlockLoot(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        for (WoodFamily family : NRWoods.ALL) {
            dropSelf(family.log.get());
            dropSelf(family.wood.get());
            dropSelf(family.strippedLog.get());
            dropSelf(family.strippedWood.get());
            dropSelf(family.planks.get());
            dropSelf(family.stairs.get());
            add(family.slab.get(), createSlabItemTable(family.slab.get()));
            dropSelf(family.fence.get());
            dropSelf(family.fenceGate.get());
            add(family.door.get(), createDoorTable(family.door.get()));
            dropSelf(family.trapdoor.get());
            dropSelf(family.button.get());
            dropSelf(family.pressurePlate.get());
            dropSelf(family.sign.get());
            dropSelf(family.hangingSign.get());
            add(family.bookshelf.get(), createSingleItemTableWithSilkTouch(family.bookshelf.get(), Items.BOOK, ConstantValue.exactly(3.0F)));
            add(family.leaves.get(), block -> createLeavesDrops(block, family.sapling.get(), NORMAL_LEAVES_SAPLING_CHANCES));
            if (family.floweringLeaves != null) {
                add(family.floweringLeaves.get(), block -> createLeavesDrops(block, family.sapling.get(), NORMAL_LEAVES_SAPLING_CHANCES));
                add(family.fruitLeaves.get(), block -> createLeavesDrops(block, family.sapling.get(), NORMAL_LEAVES_SAPLING_CHANCES));
            }
            dropSelf(family.sapling.get());
        }
        for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
            add(bush.block().get(), this::berryBush);
        }
        add(NRPlants.BARLEY_CROP.get(), createCropDrops(NRPlants.BARLEY_CROP.get(), NRPlants.BARLEY.get(), NRPlants.BARLEY_SEEDS.get(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(NRPlants.BARLEY_CROP.get())
                        .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(BarleyCropBlock.AGE, BarleyCropBlock.MAX_AGE))));
        add(NRPlants.COTTON_CROP.get(), createCropDrops(NRPlants.COTTON_CROP.get(), NRPlants.COTTON.get(), NRPlants.COTTON_SEEDS.get(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(NRPlants.COTTON_CROP.get())
                        .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(CottonCropBlock.AGE, CottonCropBlock.MAX_AGE))));
        dropSelf(NRPlants.BLUEBELLS.get());
        for (NRPlants.GrassSet grass : NRPlants.GRASSES) {
            dropSelf(grass.block().get());
        }
        add(NRContent.GHOST_NYLIUM.get(), block -> createSingleItemTableWithSilkTouch(block, Blocks.NETHERRACK));
        add(NRContent.BLOOD_NYLIUM.get(), block -> createSingleItemTableWithSilkTouch(block, Blocks.NETHERRACK));
        add(NRContent.DARK_NYLIUM.get(), block -> createSingleItemTableWithSilkTouch(block, Blocks.NETHERRACK));
        add(NRContent.FUSE_NYLIUM.get(), block -> createSingleItemTableWithSilkTouch(block, Blocks.NETHERRACK));
        dropSelf(NRContent.PYREFLY_BUSH.get());
        dropSelf(NRContent.HEAT_SAND.get());
        dropSelf(NRContent.TAINTED_SOIL.get());
        dropSelf(NRContent.TAINTED_FARMLAND.get());
        dropSelf(NRContent.THORNVINE.get());
        add(NRContent.SOUL_GLASS.get(), createSilkTouchOnlyTable(NRContent.SOUL_GLASS.get()));
        add(NRContent.HEAT_GLASS.get(), createSilkTouchOnlyTable(NRContent.HEAT_GLASS.get()));
        dropSelf(NRContent.WHITE_CLOUD.get());
        dropSelf(NRContent.DARK_CLOUD.get());
        dropSelf(NRContent.ASH_CLOUD.get());
        dropSelf(NRContent.SULFUR_CLOUD.get());
        dropSelf(NRContent.SAGUARO.get());
        add(NRContent.SAGUARO_FRUIT_BLOCK.get(), block -> applyExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(NRContent.SAGUARO_FRUIT.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(net.minecraft.world.level.block.CocoaBlock.AGE, 2))))
                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.5714286F, 1))))));
        dropSelf(NRContent.GREEN_GLOWSHROOM.get());
        dropSelf(NRContent.BLUE_GLOWSHROOM.get());
        dropSelf(NRContent.PURPLE_GLOWSHROOM.get());
        dropSelf(NRContent.GREEN_GLOWSHROOM_STEM.get());
        dropSelf(NRContent.BLUE_GLOWSHROOM_STEM.get());
        dropSelf(NRContent.PURPLE_GLOWSHROOM_STEM.get());
        dropSelf(NRContent.GREEN_GLOWSHROOM_CAP.get());
        dropSelf(NRContent.BLUE_GLOWSHROOM_CAP.get());
        dropSelf(NRContent.PURPLE_GLOWSHROOM_CAP.get());
        dropSelf(NRContent.NETHERRACK_FURNACE.get());
        dropSelf(NRContent.BLAZE_HOPPER.get());
        dropSelf(NRContent.BLAZE_RAIL.get());
        dropSelf(NRContent.BLAZE_POWERED_RAIL.get());
        dropSelf(NRContent.BLAZE_DETECTOR_RAIL.get());
        dropSelf(NRContent.BLAZE_ACTIVATOR_RAIL.get());
        dropSelf(NRContent.NETHER_LEVER.get());
    }

    private LootTable.Builder berryBush(Block block) {
        return applyExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool()
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                        .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(BerryBushBlock.AGE, BerryBushBlock.MAX_AGE)))
                .add(LootItem.lootTableItem(((BerryBushBlock) block).berries())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (WoodFamily family : NRWoods.ALL) {
            blocks.add(family.log.get());
            blocks.add(family.wood.get());
            blocks.add(family.strippedLog.get());
            blocks.add(family.strippedWood.get());
            blocks.add(family.planks.get());
            blocks.add(family.stairs.get());
            blocks.add(family.slab.get());
            blocks.add(family.fence.get());
            blocks.add(family.fenceGate.get());
            blocks.add(family.door.get());
            blocks.add(family.trapdoor.get());
            blocks.add(family.button.get());
            blocks.add(family.pressurePlate.get());
            blocks.add(family.sign.get());
            blocks.add(family.hangingSign.get());
            blocks.add(family.bookshelf.get());
            blocks.add(family.leaves.get());
            if (family.floweringLeaves != null) {
                blocks.add(family.floweringLeaves.get());
                blocks.add(family.fruitLeaves.get());
            }
            blocks.add(family.sapling.get());
        }
        for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
            blocks.add(bush.block().get());
        }
        blocks.add(NRPlants.BARLEY_CROP.get());
        blocks.add(NRPlants.COTTON_CROP.get());
        blocks.add(NRPlants.BLUEBELLS.get());
        for (NRPlants.GrassSet grass : NRPlants.GRASSES) {
            blocks.add(grass.block().get());
        }
        blocks.add(NRContent.GHOST_NYLIUM.get());
        blocks.add(NRContent.BLOOD_NYLIUM.get());
        blocks.add(NRContent.DARK_NYLIUM.get());
        blocks.add(NRContent.FUSE_NYLIUM.get());
        blocks.add(NRContent.PYREFLY_BUSH.get());
        blocks.add(NRContent.HEAT_SAND.get());
        blocks.add(NRContent.TAINTED_SOIL.get());
        blocks.add(NRContent.TAINTED_FARMLAND.get());
        blocks.add(NRContent.THORNVINE.get());
        blocks.add(NRContent.SOUL_GLASS.get());
        blocks.add(NRContent.HEAT_GLASS.get());
        blocks.add(NRContent.WHITE_CLOUD.get());
        blocks.add(NRContent.DARK_CLOUD.get());
        blocks.add(NRContent.ASH_CLOUD.get());
        blocks.add(NRContent.SULFUR_CLOUD.get());
        blocks.add(NRContent.SAGUARO.get());
        blocks.add(NRContent.SAGUARO_FRUIT_BLOCK.get());
        blocks.add(NRContent.GREEN_GLOWSHROOM.get());
        blocks.add(NRContent.BLUE_GLOWSHROOM.get());
        blocks.add(NRContent.PURPLE_GLOWSHROOM.get());
        blocks.add(NRContent.GREEN_GLOWSHROOM_STEM.get());
        blocks.add(NRContent.BLUE_GLOWSHROOM_STEM.get());
        blocks.add(NRContent.PURPLE_GLOWSHROOM_STEM.get());
        blocks.add(NRContent.GREEN_GLOWSHROOM_CAP.get());
        blocks.add(NRContent.BLUE_GLOWSHROOM_CAP.get());
        blocks.add(NRContent.PURPLE_GLOWSHROOM_CAP.get());
        blocks.add(NRContent.NETHERRACK_FURNACE.get());
        blocks.add(NRContent.BLAZE_HOPPER.get());
        blocks.add(NRContent.BLAZE_RAIL.get());
        blocks.add(NRContent.BLAZE_POWERED_RAIL.get());
        blocks.add(NRContent.BLAZE_DETECTOR_RAIL.get());
        blocks.add(NRContent.BLAZE_ACTIVATOR_RAIL.get());
        blocks.add(NRContent.NETHER_LEVER.get());
        return blocks;
    }
}

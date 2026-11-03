package com.naturareimagined.data;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRTags;
import com.naturareimagined.registry.NRWoods;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class NRItemTagsProvider extends ItemTagsProvider {

    public NRItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup,
                              CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper helper) {
        super(output, lookup, blockTags, NaturaConstants.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (WoodFamily family : NRWoods.ALL) {
            copy(family.logTag, family.logItemTag);
            tag(ItemTags.LOGS).addTag(family.logItemTag);
            tag(Tags.Items.STRIPPED_LOGS).add(family.strippedLog.get().asItem());
            tag(Tags.Items.STRIPPED_WOODS).add(family.strippedWood.get().asItem());
            tag(Tags.Items.BOOKSHELVES).add(family.bookshelf.get().asItem());
            tag(Tags.Items.FENCE_GATES_WOODEN).add(family.fenceGate.get().asItem());
            tag(ItemTags.PLANKS).add(family.planks.get().asItem());
            tag(ItemTags.WOODEN_STAIRS).add(family.stairs.get().asItem());
            tag(ItemTags.WOODEN_SLABS).add(family.slab.get().asItem());
            tag(ItemTags.WOODEN_FENCES).add(family.fence.get().asItem());
            tag(ItemTags.FENCE_GATES).add(family.fenceGate.get().asItem());
            tag(ItemTags.WOODEN_DOORS).add(family.door.get().asItem());
            tag(ItemTags.WOODEN_TRAPDOORS).add(family.trapdoor.get().asItem());
            tag(ItemTags.WOODEN_BUTTONS).add(family.button.get().asItem());
            tag(ItemTags.WOODEN_PRESSURE_PLATES).add(family.pressurePlate.get().asItem());
            tag(ItemTags.SIGNS).add(family.sign.get().asItem());
            tag(ItemTags.HANGING_SIGNS).add(family.hangingSign.get().asItem());
            tag(ItemTags.LEAVES).add(family.leaves.get().asItem());
            tag(ItemTags.SAPLINGS).add(family.sapling.get().asItem());
            if (!family.nether) {
                tag(ItemTags.LOGS_THAT_BURN).addTag(family.logItemTag);
            }
            if (family.floweringLeaves != null) {
                tag(ItemTags.LEAVES).add(family.floweringLeaves.get().asItem(), family.fruitLeaves.get().asItem());
            }
            if (family.nether) {
                tag(ItemTags.NON_FLAMMABLE_WOOD).add(
                        family.log.get().asItem(), family.wood.get().asItem(),
                        family.strippedLog.get().asItem(), family.strippedWood.get().asItem(),
                        family.planks.get().asItem(), family.stairs.get().asItem(), family.slab.get().asItem(),
                        family.fence.get().asItem(), family.fenceGate.get().asItem(),
                        family.door.get().asItem(), family.trapdoor.get().asItem(),
                        family.button.get().asItem(), family.pressurePlate.get().asItem(),
                        family.sign.get().asItem(), family.hangingSign.get().asItem());
            }
            if (family.boat != null) {
                tag(ItemTags.BOATS).add(family.boat.get());
                tag(ItemTags.CHEST_BOATS).add(family.chestBoat.get());
            }
        }
        TagKey<Item> barleySeeds = commonItem("seeds/barley");
        TagKey<Item> cottonSeeds = commonItem("seeds/cotton");
        TagKey<Item> barleyCrop = commonItem("crops/barley");
        TagKey<Item> cottonCrop = commonItem("crops/cotton");
        tag(barleySeeds).add(NRPlants.BARLEY_SEEDS.get());
        tag(cottonSeeds).add(NRPlants.COTTON_SEEDS.get());
        tag(Tags.Items.SEEDS).addTag(barleySeeds).addTag(cottonSeeds);
        tag(barleyCrop).add(NRPlants.BARLEY.get());
        tag(cottonCrop).add(NRPlants.COTTON.get());
        tag(Tags.Items.CROPS).addTag(barleyCrop).addTag(cottonCrop);
        tag(ItemTags.PIG_FOOD).add(NRPlants.BARLEY_SEEDS.get(), NRPlants.COTTON_SEEDS.get());
        for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
            tag(Tags.Items.FOODS).add(bush.berries().get());
            tag(Tags.Items.FOODS_BERRY).add(bush.berries().get());
            if (!bush.nether()) {
                tag(ItemTags.FOX_FOOD).add(bush.berries().get());
            }
        }
        tag(Tags.Items.FOODS).add(NRPlants.BERRY_MEDLEY_ITEM.get());
        TagKey<Item> saplings = TagKey.create(Registries.ITEM, com.naturareimagined.NaturaConstants.id("saplings"));
        for (WoodFamily family : NRWoods.ALL) {
            tag(saplings).add(family.sapling.get().asItem());
        }
        tag(ItemTags.SWORDS).add(NRContent.QUARTZ_SWORD.get());
        tag(ItemTags.PICKAXES).add(NRContent.QUARTZ_PICKAXE.get());
        tag(ItemTags.AXES).add(NRContent.QUARTZ_AXE.get());
        tag(ItemTags.SHOVELS).add(NRContent.QUARTZ_SHOVEL.get());
        tag(ItemTags.HOES).add(NRContent.QUARTZ_HOE.get());
        for (var hatchet : NRContent.HATCHETS) {
            tag(NRTags.HATCHETS).add(hatchet.get());
        }
        tag(ItemTags.DURABILITY_ENCHANTABLE).addTag(NRTags.HATCHETS);
        tag(ItemTags.MINING_ENCHANTABLE).addTag(NRTags.HATCHETS);
        tag(ItemTags.MINING_LOOT_ENCHANTABLE).addTag(NRTags.HATCHETS);
        tag(ItemTags.HEAD_ARMOR).add(NRContent.IMP_HELMET.get());
        tag(ItemTags.CHEST_ARMOR).add(NRContent.IMP_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR).add(NRContent.IMP_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR).add(NRContent.IMP_BOOTS.get());
        tag(Tags.Items.FOODS).add(NRContent.RAW_IMP_MEAT.get(), NRContent.COOKED_IMP_MEAT.get(), NRContent.POTASH_APPLE.get(),
                NRContent.CACTUS_JUICE.get(), NRContent.GREEN_GLOWSHROOM_STEW.get(), NRContent.BLUE_GLOWSHROOM_STEW.get(),
                NRContent.PURPLE_GLOWSHROOM_STEW.get(), NRContent.SAGUARO_FRUIT.get());
        tag(Tags.Items.FOODS_RAW_MEAT).add(NRContent.RAW_IMP_MEAT.get());
        tag(Tags.Items.FOODS_COOKED_MEAT).add(NRContent.COOKED_IMP_MEAT.get());
        tag(ItemTags.MEAT).add(NRContent.RAW_IMP_MEAT.get(), NRContent.COOKED_IMP_MEAT.get());
        tag(Tags.Items.STRINGS).add(NRContent.FLAME_STRING.get());
        tag(Tags.Items.LEATHERS).add(NRContent.IMP_LEATHER.get());
        tag(Tags.Items.DUSTS).add(NRContent.SULFUR_POWDER.get());
    }

    private static TagKey<Item> commonItem(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
    }
}

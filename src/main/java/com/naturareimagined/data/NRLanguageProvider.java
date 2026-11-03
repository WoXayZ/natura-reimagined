package com.naturareimagined.data;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRWoods;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class NRLanguageProvider extends LanguageProvider {

    public NRLanguageProvider(PackOutput output) {
        super(output, NaturaConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.naturareimagined.main", "Natura Reimagined");
        for (WoodFamily family : NRWoods.ALL) {
            String wood = family.englishName;
            add(family.log.get(), wood + " Log");
            add(family.wood.get(), wood + " Wood");
            add(family.strippedLog.get(), "Stripped " + wood + " Log");
            add(family.strippedWood.get(), "Stripped " + wood + " Wood");
            add(family.planks.get(), wood + " Planks");
            add(family.stairs.get(), wood + " Stairs");
            add(family.slab.get(), wood + " Slab");
            add(family.fence.get(), wood + " Fence");
            add(family.fenceGate.get(), wood + " Fence Gate");
            add(family.door.get(), wood + " Door");
            add(family.trapdoor.get(), wood + " Trapdoor");
            add(family.button.get(), wood + " Button");
            add(family.pressurePlate.get(), wood + " Pressure Plate");
            add(family.sign.get(), wood + " Sign");
            add(family.hangingSign.get(), wood + " Hanging Sign");
            add("item.naturareimagined." + family.name + "_sign", wood + " Sign");
            add("item.naturareimagined." + family.name + "_hanging_sign", wood + " Hanging Sign");
            add(family.bookshelf.get(), wood + " Bookshelf");
            add(family.leaves.get(), wood + " Leaves");
            add(family.sapling.get(), wood + " Sapling");
            if (family.boat != null) {
                add(family.boat.get(), wood + " Boat");
                add(family.chestBoat.get(), wood + " Chest Boat");
            }
            if (family.floweringLeaves != null) {
                add(family.floweringLeaves.get(), "Flowering " + wood + " Leaves");
                add(family.fruitLeaves.get(), wood + " Fruit Leaves");
            }
        }
        for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
            add(bush.block().get(), bush.englishBush());
            add(bush.berries().get(), bush.englishBerries());
        }
        add(NRPlants.BARLEY_CROP.get(), "Barley");
        add(NRPlants.BARLEY_SEEDS.get(), "Barley Seeds");
        add(NRPlants.BARLEY.get(), "Barley");
        add(NRPlants.BARLEY_FLOUR.get(), "Barley Flour");
        add(NRPlants.COTTON_CROP.get(), "Cotton");
        add(NRPlants.COTTON_SEEDS.get(), "Cotton Seeds");
        add(NRPlants.COTTON.get(), "Cotton");
        add(NRPlants.WHEAT_FLOUR.get(), "Wheat Flour");
        add(NRPlants.BLUEBELLS.get(), "Bluebells");
        add(NRPlants.BERRY_MEDLEY_ITEM.get(), "Berry Medley");
        add(NRPlants.EMPTY_SEED_BAG.get(), "Empty Seed Bag");
        add(NRPlants.WHEAT_SEED_BAG.get(), "Wheat Seed Bag");
        add(NRPlants.CARROT_SEED_BAG.get(), "Carrot Seed Bag");
        add(NRPlants.POTATO_SEED_BAG.get(), "Potato Seed Bag");
        add(NRPlants.BEETROOT_SEED_BAG.get(), "Beetroot Seed Bag");
        add(NRPlants.NETHER_WART_SEED_BAG.get(), "Nether Wart Seed Bag");
        add(NRPlants.BARLEY_SEED_BAG.get(), "Barley Seed Bag");
        add(NRPlants.COTTON_SEED_BAG.get(), "Cotton Seed Bag");
        add(NRPlants.BONE_MEAL_BAG.get(), "Bone Meal Bag");
        for (NRPlants.GrassSet grass : NRPlants.GRASSES) {
            add(grass.block().get(), grass.english());
        }
        add(NRContent.GHOST_NYLIUM.get(), "Ghost Nylium");
        add(NRContent.HEAT_SAND.get(), "Heat Sand");
        add(NRContent.TAINTED_SOIL.get(), "Tainted Soil");
        add(NRContent.TAINTED_FARMLAND.get(), "Tainted Farmland");
        add(NRContent.THORNVINE.get(), "Thorn Vines");
        add(NRContent.SOUL_GLASS.get(), "Soul Glass");
        add(NRContent.HEAT_GLASS.get(), "Heat Glass");
        add(NRContent.WHITE_CLOUD.get(), "White Cloud");
        add(NRContent.DARK_CLOUD.get(), "Dark Cloud");
        add(NRContent.ASH_CLOUD.get(), "Ash Cloud");
        add(NRContent.SULFUR_CLOUD.get(), "Sulfur Cloud");
        add(NRContent.SAGUARO.get(), "Saguaro");
        add(NRContent.SAGUARO_FRUIT_BLOCK.get(), "Saguaro Fruit");
        add(NRContent.GREEN_GLOWSHROOM.get(), "Green Glowshroom");
        add(NRContent.BLUE_GLOWSHROOM.get(), "Blue Glowshroom");
        add(NRContent.PURPLE_GLOWSHROOM.get(), "Purple Glowshroom");
        add(NRContent.GREEN_GLOWSHROOM_STEM.get(), "Green Glowshroom Stem");
        add(NRContent.BLUE_GLOWSHROOM_STEM.get(), "Blue Glowshroom Stem");
        add(NRContent.PURPLE_GLOWSHROOM_STEM.get(), "Purple Glowshroom Stem");
        add(NRContent.GREEN_GLOWSHROOM_CAP.get(), "Green Glowshroom Cap");
        add(NRContent.BLUE_GLOWSHROOM_CAP.get(), "Blue Glowshroom Cap");
        add(NRContent.PURPLE_GLOWSHROOM_CAP.get(), "Purple Glowshroom Cap");
        add(NRContent.NETHERRACK_FURNACE.get(), "Netherrack Furnace");
        add(NRContent.BLAZE_HOPPER.get(), "Blaze Hopper");
        add(NRContent.BLAZE_RAIL.get(), "Blaze Rail");
        add(NRContent.BLAZE_POWERED_RAIL.get(), "Powered Blaze Rail");
        add(NRContent.BLAZE_DETECTOR_RAIL.get(), "Detector Blaze Rail");
        add(NRContent.BLAZE_ACTIVATOR_RAIL.get(), "Activator Blaze Rail");
        add(NRContent.NETHER_LEVER.get(), "Nether Lever");
        add(NRContent.SULFUR_POWDER.get(), "Sulfur Powder");
        add(NRContent.FLAME_STRING.get(), "Flame String");
        add(NRContent.IMP_LEATHER.get(), "Imp Leather");
        add(NRContent.RAW_IMP_MEAT.get(), "Raw Imp Meat");
        add(NRContent.COOKED_IMP_MEAT.get(), "Cooked Imp Meat");
        add(NRContent.POTASH_APPLE.get(), "Potash Apple");
        add(NRContent.CACTUS_JUICE.get(), "Cactus Juice");
        add(NRContent.GREEN_GLOWSHROOM_STEW.get(), "Green Glowshroom Stew");
        add(NRContent.BLUE_GLOWSHROOM_STEW.get(), "Blue Glowshroom Stew");
        add(NRContent.PURPLE_GLOWSHROOM_STEW.get(), "Purple Glowshroom Stew");
        add(NRContent.FLINT_AND_BLAZE.get(), "Flint and Blaze");
        add(NRContent.QUARTZ_SWORD.get(), "Quartz Sword");
        add(NRContent.QUARTZ_PICKAXE.get(), "Quartz Pickaxe");
        add(NRContent.QUARTZ_AXE.get(), "Quartz Axe");
        add(NRContent.QUARTZ_SHOVEL.get(), "Quartz Shovel");
        add(NRContent.QUARTZ_HOE.get(), "Quartz Hoe");
        add(NRContent.WOODEN_HATCHET.get(), "Wooden Hatchet");
        add(NRContent.STONE_HATCHET.get(), "Stone Hatchet");
        add(NRContent.IRON_HATCHET.get(), "Iron Hatchet");
        add(NRContent.GOLDEN_HATCHET.get(), "Golden Hatchet");
        add(NRContent.DIAMOND_HATCHET.get(), "Diamond Hatchet");
        add(NRContent.NETHERITE_HATCHET.get(), "Netherite Hatchet");
        add(NRContent.QUARTZ_HATCHET.get(), "Quartz Hatchet");
        add(NRContent.IMP_HELMET.get(), "Imp Cap");
        add(NRContent.IMP_CHESTPLATE.get(), "Imp Tunic");
        add(NRContent.IMP_LEGGINGS.get(), "Imp Pants");
        add(NRContent.IMP_BOOTS.get(), "Imp Boots");
        add(NRContent.IMP_SPAWN_EGG.get(), "Imp Spawn Egg");
        add(NRContent.HEATSCAR_SPAWN_EGG.get(), "Heatscar Spider Spawn Egg");
        add(NRContent.BABY_HEATSCAR_SPAWN_EGG.get(), "Baby Heatscar Spider Spawn Egg");
        add(NRContent.NITRO_SPAWN_EGG.get(), "Nitro Creeper Spawn Egg");
        add("entity.naturareimagined.imp", "Imp");
        add("entity.naturareimagined.heatscarspider", "Heatscar Spider");
        add("entity.naturareimagined.baby_heatscarspider", "Baby Heatscar Spider");
        add(NRContent.NITRO.get(), "Nitro Creeper");
        add("advancements.naturareimagined.sapling.title", "A Seed of Something Else");
        add("advancements.naturareimagined.sapling.description", "Obtain a Natura sapling");
        add("advancements.naturareimagined.redwood.title", "That Took a While");
        add("advancements.naturareimagined.redwood.description", "Grow a redwood");
        add("advancements.naturareimagined.nether_berry.title", "Nether Snack");
        add("advancements.naturareimagined.nether_berry.description", "Eat a nether berry");
        add("advancements.naturareimagined.all_berries.title", "Berry Medley");
        add("advancements.naturareimagined.all_berries.description", "Eat every kind of Natura berry");
        add("advancements.naturareimagined.hatchet.title", "Little Axe");
        add("advancements.naturareimagined.hatchet.description", "Craft a hatchet");
        add("advancements.naturareimagined.breed_imp.title", "Impish Appetite");
        add("advancements.naturareimagined.breed_imp.description", "Breed two imps");
        add("advancements.naturareimagined.imp_armor.title", "Dressed for the Nether");
        add("advancements.naturareimagined.imp_armor.description", "Wear a full set of imp armor");
        add("advancements.naturareimagined.survive_nitro.title", "Still Here");
        add("advancements.naturareimagined.survive_nitro.description", "Survive a nitro creeper explosion");
    }
}

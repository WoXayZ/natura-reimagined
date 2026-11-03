package com.naturareimagined.data;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRWoods;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class NRItemModelProvider extends ItemModelProvider {

    public NRItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, NaturaConstants.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        for (WoodFamily family : NRWoods.ALL) {
            basicItem(family.door.get().asItem());
            basicItem(family.sign.get().asItem());
            basicItem(family.hangingSign.get().asItem());
            withExistingParent(family.sapling.getId().getPath(), "item/generated")
                    .texture("layer0", modLoc("block/" + family.name + "_sapling"));
            if (family.boat != null) {
                basicItem(family.boat.get());
                basicItem(family.chestBoat.get());
            }
            fenceInventory(family.fence.getId().getPath(), modLoc("block/" + family.name + "_planks"));
            buttonInventory(family.button.getId().getPath(), modLoc("block/" + family.name + "_planks"));
        }
        for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
            basicItem(bush.berries().get());
        }
        basicItem(NRPlants.BARLEY_SEEDS.get());
        basicItem(NRPlants.BARLEY.get());
        basicItem(NRPlants.BARLEY_FLOUR.get());
        basicItem(NRPlants.COTTON_SEEDS.get());
        basicItem(NRPlants.COTTON.get());
        basicItem(NRPlants.WHEAT_FLOUR.get());
        withExistingParent("bluebells", "item/generated").texture("layer0", modLoc("block/bluebells_flowers"));
        basicItem(NRPlants.EMPTY_SEED_BAG.get());
        basicItem(NRPlants.WHEAT_SEED_BAG.get());
        basicItem(NRPlants.CARROT_SEED_BAG.get());
        basicItem(NRPlants.POTATO_SEED_BAG.get());
        basicItem(NRPlants.BEETROOT_SEED_BAG.get());
        basicItem(NRPlants.NETHER_WART_SEED_BAG.get());
        basicItem(NRPlants.BARLEY_SEED_BAG.get());
        basicItem(NRPlants.COTTON_SEED_BAG.get());
        basicItem(NRPlants.BONE_MEAL_BAG.get());
        basicItem(NRPlants.BERRY_MEDLEY_ITEM.get());
        basicItem(NRContent.SULFUR_POWDER.get());
        basicItem(NRContent.FLAME_STRING.get());
        basicItem(NRContent.IMP_LEATHER.get());
        basicItem(NRContent.RAW_IMP_MEAT.get());
        basicItem(NRContent.COOKED_IMP_MEAT.get());
        basicItem(NRContent.POTASH_APPLE.get());
        basicItem(NRContent.CACTUS_JUICE.get());
        basicItem(NRContent.GREEN_GLOWSHROOM_STEW.get());
        basicItem(NRContent.BLUE_GLOWSHROOM_STEW.get());
        basicItem(NRContent.PURPLE_GLOWSHROOM_STEW.get());
        basicItem(NRContent.SAGUARO_FRUIT.get());
        basicItem(NRContent.FLINT_AND_BLAZE.get());
        basicItem(NRContent.QUARTZ_SWORD.get());
        basicItem(NRContent.QUARTZ_PICKAXE.get());
        basicItem(NRContent.QUARTZ_AXE.get());
        basicItem(NRContent.QUARTZ_SHOVEL.get());
        basicItem(NRContent.QUARTZ_HOE.get());
        for (var hatchet : NRContent.HATCHETS) {
            basicItem(hatchet.get());
        }
        basicItem(NRContent.IMP_HELMET.get());
        basicItem(NRContent.IMP_CHESTPLATE.get());
        basicItem(NRContent.IMP_LEGGINGS.get());
        basicItem(NRContent.IMP_BOOTS.get());
        spawnEggItem(NRContent.IMP_SPAWN_EGG.get());
        spawnEggItem(NRContent.HEATSCAR_SPAWN_EGG.get());
        spawnEggItem(NRContent.BABY_HEATSCAR_SPAWN_EGG.get());
        spawnEggItem(NRContent.NITRO_SPAWN_EGG.get());
    }
}

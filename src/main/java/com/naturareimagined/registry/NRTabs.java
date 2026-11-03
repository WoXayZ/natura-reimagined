package com.naturareimagined.registry;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.wood.WoodFamily;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = NaturaConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class NRTabs {

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NaturaConstants.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.naturareimagined.main"))
            .icon(() -> NRWoods.MAPLE.log.toStack())
            .displayItems((parameters, output) -> {
                for (WoodFamily family : NRWoods.ALL) {
                    family.items.forEach(item -> output.accept(item.get()));
                }
                for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
                    output.accept(bush.bushItem().get());
                    output.accept(bush.berries().get());
                }
                output.accept(NRPlants.BARLEY_SEEDS.get());
                output.accept(NRPlants.BARLEY.get());
                output.accept(NRPlants.BARLEY_FLOUR.get());
                output.accept(NRPlants.COTTON_SEEDS.get());
                output.accept(NRPlants.COTTON.get());
                output.accept(NRPlants.WHEAT_FLOUR.get());
                output.accept(NRPlants.BLUEBELLS_ITEM.get());
                output.accept(NRPlants.BERRY_MEDLEY_ITEM.get());
                for (NRPlants.GrassSet grass : NRPlants.GRASSES) {
                    output.accept(grass.item().get());
                }
                output.accept(NRPlants.EMPTY_SEED_BAG.get());
                output.accept(NRPlants.WHEAT_SEED_BAG.get());
                output.accept(NRPlants.CARROT_SEED_BAG.get());
                output.accept(NRPlants.POTATO_SEED_BAG.get());
                output.accept(NRPlants.BEETROOT_SEED_BAG.get());
                output.accept(NRPlants.NETHER_WART_SEED_BAG.get());
                output.accept(NRPlants.BARLEY_SEED_BAG.get());
                output.accept(NRPlants.COTTON_SEED_BAG.get());
                output.accept(NRPlants.BONE_MEAL_BAG.get());
                output.accept(NRContent.GHOST_NYLIUM.get());
                output.accept(NRContent.BLOOD_NYLIUM.get());
                output.accept(NRContent.DARK_NYLIUM.get());
                output.accept(NRContent.FUSE_NYLIUM.get());
                output.accept(NRContent.PYREFLY_BUSH.get());
                output.accept(NRContent.HEAT_SAND.get());
                output.accept(NRContent.TAINTED_SOIL.get());
                output.accept(NRContent.TAINTED_FARMLAND.get());
                output.accept(NRContent.THORNVINE.get());
                output.accept(NRContent.SOUL_GLASS.get());
                output.accept(NRContent.HEAT_GLASS.get());
                output.accept(NRContent.WHITE_CLOUD.get());
                output.accept(NRContent.DARK_CLOUD.get());
                output.accept(NRContent.ASH_CLOUD.get());
                output.accept(NRContent.SULFUR_CLOUD.get());
                output.accept(NRContent.SAGUARO_ITEM.get());
                output.accept(NRContent.SAGUARO_FRUIT.get());
                output.accept(NRContent.GREEN_GLOWSHROOM.get());
                output.accept(NRContent.GREEN_GLOWSHROOM_STEM.get());
                output.accept(NRContent.GREEN_GLOWSHROOM_CAP.get());
                output.accept(NRContent.BLUE_GLOWSHROOM.get());
                output.accept(NRContent.BLUE_GLOWSHROOM_STEM.get());
                output.accept(NRContent.BLUE_GLOWSHROOM_CAP.get());
                output.accept(NRContent.PURPLE_GLOWSHROOM.get());
                output.accept(NRContent.PURPLE_GLOWSHROOM_STEM.get());
                output.accept(NRContent.PURPLE_GLOWSHROOM_CAP.get());
                output.accept(NRContent.NETHERRACK_FURNACE.get());
                output.accept(NRContent.BLAZE_HOPPER.get());
                output.accept(NRContent.BLAZE_RAIL.get());
                output.accept(NRContent.BLAZE_POWERED_RAIL.get());
                output.accept(NRContent.BLAZE_DETECTOR_RAIL.get());
                output.accept(NRContent.BLAZE_ACTIVATOR_RAIL.get());
                output.accept(NRContent.NETHER_LEVER.get());
                output.accept(NRContent.SULFUR_POWDER.get());
                output.accept(NRContent.FLAME_STRING.get());
                output.accept(NRContent.IMP_LEATHER.get());
                output.accept(NRContent.RAW_IMP_MEAT.get());
                output.accept(NRContent.COOKED_IMP_MEAT.get());
                output.accept(NRContent.POTASH_APPLE.get());
                output.accept(NRContent.CACTUS_JUICE.get());
                output.accept(NRContent.GREEN_GLOWSHROOM_STEW.get());
                output.accept(NRContent.BLUE_GLOWSHROOM_STEW.get());
                output.accept(NRContent.PURPLE_GLOWSHROOM_STEW.get());
                output.accept(NRContent.FLINT_AND_BLAZE.get());
                output.accept(NRContent.QUARTZ_SWORD.get());
                output.accept(NRContent.QUARTZ_SHOVEL.get());
                output.accept(NRContent.QUARTZ_PICKAXE.get());
                output.accept(NRContent.QUARTZ_AXE.get());
                output.accept(NRContent.QUARTZ_HOE.get());
                NRContent.HATCHETS.forEach(item -> output.accept(item.get()));
                output.accept(NRContent.IMP_HELMET.get());
                output.accept(NRContent.IMP_CHESTPLATE.get());
                output.accept(NRContent.IMP_LEGGINGS.get());
                output.accept(NRContent.IMP_BOOTS.get());
                output.accept(NRContent.IMP_SPAWN_EGG.get());
                output.accept(NRContent.HEATSCAR_SPAWN_EGG.get());
                output.accept(NRContent.BABY_HEATSCAR_SPAWN_EGG.get());
                output.accept(NRContent.NITRO_SPAWN_EGG.get());
                output.accept(NRContent.SAKURA_FOX_SPAWN_EGG.get());
                output.accept(NRContent.AMARANTH_BEE_SPAWN_EGG.get());
                output.accept(NRContent.WILLOW_FROG_SPAWN_EGG.get());
                output.accept(NRContent.ASHEN_SKELETON_SPAWN_EGG.get());
                output.accept(NRContent.GHOST_BAT_SPAWN_EGG.get());
                output.accept(NRContent.EMBER_BAT_SPAWN_EGG.get());
                output.accept(NRContent.GREEN_GLOWSHROOM_COW_SPAWN_EGG.get());
                output.accept(NRContent.BLUE_GLOWSHROOM_COW_SPAWN_EGG.get());
                output.accept(NRContent.PURPLE_GLOWSHROOM_COW_SPAWN_EGG.get());
                output.accept(NRContent.CINDERLIN_SPAWN_EGG.get());
                output.accept(NRContent.HELLHOUND_SPAWN_EGG.get());
            })
            .build());

    private NRTabs() {}

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }

    @SubscribeEvent
    public static void addToVanillaTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            for (WoodFamily family : NRWoods.ALL) {
                event.accept(family.log);
                event.accept(family.wood);
                event.accept(family.strippedLog);
                event.accept(family.strippedWood);
                event.accept(family.planks);
                event.accept(family.stairs);
                event.accept(family.slab);
                event.accept(family.fence);
                event.accept(family.fenceGate);
                event.accept(family.door);
                event.accept(family.trapdoor);
                event.accept(family.pressurePlate);
                event.accept(family.button);
                event.accept(family.bookshelf);
            }
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            for (WoodFamily family : NRWoods.ALL) {
                event.accept(family.log);
                event.accept(family.strippedLog);
                event.accept(family.leaves);
                if (family.floweringLeaves != null) {
                    event.accept(family.floweringLeaves);
                    event.accept(family.fruitLeaves);
                }
                event.accept(family.sapling);
            }
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            for (WoodFamily family : NRWoods.ALL) {
                event.accept(family.sign.get());
                event.accept(family.hangingSign.get());
            }
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            for (WoodFamily family : NRWoods.ALL) {
                if (family.boat != null) {
                    event.accept(family.boat.get());
                    event.accept(family.chestBoat.get());
                }
            }
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
                event.accept(bush.berries().get());
            }
            event.accept(NRPlants.BERRY_MEDLEY_ITEM.get());
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(NRPlants.BARLEY_SEEDS.get());
            event.accept(NRPlants.BARLEY.get());
            event.accept(NRPlants.COTTON_SEEDS.get());
            event.accept(NRPlants.COTTON.get());
            event.accept(NRPlants.BLUEBELLS_ITEM.get());
            for (NRPlants.BushPlant bush : NRPlants.BUSHES) {
                event.accept(bush.bushItem().get());
            }
            for (NRPlants.GrassSet grass : NRPlants.GRASSES) {
                event.accept(grass.item().get());
            }
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            for (NRPlants.GrassSet grass : NRPlants.GRASSES) {
                event.accept(grass.item().get());
            }
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(NRPlants.BARLEY_FLOUR.get());
            event.accept(NRPlants.WHEAT_FLOUR.get());
            event.accept(NRPlants.COTTON.get());
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(NRPlants.EMPTY_SEED_BAG.get());
            event.accept(NRPlants.WHEAT_SEED_BAG.get());
            event.accept(NRPlants.CARROT_SEED_BAG.get());
            event.accept(NRPlants.POTATO_SEED_BAG.get());
            event.accept(NRPlants.BEETROOT_SEED_BAG.get());
            event.accept(NRPlants.NETHER_WART_SEED_BAG.get());
            event.accept(NRPlants.BARLEY_SEED_BAG.get());
            event.accept(NRPlants.COTTON_SEED_BAG.get());
            event.accept(NRPlants.BONE_MEAL_BAG.get());
            event.accept(NRContent.FLINT_AND_BLAZE.get());
            NRContent.HATCHETS.forEach(event::accept);
            event.accept(NRContent.QUARTZ_SHOVEL.get());
            event.accept(NRContent.QUARTZ_PICKAXE.get());
            event.accept(NRContent.QUARTZ_AXE.get());
            event.accept(NRContent.QUARTZ_HOE.get());
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(NRContent.QUARTZ_SWORD.get());
            NRContent.HATCHETS.forEach(event::accept);
            event.accept(NRContent.IMP_HELMET.get());
            event.accept(NRContent.IMP_CHESTPLATE.get());
            event.accept(NRContent.IMP_LEGGINGS.get());
            event.accept(NRContent.IMP_BOOTS.get());
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(NRContent.RAW_IMP_MEAT.get());
            event.accept(NRContent.COOKED_IMP_MEAT.get());
            event.accept(NRContent.POTASH_APPLE.get());
            event.accept(NRContent.CACTUS_JUICE.get());
            event.accept(NRContent.GREEN_GLOWSHROOM_STEW.get());
            event.accept(NRContent.BLUE_GLOWSHROOM_STEW.get());
            event.accept(NRContent.PURPLE_GLOWSHROOM_STEW.get());
            event.accept(NRContent.SAGUARO_FRUIT.get());
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(NRContent.GHOST_NYLIUM.get());
            event.accept(NRContent.BLOOD_NYLIUM.get());
            event.accept(NRContent.DARK_NYLIUM.get());
            event.accept(NRContent.FUSE_NYLIUM.get());
            event.accept(NRContent.PYREFLY_BUSH.get());
            event.accept(NRContent.HEAT_SAND.get());
            event.accept(NRContent.TAINTED_SOIL.get());
            event.accept(NRContent.THORNVINE.get());
            event.accept(NRContent.WHITE_CLOUD.get());
            event.accept(NRContent.DARK_CLOUD.get());
            event.accept(NRContent.ASH_CLOUD.get());
            event.accept(NRContent.SULFUR_CLOUD.get());
            event.accept(NRContent.SAGUARO_ITEM.get());
            event.accept(NRContent.SAGUARO_FRUIT.get());
            event.accept(NRContent.GREEN_GLOWSHROOM.get());
            event.accept(NRContent.GREEN_GLOWSHROOM_STEM.get());
            event.accept(NRContent.GREEN_GLOWSHROOM_CAP.get());
            event.accept(NRContent.BLUE_GLOWSHROOM.get());
            event.accept(NRContent.BLUE_GLOWSHROOM_STEM.get());
            event.accept(NRContent.BLUE_GLOWSHROOM_CAP.get());
            event.accept(NRContent.PURPLE_GLOWSHROOM.get());
            event.accept(NRContent.PURPLE_GLOWSHROOM_STEM.get());
            event.accept(NRContent.PURPLE_GLOWSHROOM_CAP.get());
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(NRContent.NETHERRACK_FURNACE.get());
            event.accept(NRContent.SOUL_GLASS.get());
            event.accept(NRContent.HEAT_GLASS.get());
        }
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(NRContent.BLAZE_HOPPER.get());
            event.accept(NRContent.BLAZE_RAIL.get());
            event.accept(NRContent.BLAZE_POWERED_RAIL.get());
            event.accept(NRContent.BLAZE_DETECTOR_RAIL.get());
            event.accept(NRContent.BLAZE_ACTIVATOR_RAIL.get());
            event.accept(NRContent.NETHER_LEVER.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(NRContent.SULFUR_POWDER.get());
            event.accept(NRContent.FLAME_STRING.get());
            event.accept(NRContent.IMP_LEATHER.get());
        }
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(NRContent.IMP_SPAWN_EGG.get());
            event.accept(NRContent.HEATSCAR_SPAWN_EGG.get());
            event.accept(NRContent.BABY_HEATSCAR_SPAWN_EGG.get());
            event.accept(NRContent.NITRO_SPAWN_EGG.get());
            event.accept(NRContent.SAKURA_FOX_SPAWN_EGG.get());
            event.accept(NRContent.AMARANTH_BEE_SPAWN_EGG.get());
            event.accept(NRContent.WILLOW_FROG_SPAWN_EGG.get());
            event.accept(NRContent.ASHEN_SKELETON_SPAWN_EGG.get());
            event.accept(NRContent.GHOST_BAT_SPAWN_EGG.get());
            event.accept(NRContent.EMBER_BAT_SPAWN_EGG.get());
            event.accept(NRContent.GREEN_GLOWSHROOM_COW_SPAWN_EGG.get());
            event.accept(NRContent.BLUE_GLOWSHROOM_COW_SPAWN_EGG.get());
            event.accept(NRContent.PURPLE_GLOWSHROOM_COW_SPAWN_EGG.get());
            event.accept(NRContent.CINDERLIN_SPAWN_EGG.get());
            event.accept(NRContent.HELLHOUND_SPAWN_EGG.get());
        }
    }
}

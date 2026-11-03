package com.naturareimagined;

import com.naturareimagined.config.NRConfig;
import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.registry.NRBlocks;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRConditions;
import com.naturareimagined.registry.NRFeatures;
import com.naturareimagined.registry.NRItems;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRTabs;
import com.naturareimagined.registry.NRWoods;
import com.naturareimagined.world.NRRegions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(NaturaConstants.MOD_ID)
public final class NaturaReimagined {

    public static final Logger LOGGER = LoggerFactory.getLogger(NaturaReimagined.class);

    public NaturaReimagined(IEventBus modBus, ModContainer container) {
        NRWoods.ALL.size();
        NRPlants.BUSHES.size();
        NRContent.HEAT_SAND.getId();
        NRBlocks.register(modBus);
        NRContent.register(modBus);
        NRItems.register(modBus);
        NRTabs.register(modBus);
        NRFeatures.register(modBus);
        NRConditions.register(modBus);
        modBus.addListener(NaturaReimagined::commonSetup);
        container.registerConfig(ModConfig.Type.COMMON, NRConfig.SPEC);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerBlockInteractions();
            NRRegions.register();
        });
    }

    private static void registerBlockInteractions() {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        for (WoodFamily family : NRWoods.ALL) {
            if (!family.nether) {
                fire.setFlammable(family.log.get(), 5, 5);
                fire.setFlammable(family.wood.get(), 5, 5);
                fire.setFlammable(family.strippedLog.get(), 5, 5);
                fire.setFlammable(family.strippedWood.get(), 5, 5);
                fire.setFlammable(family.planks.get(), 5, 20);
                fire.setFlammable(family.stairs.get(), 5, 20);
                fire.setFlammable(family.slab.get(), 5, 20);
                fire.setFlammable(family.fence.get(), 5, 20);
                fire.setFlammable(family.fenceGate.get(), 5, 20);
                fire.setFlammable(family.bookshelf.get(), 30, 20);
                fire.setFlammable(family.leaves.get(), 30, 60);
            }
            ComposterBlock.COMPOSTABLES.put(family.leaves.get().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(family.sapling.get().asItem(), 0.3F);
            if (family.floweringLeaves != null) {
                ComposterBlock.COMPOSTABLES.put(family.floweringLeaves.get().asItem(), 0.3F);
                ComposterBlock.COMPOSTABLES.put(family.fruitLeaves.get().asItem(), 0.3F);
            }
        }
        for (var bush : NRPlants.BUSHES) {
            ComposterBlock.COMPOSTABLES.put(bush.berries().get(), 0.3F);
        }
        ComposterBlock.COMPOSTABLES.put(NRPlants.BARLEY_SEEDS.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(NRPlants.COTTON_SEEDS.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(NRPlants.BARLEY.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(NRPlants.COTTON.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(NRPlants.BARLEY_FLOUR.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(NRPlants.WHEAT_FLOUR.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(NRPlants.BLUEBELLS_ITEM.get(), 0.65F);
    }
}

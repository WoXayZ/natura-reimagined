package com.naturareimagined.data;

import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRWoods;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;

public final class NRDataMapProvider extends DataMapProvider {

    public NRDataMapProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        var strippables = builder(NeoForgeDataMaps.STRIPPABLES);
        var compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
        for (WoodFamily family : NRWoods.ALL) {
            strippables.add(family.log.get().builtInRegistryHolder(), new Strippable(family.strippedLog.get()), false);
            strippables.add(family.wood.get().builtInRegistryHolder(), new Strippable(family.strippedWood.get()), false);
            compostables.add(family.leaves.get().asItem().builtInRegistryHolder(), new Compostable(0.3F, true), false);
            compostables.add(family.sapling.get().asItem().builtInRegistryHolder(), new Compostable(0.3F, true), false);
            if (family.floweringLeaves != null) {
                compostables.add(family.floweringLeaves.get().asItem().builtInRegistryHolder(), new Compostable(0.3F, true), false);
                compostables.add(family.fruitLeaves.get().asItem().builtInRegistryHolder(), new Compostable(0.3F, true), false);
            }
        }
        for (var bush : NRPlants.BUSHES) {
            compostables.add(bush.berries().get().builtInRegistryHolder(), new Compostable(0.3F, true), false);
        }
        compostables.add(NRPlants.BARLEY_SEEDS.get().builtInRegistryHolder(), new Compostable(0.3F, true), false);
        compostables.add(NRPlants.COTTON_SEEDS.get().builtInRegistryHolder(), new Compostable(0.3F, true), false);
        compostables.add(NRPlants.BARLEY.get().builtInRegistryHolder(), new Compostable(0.65F, true), false);
        compostables.add(NRPlants.COTTON.get().builtInRegistryHolder(), new Compostable(0.65F, true), false);
        compostables.add(NRPlants.BARLEY_FLOUR.get().builtInRegistryHolder(), new Compostable(0.65F, true), false);
        compostables.add(NRPlants.WHEAT_FLOUR.get().builtInRegistryHolder(), new Compostable(0.65F, true), false);
        compostables.add(NRPlants.BLUEBELLS_ITEM.get().builtInRegistryHolder(), new Compostable(0.65F, true), false);
    }
}

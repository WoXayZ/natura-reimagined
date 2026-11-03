package com.naturareimagined.registry;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.plant.FlowerPatchFeature;
import com.naturareimagined.content.plant.StackedBushFeature;
import com.naturareimagined.content.world.ScatterFeature;
import com.naturareimagined.content.world.SpecialTreeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NRFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, NaturaConstants.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<SimpleBlockConfiguration>> STACKED_BUSH =
            FEATURES.register("stacked_bush", StackedBushFeature::new);
    public static final DeferredHolder<Feature<?>, Feature<SimpleBlockConfiguration>> FLOWER_PATCH =
            FEATURES.register("flower_patch", FlowerPatchFeature::new);
    public static final DeferredHolder<Feature<?>, Feature<SimpleBlockConfiguration>> SCATTER =
            FEATURES.register("scatter", ScatterFeature::new);
    public static final DeferredHolder<Feature<?>, Feature<SpecialTreeFeature.Config>> SPECIAL_TREE =
            FEATURES.register("special_tree", SpecialTreeFeature::new);

    private NRFeatures() {}

    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}

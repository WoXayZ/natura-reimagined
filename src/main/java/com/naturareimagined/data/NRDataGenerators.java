package com.naturareimagined.data;

import com.naturareimagined.NaturaConstants;
import java.util.Set;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = NaturaConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class NRDataGenerators {

    private NRDataGenerators() {}

    @SubscribeEvent
    public static void gather(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        var registries = event.getLookupProvider();
        var helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new NRBlockStateProvider(output, helper));
        generator.addProvider(event.includeClient(), new NRItemModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new NRLanguageProvider(output));

        NRBlockTagsProvider blockTags = new NRBlockTagsProvider(output, registries, helper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new NRItemTagsProvider(output, registries, blockTags.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new NRRecipeProvider(output, registries));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(),
                java.util.List.of(
                        new LootTableProvider.SubProviderEntry(NRBlockLoot::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(NREntityLoot::new, LootContextParamSets.ENTITY)),
                registries));
        generator.addProvider(event.includeServer(), new NRWorldgenProvider(output, registries));
        generator.addProvider(event.includeServer(), new NRDataMapProvider(output, registries));
    }
}

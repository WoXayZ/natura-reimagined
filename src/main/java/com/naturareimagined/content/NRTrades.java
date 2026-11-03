package com.naturareimagined.content;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRWoods;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

@EventBusSubscriber(modid = NaturaConstants.MOD_ID)
public final class NRTrades {

    private NRTrades() {}

    @SubscribeEvent
    public static void wanderer(WandererTradesEvent event) {
        var trades = event.getGenericTrades();
        for (WoodFamily family : NRWoods.ALL) {
            if (!family.nether) {
                trades.add(new VillagerTrades.ItemsForEmeralds(family.sapling.get(), 5, 1, 8, 1));
            }
        }
        trades.add(new VillagerTrades.ItemsForEmeralds(NRPlants.BLUEBELLS_ITEM.get(), 1, 1, 12, 1));
        for (var bush : NRPlants.BUSHES) {
            if (!bush.nether()) {
                trades.add(new VillagerTrades.ItemsForEmeralds(bush.bushItem().get(), 1, 1, 12, 1));
            }
        }
    }
}

package com.naturareimagined.compat.rei;

import com.naturareimagined.compat.CinderlinBarterOffers;
import com.naturareimagined.registry.NRContent;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.forge.REIPluginClient;
import java.util.List;

@REIPluginClient
public class NRReiPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CinderlinReiCategory());
        registry.addWorkstations(CinderlinReiCategory.ID, EntryIngredients.of(CinderlinBarterOffers.currency()), EntryIngredients.of(NRContent.CINDERLIN_SPAWN_EGG.get()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        List<CinderlinBarterOffers.Offer> offers = CinderlinBarterOffers.read();
        if (!offers.isEmpty()) {
            registry.add(new CinderlinReiDisplay(offers));
        }
    }
}

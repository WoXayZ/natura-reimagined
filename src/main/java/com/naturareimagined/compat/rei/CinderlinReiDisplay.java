package com.naturareimagined.compat.rei;

import com.naturareimagined.compat.CinderlinBarterOffers;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class CinderlinReiDisplay extends BasicDisplay {

    public CinderlinReiDisplay(List<CinderlinBarterOffers.Offer> offers) {
        super(List.of(EntryIngredients.of(CinderlinBarterOffers.currency())), outputs(offers));
    }

    private static List<EntryIngredient> outputs(List<CinderlinBarterOffers.Offer> offers) {
        int total = CinderlinBarterOffers.totalWeight(offers);
        List<EntryIngredient> outputs = new ArrayList<>();
        for (CinderlinBarterOffers.Offer offer : offers) {
            List<Component> lines = new ArrayList<>();
            lines.add(Component.translatable("jei.naturareimagined.cinderlin_chance", CinderlinBarterOffers.chance(offer, total)));
            if (offer.minCount() != offer.maxCount()) {
                lines.add(Component.translatable("jei.naturareimagined.cinderlin_count", offer.minCount(), offer.maxCount()));
            }
            outputs.add(EntryIngredient.of(EntryStacks.of(offer.stack()).tooltip(lines)));
        }
        return outputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CinderlinReiCategory.ID;
    }
}

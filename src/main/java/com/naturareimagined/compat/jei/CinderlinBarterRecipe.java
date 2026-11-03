package com.naturareimagined.compat.jei;

import com.naturareimagined.compat.CinderlinBarterOffers;

import java.util.List;

public record CinderlinBarterRecipe(List<CinderlinBarterOffers.Offer> offers) {

    public int totalWeight() {
        return CinderlinBarterOffers.totalWeight(offers);
    }
}

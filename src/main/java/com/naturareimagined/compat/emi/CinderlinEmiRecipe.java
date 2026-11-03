package com.naturareimagined.compat.emi;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.compat.CinderlinBarterOffers;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CinderlinEmiRecipe implements EmiRecipe {

    private static final int COLUMNS = 8;
    private final List<CinderlinBarterOffers.Offer> offers;
    private final ItemStack currency;
    private final int total;

    public CinderlinEmiRecipe(List<CinderlinBarterOffers.Offer> offers, ItemStack currency) {
        this.offers = offers;
        this.currency = currency;
        this.total = CinderlinBarterOffers.totalWeight(offers);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return NREmiPlugin.CINDERLIN;
    }

    @Override
    public ResourceLocation getId() {
        return NaturaConstants.id("cinderlin_bartering");
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(EmiStack.of(this.currency));
    }

    @Override
    public List<EmiStack> getOutputs() {
        return offers.stream().map(offer -> EmiStack.of(offer.stack()).setChance(offer.weight() / (float) total)).toList();
    }

    @Override
    public int getDisplayWidth() {
        return 18 + 24 + COLUMNS * 18;
    }

    @Override
    public int getDisplayHeight() {
        return 18 * 3;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(EmiStack.of(this.currency), 1, 19);
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 20, 19);
        for (int index = 0; index < offers.size(); index++) {
            CinderlinBarterOffers.Offer offer = offers.get(index);
            EmiStack stack = EmiStack.of(offer.stack()).setChance(offer.weight() / (float) total);
            var slot = widgets.addSlot(stack, 42 + (index % COLUMNS) * 18, 1 + (index / COLUMNS) * 18).recipeContext(this);
            if (offer.minCount() != offer.maxCount()) {
                slot.appendTooltip(Component.translatable("jei.naturareimagined.cinderlin_count", offer.minCount(), offer.maxCount()));
            }
        }
    }
}

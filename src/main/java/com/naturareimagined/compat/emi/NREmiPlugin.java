package com.naturareimagined.compat.emi;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.compat.CinderlinBarterOffers;
import com.naturareimagined.registry.NRContent;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

@EmiEntrypoint
public class NREmiPlugin implements EmiPlugin {

    public static EmiRecipeCategory CINDERLIN;

    @Override
    public void register(EmiRegistry registry) {
        ItemStack currency = CinderlinBarterOffers.currency();
        CINDERLIN = new EmiRecipeCategory(NaturaConstants.id("cinderlin_bartering"), EmiStack.of(currency)) {
            @Override
            public Component getName() {
                return Component.translatable("jei.naturareimagined.cinderlin_bartering");
            }
        };
        registry.addCategory(CINDERLIN);
        registry.addWorkstation(CINDERLIN, EmiStack.of(currency));
        registry.addWorkstation(CINDERLIN, EmiStack.of(NRContent.CINDERLIN_SPAWN_EGG.get()));
        List<CinderlinBarterOffers.Offer> offers = CinderlinBarterOffers.read();
        if (!offers.isEmpty()) {
            registry.addRecipe(new CinderlinEmiRecipe(offers, currency));
        }
    }
}

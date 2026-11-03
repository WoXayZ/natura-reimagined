package com.naturareimagined.compat.jei;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.compat.CinderlinBarterOffers;
import com.naturareimagined.registry.NRContent;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import java.util.List;

@JeiPlugin
public class NRJeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return NaturaConstants.id("jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CinderlinBarterCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<CinderlinBarterOffers.Offer> offers = CinderlinBarterOffers.read();
        if (!offers.isEmpty()) {
            registration.addRecipes(CinderlinBarterCategory.TYPE, List.of(new CinderlinBarterRecipe(offers)));
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(CinderlinBarterOffers.currency(), CinderlinBarterCategory.TYPE);
        registration.addRecipeCatalyst(NRContent.CINDERLIN_SPAWN_EGG.get(), CinderlinBarterCategory.TYPE);
    }
}

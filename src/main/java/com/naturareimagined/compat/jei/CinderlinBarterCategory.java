package com.naturareimagined.compat.jei;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.compat.CinderlinBarterOffers;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CinderlinBarterCategory implements IRecipeCategory<CinderlinBarterRecipe> {

    public static final RecipeType<CinderlinBarterRecipe> TYPE = RecipeType.create(NaturaConstants.MOD_ID, "cinderlin_bartering", CinderlinBarterRecipe.class);
    private static final int COLUMNS = 8;

    private final ItemStack input;
    private final IDrawable icon;
    private final IDrawable background;
    private final IDrawable arrow;

    public CinderlinBarterCategory(IGuiHelper gui) {
        this.input = CinderlinBarterOffers.currency();
        this.icon = gui.createDrawableItemStack(this.input);
        this.background = gui.createBlankDrawable(18 + 24 + COLUMNS * 18, 18 * 3);
        this.arrow = gui.getRecipeArrow();
    }

    @Override
    public RecipeType<CinderlinBarterRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.naturareimagined.cinderlin_bartering");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CinderlinBarterRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 19).addItemStack(this.input);
        int total = recipe.totalWeight();
        List<CinderlinBarterOffers.Offer> offers = recipe.offers();
        for (int index = 0; index < offers.size(); index++) {
            CinderlinBarterOffers.Offer offer = offers.get(index);
            int x = 42 + (index % COLUMNS) * 18;
            int y = 1 + (index / COLUMNS) * 18;
            builder.addSlot(RecipeIngredientRole.OUTPUT, x, y)
                    .addItemStack(offer.stack())
                    .addRichTooltipCallback((view, tooltip) -> tooltip(tooltip, offer, total));
        }
    }

    private static void tooltip(ITooltipBuilder tooltip, CinderlinBarterOffers.Offer offer, int total) {
        tooltip.add(Component.translatable("jei.naturareimagined.cinderlin_chance", CinderlinBarterOffers.chance(offer, total)));
        if (offer.minCount() != offer.maxCount()) {
            tooltip.add(Component.translatable("jei.naturareimagined.cinderlin_count", offer.minCount(), offer.maxCount()));
        }
    }

    @Override
    public void draw(CinderlinBarterRecipe recipe, mezz.jei.api.gui.ingredient.IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        arrow.draw(graphics, 20, 19);
    }
}

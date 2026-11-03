package com.naturareimagined.compat.rei;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.compat.CinderlinBarterOffers;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class CinderlinReiCategory implements DisplayCategory<CinderlinReiDisplay> {

    public static final CategoryIdentifier<CinderlinReiDisplay> ID = CategoryIdentifier.of(NaturaConstants.MOD_ID, "cinderlin_bartering");
    private static final int COLUMNS = 8;

    @Override
    public CategoryIdentifier<? extends CinderlinReiDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.naturareimagined.cinderlin_bartering");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(CinderlinBarterOffers.currency());
    }

    @Override
    public int getDisplayHeight() {
        return 6 + 18 * 3;
    }

    @Override
    public List<Widget> setupDisplay(CinderlinReiDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 6, bounds.y + 24)).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createArrow(new Point(bounds.x + 26, bounds.y + 24)));
        List<me.shedaniel.rei.api.common.entry.EntryIngredient> outputs = display.getOutputEntries();
        for (int index = 0; index < outputs.size(); index++) {
            widgets.add(Widgets.createSlot(new Point(bounds.x + 54 + (index % COLUMNS) * 18, bounds.y + 6 + (index / COLUMNS) * 18))
                    .entries(outputs.get(index))
                    .markOutput());
        }
        return widgets;
    }
}

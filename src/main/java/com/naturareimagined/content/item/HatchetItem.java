package com.naturareimagined.content.item;

import com.naturareimagined.registry.NRTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;

public class HatchetItem extends DiggerItem {

    public HatchetItem(Tier tier, Properties properties) {
        super(tier, NRTags.MINEABLE_HATCHET, properties.attributes(DiggerItem.createAttributes(tier, 3.0F, -2.4F)));
    }
}

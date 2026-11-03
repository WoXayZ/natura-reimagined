package com.naturareimagined.content.plant;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class PlantBlockItem extends BlockItem {

    public PlantBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}

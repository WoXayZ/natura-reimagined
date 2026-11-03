package com.naturareimagined.registry;

import com.naturareimagined.NaturaConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class NRTags {

    public static final TagKey<Block> MINEABLE_HATCHET = TagKey.create(Registries.BLOCK, NaturaConstants.id("mineable/hatchet"));
    public static final TagKey<Item> HATCHETS = TagKey.create(Registries.ITEM, NaturaConstants.id("hatchets"));

    private NRTags() {}
}

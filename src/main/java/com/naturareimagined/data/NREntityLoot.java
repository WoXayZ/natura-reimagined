package com.naturareimagined.data;

import com.naturareimagined.registry.NRContent;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class NREntityLoot extends EntityLootSubProvider {

    public NREntityLoot(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        add(NRContent.IMP.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(NRContent.RAW_IMP_MEAT.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(NRContent.IMP_LEATHER.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))));
        add(NRContent.HEATSCAR.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(NRContent.FLAME_STRING.get())
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));
        add(NRContent.BABY_HEATSCAR.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(NRContent.FLAME_STRING.get())
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))))));
        add(NRContent.NITRO.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.GUNPOWDER)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(NRContent.SULFUR_POWDER.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(NRContent.IMP.get(), NRContent.HEATSCAR.get(), NRContent.BABY_HEATSCAR.get(), NRContent.NITRO.get());
    }
}

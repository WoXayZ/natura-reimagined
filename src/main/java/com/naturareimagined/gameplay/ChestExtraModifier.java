package com.naturareimagined.gameplay;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naturareimagined.config.NRConfig;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRWoods;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class ChestExtraModifier extends LootModifier {

    public static final MapCodec<ChestExtraModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> codecStart(instance).and(
            com.mojang.serialization.Codec.BOOL.fieldOf("nether").forGetter(modifier -> modifier.nether)
    ).and(
            com.mojang.serialization.Codec.FLOAT.fieldOf("chance").forGetter(modifier -> modifier.chance)
    ).apply(instance, ChestExtraModifier::new));

    private final boolean nether;
    private final float chance;

    public ChestExtraModifier(LootItemCondition[] conditions, boolean nether, float chance) {
        super(conditions);
        this.nether = nether;
        this.chance = chance;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generated, LootContext context) {
        boolean enabled = this.nether ? NRConfig.ADD_NETHER_CHEST_LOOT.get() : NRConfig.ADD_OVERWORLD_CHEST_LOOT.get();
        if (!enabled || context.getRandom().nextFloat() > this.chance) {
            return generated;
        }
        ItemStack extra = this.nether ? netherRoll(context) : overworldRoll(context);
        if (!extra.isEmpty()) {
            generated.add(extra);
        }
        return generated;
    }

    private static ItemStack overworldRoll(LootContext context) {
        int bound = NRWoods.ALL.stream().filter(family -> !family.nether).toList().size() + NRPlants.BUSHES.size() + 2;
        int roll = context.getRandom().nextInt(bound);
        for (var family : NRWoods.ALL) {
            if (family.nether) {
                continue;
            }
            if (roll-- == 0) {
                return family.sapling.toStack();
            }
        }
        for (var bush : NRPlants.BUSHES) {
            if (roll-- == 0) {
                return new ItemStack(bush.berries().get());
            }
        }
        return new ItemStack(roll == 0 ? NRPlants.BARLEY_SEEDS.get() : NRPlants.COTTON_SEEDS.get());
    }

    private static ItemStack netherRoll(LootContext context) {
        int saplings = 0;
        for (var family : NRWoods.ALL) {
            if (family.nether) {
                saplings++;
            }
        }
        int roll = context.getRandom().nextInt(saplings + 3);
        if (roll == 0) {
            return new ItemStack(NRContent.RAW_IMP_MEAT.get());
        }
        if (roll == 1) {
            return new ItemStack(NRContent.IMP_LEATHER.get());
        }
        if (roll == 2) {
            return new ItemStack(NRContent.SULFUR_POWDER.get());
        }
        roll -= 3;
        for (var family : NRWoods.ALL) {
            if (!family.nether) {
                continue;
            }
            if (roll-- == 0) {
                return family.sapling.toStack();
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}

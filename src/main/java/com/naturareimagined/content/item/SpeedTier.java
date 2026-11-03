package com.naturareimagined.content.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public record SpeedTier(Tier base, float speed, Ingredient repair) implements Tier {

    @Override
    public int getUses() {
        return this.base.getUses();
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.base.getAttackDamageBonus();
    }

    @Override
    public net.minecraft.tags.TagKey<Block> getIncorrectBlocksForDrops() {
        return this.base.getIncorrectBlocksForDrops();
    }

    @Override
    public int getEnchantmentValue() {
        return this.base.getEnchantmentValue();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repair;
    }

    public static SpeedTier of(Tier base, float speed) {
        return new SpeedTier(base, speed, base.getRepairIngredient());
    }
}

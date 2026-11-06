package com.naturareimagined.content.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;

public class BabyHeatscarSpiderEntity extends HeatscarSpiderEntity {

    public BabyHeatscarSpiderEntity(EntityType<? extends Spider> type, Level level) {
        super(type, level);
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        var health = this.getAttribute(Attributes.MAX_HEALTH);
        if (health != null && health.getBaseValue() != 16.0D) {
            health.setBaseValue(16.0D);
            if (this.getHealth() > 16.0F) {
                this.setHealth(16.0F);
            }
        }
    }

    @Override
    protected boolean leavesBabies() {
        return false;
    }

    @Override
    public boolean isBaby() {
        return true;
    }
}

package com.naturareimagined.content.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;

public class BabyHeatscarSpiderEntity extends HeatscarSpiderEntity {

    public BabyHeatscarSpiderEntity(EntityType<? extends Spider> type, Level level) {
        super(type, level);
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

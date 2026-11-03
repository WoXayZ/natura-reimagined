package com.naturareimagined.content.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.level.Level;

public class GhostBatEntity extends Bat {

    public GhostBatEntity(EntityType<? extends Bat> type, Level level) {
        super(type, level);
    }
}

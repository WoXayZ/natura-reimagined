package com.naturareimagined.content.entity;

import com.naturareimagined.registry.NRContent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;

public class AmaranthBeeEntity extends Bee {

    public AmaranthBeeEntity(EntityType<? extends Bee> type, Level level) {
        super(type, level);
    }

    @Override
    public Bee getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return NRContent.AMARANTH_BEE.get().create(level);
    }
}

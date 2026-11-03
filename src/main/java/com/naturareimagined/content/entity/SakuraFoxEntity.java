package com.naturareimagined.content.entity;

import com.naturareimagined.registry.NRContent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;

public class SakuraFoxEntity extends Fox {

    public SakuraFoxEntity(EntityType<? extends Fox> type, Level level) {
        super(type, level);
    }

    @Override
    public Fox getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return NRContent.SAKURA_FOX.get().create(level);
    }
}

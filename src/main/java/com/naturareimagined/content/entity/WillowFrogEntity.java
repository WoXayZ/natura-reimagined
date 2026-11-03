package com.naturareimagined.content.entity;

import com.naturareimagined.registry.NRContent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WillowFrogEntity extends Frog {

    public WillowFrogEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return NRContent.WILLOW_FROG.get().create(level);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !target.isAlive() && target instanceof Slime slime && slime.getSize() <= 1 && !this.level().isClientSide) {
            BlockPos pos = target.blockPosition();
            BlockState ground = this.level().getBlockState(pos);
            if (ground.isAir() || ground.canBeReplaced()) {
                BlockState light = switch (this.random.nextInt(3)) {
                    case 0 -> Blocks.OCHRE_FROGLIGHT.defaultBlockState();
                    case 1 -> Blocks.VERDANT_FROGLIGHT.defaultBlockState();
                    default -> Blocks.PEARLESCENT_FROGLIGHT.defaultBlockState();
                };
                this.level().setBlock(pos, light, 3);
            }
        }
        return hit;
    }
}

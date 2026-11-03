package com.naturareimagined.content.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EmberBatEntity extends Bat {

    private int nextIgnite;

    public EmberBatEntity(EntityType<? extends Bat> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide || this.tickCount < this.nextIgnite) {
            return;
        }
        Player player = this.level().getNearestPlayer(this, 1.5);
        if (player != null && !player.isSpectator() && !player.isCreative() && player.distanceTo(this) <= 1.5F) {
            player.setRemainingFireTicks(Math.max(player.getRemainingFireTicks(), 40));
            this.nextIgnite = this.tickCount + 80;
        }
    }
}

package com.naturareimagined.content.entity;

import com.naturareimagined.config.NRConfig;
import com.naturareimagined.registry.NRContent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

public class HeatscarSpiderEntity extends Spider {

    public HeatscarSpiderEntity(EntityType<? extends Spider> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Spider.createAttributes().add(Attributes.MAX_HEALTH, 50.0D).add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    public static boolean checkHeatscarSpawn(EntityType<HeatscarSpiderEntity> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return NRConfig.SPAWN_HEATSCAR.get() && checkMonsterSpawnRules(type, level, reason, pos, random);
    }

    protected boolean leavesBabies() {
        return true;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hurt = super.doHurtTarget(target);
        if (hurt && !(target instanceof Player)) {
            target.igniteForSeconds(4);
        }
        return hurt;
    }

    @Override
    public void die(DamageSource source) {
        if (this.leavesBabies() && this.level() instanceof ServerLevel server) {
            int min = NRConfig.HEATSCAR_BABY_MIN.get();
            int max = Math.max(min, NRConfig.HEATSCAR_BABY_MAX.get());
            int count = min + this.random.nextInt(max - min + 1);
            for (int i = 0; i < count; i++) {
                BabyHeatscarSpiderEntity baby = NRContent.BABY_HEATSCAR.get().create(server);
                if (baby == null) {
                    continue;
                }
                baby.moveTo(this.getX(), this.getY(), this.getZ(), this.random.nextFloat() * 360.0F, 0.0F);
                baby.finalizeSpawn(server, server.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
                server.addFreshEntity(baby);
            }
        }
        super.die(source);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType reason) {
        return NRConfig.SPAWN_HEATSCAR.get() && super.checkSpawnRules(level, reason);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }
}

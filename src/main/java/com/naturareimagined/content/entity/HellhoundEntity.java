package com.naturareimagined.content.entity;

import com.naturareimagined.registry.NRContent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class HellhoundEntity extends Wolf {

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType reason) {
        return level instanceof ServerLevelAccessor server
                && NRContent.checkHellhoundSpawn(NRContent.HELLHOUND.get(), server, reason, this.blockPosition(), this.random);
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return !this.isTame();
    }

    public HellhoundEntity(EntityType<? extends Wolf> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, ImpEntity.class, true) {
            @Override
            public boolean canUse() {
                return !HellhoundEntity.this.isTame() && super.canUse();
            }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Monster.class, 10, true, false, target ->
                !(target instanceof HellhoundEntity) && !(target instanceof Creeper) && !HellhoundEntity.this.isOwnedBy(target)) {
            @Override
            public boolean canUse() {
                return !HellhoundEntity.this.isOrderedToSit() && super.canUse();
            }
        });
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(NRContent.RAW_IMP_MEAT.get()) || stack.is(NRContent.COOKED_IMP_MEAT.get());
    }

    @Override
    public Wolf getBreedOffspring(ServerLevel level, AgeableMob partner) {
        HellhoundEntity baby = NRContent.HELLHOUND.get().create(level);
        if (baby != null && this.isTame()) {
            baby.setOwnerUUID(this.getOwnerUUID());
            baby.setTame(true, true);
        }
        return baby;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData data) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, data);
        if (this.getAttribute(Attributes.MAX_HEALTH) != null) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(25.0);
            this.setHealth(25.0F);
        }
        return result;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            living.setRemainingFireTicks(Math.max(living.getRemainingFireTicks(), 80));
        }
        return hit;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide || this.isTame() || !this.isAngry() || this.tickCount % 5 != 0) {
            return;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() < 0.0008) {
            return;
        }
        var pos = this.blockPosition();
        if (!this.level().getBlockState(pos).isAir()) {
            return;
        }
        var fire = Blocks.FIRE.defaultBlockState();
        if (fire.canSurvive(this.level(), pos)) {
            this.level().setBlock(pos, fire, 3);
        }
    }
}

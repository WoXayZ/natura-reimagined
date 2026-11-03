package com.naturareimagined.content.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class AshenSkeletonEntity extends Skeleton implements CrossbowAttackMob {

    public AshenSkeletonEntity(EntityType<? extends Skeleton> type, Level level) {
        super(type, level);
    }

    @Override
    public void reassessWeaponGoal() {
        if (this.level() == null || this.level().isClientSide) {
            return;
        }
        this.goalSelector.removeAllGoals(goal -> goal instanceof MeleeAttackGoal || goal instanceof RangedBowAttackGoal || goal instanceof RangedCrossbowAttackGoal);
        this.goalSelector.addGoal(4, new RangedCrossbowAttackGoal<>(this, 1.0, 8.0F));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData data) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, data);
        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        crossbow.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FLAME), 1);
        this.setItemSlot(EquipmentSlot.MAINHAND, crossbow);
        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        this.reassessWeaponGoal();
        return result;
    }

    @Override
    public void setChargingCrossbow(boolean charging) {
        this.setAggressive(charging);
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }

    @Override
    public void performRangedAttack(net.minecraft.world.entity.LivingEntity target, float power) {
        this.performCrossbowAttack(this, 1.6F);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.reassessWeaponGoal();
    }
}

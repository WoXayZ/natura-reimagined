package com.naturareimagined.content.entity;

import com.naturareimagined.config.NRConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

public class NitroCreeperEntity extends Creeper {

    public NitroCreeperEntity(EntityType<NitroCreeperEntity> type, Level level) {
        super(type, level);
        try {
            var fuse = Creeper.class.getDeclaredField("maxSwell");
            fuse.setAccessible(true);
            fuse.setInt(this, 12);
        } catch (ReflectiveOperationException ignored) {
            // Vanilla fuse stays in place if the field moves.
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Creeper.createAttributes();
    }

    public static boolean checkNitroSpawn(EntityType<NitroCreeperEntity> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return NRConfig.SPAWN_NITRO.get() && checkMonsterSpawnRules(type, level, reason, pos, random);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof IronGolem) {
            return super.hurt(source, this.getHealth());
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType reason) {
        return NRConfig.SPAWN_NITRO.get() && super.checkSpawnRules(level, reason);
    }
}

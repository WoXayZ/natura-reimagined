package com.naturareimagined.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;

public record ConfigEnabledCondition(String key) implements ICondition {

    public static final MapCodec<ConfigEnabledCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("key").forGetter(ConfigEnabledCondition::key)
    ).apply(instance, ConfigEnabledCondition::new));

    @Override
    public boolean test(IContext context) {
        return switch (this.key) {
            case "enableWheatFlourRecipe" -> NRConfig.ENABLE_WHEAT_FLOUR.get();
            default -> false;
        };
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}

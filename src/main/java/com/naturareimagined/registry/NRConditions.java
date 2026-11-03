package com.naturareimagined.registry;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.config.ConfigEnabledCondition;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class NRConditions {

    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, NaturaConstants.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ConfigEnabledCondition>> CONFIG_ENABLED =
            CONDITIONS.register("config_enabled", () -> ConfigEnabledCondition.CODEC);

    private NRConditions() {}

    public static void register(IEventBus bus) {
        CONDITIONS.register(bus);
    }
}

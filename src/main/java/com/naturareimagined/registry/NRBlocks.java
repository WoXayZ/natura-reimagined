package com.naturareimagined.registry;

import com.naturareimagined.NaturaConstants;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NRBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NaturaConstants.MOD_ID);

    private NRBlocks() {}

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}

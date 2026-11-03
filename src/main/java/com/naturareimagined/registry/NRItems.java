package com.naturareimagined.registry;

import com.naturareimagined.NaturaConstants;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NRItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NaturaConstants.MOD_ID);

    private NRItems() {}

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}

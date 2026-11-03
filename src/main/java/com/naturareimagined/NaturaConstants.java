package com.naturareimagined;

import net.minecraft.resources.ResourceLocation;

public final class NaturaConstants {

    public static final String MOD_ID = "naturareimagined";

    private NaturaConstants() {}

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}

package com.naturareimagined.registry;

import com.naturareimagined.content.wood.WoodFamily;
import java.util.List;
import net.minecraft.world.level.material.MapColor;

public final class NRWoods {

    public static final WoodFamily MAPLE = wood("maple", "Maple", MapColor.TERRACOTTA_ORANGE, false, 0xCC5718);
    public static final WoodFamily SILVERBELL = wood("silverbell", "Silverbell", MapColor.QUARTZ, false, 0xFFFFFF);
    public static final WoodFamily AMARANTH = wood("amaranth", "Amaranth", MapColor.COLOR_MAGENTA, false, 0);
    public static final WoodFamily TIGER = wood("tiger", "Tiger", MapColor.TERRACOTTA_YELLOW, false, 0xFFFFFF);
    public static final WoodFamily WILLOW = wood("willow", "Willow", MapColor.COLOR_GREEN, false, 0xFFFFFF);
    public static final WoodFamily EUCALYPTUS = wood("eucalyptus", "Eucalyptus", MapColor.COLOR_LIGHT_GREEN, false, 0);
    public static final WoodFamily HOPSEED = wood("hopseed", "Hopseed", MapColor.COLOR_GREEN, false, 0);
    public static final WoodFamily SAKURA = wood("sakura", "Sakura", MapColor.COLOR_PINK, false, 0xFFFFFF);
    public static final WoodFamily REDWOOD = wood("redwood", "Redwood", MapColor.COLOR_BROWN, false, 0);
    public static final WoodFamily GHOSTWOOD = wood("ghostwood", "Ghostwood", MapColor.COLOR_LIGHT_GRAY, true, 0xFFFFFF);
    public static final WoodFamily BLOODWOOD = wood("bloodwood", "Bloodwood", MapColor.COLOR_RED, true, 0x8B0000);
    public static final WoodFamily FUSEWOOD = wood("fusewood", "Fusewood", MapColor.COLOR_BROWN, true, 0xFFFFFF);
    public static final WoodFamily DARKWOOD = wood("darkwood", "Darkwood", MapColor.COLOR_BLACK, true, 0xFFFFFF);

    public static final List<WoodFamily> ALL = List.of(
            MAPLE, SILVERBELL, AMARANTH, TIGER, WILLOW, EUCALYPTUS, HOPSEED, SAKURA, REDWOOD,
            GHOSTWOOD, BLOODWOOD, FUSEWOOD, DARKWOOD);

    private NRWoods() {}

    public static WoodFamily named(String name) {
        for (WoodFamily family : ALL) {
            if (family.name.equals(name)) {
                return family;
            }
        }
        throw new IllegalArgumentException(name);
    }

    private static WoodFamily wood(String name, String english, MapColor color, boolean nether, int leavesColor) {
        return new WoodFamily(name, english, color, nether, leavesColor);
    }
}

package com.naturareimagined.content.wood;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.registry.NRWoods;
import java.util.function.Supplier;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public final class NRBoatTypes {

    public static final EnumProxy<Boat.Type> MAPLE = proxy("maple");
    public static final EnumProxy<Boat.Type> SILVERBELL = proxy("silverbell");
    public static final EnumProxy<Boat.Type> AMARANTH = proxy("amaranth");
    public static final EnumProxy<Boat.Type> TIGER = proxy("tiger");
    public static final EnumProxy<Boat.Type> WILLOW = proxy("willow");
    public static final EnumProxy<Boat.Type> EUCALYPTUS = proxy("eucalyptus");
    public static final EnumProxy<Boat.Type> HOPSEED = proxy("hopseed");
    public static final EnumProxy<Boat.Type> SAKURA = proxy("sakura");
    public static final EnumProxy<Boat.Type> REDWOOD = proxy("redwood");

    private NRBoatTypes() {}

    public static Boat.Type type(String name) {
        return switch (name) {
            case "maple" -> MAPLE.getValue();
            case "silverbell" -> SILVERBELL.getValue();
            case "amaranth" -> AMARANTH.getValue();
            case "tiger" -> TIGER.getValue();
            case "willow" -> WILLOW.getValue();
            case "eucalyptus" -> EUCALYPTUS.getValue();
            case "hopseed" -> HOPSEED.getValue();
            case "sakura" -> SAKURA.getValue();
            case "redwood" -> REDWOOD.getValue();
            default -> throw new IllegalArgumentException(name);
        };
    }

    private static EnumProxy<Boat.Type> proxy(String name) {
        return new EnumProxy<>(
                Boat.Type.class,
                (Supplier<Block>) () -> NRWoods.named(name).planks.get(),
                NaturaConstants.MOD_ID + ":" + name,
                (Supplier<Item>) () -> NRWoods.named(name).boat.get(),
                (Supplier<Item>) () -> NRWoods.named(name).chestBoat.get(),
                (Supplier<Item>) () -> Items.STICK,
                false);
    }
}

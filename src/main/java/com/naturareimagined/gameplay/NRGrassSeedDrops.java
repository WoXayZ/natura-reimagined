package com.naturareimagined.gameplay;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.config.NRConfig;
import com.naturareimagined.registry.NRPlants;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

@EventBusSubscriber(modid = NaturaConstants.MOD_ID)
public final class NRGrassSeedDrops {

    private NRGrassSeedDrops() {}

    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        if (!NRConfig.GRASS_DROPS_SEEDS.get() || event.getTool().is(Items.SHEARS)) {
            return;
        }
        if (!event.getState().is(Blocks.SHORT_GRASS) && !event.getState().is(Blocks.TALL_GRASS)) {
            return;
        }
        float chance = NRConfig.GRASS_SEED_CHANCE.get().floatValue();
        var level = event.getLevel();
        var pos = event.getPos();
        if (level.getRandom().nextFloat() < chance) {
            event.getDrops().add(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(NRPlants.BARLEY_SEEDS.get())));
        }
        if (level.getRandom().nextFloat() < chance) {
            event.getDrops().add(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(NRPlants.COTTON_SEEDS.get())));
        }
    }
}

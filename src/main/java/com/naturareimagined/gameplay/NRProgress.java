package com.naturareimagined.gameplay;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.item.HatchetItem;
import com.naturareimagined.registry.NRContent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = NaturaConstants.MOD_ID)
public final class NRProgress {

    private NRProgress() {}

    @SubscribeEvent
    public static void crafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && event.getCrafting().getItem() instanceof HatchetItem) {
            NRAdvancements.grant(player, "husbandry/craft_hatchet");
        }
    }

    @SubscribeEvent
    public static void equipped(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(NRContent.IMP_HELMET.get())
                && player.getItemBySlot(EquipmentSlot.CHEST).is(NRContent.IMP_CHESTPLATE.get())
                && player.getItemBySlot(EquipmentSlot.LEGS).is(NRContent.IMP_LEGGINGS.get())
                && player.getItemBySlot(EquipmentSlot.FEET).is(NRContent.IMP_BOOTS.get())) {
            NRAdvancements.grant(player, "adventure/imp_armor");
        }
    }
}

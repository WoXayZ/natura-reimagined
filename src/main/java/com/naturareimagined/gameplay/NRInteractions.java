package com.naturareimagined.gameplay;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.entity.GlowshroomCowEntity;
import com.naturareimagined.registry.NRContent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = NaturaConstants.MOD_ID)
public final class NRInteractions {

    private NRInteractions() {}

    @SubscribeEvent
    public static void stewOnMooshroom(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof MushroomCow mooshroom) || mooshroom.isBaby()) {
            return;
        }
        EntityType<GlowshroomCowEntity> type = cow(event.getItemStack());
        if (type == null) {
            return;
        }
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
        if (event.getLevel().isClientSide()) {
            return;
        }
        Player player = event.getEntity();
        player.setItemInHand(event.getHand(), ItemUtils.createFilledResult(event.getItemStack(), player, new ItemStack(Items.BOWL)));
        ServerLevel level = (ServerLevel) event.getLevel();
        GlowshroomCowEntity cow = type.create(level);
        if (cow == null) {
            return;
        }
        cow.moveTo(mooshroom.getX(), mooshroom.getY(), mooshroom.getZ(), mooshroom.getYRot(), mooshroom.getXRot());
        cow.setHealth(mooshroom.getHealth());
        cow.setAge(mooshroom.getAge());
        cow.setCustomName(mooshroom.getCustomName());
        cow.setPersistenceRequired();
        for (MobEffectInstance effect : mooshroom.getActiveEffects()) {
            cow.addEffect(new MobEffectInstance(effect));
        }
        level.addFreshEntity(cow);
        mooshroom.discard();
    }

    private static EntityType<GlowshroomCowEntity> cow(ItemStack stack) {
        if (stack.is(NRContent.GREEN_GLOWSHROOM_STEW.get())) {
            return NRContent.GREEN_GLOWSHROOM_COW.get();
        }
        if (stack.is(NRContent.BLUE_GLOWSHROOM_STEW.get())) {
            return NRContent.BLUE_GLOWSHROOM_COW.get();
        }
        if (stack.is(NRContent.PURPLE_GLOWSHROOM_STEW.get())) {
            return NRContent.PURPLE_GLOWSHROOM_COW.get();
        }
        return null;
    }
}

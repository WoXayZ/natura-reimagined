package com.naturareimagined.gameplay;

import com.naturareimagined.NaturaConstants;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.server.level.ServerPlayer;

public final class NRAdvancements {

    private NRAdvancements() {}

    public static void grant(ServerPlayer player, String path) {
        AdvancementHolder holder = player.server.getAdvancements().get(NaturaConstants.id(path));
        if (holder == null) {
            return;
        }
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(holder);
        if (progress.isDone()) {
            return;
        }
        for (String criterion : progress.getRemainingCriteria()) {
            player.getAdvancements().award(holder, criterion);
        }
    }
}

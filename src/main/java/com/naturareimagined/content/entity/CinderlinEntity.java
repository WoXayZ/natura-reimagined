package com.naturareimagined.content.entity;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.registry.NRContent;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;
import com.naturareimagined.compat.CinderlinBarterOffers;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class CinderlinEntity extends Piglin {

    public static final ResourceKey<LootTable> BARTER = ResourceKey.create(Registries.LOOT_TABLE, NaturaConstants.id("gameplay/cinderlin_bartering"));

    public CinderlinEntity(EntityType<? extends AbstractPiglin> type, Level level) {
        super(type, level);
    }

    @Override
    protected boolean canHunt() {
        return false;
    }

    @Override
    public boolean wantsToPickUp(ItemStack stack) {
        return this.isAdult()
                && this.canPickUpLoot()
                && net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.level(), this)
                && this.acceptsBarter(stack)
                && !this.acceptsBarter(this.getOffhandItem());
    }

    @Override
    protected void customServerAiStep() {
        if (this.acceptsBarter(this.getOffhandItem())
                && this.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_ITEM)
                && this.getBrain().getTimeUntilExpiry(MemoryModuleType.ADMIRING_ITEM) <= 1L) {
            this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
            this.throwBarter((ServerLevel) this.level(), this.level().getNearestPlayer(this, 16.0));
        }
        super.customServerAiStep();
    }

    @Override
    protected boolean canReplaceCurrentItem(ItemStack replacement, ItemStack current) {
        return false;
    }

    @Override
    public PiglinArmPose getArmPose() {
        if (this.swinging) {
            return super.getArmPose();
        }
        PiglinArmPose pose = super.getArmPose();
        if (pose == PiglinArmPose.ADMIRING_ITEM || pose == PiglinArmPose.DANCING
                || pose == PiglinArmPose.CROSSBOW_HOLD || pose == PiglinArmPose.CROSSBOW_CHARGE) {
            return pose;
        }
        ItemStack held = this.getMainHandItem();
        if (held.getItem() instanceof net.minecraft.world.item.SwordItem || held.getItem() instanceof net.minecraft.world.item.AxeItem) {
            return PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON;
        }
        return PiglinArmPose.DEFAULT;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData data) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, data);
        if (this.getAttribute(Attributes.MAX_HEALTH) != null) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0);
            this.setHealth(30.0F);
        }
        ItemStack weapon = switch (this.random.nextInt(3)) {
            case 0 -> new ItemStack(NRContent.QUARTZ_SWORD.get());
            case 1 -> new ItemStack(NRContent.QUARTZ_AXE.get());
            default -> flameCrossbow(level);
        };
        this.holdInMainHand(weapon);
        return result;
    }

    private static ItemStack flameCrossbow(ServerLevelAccessor level) {
        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        crossbow.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FLAME), 1);
        return crossbow;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.isAdult() && CinderlinBarterOffers.accepts(stack, this.level().isClientSide
                ? CinderlinBarterOffers.inputs()
                : CinderlinBarterOffers.inputs(((ServerLevel) this.level()).getServer().getResourceManager()))) {
            if (!this.level().isClientSide) {
                stack.consume(1, player);
                this.getBrain().setMemoryWithExpiry(MemoryModuleType.ADMIRING_ITEM, true, 200L);
                this.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
                this.getBrain().eraseMemory(MemoryModuleType.ADMIRING_DISABLED);
                this.setTarget(null);
                this.throwBarter((ServerLevel) this.level(), player);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide || this.isBaby() || this.tickCount % 20 != 0) {
            return;
        }
        if (this.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_ITEM)) {
            this.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
            this.setTarget(null);
            return;
        }
        Player player = this.level().getNearestPlayer(this, 16.0);
        if (player != null && !player.isCreative() && !player.isSpectator() && this.hasLineOfSight(player)) {
            this.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, player.getUUID(), 200L);
        }
    }

    private boolean acceptsBarter(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (this.level().isClientSide) {
            return stack.is(Items.GOLD_INGOT);
        }
        return CinderlinBarterOffers.accepts(stack, CinderlinBarterOffers.inputs(((ServerLevel) this.level()).getServer().getResourceManager()));
    }

    private void throwBarter(ServerLevel level, Player player) {
        LootTable table = level.getServer().reloadableRegistries().getLootTable(BARTER);
        LootParams params = new LootParams.Builder(level)
                .withParameter(LootContextParams.THIS_ENTITY, this)
                .create(LootContextParamSets.PIGLIN_BARTER);
        List<ItemStack> rewards = table.getRandomItems(params);
        Vec3 toward = player == null ? this.getLookAngle() : player.position().subtract(this.position());
        if (toward.lengthSqr() < 1.0E-4) {
            toward = this.getLookAngle();
        }
        toward = toward.normalize().scale(0.3);
        for (ItemStack reward : rewards) {
            ItemEntity drop = new ItemEntity(level, this.getX(), this.getEyeY() - 0.3, this.getZ(), reward);
            drop.setDeltaMovement(toward.x, 0.2, toward.z);
            drop.setPickUpDelay(20);
            level.addFreshEntity(drop);
        }
    }
}

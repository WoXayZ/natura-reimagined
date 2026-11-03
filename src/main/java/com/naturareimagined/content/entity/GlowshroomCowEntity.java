package com.naturareimagined.content.entity;

import com.naturareimagined.registry.NRContent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class GlowshroomCowEntity extends Cow {

    public GlowshroomCowEntity(EntityType<? extends Cow> type, Level level) {
        super(type, level);
    }

    public String color() {
        if (this.getType() == NRContent.BLUE_GLOWSHROOM_COW.get()) {
            return "blue";
        }
        if (this.getType() == NRContent.PURPLE_GLOWSHROOM_COW.get()) {
            return "purple";
        }
        return "green";
    }

    @Override
    public boolean canMate(Animal other) {
        return other.getType() == this.getType() && super.canMate(other);
    }

    @Override
    public Cow getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return (Cow) this.getType().create(level);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.isBaby()) {
            return super.mobInteract(player, hand);
        }
        if (stack.is(Items.BOWL)) {
            Item stew = switch (this.color()) {
                case "blue" -> NRContent.BLUE_GLOWSHROOM_STEW.get();
                case "purple" -> NRContent.PURPLE_GLOWSHROOM_STEW.get();
                default -> NRContent.GREEN_GLOWSHROOM_STEW.get();
            };
            this.playSound(SoundEvents.MOOSHROOM_MILK, 1.0F, 1.0F);
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(stew)));
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (stack.is(Items.SHEARS)) {
            if (!this.level().isClientSide) {
                this.shearIntoCow((ServerLevel) this.level(), player, hand, stack);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    private void shearIntoCow(ServerLevel level, Player player, InteractionHand hand, ItemStack shears) {
        this.playSound(SoundEvents.MOOSHROOM_SHEAR, 1.0F, 1.0F);
        shears.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        Item glowshroom = switch (this.color()) {
            case "blue" -> NRContent.BLUE_GLOWSHROOM.get().asItem();
            case "purple" -> NRContent.PURPLE_GLOWSHROOM.get().asItem();
            default -> NRContent.GREEN_GLOWSHROOM.get().asItem();
        };
        for (int i = 0; i < 5; i++) {
            this.spawnAtLocation(new ItemStack(glowshroom));
        }
        Cow cow = EntityType.COW.create(level);
        if (cow == null) {
            return;
        }
        cow.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
        cow.setHealth(this.getHealth());
        cow.setAge(this.getAge());
        cow.setCustomName(this.getCustomName());
        cow.setPersistenceRequired();
        for (MobEffectInstance effect : this.getActiveEffects()) {
            cow.addEffect(new MobEffectInstance(effect));
        }
        level.addFreshEntity(cow);
        this.discard();
    }
}

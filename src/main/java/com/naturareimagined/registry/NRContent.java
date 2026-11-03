package com.naturareimagined.registry;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.block.GhostNyliumBlock;
import com.naturareimagined.content.entity.BabyHeatscarSpiderEntity;
import com.naturareimagined.content.entity.HeatscarSpiderEntity;
import com.naturareimagined.content.entity.ImpEntity;
import com.naturareimagined.content.entity.NitroCreeperEntity;
import com.naturareimagined.content.item.CactusJuiceItem;
import com.naturareimagined.content.item.FlintAndBlazeItem;
import com.naturareimagined.content.item.HatchetItem;
import com.naturareimagined.content.item.SpeedTier;
import com.naturareimagined.content.machine.NetherrackFurnaceBlock;
import com.naturareimagined.content.machine.NetherrackFurnaceBlockEntity;
import com.naturareimagined.content.plant.GlowshroomBlock;
import com.naturareimagined.content.plant.SaguaroBlock;
import com.naturareimagined.content.plant.SaguaroFruitBlock;
import com.naturareimagined.content.world.BlazeHopperBlock;
import com.naturareimagined.content.world.BlazeHopperBlockEntity;
import com.naturareimagined.content.world.BlazeRails;
import com.naturareimagined.content.world.CloudBlock;
import com.naturareimagined.content.world.HeatSandBlock;
import com.naturareimagined.content.world.NetherGlassBlock;
import com.naturareimagined.content.world.TaintedFarmlandBlock;
import com.naturareimagined.content.world.TaintedSoilBlock;
import com.naturareimagined.content.world.ThornVineBlock;
import com.mojang.serialization.MapCodec;
import com.naturareimagined.gameplay.ChestExtraModifier;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@EventBusSubscriber(modid = NaturaConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class NRContent {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, NaturaConstants.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, NaturaConstants.MOD_ID);
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, NaturaConstants.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, NaturaConstants.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ChestExtraModifier>> CHEST_LOOT =
            LOOT_MODIFIERS.register("chest_extra", () -> ChestExtraModifier.CODEC);

    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_WILD =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id("redwood_wild"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAGUARO_CONFIGURED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id("saguaro"));
    public static final ResourceKey<PlacedFeature> SAGUARO_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, NaturaConstants.id("saguaro"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSHROOM_CONFIGURED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id("glowshrooms"));
    public static final ResourceKey<PlacedFeature> GLOWSHROOM_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, NaturaConstants.id("glowshrooms"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_CONFIGURED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id("clouds"));
    public static final ResourceKey<PlacedFeature> CLOUD_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, NaturaConstants.id("clouds"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> THORN_CONFIGURED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id("thornvines"));
    public static final ResourceKey<PlacedFeature> THORN_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, NaturaConstants.id("thornvines"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> TAINTED_CONFIGURED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id("tainted_soil"));
    public static final ResourceKey<PlacedFeature> TAINTED_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, NaturaConstants.id("tainted_soil"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> HEAT_SAND_CONFIGURED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id("heat_sand"));
    public static final ResourceKey<PlacedFeature> HEAT_SAND_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, NaturaConstants.id("heat_sand"));

    private static final Tier QUARTZ = new Tier() {
        @Override public int getUses() { return 191; }
        @Override public float getSpeed() { return 5.0F; }
        @Override public float getAttackDamageBonus() { return 1.5F; }
        @Override public net.minecraft.tags.TagKey<Block> getIncorrectBlocksForDrops() { return BlockTags.INCORRECT_FOR_IRON_TOOL; }
        @Override public int getEnchantmentValue() { return 10; }
        @Override public Ingredient getRepairIngredient() { return Ingredient.of(Items.QUARTZ); }
    };

    public static final DeferredBlock<GhostNyliumBlock> GHOST_NYLIUM = block("ghost_nylium", () -> new GhostNyliumBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WARPED_NYLIUM).strength(0.4F).sound(SoundType.NYLIUM).randomTicks()));
    public static final DeferredBlock<HeatSandBlock> HEAT_SAND = block("heat_sand", () -> new HeatSandBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.TERRACOTTA_ORANGE).strength(0.5F).sound(SoundType.SAND)));
    public static final DeferredBlock<TaintedSoilBlock> TAINTED_SOIL = block("tainted_soil", () -> new TaintedSoilBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK).strength(0.5F).sound(SoundType.SOUL_SOIL)));
    public static final DeferredBlock<TaintedFarmlandBlock> TAINTED_FARMLAND = NRBlocks.BLOCKS.register("tainted_farmland", () -> new TaintedFarmlandBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK).strength(0.6F).sound(SoundType.GRAVEL).randomTicks()));
    public static final DeferredItem<BlockItem> TAINTED_FARMLAND_ITEM = NRItems.ITEMS.register("tainted_farmland", () -> new BlockItem(TAINTED_FARMLAND.get(), new Item.Properties()));
    public static final DeferredBlock<ThornVineBlock> THORNVINE = block("thorn_vines", () -> new ThornVineBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_GREEN).replaceable().noCollission().strength(0.2F).sound(SoundType.VINE).ignitedByLava()));
    public static final DeferredBlock<NetherGlassBlock> SOUL_GLASS = block("soul_glass", () -> new NetherGlassBlock(false, true, BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN).strength(0.3F).sound(SoundType.GLASS).noOcclusion().noCollission()));
    public static final DeferredBlock<NetherGlassBlock> HEAT_GLASS = block("heat_glass", () -> new NetherGlassBlock(true, false, BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_ORANGE).strength(0.3F).sound(SoundType.GLASS).noOcclusion()));
    public static final DeferredBlock<CloudBlock> WHITE_CLOUD = cloud("white_cloud", false, MapColor.SNOW);
    public static final DeferredBlock<CloudBlock> DARK_CLOUD = cloud("dark_cloud", false, MapColor.COLOR_BLACK);
    public static final DeferredBlock<CloudBlock> ASH_CLOUD = cloud("ash_cloud", false, MapColor.COLOR_GRAY);
    public static final DeferredBlock<CloudBlock> SULFUR_CLOUD = cloud("sulfur_cloud", true, MapColor.COLOR_YELLOW);
    public static final DeferredBlock<SaguaroBlock> SAGUARO = NRBlocks.BLOCKS.register("saguaro", () -> new SaguaroBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_GREEN).strength(0.4F).sound(SoundType.WOOL).randomTicks()));
    public static final DeferredItem<BlockItem> SAGUARO_ITEM = NRItems.ITEMS.register("saguaro", () -> new BlockItem(SAGUARO.get(), new Item.Properties()));
    public static final DeferredBlock<SaguaroFruitBlock> SAGUARO_FRUIT_BLOCK = NRBlocks.BLOCKS.register("saguaro_fruit_block", () -> new SaguaroFruitBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_GREEN).strength(0.2F, 3.0F).sound(SoundType.WOOD).randomTicks().noOcclusion().forceSolidOff()));
    public static final DeferredBlock<GlowshroomBlock> GREEN_GLOWSHROOM = glow("green");
    public static final DeferredBlock<GlowshroomBlock> BLUE_GLOWSHROOM = glow("blue");
    public static final DeferredBlock<GlowshroomBlock> PURPLE_GLOWSHROOM = glow("purple");
    public static final DeferredBlock<RotatedPillarBlock> GREEN_GLOWSHROOM_STEM = registerStem("green");
    public static final DeferredBlock<RotatedPillarBlock> BLUE_GLOWSHROOM_STEM = registerStem("blue");
    public static final DeferredBlock<RotatedPillarBlock> PURPLE_GLOWSHROOM_STEM = registerStem("purple");
    public static final DeferredBlock<Block> GREEN_GLOWSHROOM_CAP = registerCap("green");
    public static final DeferredBlock<Block> BLUE_GLOWSHROOM_CAP = registerCap("blue");
    public static final DeferredBlock<Block> PURPLE_GLOWSHROOM_CAP = registerCap("purple");
    public static final DeferredBlock<NetherrackFurnaceBlock> NETHERRACK_FURNACE = block("netherrack_furnace", () -> new NetherrackFurnaceBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.NETHER).strength(3.5F).sound(SoundType.NETHERRACK).lightLevel(state -> state.getValue(net.minecraft.world.level.block.AbstractFurnaceBlock.LIT) ? 13 : 0)));
    public static final DeferredBlock<BlazeHopperBlock> BLAZE_HOPPER = block("blaze_hopper", () -> new BlazeHopperBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_ORANGE).strength(3.0F).sound(SoundType.NETHER_BRICKS).noOcclusion()));
    public static final DeferredBlock<BlazeRails.Normal> BLAZE_RAIL = block("blaze_rail", () -> new BlazeRails.Normal(rail()));
    public static final DeferredBlock<BlazeRails.Powered> BLAZE_POWERED_RAIL = block("blaze_powered_rail", () -> new BlazeRails.Powered(rail()));
    public static final DeferredBlock<BlazeRails.Detector> BLAZE_DETECTOR_RAIL = block("blaze_detector_rail", () -> new BlazeRails.Detector(rail()));
    public static final DeferredBlock<BlazeRails.Activator> BLAZE_ACTIVATOR_RAIL = block("blaze_activator_rail", () -> new BlazeRails.Activator(rail()));
    public static final DeferredBlock<LeverBlock> NETHER_LEVER = block("nether_lever", () -> new LeverBlock(BlockBehaviour.Properties.of()
            .strength(0.5F).sound(SoundType.WOOD).noOcclusion()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NetherrackFurnaceBlockEntity>> FURNACE_BE =
            BLOCK_ENTITIES.register("netherrack_furnace", () -> BlockEntityType.Builder.of(NetherrackFurnaceBlockEntity::new, NETHERRACK_FURNACE.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlazeHopperBlockEntity>> BLAZE_HOPPER_BE =
            BLOCK_ENTITIES.register("blaze_hopper", () -> BlockEntityType.Builder.of(BlazeHopperBlockEntity::new, BLAZE_HOPPER.get()).build(null));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> IMP_ARMOR = ARMOR_MATERIALS.register("imp", () -> new ArmorMaterial(
            Map.of(ArmorItem.Type.BOOTS, 1, ArmorItem.Type.LEGGINGS, 2, ArmorItem.Type.CHESTPLATE, 3, ArmorItem.Type.HELMET, 1),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(NRContent.IMP_LEATHER.get()),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(NaturaConstants.MOD_ID, "imp"))),
            0.0F,
            0.0F));

    public static final DeferredItem<Item> SULFUR_POWDER = item("sulfur_powder");
    public static final DeferredItem<Item> FLAME_STRING = item("flame_string");
    public static final DeferredItem<Item> IMP_LEATHER = item("imp_leather");
    public static final DeferredItem<Item> RAW_IMP_MEAT = NRItems.ITEMS.register("raw_impmeat", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 160), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 100), 1.0F).build())));
    public static final DeferredItem<Item> COOKED_IMP_MEAT = NRItems.ITEMS.register("cooked_impmeat", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600), 1.0F).build())));
    public static final DeferredItem<Item> POTASH_APPLE = NRItems.ITEMS.register("potash_apple", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 100), 1.0F).build())));
    public static final DeferredItem<Item> CACTUS_JUICE = NRItems.ITEMS.register("cactus_juice", () -> new CactusJuiceItem(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.1F).usingConvertsTo(Items.BOWL).build()).stacksTo(1)));
    public static final DeferredItem<Item> GREEN_GLOWSHROOM_STEW = stew("green_glowshroom_stew");
    public static final DeferredItem<Item> BLUE_GLOWSHROOM_STEW = stew("blue_glowshroom_stew");
    public static final DeferredItem<Item> PURPLE_GLOWSHROOM_STEW = stew("purple_glowshroom_stew");
    public static final DeferredItem<BlockItem> SAGUARO_FRUIT = NRItems.ITEMS.register("saguaro_fruit", () -> new BlockItem(SAGUARO_FRUIT_BLOCK.get(), new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3F).build())));
    public static final DeferredItem<FlintAndBlazeItem> FLINT_AND_BLAZE = NRItems.ITEMS.register("flint_and_blaze", () -> new FlintAndBlazeItem(new Item.Properties()
            .durability(64)));

    public static final DeferredItem<SwordItem> QUARTZ_SWORD = tool("quartz_sword", () -> new SwordItem(QUARTZ, new Item.Properties().attributes(SwordItem.createAttributes(QUARTZ, 3, -2.4F))));
    public static final DeferredItem<PickaxeItem> QUARTZ_PICKAXE = tool("quartz_pickaxe", () -> new PickaxeItem(QUARTZ, new Item.Properties().attributes(DiggerItem.createAttributes(QUARTZ, 1.0F, -2.8F))));
    public static final DeferredItem<AxeItem> QUARTZ_AXE = tool("quartz_axe", () -> new AxeItem(QUARTZ, new Item.Properties().attributes(DiggerItem.createAttributes(QUARTZ, 6.5F, -3.15F))));
    public static final DeferredItem<ShovelItem> QUARTZ_SHOVEL = tool("quartz_shovel", () -> new ShovelItem(QUARTZ, new Item.Properties().attributes(DiggerItem.createAttributes(QUARTZ, 1.5F, -3.0F))));
    public static final DeferredItem<HoeItem> QUARTZ_HOE = tool("quartz_hoe", () -> new HoeItem(QUARTZ, new Item.Properties().attributes(DiggerItem.createAttributes(QUARTZ, -1.0F, -1.5F))));

    public static final DeferredItem<HatchetItem> WOODEN_HATCHET = hatchet("wooden_hatchet", SpeedTier.of(Tiers.WOOD, 3.0F));
    public static final DeferredItem<HatchetItem> STONE_HATCHET = hatchet("stone_hatchet", SpeedTier.of(Tiers.STONE, 6.0F));
    public static final DeferredItem<HatchetItem> IRON_HATCHET = hatchet("iron_hatchet", SpeedTier.of(Tiers.IRON, 9.0F));
    public static final DeferredItem<HatchetItem> GOLDEN_HATCHET = hatchet("golden_hatchet", SpeedTier.of(Tiers.GOLD, 18.0F));
    public static final DeferredItem<HatchetItem> DIAMOND_HATCHET = hatchet("diamond_hatchet", SpeedTier.of(Tiers.DIAMOND, 12.0F));
    public static final DeferredItem<HatchetItem> NETHERITE_HATCHET = hatchet("netherite_hatchet", SpeedTier.of(Tiers.NETHERITE, 13.5F));
    public static final DeferredItem<HatchetItem> QUARTZ_HATCHET = hatchet("quartz_hatchet", new SpeedTier(QUARTZ, 7.5F, Ingredient.of(Items.QUARTZ)));

    public static final DeferredItem<ArmorItem> IMP_HELMET = armor("imp_helmet", ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> IMP_CHESTPLATE = armor("imp_chestplate", ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> IMP_LEGGINGS = armor("imp_leggings", ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> IMP_BOOTS = armor("imp_boots", ArmorItem.Type.BOOTS);

    public static final DeferredHolder<EntityType<?>, EntityType<ImpEntity>> IMP = ENTITIES.register("imp", () -> EntityType.Builder.of(ImpEntity::new, MobCategory.CREATURE).sized(0.9F, 1.5F).fireImmune().build("imp"));
    public static final DeferredHolder<EntityType<?>, EntityType<HeatscarSpiderEntity>> HEATSCAR = ENTITIES.register("heatscarspider", () -> EntityType.Builder.of(HeatscarSpiderEntity::new, MobCategory.MONSTER).sized(1.6F, 1.0F).fireImmune().build("heatscarspider"));
    public static final DeferredHolder<EntityType<?>, EntityType<BabyHeatscarSpiderEntity>> BABY_HEATSCAR = ENTITIES.register("baby_heatscarspider", () -> EntityType.Builder.of(BabyHeatscarSpiderEntity::new, MobCategory.MONSTER).sized(0.6F, 0.4F).fireImmune().build("baby_heatscarspider"));
    public static final DeferredHolder<EntityType<?>, EntityType<NitroCreeperEntity>> NITRO = ENTITIES.register("nitro_creeper", () -> EntityType.Builder.of(NitroCreeperEntity::new, MobCategory.MONSTER).sized(0.6F, 1.7F).fireImmune().build("nitro_creeper"));

    public static final DeferredItem<SpawnEggItem> IMP_SPAWN_EGG = egg("imp_spawn_egg", IMP, 0xC45A12, 0x3A1A10);
    public static final DeferredItem<SpawnEggItem> HEATSCAR_SPAWN_EGG = egg("heatscarspider_spawn_egg", HEATSCAR, 0x6B1D12, 0x1A1A1A);
    public static final DeferredItem<SpawnEggItem> BABY_HEATSCAR_SPAWN_EGG = egg("baby_heatscarspider_spawn_egg", BABY_HEATSCAR, 0xE07A3D, 0x3A1A10);
    public static final DeferredItem<SpawnEggItem> NITRO_SPAWN_EGG = egg("nitro_creeper_spawn_egg", NITRO, 0xE4252A, 0x3A0A0C);

    public static final List<DeferredItem<? extends Item>> HATCHETS = List.of(
            WOODEN_HATCHET, STONE_HATCHET, IRON_HATCHET, GOLDEN_HATCHET, DIAMOND_HATCHET, NETHERITE_HATCHET, QUARTZ_HATCHET);

    private NRContent() {}

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
        ENTITIES.register(bus);
        ARMOR_MATERIALS.register(bus);
        LOOT_MODIFIERS.register(bus);
    }

    public static Block stem(String color) {
        return switch (color) {
            case "blue" -> BLUE_GLOWSHROOM_STEM.get();
            case "purple" -> PURPLE_GLOWSHROOM_STEM.get();
            default -> GREEN_GLOWSHROOM_STEM.get();
        };
    }

    public static Block cap(String color) {
        return switch (color) {
            case "blue" -> BLUE_GLOWSHROOM_CAP.get();
            case "purple" -> PURPLE_GLOWSHROOM_CAP.get();
            default -> GREEN_GLOWSHROOM_CAP.get();
        };
    }

    @SubscribeEvent
    public static void blockEntities(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.HOPPER, BLAZE_HOPPER.get());
        for (var family : NRWoods.ALL) {
            event.modify(BlockEntityType.SIGN, family.sign.get(), family.wallSign.get());
            event.modify(BlockEntityType.HANGING_SIGN, family.hangingSign.get(), family.wallHangingSign.get());
        }
    }

    @SubscribeEvent
    public static void attributes(EntityAttributeCreationEvent event) {
        event.put(IMP.get(), ImpEntity.createAttributes().build());
        event.put(HEATSCAR.get(), HeatscarSpiderEntity.createAttributes().build());
        event.put(BABY_HEATSCAR.get(), HeatscarSpiderEntity.createAttributes().build());
        event.put(NITRO.get(), NitroCreeperEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void spawns(RegisterSpawnPlacementsEvent event) {
        event.register(IMP.get(), net.minecraft.world.entity.SpawnPlacementTypes.ON_GROUND, net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ImpEntity::checkImpSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(HEATSCAR.get(), net.minecraft.world.entity.SpawnPlacementTypes.ON_GROUND, net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HeatscarSpiderEntity::checkHeatscarSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(NITRO.get(), net.minecraft.world.entity.SpawnPlacementTypes.ON_GROUND, net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NitroCreeperEntity::checkNitroSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    private static DeferredBlock<CloudBlock> cloud(String name, boolean explodes, MapColor color) {
        return block(name, () -> new CloudBlock(explodes, BlockBehaviour.Properties.of().mapColor(color).strength(0.2F).sound(SoundType.WOOL).noOcclusion().noCollission()));
    }

    private static DeferredBlock<GlowshroomBlock> glow(String color) {
        DeferredBlock<GlowshroomBlock> block = NRBlocks.BLOCKS.register(color + "_glowshroom", () -> new GlowshroomBlock(color, BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_CYAN).instabreak().sound(SoundType.FUNGUS).lightLevel(state -> 9).noOcclusion().noCollission()));
        NRItems.ITEMS.register(color + "_glowshroom", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static DeferredBlock<RotatedPillarBlock> registerStem(String color) {
        DeferredBlock<RotatedPillarBlock> block = NRBlocks.BLOCKS.register(color + "_glowshroom_stem", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                .mapColor(MapColor.TERRACOTTA_WHITE).strength(0.2F).sound(SoundType.FUNGUS).lightLevel(state -> 6).noOcclusion()));
        NRItems.ITEMS.register(color + "_glowshroom_stem", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static DeferredBlock<Block> registerCap(String color) {
        DeferredBlock<Block> block = NRBlocks.BLOCKS.register(color + "_glowshroom_cap", () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_CYAN).strength(0.2F).sound(SoundType.FUNGUS).lightLevel(state -> 12).noOcclusion()));
        NRItems.ITEMS.register(color + "_glowshroom_cap", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <T extends Block> DeferredBlock<T> block(String name, Supplier<T> supplier) {
        DeferredBlock<T> registered = NRBlocks.BLOCKS.register(name, supplier);
        NRItems.ITEMS.register(name, () -> new BlockItem(registered.get(), new Item.Properties()));
        return registered;
    }

    private static DeferredItem<Item> stew(String name) {
        return NRItems.ITEMS.register(name, () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(6).saturationModifier(0.6F).usingConvertsTo(Items.BOWL)
                .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 900), 1.0F)
                .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 160), 1.0F).build()).stacksTo(1)));
    }

    private static DeferredItem<Item> item(String name) {
        return NRItems.ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> DeferredItem<T> tool(String name, Supplier<T> supplier) {
        return NRItems.ITEMS.register(name, supplier);
    }

    private static DeferredItem<HatchetItem> hatchet(String name, Tier tier) {
        return NRItems.ITEMS.register(name, () -> new HatchetItem(tier, new Item.Properties()));
    }

    private static DeferredItem<ArmorItem> armor(String name, ArmorItem.Type type) {
        return NRItems.ITEMS.register(name, () -> new ArmorItem(IMP_ARMOR, type, new Item.Properties().durability(type.getDurability(5))));
    }

    private static DeferredItem<SpawnEggItem> egg(String name, Supplier<? extends EntityType<? extends net.minecraft.world.entity.Mob>> type, int background, int highlight) {
        return NRItems.ITEMS.register(name, () -> new SpawnEggItem(type.get(), background, highlight, new Item.Properties()));
    }

    private static BlockBehaviour.Properties rail() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.7F).sound(SoundType.METAL).noOcclusion();
    }

}

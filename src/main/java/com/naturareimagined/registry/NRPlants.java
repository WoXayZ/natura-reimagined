package com.naturareimagined.registry;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.plant.BarleyCropBlock;
import com.naturareimagined.content.plant.BerryBushBlock;
import com.naturareimagined.content.plant.BoneMealBagItem;
import com.naturareimagined.content.plant.ColoredGrassBlock;
import com.naturareimagined.content.plant.CottonCropBlock;
import com.naturareimagined.content.plant.PlantBlockItem;
import com.naturareimagined.content.plant.SeedBagItem;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public final class NRPlants {

    private static final FoodProperties SWEET = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build();
    private static final FoodProperties BLIGHT = berry(new MobEffectInstance(MobEffects.REGENERATION, 80), new MobEffectInstance(MobEffects.POISON, 80));
    private static final FoodProperties DUSK = berry(new MobEffectInstance(MobEffects.NIGHT_VISION, 600), new MobEffectInstance(MobEffects.BLINDNESS, 40));
    private static final FoodProperties SKY = berry(new MobEffectInstance(MobEffects.JUMP, 200), new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40));
    private static final FoodProperties STING = berry(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300), new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 160));
    public static final FoodProperties BERRY_MEDLEY = new FoodProperties.Builder()
            .nutrition(6)
            .saturationModifier(0.6F)
            .usingConvertsTo(net.minecraft.world.item.Items.BOWL)
            .build();

    public static final List<BushPlant> BUSHES = List.of(
            bush("raspberry", "rasp_berries", "Raspberry Bush", "Raspberries", false, "c:is_forest", SWEET),
            bush("blueberry", "blue_berries", "Blueberry Bush", "Blueberries", false, "c:is_mountain", SWEET),
            bush("blackberry", "black_berries", "Blackberry Bush", "Blackberries", false, "c:is_forest", SWEET),
            bush("maloberry", "malo_berries", "Maloberry Bush", "Maloberries", false, "c:is_swamp", SWEET),
            bush("blightberry", "blight_berries", "Blightberry Bush", "Blightberries", true, "c:is_nether", BLIGHT),
            bush("duskberry", "dusk_berries", "Duskberry Bush", "Duskberries", true, "c:is_nether", DUSK),
            bush("skyberry", "sky_berries", "Skyberry Bush", "Skyberries", true, "c:is_nether", SKY),
            bush("stingberry", "sting_berries", "Stingberry Bush", "Stingberries", true, "c:is_nether", STING));

    public static final DeferredBlock<BarleyCropBlock> BARLEY_CROP = NRBlocks.BLOCKS.register("barley_crop",
            () -> new BarleyCropBlock(cropProperties()));
    public static final DeferredItem<BlockItem> BARLEY_SEEDS = NRItems.ITEMS.register("barley_seeds",
            () -> new PlantBlockItem(BARLEY_CROP.get(), new Item.Properties()));
    public static final DeferredItem<Item> BARLEY = NRItems.ITEMS.register("barley", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BARLEY_FLOUR = NRItems.ITEMS.register("barley_flour", () -> new Item(new Item.Properties()));

    public static final DeferredBlock<CottonCropBlock> COTTON_CROP = NRBlocks.BLOCKS.register("cotton_crop",
            () -> new CottonCropBlock(cropProperties()));
    public static final DeferredItem<BlockItem> COTTON_SEEDS = NRItems.ITEMS.register("cotton_seeds",
            () -> new PlantBlockItem(COTTON_CROP.get(), new Item.Properties()));
    public static final DeferredItem<Item> COTTON = NRItems.ITEMS.register("cotton", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WHEAT_FLOUR = NRItems.ITEMS.register("wheat_flour", () -> new Item(new Item.Properties()));

    public static final DeferredBlock<FlowerBlock> BLUEBELLS = NRBlocks.BLOCKS.register("bluebells",
            () -> new FlowerBlock(MobEffects.LUCK, 5.0F, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));
    public static final DeferredItem<BlockItem> BLUEBELLS_ITEM = blockItem("bluebells", BLUEBELLS);
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUEBELL_CONFIGURED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id("bluebells"));
    public static final ResourceKey<PlacedFeature> BLUEBELL_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, NaturaConstants.id("bluebells"));

    public static final GrassSet TOPIARY = grass("topiary_grass", "Topiary Grass");
    public static final GrassSet BLUEGRASS = grass("bluegrass", "Bluegrass");
    public static final GrassSet AUTUMNAL = grass("autumnal_grass", "Autumnal Grass");
    public static final List<GrassSet> GRASSES = List.of(TOPIARY, BLUEGRASS, AUTUMNAL);

    public static final DeferredItem<Item> EMPTY_SEED_BAG = NRItems.ITEMS.register("empty_seed_bag", () -> new Item(new Item.Properties()));
    public static final DeferredItem<SeedBagItem> WHEAT_SEED_BAG = seedBag("wheat_seed_bag", () -> Blocks.WHEAT.defaultBlockState(), Blocks.FARMLAND);
    public static final DeferredItem<SeedBagItem> CARROT_SEED_BAG = seedBag("carrot_seed_bag", () -> Blocks.CARROTS.defaultBlockState(), Blocks.FARMLAND);
    public static final DeferredItem<SeedBagItem> POTATO_SEED_BAG = seedBag("potato_seed_bag", () -> Blocks.POTATOES.defaultBlockState(), Blocks.FARMLAND);
    public static final DeferredItem<SeedBagItem> BEETROOT_SEED_BAG = seedBag("beetroot_seed_bag", () -> Blocks.BEETROOTS.defaultBlockState(), Blocks.FARMLAND);
    public static final DeferredItem<SeedBagItem> NETHER_WART_SEED_BAG = seedBag("nether_wart_seed_bag", () -> Blocks.NETHER_WART.defaultBlockState(), Blocks.SOUL_SAND);
    public static final DeferredItem<SeedBagItem> BARLEY_SEED_BAG = seedBag("barley_seed_bag", () -> BARLEY_CROP.get().defaultBlockState(), Blocks.FARMLAND);
    public static final DeferredItem<SeedBagItem> COTTON_SEED_BAG = seedBag("cotton_seed_bag", () -> COTTON_CROP.get().defaultBlockState(), Blocks.FARMLAND);
    public static final DeferredItem<BoneMealBagItem> BONE_MEAL_BAG = NRItems.ITEMS.register("bone_meal_bag", () -> new BoneMealBagItem(new Item.Properties()));
    public static final DeferredItem<Item> BERRY_MEDLEY_ITEM = NRItems.ITEMS.register("berry_medley", () -> new Item(new Item.Properties().food(BERRY_MEDLEY).stacksTo(1)));

    private NRPlants() {}

    public static BushPlant bush(String blockName) {
        for (BushPlant plant : BUSHES) {
            if (plant.blockName().equals(blockName)) {
                return plant;
            }
        }
        throw new IllegalArgumentException(blockName);
    }

    private static FoodProperties berry(MobEffectInstance... effects) {
        FoodProperties.Builder builder = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F);
        for (MobEffectInstance effect : effects) {
            builder.effect(() -> new MobEffectInstance(effect), 1.0F);
        }
        return builder.build();
    }

    private static BushPlant bush(String blockName, String itemName, String englishBush, String englishBerries,
                                  boolean nether, String biomeTag, FoodProperties food) {
        DeferredBlock<BerryBushBlock> block = NRBlocks.BLOCKS.register(blockName,
                () -> new BerryBushBlock(nether, () -> bushItem(itemName), bushProperties()));
        DeferredItem<Item> berries = NRItems.ITEMS.register(itemName, () -> new Item(new Item.Properties().food(food)));
        DeferredItem<BlockItem> bushItem = NRItems.ITEMS.register(blockName, () -> new BlockItem(block.get(), new Item.Properties()));
        return new BushPlant(blockName, itemName, englishBush, englishBerries, nether, biomeTag, block, berries, bushItem,
                ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturaConstants.id(blockName + "_bush")),
                ResourceKey.create(Registries.PLACED_FEATURE, NaturaConstants.id(blockName + "_bush")));
    }

    private static Item bushItem(String itemName) {
        for (BushPlant plant : BUSHES) {
            if (plant.itemName().equals(itemName)) {
                return plant.berries().get();
            }
        }
        throw new IllegalStateException(itemName);
    }

    private static GrassSet grass(String name, String english) {
        DeferredBlock<ColoredGrassBlock> block = NRBlocks.BLOCKS.register(name, () -> new ColoredGrassBlock(grassProperties()));
        return new GrassSet(name, english, block, blockItem(name, block));
    }

    private static <B extends Block> DeferredItem<BlockItem> blockItem(String name, DeferredBlock<B> block) {
        return NRItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static DeferredItem<SeedBagItem> seedBag(String name, Supplier<net.minecraft.world.level.block.state.BlockState> plant, Block ground) {
        return NRItems.ITEMS.register(name, () -> new SeedBagItem(new Item.Properties(), plant, ground));
    }

    private static BlockBehaviour.Properties bushProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noOcclusion()
                .randomTicks()
                .instabreak()
                .sound(SoundType.SWEET_BERRY_BUSH)
                .pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties cropProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.CROP)
                .pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties grassProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.GRASS)
                .strength(0.6F)
                .sound(SoundType.GRASS);
    }

    public record BushPlant(
            String blockName,
            String itemName,
            String englishBush,
            String englishBerries,
            boolean nether,
            String biomeTag,
            DeferredBlock<BerryBushBlock> block,
            DeferredItem<Item> berries,
            DeferredItem<BlockItem> bushItem,
            ResourceKey<ConfiguredFeature<?, ?>> configured,
            ResourceKey<PlacedFeature> placed) {}

    public record GrassSet(
            String name,
            String english,
            DeferredBlock<ColoredGrassBlock> block,
            DeferredItem<BlockItem> item) {}
}

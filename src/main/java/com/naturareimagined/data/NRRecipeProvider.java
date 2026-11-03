package com.naturareimagined.data;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.config.ConfigEnabledCondition;
import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRWoods;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

public final class NRRecipeProvider extends RecipeProvider {

    public NRRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        for (WoodFamily family : NRWoods.ALL) {
            String name = family.name;
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, family.planks.get(), 4)
                    .requires(family.logItemTag)
                    .unlockedBy("has_log", has(family.logItemTag))
                    .save(output, name + "_planks");
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.wood.get(), 3)
                    .pattern("LL")
                    .pattern("LL")
                    .define('L', family.log.get())
                    .unlockedBy("has_log", has(family.log.get()))
                    .save(output);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.stairs.get(), 4)
                    .pattern("P  ")
                    .pattern("PP ")
                    .pattern("PPP")
                    .define('P', family.planks.get())
                    .unlockedBy("has_planks", has(family.planks.get()))
                    .save(output);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.slab.get(), 6)
                    .pattern("PPP")
                    .define('P', family.planks.get())
                    .unlockedBy("has_planks", has(family.planks.get()))
                    .save(output);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, family.fence.get(), 3)
                    .pattern("PSP")
                    .pattern("PSP")
                    .define('P', family.planks.get())
                    .define('S', Items.STICK)
                    .unlockedBy("has_planks", has(family.planks.get()))
                    .save(output);
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, family.fenceGate.get())
                    .pattern("SPS")
                    .pattern("SPS")
                    .define('P', family.planks.get())
                    .define('S', Items.STICK)
                    .unlockedBy("has_planks", has(family.planks.get()))
                    .save(output);
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, family.door.get(), 3)
                    .pattern("PP")
                    .pattern("PP")
                    .pattern("PP")
                    .define('P', family.planks.get())
                    .unlockedBy("has_planks", has(family.planks.get()))
                    .save(output);
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, family.trapdoor.get(), 2)
                    .pattern("PPP")
                    .pattern("PPP")
                    .define('P', family.planks.get())
                    .unlockedBy("has_planks", has(family.planks.get()))
                    .save(output);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, family.button.get())
                    .requires(family.planks.get())
                    .unlockedBy("has_planks", has(family.planks.get()))
                    .save(output);
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, family.pressurePlate.get())
                    .pattern("PP")
                    .define('P', family.planks.get())
                    .unlockedBy("has_planks", has(family.planks.get()))
                    .save(output);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, family.sign.get(), 3)
                    .pattern("PPP")
                    .pattern("PPP")
                    .pattern(" S ")
                    .define('P', family.planks.get())
                    .define('S', Items.STICK)
                    .unlockedBy("has_planks", has(family.planks.get()))
                    .save(output);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, family.hangingSign.get(), 6)
                    .pattern("C C")
                    .pattern("LLL")
                    .pattern("LLL")
                    .define('C', Items.CHAIN)
                    .define('L', family.strippedLog.get())
                    .unlockedBy("has_stripped_log", has(family.strippedLog.get()))
                    .save(output);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.bookshelf.get())
                    .pattern("PPP")
                    .pattern("BBB")
                    .pattern("PPP")
                    .define('P', family.planks.get())
                    .define('B', Items.BOOK)
                    .unlockedBy("has_book", has(Items.BOOK))
                    .save(output);
            if (family.boat != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, family.boat.get())
                        .pattern("# #")
                        .pattern("###")
                        .define('#', family.planks.get())
                        .unlockedBy("has_planks", has(family.planks.get()))
                        .save(output);
                ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, family.chestBoat.get())
                        .requires(family.boat.get())
                        .requires(Items.CHEST)
                        .unlockedBy("has_boat", has(family.boat.get()))
                        .save(output);
            }
        }
        plants(output);
    }

    private void plants(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, NRPlants.EMPTY_SEED_BAG.get())
                .pattern("S S")
                .pattern("SPS")
                .pattern("S S")
                .define('S', Items.STRING)
                .define('P', Items.PAPER)
                .unlockedBy("has_string", has(Items.STRING))
                .save(output);
        seedBag(output, NRPlants.WHEAT_SEED_BAG.get(), Items.WHEAT_SEEDS);
        seedBag(output, NRPlants.CARROT_SEED_BAG.get(), Items.CARROT);
        seedBag(output, NRPlants.POTATO_SEED_BAG.get(), Items.POTATO);
        seedBag(output, NRPlants.BEETROOT_SEED_BAG.get(), Items.BEETROOT_SEEDS);
        seedBag(output, NRPlants.NETHER_WART_SEED_BAG.get(), Items.NETHER_WART);
        seedBag(output, NRPlants.BARLEY_SEED_BAG.get(), NRPlants.BARLEY_SEEDS.get());
        seedBag(output, NRPlants.COTTON_SEED_BAG.get(), NRPlants.COTTON_SEEDS.get());
        seedBag(output, NRPlants.BONE_MEAL_BAG.get(), Items.BONE_MEAL);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NRPlants.BARLEY_FLOUR.get())
                .requires(NRPlants.BARLEY.get())
                .unlockedBy("has_barley", has(NRPlants.BARLEY.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NRPlants.WHEAT_FLOUR.get())
                .requires(Items.WHEAT)
                .unlockedBy("has_wheat", has(Items.WHEAT))
                .save(output.withConditions(new ConfigEnabledCondition("enableWheatFlourRecipe")), NaturaConstants.id("wheat_flour"));
        smeltFlour(output, NRPlants.BARLEY_FLOUR.get(), "barley_flour_to_bread");
        smeltFlour(output, NRPlants.WHEAT_FLOUR.get(), "wheat_flour_to_bread");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING)
                .pattern("CCC")
                .define('C', NRPlants.COTTON.get())
                .unlockedBy("has_cotton", has(NRPlants.COTTON.get()))
                .save(output, NaturaConstants.id("cotton_to_string"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLUE_DYE)
                .requires(NRPlants.BLUEBELLS_ITEM.get())
                .unlockedBy("has_bluebells", has(NRPlants.BLUEBELLS_ITEM.get()))
                .save(output, NaturaConstants.id("blue_dye_from_bluebells"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NRPlants.BERRY_MEDLEY_ITEM.get())
                .requires(Items.BOWL)
                .requires(NRPlants.bush("raspberry").berries().get())
                .requires(NRPlants.bush("blueberry").berries().get())
                .requires(NRPlants.bush("blackberry").berries().get())
                .requires(NRPlants.bush("maloberry").berries().get())
                .unlockedBy("has_berries", has(NRPlants.bush("raspberry").berries().get()))
                .save(output);
        for (NRPlants.GrassSet grass : NRPlants.GRASSES) {
            if (grass == NRPlants.TOPIARY) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, grass.block().get())
                        .pattern("SSS")
                        .pattern("SDS")
                        .pattern("SSS")
                        .define('S', Items.WHEAT_SEEDS)
                        .define('D', Items.DIRT)
                        .unlockedBy("has_seeds", has(Items.WHEAT_SEEDS))
                        .save(output);
            }
        }
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, NRPlants.BLUEGRASS.block().get())
                .requires(NRPlants.TOPIARY.block().get())
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("has_topiary", has(NRPlants.TOPIARY.block().get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, NRPlants.AUTUMNAL.block().get())
                .requires(NRPlants.TOPIARY.block().get())
                .requires(Tags.Items.DYES_RED)
                .unlockedBy("has_topiary", has(NRPlants.TOPIARY.block().get()))
                .save(output);
        contentRecipes(output);
    }

    private void contentRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NRContent.NETHERRACK_FURNACE.get())
                .pattern("NNN").pattern("NFN").pattern("NNN")
                .define('N', Items.NETHERRACK).define('F', Items.FURNACE)
                .unlockedBy("has_furnace", has(Items.FURNACE)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, NRContent.BLAZE_HOPPER.get())
                .pattern("B B").pattern("BCB").pattern(" B ")
                .define('B', Items.BLAZE_ROD).define('C', Items.HOPPER)
                .unlockedBy("has_blaze", has(Items.BLAZE_ROD)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.LEATHER)
                .requires(NRContent.IMP_LEATHER.get(), 2)
                .unlockedBy("has_imp_leather", has(NRContent.IMP_LEATHER.get()))
                .save(output, NaturaConstants.id("leather_from_imp_leather"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, NRContent.BLAZE_RAIL.get(), 16)
                .pattern("B B").pattern("BSB").pattern("B B")
                .define('B', Items.BLAZE_ROD).define('S', Items.STICK)
                .unlockedBy("has_blaze", has(Items.BLAZE_ROD)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, NRContent.BLAZE_POWERED_RAIL.get(), 6)
                .pattern("R R").pattern("RGR").pattern("R R")
                .define('R', NRContent.BLAZE_RAIL.get()).define('G', Items.GOLD_INGOT)
                .unlockedBy("has_rail", has(NRContent.BLAZE_RAIL.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, NRContent.BLAZE_DETECTOR_RAIL.get(), 6)
                .pattern("R R").pattern("RPR").pattern("R R")
                .define('R', NRContent.BLAZE_RAIL.get()).define('P', Items.STONE_PRESSURE_PLATE)
                .unlockedBy("has_rail", has(NRContent.BLAZE_RAIL.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, NRContent.BLAZE_ACTIVATOR_RAIL.get(), 6)
                .pattern("RSR").pattern("RTR").pattern("RSR")
                .define('R', NRContent.BLAZE_RAIL.get()).define('S', Items.REDSTONE).define('T', Items.REDSTONE_TORCH)
                .unlockedBy("has_rail", has(NRContent.BLAZE_RAIL.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, NRContent.NETHER_LEVER.get())
                .pattern("S").pattern("N")
                .define('S', Items.STICK).define('N', Items.NETHERRACK)
                .unlockedBy("has_netherrack", has(Items.NETHERRACK)).save(output);
        tool(output, NRContent.QUARTZ_SWORD.get(), Items.QUARTZ, "Q", "Q", "S");
        tool(output, NRContent.QUARTZ_PICKAXE.get(), Items.QUARTZ, "QQQ", " S ", " S ");
        tool(output, NRContent.QUARTZ_AXE.get(), Items.QUARTZ, "QQ", "QS", " S");
        tool(output, NRContent.QUARTZ_SHOVEL.get(), Items.QUARTZ, "Q", "S", "S");
        tool(output, NRContent.QUARTZ_HOE.get(), Items.QUARTZ, "QQ", " S", " S");
        hatchet(output, NRContent.WOODEN_HATCHET.get(), ItemTags.PLANKS);
        hatchet(output, NRContent.STONE_HATCHET.get(), Items.COBBLESTONE);
        hatchet(output, NRContent.IRON_HATCHET.get(), Items.IRON_INGOT);
        hatchet(output, NRContent.GOLDEN_HATCHET.get(), Items.GOLD_INGOT);
        hatchet(output, NRContent.DIAMOND_HATCHET.get(), Items.DIAMOND);
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(NRContent.DIAMOND_HATCHET.get()),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        RecipeCategory.TOOLS,
                        NRContent.NETHERITE_HATCHET.get())
                .unlocks("has_diamond_hatchet", has(NRContent.DIAMOND_HATCHET.get()))
                .save(output, NaturaConstants.id("netherite_hatchet"));
        hatchet(output, NRContent.QUARTZ_HATCHET.get(), Items.QUARTZ);
        armor(output, NRContent.IMP_HELMET.get(), "LLL", "L L");
        armor(output, NRContent.IMP_CHESTPLATE.get(), "L L", "LLL", "LLL");
        armor(output, NRContent.IMP_LEGGINGS.get(), "LLL", "L L", "L L");
        armor(output, NRContent.IMP_BOOTS.get(), "L L", "L L");
        stew(output, NRContent.GREEN_GLOWSHROOM_STEW.get(), NRContent.GREEN_GLOWSHROOM.get(), "green_glowshroom_stew");
        stew(output, NRContent.BLUE_GLOWSHROOM_STEW.get(), NRContent.BLUE_GLOWSHROOM.get(), "blue_glowshroom_stew");
        stew(output, NRContent.PURPLE_GLOWSHROOM_STEW.get(), NRContent.PURPLE_GLOWSHROOM.get(), "purple_glowshroom_stew");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NRContent.CACTUS_JUICE.get())
                .requires(Items.CACTUS).requires(Items.BOWL)
                .unlockedBy("has_cactus", has(Items.CACTUS)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NRContent.SAGUARO_FRUIT.get())
                .requires(NRContent.SAGUARO_ITEM.get()).requires(Items.BOWL)
                .unlockedBy("has_saguaro", has(NRContent.SAGUARO_ITEM.get())).save(output, NaturaConstants.id("saguaro_fruit"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NRContent.POTASH_APPLE.get())
                .requires(Items.APPLE).requires(NRContent.SULFUR_POWDER.get())
                .unlockedBy("has_sulfur", has(NRContent.SULFUR_POWDER.get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, NRContent.FLINT_AND_BLAZE.get())
                .requires(Items.FLINT).requires(Items.BLAZE_POWDER)
                .unlockedBy("has_blaze", has(Items.BLAZE_POWDER)).save(output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NRContent.RAW_IMP_MEAT.get()), RecipeCategory.FOOD, NRContent.COOKED_IMP_MEAT.get(), 0.35F, 200)
                .unlockedBy("has_raw", has(NRContent.RAW_IMP_MEAT.get())).save(output, NaturaConstants.id("cooked_impmeat"));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(NRContent.RAW_IMP_MEAT.get()), RecipeCategory.FOOD, NRContent.COOKED_IMP_MEAT.get(), 0.35F, 100)
                .unlockedBy("has_raw", has(NRContent.RAW_IMP_MEAT.get())).save(output, NaturaConstants.id("cooked_impmeat_smoking"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(NRContent.RAW_IMP_MEAT.get()), RecipeCategory.FOOD, NRContent.COOKED_IMP_MEAT.get(), 0.35F, 600)
                .unlockedBy("has_raw", has(NRContent.RAW_IMP_MEAT.get())).save(output, NaturaConstants.id("cooked_impmeat_campfire"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.SOUL_SAND, Items.SOUL_SOIL), RecipeCategory.BUILDING_BLOCKS, NRContent.SOUL_GLASS.get(), 0.1F, 200)
                .unlockedBy("has_soul_sand", has(Items.SOUL_SAND)).save(output, NaturaConstants.id("soul_glass"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NRContent.HEAT_SAND.get()), RecipeCategory.BUILDING_BLOCKS, NRContent.HEAT_GLASS.get(), 0.1F, 200)
                .unlockedBy("has_heat_sand", has(NRContent.HEAT_SAND.get())).save(output, NaturaConstants.id("heat_glass"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NRContent.SULFUR_CLOUD.get()), RecipeCategory.MISC, NRContent.SULFUR_POWDER.get(), 0.1F, 200)
                .unlockedBy("has_sulfur_cloud", has(NRContent.SULFUR_CLOUD.get())).save(output, NaturaConstants.id("sulfur_powder_from_cloud"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GUNPOWDER)
                .requires(NRContent.SULFUR_POWDER.get(), 4)
                .unlockedBy("has_sulfur", has(NRContent.SULFUR_POWDER.get())).save(output, NaturaConstants.id("gunpowder_from_sulfur"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NRContent.SAGUARO_FRUIT.get()), RecipeCategory.MISC, Items.GREEN_DYE, 0.1F, 200)
                .unlockedBy("has_fruit", has(NRContent.SAGUARO_FRUIT.get())).save(output, NaturaConstants.id("green_dye_from_saguaro"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CHARCOAL)
                .requires(NRContent.ASH_CLOUD.get(), 9)
                .unlockedBy("has_ash", has(NRContent.ASH_CLOUD.get())).save(output, NaturaConstants.id("charcoal_from_ash"));
    }

    private void stew(RecipeOutput output, ItemLike result, ItemLike glowshroom, String id) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, result)
                .requires(glowshroom, 2).requires(Items.BOWL)
                .unlockedBy("has_glowshroom", has(glowshroom)).save(output, NaturaConstants.id(id));
    }

    private void tool(RecipeOutput output, ItemLike result, ItemLike material, String top, String mid, String bot) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern(top).pattern(mid).pattern(bot)
                .define('Q', material).define('S', Items.STICK)
                .unlockedBy("has_material", has(material)).save(output);
    }

    private void hatchet(RecipeOutput output, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("M ").pattern("SM")
                .define('M', material).define('S', Items.STICK)
                .unlockedBy("has_material", has(material)).save(output);
    }

    private void hatchet(RecipeOutput output, ItemLike result, net.minecraft.tags.TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("M ").pattern("SM")
                .define('M', material).define('S', Items.STICK)
                .unlockedBy("has_material", has(material)).save(output);
    }

    private void armor(RecipeOutput output, ItemLike result, String... rows) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result);
        for (String row : rows) {
            builder.pattern(row);
        }
        builder.define('L', NRContent.IMP_LEATHER.get())
                .unlockedBy("has_leather", has(NRContent.IMP_LEATHER.get())).save(output);
    }

    private void seedBag(RecipeOutput output, Item bag, ItemLike contents) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, bag)
                .pattern("###")
                .pattern("#E#")
                .pattern("###")
                .define('#', contents)
                .define('E', NRPlants.EMPTY_SEED_BAG.get())
                .unlockedBy("has_contents", has(contents))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, contents, 8)
                .requires(bag)
                .unlockedBy("has_bag", has(bag))
                .save(output, NaturaConstants.id(bag.builtInRegistryHolder().key().location().getPath() + "_unpack"));
    }

    private void smeltFlour(RecipeOutput output, ItemLike flour, String name) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(flour), RecipeCategory.FOOD, Items.BREAD, 0.35F, 200)
                .unlockedBy("has_flour", has(flour))
                .save(output, NaturaConstants.id(name));
    }
}

package com.naturareimagined.compat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.entity.CinderlinEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.alchemy.PotionContents;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public final class CinderlinBarterOffers {

    public record Offer(ItemStack stack, int weight, int minCount, int maxCount) {}

    private CinderlinBarterOffers() {}

    public static List<Offer> read() {
        JsonObject root = load(null);
        return root == null ? List.of() : parse(root);
    }

    public static List<ItemStack> inputs() {
        return inputs((ResourceManager) null);
    }

    public static List<ItemStack> inputs(ResourceManager manager) {
        JsonObject root = load(manager);
        List<ItemStack> items = new ArrayList<>();
        if (root != null && root.has("inputs")) {
            for (JsonElement element : root.getAsJsonArray("inputs")) {
                ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(element.getAsString())));
                if (!stack.is(Items.AIR)) {
                    items.add(stack);
                }
            }
        }
        if (items.isEmpty()) {
            items.add(new ItemStack(Items.GOLD_INGOT));
        }
        return List.copyOf(items);
    }

    public static ItemStack currency() {
        return inputs().getFirst();
    }

    public static boolean accepts(ItemStack stack, List<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            if (stack.is(input.getItem())) {
                return true;
            }
        }
        return false;
    }

    public static int totalWeight(List<Offer> offers) {
        int total = 0;
        for (Offer offer : offers) {
            total += offer.weight();
        }
        return Math.max(1, total);
    }

    public static String chance(Offer offer, int total) {
        double value = offer.weight() * 100.0 / total;
        if (value >= 10.0 || Math.abs(value - Math.rint(value)) < 0.05) {
            return String.format(Locale.ROOT, "%.0f", value);
        }
        return String.format(Locale.ROOT, "%.1f", value);
    }

    private static JsonObject load(ResourceManager manager) {
        try (Reader reader = open(manager)) {
            return reader == null ? null : JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception exception) {
            return null;
        }
    }

    private static Reader open(ResourceManager manager) throws Exception {
        ResourceLocation file = ResourceLocation.fromNamespaceAndPath(NaturaConstants.MOD_ID, "loot_table/" + CinderlinEntity.BARTER.location().getPath() + ".json");
        boolean classpathFallback = false;
        if (manager == null) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.getSingleplayerServer() != null) {
                manager = minecraft.getSingleplayerServer().getResourceManager();
            } else {
                manager = minecraft.getResourceManager();
                classpathFallback = true;
            }
        }
        Optional<Resource> resource = manager.getResource(file);
        if (resource.isPresent()) {
            return resource.get().openAsReader();
        }
        if (!classpathFallback) {
            return null;
        }
        InputStream stream = CinderlinBarterOffers.class.getResourceAsStream("/data/" + NaturaConstants.MOD_ID + "/loot_table/gameplay/cinderlin_bartering.json");
        return stream == null ? null : new InputStreamReader(stream, StandardCharsets.UTF_8);
    }

    private static List<Offer> parse(JsonObject root) {
        List<Offer> offers = new ArrayList<>();
        if (!root.has("pools")) {
            return offers;
        }
        for (JsonElement poolElement : root.getAsJsonArray("pools")) {
            JsonObject pool = poolElement.getAsJsonObject();
            if (!pool.has("entries")) {
                continue;
            }
            for (JsonElement entryElement : pool.getAsJsonArray("entries")) {
                Offer offer = entry(entryElement.getAsJsonObject());
                if (offer != null) {
                    offers.add(offer);
                }
            }
        }
        return List.copyOf(offers);
    }

    private static Offer entry(JsonObject entry) {
        if (!entry.has("type") || !entry.get("type").getAsString().equals("minecraft:item") || !entry.has("name")) {
            return null;
        }
        ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(entry.get("name").getAsString())));
        if (stack.is(Items.AIR)) {
            return null;
        }
        int weight = entry.has("weight") ? entry.get("weight").getAsInt() : 1;
        int min = 1;
        int max = 1;
        if (entry.has("functions")) {
            for (JsonElement functionElement : entry.getAsJsonArray("functions")) {
                JsonObject function = functionElement.getAsJsonObject();
                String name = function.get("function").getAsString();
                if (name.equals("minecraft:set_count") && function.has("count")) {
                    JsonElement count = function.get("count");
                    if (count.isJsonObject()) {
                        min = count.getAsJsonObject().get("min").getAsInt();
                        max = count.getAsJsonObject().get("max").getAsInt();
                    } else {
                        min = count.getAsInt();
                        max = min;
                    }
                } else if (name.equals("minecraft:set_potion") && function.has("id")) {
                    BuiltInRegistries.POTION.getHolder(ResourceLocation.parse(function.get("id").getAsString()))
                            .ifPresent(potion -> stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion)));
                }
            }
        }
        stack.setCount(Math.max(1, max));
        return new Offer(stack, Math.max(1, weight), min, max);
    }
}

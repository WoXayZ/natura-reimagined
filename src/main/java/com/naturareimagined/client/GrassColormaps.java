package com.naturareimagined.client;

import com.naturareimagined.NaturaConstants;
import java.io.IOException;
import java.io.InputStream;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;

public final class GrassColormaps {

    public static final net.minecraft.world.level.ColorResolver BLUE_RESOLVER = (biome, x, z) ->
            climate(biome, 0.95F, 0.4F) || climate(biome, 0.25F, 0.65F) ? blue(biome) : biome.getGrassColor(x, z);
    public static final net.minecraft.world.level.ColorResolver ORANGE_RESOLVER = (biome, x, z) ->
            climate(biome, 0.6F, 0.7F) ? orange(biome) : biome.getGrassColor(x, z);
    public static final net.minecraft.world.level.ColorResolver TOPIARY_RESOLVER = (biome, x, z) ->
            climate(biome, 0.9F, 0.95F) ? topiary(biome) : biome.getGrassColor(x, z);

    public static final ResourceLocation BLUE = NaturaConstants.id("textures/colormap/blue_grass.png");
    public static final ResourceLocation ORANGE = NaturaConstants.id("textures/colormap/orange_grass.png");
    public static final ResourceLocation TOPIARY = NaturaConstants.id("textures/colormap/topiary_grass.png");
    private static int[] blue = fallback(0x3A9BB5);
    private static int[] orange = fallback(0xD46A1E);
    private static int[] topiary = fallback(0x3C7A45);

    private GrassColormaps() {}

    public static void load(ResourceManager manager) {
        blue = read(manager, BLUE, 0x3A9BB5);
        orange = read(manager, ORANGE, 0xD46A1E);
        topiary = read(manager, TOPIARY, 0x3C7A45);
    }

    public static int blue(Biome biome) {
        return sample(blue, biome);
    }

    public static int orange(Biome biome) {
        return sample(orange, biome);
    }

    public static int blueItem() {
        return sample(blue, 0.8, 0.4);
    }

    public static int orangeItem() {
        return sample(orange, 0.8, 0.4);
    }

    public static int topiary(Biome biome) {
        return sample(topiary, biome);
    }

    public static int topiaryItem() {
        return sample(topiary, 0.8, 0.4);
    }

    private static boolean climate(Biome biome, float temperature, float downfall) {
        return Math.abs(biome.getBaseTemperature() - temperature) < 0.01F
                && Math.abs(biome.getModifiedClimateSettings().downfall() - downfall) < 0.01F;
    }

    private static int sample(int[] map, Biome biome) {
        return sample(map, biome.getBaseTemperature(), biome.getModifiedClimateSettings().downfall());
    }

    private static int sample(int[] map, double temperature, double downfall) {
        temperature = Mth.clamp(temperature, 0.0, 1.0);
        downfall = Mth.clamp(downfall, 0.0, 1.0) * temperature;
        int x = (int) ((1.0 - temperature) * 255.0);
        int y = (int) ((1.0 - downfall) * 255.0);
        return map[y << 8 | x];
    }

    private static int[] read(ResourceManager manager, ResourceLocation location, int fallback) {
        try (InputStream stream = manager.open(location); NativeImage image = NativeImage.read(stream)) {
            int[] colors = new int[65536];
            for (int y = 0; y < 256; y++) {
                for (int x = 0; x < 256; x++) {
                    int abgr = image.getPixelRGBA(x, y);
                    int red = abgr & 255;
                    int green = (abgr >> 8) & 255;
                    int blue = (abgr >> 16) & 255;
                    colors[y << 8 | x] = (red << 16) | (green << 8) | blue;
                }
            }
            return colors;
        } catch (IOException exception) {
            return fallback(fallback);
        }
    }

    private static int[] fallback(int color) {
        int[] colors = new int[65536];
        java.util.Arrays.fill(colors, color);
        return colors;
    }
}

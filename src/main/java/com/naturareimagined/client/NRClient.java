package com.naturareimagined.client;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.wood.WoodFamily;
import com.naturareimagined.registry.NRContent;
import com.naturareimagined.registry.NRPlants;
import com.naturareimagined.registry.NRWoods;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = NaturaConstants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class NRClient {

    private NRClient() {}

    @SubscribeEvent
    public static void registerColorResolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(GrassColormaps.BLUE_RESOLVER);
        event.register(GrassColormaps.ORANGE_RESOLVER);
        event.register(GrassColormaps.TOPIARY_RESOLVER);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        for (WoodFamily family : NRWoods.ALL) {
            if (family.leavesColor == 0) {
                event.register((state, level, pos, tintIndex) -> level != null && pos != null
                        ? BiomeColors.getAverageFoliageColor(level, pos)
                        : FoliageColor.getDefaultColor(), family.leaves.get());
            } else {
                event.register((state, level, pos, tintIndex) -> family.leavesColor, family.leaves.get());
            }
        }
        event.register((state, level, pos, tintIndex) -> level != null && pos != null
                ? level.getBlockTint(pos, GrassColormaps.TOPIARY_RESOLVER)
                : GrassColormaps.topiaryItem(), NRPlants.TOPIARY.block().get());
        event.register((state, level, pos, tintIndex) -> level != null && pos != null
                ? level.getBlockTint(pos, GrassColormaps.BLUE_RESOLVER)
                : GrassColormaps.blueItem(), NRPlants.BLUEGRASS.block().get());
        event.register((state, level, pos, tintIndex) -> level != null && pos != null
                ? level.getBlockTint(pos, GrassColormaps.ORANGE_RESOLVER)
                : GrassColormaps.orangeItem(), NRPlants.AUTUMNAL.block().get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        for (WoodFamily family : NRWoods.ALL) {
            int color = family.leavesColor == 0 ? FoliageColor.getDefaultColor() : family.leavesColor;
            event.register((stack, tintIndex) -> color, family.leaves.get());
        }
        event.register((stack, tintIndex) -> GrassColormaps.topiaryItem(), NRPlants.TOPIARY.item().get());
        event.register((stack, tintIndex) -> GrassColormaps.blueItem(), NRPlants.BLUEGRASS.item().get());
        event.register((stack, tintIndex) -> GrassColormaps.orangeItem(), NRPlants.AUTUMNAL.item().get());
    }

    @SubscribeEvent
    public static void layers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(NREntityRenderers.IMP, ImpModel::createBodyLayer);
        event.registerLayerDefinition(NREntityRenderers.HEATSCAR, SpiderModel::createSpiderBodyLayer);
    }

    @SubscribeEvent
    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NRContent.IMP.get(), NREntityRenderers.Imp::new);
        event.registerEntityRenderer(NRContent.HEATSCAR.get(), context -> new NREntityRenderers.Heatscar<>(context));
        event.registerEntityRenderer(NRContent.BABY_HEATSCAR.get(), context -> new NREntityRenderers.Heatscar<>(context));
        event.registerEntityRenderer(NRContent.NITRO.get(), NREntityRenderers.Nitro::new);
    }

    @SubscribeEvent
    public static void reloadColormaps(RegisterClientReloadListenersEvent event) {
        for (WoodFamily family : NRWoods.ALL) {
            Sheets.SIGN_MATERIALS.put(family.woodType, new Material(Sheets.SIGN_SHEET,
                    ResourceLocation.fromNamespaceAndPath(NaturaConstants.MOD_ID, "entity/signs/" + family.name)));
            Sheets.HANGING_SIGN_MATERIALS.put(family.woodType, new Material(Sheets.SIGN_SHEET,
                    ResourceLocation.fromNamespaceAndPath(NaturaConstants.MOD_ID, "entity/signs/hanging/" + family.name)));
        }
        event.registerReloadListener(new SimplePreparableReloadListener<Void>() {
            @Override
            protected Void prepare(ResourceManager manager, ProfilerFiller profiler) {
                GrassColormaps.load(manager);
                return null;
            }

            @Override
            protected void apply(Void prepared, ResourceManager manager, ProfilerFiller profiler) {}
        });
    }
}

package com.naturareimagined.client;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.entity.BabyHeatscarSpiderEntity;
import com.naturareimagined.content.entity.HeatscarSpiderEntity;
import com.naturareimagined.content.entity.ImpEntity;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;

public final class NREntityRenderers {

    public static final ModelLayerLocation IMP = new ModelLayerLocation(NaturaConstants.id("imp"), "main");
    public static final ModelLayerLocation HEATSCAR = new ModelLayerLocation(NaturaConstants.id("heatscarspider"), "main");
    private NREntityRenderers() {}

    public static final class Imp extends MobRenderer<ImpEntity, ImpModel> {
        public Imp(EntityRendererProvider.Context context) {
            super(context, new ImpModel(context.bakeLayer(IMP)), 0.4F);
        }

        @Override
        public ResourceLocation getTextureLocation(ImpEntity entity) {
            return NaturaConstants.id("textures/entity/imp/imp.png");
        }
    }

    public static final class Heatscar<T extends HeatscarSpiderEntity> extends MobRenderer<T, SpiderModel<T>> {
        public Heatscar(EntityRendererProvider.Context context) {
            super(context, new SpiderModel<>(context.bakeLayer(HEATSCAR)), 0.8F);
        }

        @Override
        public ResourceLocation getTextureLocation(T entity) {
            return NaturaConstants.id("textures/entity/heatscarspider/heatscarspider.png");
        }

        @Override
        protected void scale(T entity, PoseStack pose, float partial) {
            float size = entity instanceof BabyHeatscarSpiderEntity ? 0.6F : 1.15F;
            pose.scale(size, size, size);
        }
    }

    public static final class Nitro extends CreeperRenderer {
        public Nitro(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public ResourceLocation getTextureLocation(Creeper entity) {
            return NaturaConstants.id("textures/entity/nitro_creeper/nitro_creeper.png");
        }
    }
}

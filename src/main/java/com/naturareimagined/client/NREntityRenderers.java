package com.naturareimagined.client;

import com.naturareimagined.NaturaConstants;
import com.naturareimagined.content.entity.AmaranthBeeEntity;
import com.naturareimagined.content.entity.AshenSkeletonEntity;
import com.naturareimagined.content.entity.BabyHeatscarSpiderEntity;
import com.naturareimagined.content.entity.CinderlinEntity;
import com.naturareimagined.content.entity.EmberBatEntity;
import com.naturareimagined.content.entity.GhostBatEntity;
import com.naturareimagined.content.entity.GlowshroomCowEntity;
import com.naturareimagined.content.entity.HeatscarSpiderEntity;
import com.naturareimagined.content.entity.HellhoundEntity;
import com.naturareimagined.content.entity.ImpEntity;
import com.naturareimagined.content.entity.SakuraFoxEntity;
import com.naturareimagined.content.entity.WillowFrogEntity;
import com.naturareimagined.registry.NRContent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BatModel;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.entity.BatRenderer;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.FrogRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.resources.ResourceLocation;

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

    public static final class SakuraFox extends FoxRenderer {
        public SakuraFox(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public ResourceLocation getTextureLocation(Fox fox) {
            return NaturaConstants.id(fox.isSleeping()
                    ? "textures/entity/sakura_fox/sakura_fox_sleep.png"
                    : "textures/entity/sakura_fox/sakura_fox.png");
        }
    }

    public static final class AmaranthBee extends BeeRenderer {
        private final net.minecraft.client.model.geom.ModelPart bone;
        private final net.minecraft.client.model.geom.ModelPart rightWing;
        private final net.minecraft.client.model.geom.ModelPart leftWing;

        public AmaranthBee(EntityRendererProvider.Context context) {
            super(context);
            this.bone = context.bakeLayer(ModelLayers.BEE).getChild("bone");
            this.rightWing = this.bone.getChild("right_wing");
            this.leftWing = this.bone.getChild("left_wing");
            this.addLayer(new Wings(this));
        }

        @Override
        public void render(Bee bee, float yaw, float partial, PoseStack pose, MultiBufferSource buffer, int light) {
            this.rightWing.visible = false;
            this.leftWing.visible = false;
            super.render(bee, yaw, partial, pose, buffer, light);
            this.rightWing.visible = true;
            this.leftWing.visible = true;
        }

        @Override
        public ResourceLocation getTextureLocation(Bee bee) {
            if (bee.hasStung()) {
                return NaturaConstants.id("textures/entity/amaranth_bee/amaranth_bee_stinger.png");
            }
            if (bee.isAngry()) {
                return NaturaConstants.id(bee.hasNectar()
                        ? "textures/entity/amaranth_bee/amaranth_bee_angry_nectar.png"
                        : "textures/entity/amaranth_bee/amaranth_bee_angry.png");
            }
            return NaturaConstants.id(bee.hasNectar()
                    ? "textures/entity/amaranth_bee/amaranth_bee_nectar.png"
                    : "textures/entity/amaranth_bee/amaranth_bee.png");
        }

        private final class Wings extends RenderLayer<Bee, net.minecraft.client.model.BeeModel<Bee>> {
            private Wings(AmaranthBee parent) {
                super(parent);
            }

            @Override
            public void render(PoseStack pose, MultiBufferSource buffer, int light, Bee bee, float limbSwing, float limbSwingAmount, float partial, float age, float yaw, float pitch) {
                if (bee.isInvisible() && !Minecraft.getInstance().shouldEntityAppearGlowing(bee)) {
                    return;
                }
                AmaranthBee.this.rightWing.visible = true;
                AmaranthBee.this.leftWing.visible = true;
                pose.pushPose();
                AmaranthBee.this.bone.translateAndRotate(pose);
                VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(AmaranthBee.this.getTextureLocation(bee)));
                int overlay = net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords(bee, 0.0F);
                int color = net.minecraft.util.FastColor.ARGB32.color(140, 255, 255, 255);
                AmaranthBee.this.rightWing.render(pose, consumer, light, overlay, color);
                AmaranthBee.this.leftWing.render(pose, consumer, light, overlay, color);
                pose.popPose();
                AmaranthBee.this.rightWing.visible = false;
                AmaranthBee.this.leftWing.visible = false;
            }
        }
    }

    public static final class WillowFrog extends FrogRenderer {
        public WillowFrog(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public ResourceLocation getTextureLocation(Frog frog) {
            return NaturaConstants.id("textures/entity/willow_frog/willow_frog.png");
        }
    }

    public static final class Ashen extends SkeletonRenderer<AshenSkeletonEntity> {
        public Ashen(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public ResourceLocation getTextureLocation(AshenSkeletonEntity entity) {
            return NaturaConstants.id("textures/entity/ashen_skeleton/ashen_skeleton.png");
        }
    }

    public static final class EmberBat extends BatRenderer {
        public EmberBat(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public ResourceLocation getTextureLocation(Bat entity) {
            return NaturaConstants.id("textures/entity/ember_bat.png");
        }
    }

    public static final class GhostBat extends BatRenderer {
        private static final ResourceLocation TEXTURE = NaturaConstants.id("textures/entity/ghost_bat.png");

        public GhostBat(EntityRendererProvider.Context context) {
            super(context);
            this.addLayer(new Eyes(this));
        }

        @Override
        public ResourceLocation getTextureLocation(Bat entity) {
            return TEXTURE;
        }

        @Override
        protected RenderType getRenderType(Bat entity, boolean visible, boolean translucent, boolean glowing) {
            if (translucent) {
                return RenderType.itemEntityTranslucentCull(TEXTURE);
            }
            if (glowing) {
                return RenderType.outline(TEXTURE);
            }
            return RenderType.entityTranslucent(TEXTURE);
        }

        private static final class Eyes extends RenderLayer<Bat, BatModel> {
            private static final RenderType EYES = RenderType.eyes(TEXTURE);

            private Eyes(GhostBat parent) {
                super(parent);
            }

            @Override
            public void render(PoseStack pose, MultiBufferSource buffer, int light, Bat entity, float limbSwing, float limbSwingAmount, float partial, float age, float yaw, float pitch) {
                VertexConsumer consumer = buffer.getBuffer(EYES);
                this.getParentModel().renderToBuffer(pose, consumer, 15728880, net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static EntityRenderer<EmberBatEntity> ember(EntityRendererProvider.Context context) {
        return (EntityRenderer<EmberBatEntity>) (EntityRenderer<?>) new EmberBat(context);
    }

    @SuppressWarnings("unchecked")
    public static EntityRenderer<GhostBatEntity> ghost(EntityRendererProvider.Context context) {
        return (EntityRenderer<GhostBatEntity>) (EntityRenderer<?>) new GhostBat(context);
    }

    public static final class GlowshroomCow extends CowRenderer {
        public GlowshroomCow(EntityRendererProvider.Context context) {
            super(context);
            this.addLayer(new Caps(this, context.getBlockRenderDispatcher()));
        }

        @Override
        public ResourceLocation getTextureLocation(Cow cow) {
            String color = cow instanceof GlowshroomCowEntity glow ? glow.color() : "green";
            return NaturaConstants.id("textures/entity/glowshroom_cow/" + color + "_glowshroom_cow.png");
        }

        private static final class Caps extends RenderLayer<Cow, CowModel<Cow>> {
            private final BlockRenderDispatcher blocks;

            private Caps(GlowshroomCow parent, BlockRenderDispatcher blocks) {
                super(parent);
                this.blocks = blocks;
            }

            @Override
            public void render(PoseStack pose, MultiBufferSource buffer, int light, Cow cow, float limbSwing, float limbSwingAmount, float partial, float age, float yaw, float pitch) {
                if (cow.isBaby() || !(cow instanceof GlowshroomCowEntity glow)) {
                    return;
                }
                boolean outline = Minecraft.getInstance().shouldEntityAppearGlowing(cow) && cow.isInvisible();
                if (cow.isInvisible() && !outline) {
                    return;
                }
                BlockState state = NRContent.glowshroom(glow.color()).defaultBlockState();
                BakedModel model = this.blocks.getBlockModel(state);
                int overlay = net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords(cow, 0.0F);
                pose.pushPose();
                pose.translate(0.2F, -0.35F, 0.5F);
                pose.mulPose(Axis.YP.rotationDegrees(-48.0F));
                pose.scale(-1.0F, -1.0F, 1.0F);
                pose.translate(-0.5F, -0.5F, -0.5F);
                this.block(pose, buffer, light, outline, state, overlay, model);
                pose.popPose();
                pose.pushPose();
                pose.translate(0.2F, -0.35F, 0.5F);
                pose.mulPose(Axis.YP.rotationDegrees(42.0F));
                pose.translate(0.1F, 0.0F, -0.6F);
                pose.mulPose(Axis.YP.rotationDegrees(-48.0F));
                pose.scale(-1.0F, -1.0F, 1.0F);
                pose.translate(-0.5F, -0.5F, -0.5F);
                this.block(pose, buffer, light, outline, state, overlay, model);
                pose.popPose();
                pose.pushPose();
                this.getParentModel().getHead().translateAndRotate(pose);
                pose.translate(0.0F, -0.7F, -0.2F);
                pose.mulPose(Axis.YP.rotationDegrees(-78.0F));
                pose.scale(-1.0F, -1.0F, 1.0F);
                pose.translate(-0.5F, -0.5F, -0.5F);
                this.block(pose, buffer, light, outline, state, overlay, model);
                pose.popPose();
            }

            private void block(PoseStack pose, MultiBufferSource buffer, int light, boolean outline, BlockState state, int overlay, BakedModel model) {
                if (outline) {
                    this.blocks.getModelRenderer().renderModel(pose.last(), buffer.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), state, model, 0.0F, 0.0F, 0.0F, light, overlay);
                } else {
                    this.blocks.renderSingleBlock(state, pose, buffer, light, overlay);
                }
            }
        }
    }

    public static final class Cinderlin extends PiglinRenderer {
        public Cinderlin(EntityRendererProvider.Context context) {
            super(context, ModelLayers.PIGLIN, ModelLayers.PIGLIN_INNER_ARMOR, ModelLayers.PIGLIN_OUTER_ARMOR, false);
        }

        @Override
        public ResourceLocation getTextureLocation(Mob entity) {
            return NaturaConstants.id("textures/entity/cinderlin/cinderlin.png");
        }
    }

    public static final class Hellhound extends WolfRenderer {
        public Hellhound(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public ResourceLocation getTextureLocation(Wolf wolf) {
            if (wolf.isTame()) {
                return NaturaConstants.id("textures/entity/hellhound/hellhound_tame.png");
            }
            if (wolf.isAngry()) {
                return NaturaConstants.id("textures/entity/hellhound/hellhound_angry.png");
            }
            return NaturaConstants.id("textures/entity/hellhound/hellhound.png");
        }
    }
}

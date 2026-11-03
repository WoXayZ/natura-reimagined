package com.naturareimagined.client;

import com.naturareimagined.content.entity.ImpEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Stocky imp fitted to the 64×32 texture: wide head, short body, short legs, thin arms.
 * The body box is pitched so its long face stands upright.
 */
public class ImpModel extends HierarchicalModel<ImpEntity> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public ImpModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-5.0F, 0.0F, -18.5F, 10.0F, 5.0F, 9.0F), PartPose.ZERO);
        parts.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.5F, -4.0F, 10.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 8.0F, 2.0F));
        parts.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -1.999F, 3.999F, 6.0F, 3.999F), PartPose.offset(3.0F, 18.0F, 2.0F));
        parts.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.999F, 0.0F, -1.999F, 3.999F, 6.0F, 3.999F), PartPose.offset(-3.0F, 18.0F, 2.0F));
        parts.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-1.5F, 0.0F, 1.0F, 2.0F, 5.0F, 2.0F), PartPose.offset(6.0F, 10.0F, 0.0F));
        parts.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-0.5F, 0.0F, 1.0F, 2.0F, 5.0F, 2.0F), PartPose.offset(-6.0F, 10.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(ImpEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.body.xRot = Mth.HALF_PI;
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        float swing = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftArm.xRot = swing;
        this.rightArm.xRot = -swing;
        this.leftLeg.xRot = -swing;
        this.rightLeg.xRot = swing;
    }
}

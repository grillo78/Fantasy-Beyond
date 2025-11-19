package grillo78.fantasy_beyond.client.entity.race.orc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class FemaleOrcModel<T extends Entity> extends CustomizationModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart head;
    private final ModelPart left_arm;
    private final ModelPart right_arm;

    public FemaleOrcModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.right_leg = this.root.getChild("right_leg");
        this.left_leg = this.root.getChild("left_leg");
        this.head = this.root.getChild("head");
        this.left_arm = this.root.getChild("left_arm");
        this.right_arm = this.root.getChild("right_arm");
    }

    @Override
    public void copyFrom(CustomizationModel model) {
        root.copyFrom(((FemaleOrcModel) model).root);
        body.copyFrom(((FemaleOrcModel) model).body);
        right_leg.copyFrom(((FemaleOrcModel) model).right_leg);
        left_leg.copyFrom(((FemaleOrcModel) model).left_leg);
        head.copyFrom(((FemaleOrcModel) model).head);
        left_arm.copyFrom(((FemaleOrcModel) model).left_arm);
        right_arm.copyFrom(((FemaleOrcModel) model).right_arm);
    }

    @Override
    public ModelPart getRoot() {
        return root;
    }

    @Override
    public ModelPart getArmPart(HumanoidArm arm) {
        return arm == HumanoidArm.RIGHT ? right_arm : left_arm;
    }

    public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, -1.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 30).addBox(-5.0F, 7.0F, -1.5F, 10.0F, 10.0F, 4.0F, cubeDeformation.extend(0.1F))
                .texOffs(20, 55).addBox(-5.0F, 7.0F, -1.5F, 10.0F, 10.0F, 4.0F, cubeDeformation.extend(0.35F))
                .texOffs(20, 19).addBox(-5.0F, 0.0F, -1.5F, 10.0F, 7.0F, 4.0F, cubeDeformation)
                .texOffs(20, 44).addBox(-5.0F, 0.0F, -1.5F, 10.0F, 7.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(92, 10).addBox(0.0F, 0.0F, 0.0F, 5.0F, 6.0F, 3.0F, cubeDeformation.extend(0.25F))
                .texOffs(76, 10).addBox(0.0F, 0.0F, 0.0F, 5.0F, 6.0F, 3.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, 1.0F, -1.5F, -0.436F, -0.0184F, -0.0395F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(92, 1).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 6.0F, 3.0F, cubeDeformation.extend(0.25F))
                .texOffs(76, 1).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 6.0F, 3.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, 1.0F, -1.5F, -0.436F, 0.0184F, 0.0395F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 19).addBox(-3.0F, 0.0F, -2.0F, 5.0F, 17.0F, 5.0F, cubeDeformation)
                .texOffs(0, 42).addBox(-3.0F, 0.0F, -2.0F, 5.0F, 17.0F, 5.0F, cubeDeformation.extend(0.25F)), PartPose.offset(-2.0F, 16.0F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(20, 69).addBox(-2.0F, 0.0F, -2.0F, 5.0F, 17.0F, 5.0F, cubeDeformation)
                .texOffs(0, 69).addBox(-2.0F, 0.0F, -2.0F, 5.0F, 17.0F, 5.0F, cubeDeformation.extend(0.25F)), PartPose.offset(2.0F, 16.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -4.0F, 10.0F, 10.0F, 9.0F, cubeDeformation)
                .texOffs(38, 0).addBox(-5.0F, -10.0F, -4.0F, 10.0F, 10.0F, 9.0F, cubeDeformation.extend(0.5F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(66, 24).addBox(-7.0F, -3.0F, 0.0F, 7.0F, 5.0F, 0.0F, cubeDeformation), PartPose.offsetAndRotation(-5.0F, -5.0F, 0.0F, 0.0F, 0.2182F, 0.0F));

        PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(66, 29).addBox(0.0F, -3.0F, 0.0F, 7.0F, 5.0F, 0.0F, cubeDeformation), PartPose.offsetAndRotation(5.0F, -5.0F, 0.0F, 0.0F, -0.2182F, 0.0F));

        PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(48, 19).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 18.0F, 5.0F, cubeDeformation)
                .texOffs(48, 42).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 18.0F, 5.0F, cubeDeformation.extend(0.25F)), PartPose.offset(-6.0F, 1.0F, 0.0F));

        PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 69).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 18.0F, 5.0F, cubeDeformation)
                .texOffs(58, 69).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 18.0F, 5.0F, cubeDeformation.extend(0.25F)), PartPose.offset(6.0F, 1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public void setupModel(HumanoidModel bipedModel) {
        copyFrom(head, bipedModel.head, true);
        copyFrom(body, bipedModel.body, true);
        copyFrom(left_arm, bipedModel.leftArm, true);
        copyFrom(right_arm, bipedModel.rightArm, true);
        copyFrom(left_leg, bipedModel.leftLeg, true);
        copyFrom(right_leg, bipedModel.rightLeg, true);
        left_leg.z = left_leg.z * 1.75F;
        right_leg.z = right_leg.z * 1.75F;
    }

    @Override
    public void setModelProperties(LivingEntity pLivingEntity) {
        if (pLivingEntity.isSpectator()) {
            root.getAllParts().forEach(modelPart -> modelPart.visible = false);
            head.visible = true;
        } else {
            root.getAllParts().forEach(modelPart -> modelPart.visible = true);
        }
        super.setModelProperties(pLivingEntity);
    }

    @Override
    public ModelPart getHeadPart() {
        return head;
    }
}
package grillo78.fantasy_beyond.client.entity.race.tiefling;

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

public class FemaleTieflingModel<T extends Entity> extends CustomizationModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart right_leg;
	private final ModelPart left_leg;
	private final ModelPart head;
	private final ModelPart left_arm;
	private final ModelPart right_arm;

	public FemaleTieflingModel(ModelPart root) {
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
		root.copyFrom(((FemaleTieflingModel) model).root);
		body.copyFrom(((FemaleTieflingModel) model).body);
		right_leg.copyFrom(((FemaleTieflingModel) model).right_leg);
		left_leg.copyFrom(((FemaleTieflingModel) model).left_leg);
		head.copyFrom(((FemaleTieflingModel) model).head);
		left_arm.copyFrom(((FemaleTieflingModel) model).left_arm);
		right_arm.copyFrom(((FemaleTieflingModel) model).right_arm);
	}

    @Override
    public ModelPart getArmPart(HumanoidArm arm) {
        return arm == HumanoidArm.RIGHT? right_arm : left_arm;
    }


    @Override
    public ModelPart getRoot() {
        return root;
    }

	public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 24).addBox(-4.0F, 5.0F, -1.5F, 8.0F, 7.0F, 3.0F, cubeDeformation.extend(0.1F))
		.texOffs(16, 42).addBox(-4.0F, 5.0F, -1.5F, 8.0F, 7.0F, 3.0F, cubeDeformation.extend(0.35F))
		.texOffs(16, 16).addBox(-4.0F, 0.0F, -1.5F, 8.0F, 5.0F, 3.0F, cubeDeformation)
		.texOffs(16, 34).addBox(-4.0F, 0.0F, -1.5F, 8.0F, 5.0F, 3.0F, cubeDeformation.extend(0.25F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(78, 9).addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 3.0F, cubeDeformation.extend(0.25F))
		.texOffs(64, 9).addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 3.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, 1.0F, -1.5F, -0.436F, -0.0184F, -0.0395F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(78, 2).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 4.0F, 3.0F, cubeDeformation.extend(0.25F))
		.texOffs(64, 2).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 4.0F, 3.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, 1.0F, -1.5F, -0.436F, 0.0184F, 0.0395F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, cubeDeformation)
		.texOffs(0, 34).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(-2.0F, 11.0F, 0.0F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 52).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, cubeDeformation)
		.texOffs(0, 52).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(2.0F, 11.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation)
		.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation.extend(0.5F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(52, 25).addBox(-4.0F, -3.0F, 0.0F, 4.0F, 5.0F, 0.0F, cubeDeformation), PartPose.offsetAndRotation(-4.0F, -3.0F, 0.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(52, 20).addBox(0.0F, -3.0F, 0.0F, 4.0F, 5.0F, 0.0F, cubeDeformation), PartPose.offsetAndRotation(4.0F, -3.0F, 0.0F, 0.0F, -0.8727F, 0.0F));

		PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 53).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 13.0F, 4.0F, cubeDeformation)
		.texOffs(46, 53).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 13.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(5.0F, 1.0F, 0.0F));

		PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(38, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 13.0F, 4.0F, cubeDeformation)
		.texOffs(38, 33).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 13.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(-5.0F, 1.0F, 0.0F));

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
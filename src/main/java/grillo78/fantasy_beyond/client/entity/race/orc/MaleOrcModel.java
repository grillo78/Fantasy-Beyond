package grillo78.fantasy_beyond.client.entity.race.orc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;


public class MaleOrcModel<T extends Entity> extends CustomizationModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart right_leg;
	private final ModelPart left_leg;
	private final ModelPart head;
	private final ModelPart left_arm;
	private final ModelPart right_arm;

	public MaleOrcModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.right_leg = this.root.getChild("right_leg");
		this.left_leg = this.root.getChild("left_leg");
		this.head = this.root.getChild("head");
		this.left_arm = this.root.getChild("left_arm");
		this.right_arm = this.root.getChild("right_arm");
	}

	@Override
	public ModelPart getArmPart(HumanoidArm arm) {
		return arm == HumanoidArm.RIGHT? right_arm : left_arm;
	}

	public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, -2.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 45).addBox(-5.0F, 0.0F, -2.0F, 10.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(20, 19).addBox(-5.0F, 0.0F, -2.0F, 10.0F, 10.0F, 5.0F, new CubeDeformation(0.25F))
				.texOffs(20, 34).addBox(-4.5F, 10.0F, -1.5F, 9.0F, 7.0F, 4.0F, new CubeDeformation(0.1F))
				.texOffs(20, 60).addBox(-4.5F, 10.0F, -1.5F, 9.0F, 7.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, -26.0F, 1.0F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 19).addBox(-3.0F, 0.0F, -2.0F, 5.0F, 18.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 42).addBox(-3.0F, 0.0F, -2.0F, 5.0F, 18.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, -9.0F, 1.0F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 71).addBox(-2.0F, 0.0F, -2.0F, 5.0F, 18.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(20, 71).addBox(-2.0F, 0.0F, -2.0F, 5.0F, 18.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, -9.0F, 1.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -4.0F, 10.0F, 10.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(38, 0).addBox(-5.0F, -10.0F, -4.0F, 10.0F, 10.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -26.0F, 1.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(70, 29).addBox(-7.0F, -3.0F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -5.0F, 0.0F, 0.0F, 0.2182F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(70, 24).addBox(0.0F, -3.0F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -5.0F, 0.0F, 0.0F, -0.2182F, 0.0F));

		PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(50, 19).addBox(-4.0F, -2.0F, -2.0F, 5.0F, 18.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(50, 42).addBox(-4.0F, -2.0F, -2.0F, 5.0F, 18.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(-6.0F, -24.0F, 1.0F));

		PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 71).addBox(-1.0F, -2.0F, -2.0F, 5.0F, 18.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(60, 71).addBox(-1.0F, -2.0F, -2.0F, 5.0F, 18.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(6.0F, -24.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupModel(PlayerModel bipedModel) {
		copyFrom(head, bipedModel.head, true);
		copyFrom(body, bipedModel.body, true);
		copyFrom(left_arm, bipedModel.leftArm, true);
		copyFrom(right_arm, bipedModel.rightArm, true);
		copyFrom(left_leg, bipedModel.leftLeg, true);
		copyFrom(right_leg, bipedModel.rightLeg, true);
	}

	@Override
	public void setModelProperties(Player pLivingEntity) {
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
package grillo78.fantasy_beyond.client.entity.race.human;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class FemaleHumanModel<T extends Entity> extends CustomizationModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart right_arm;
	private final ModelPart left_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public FemaleHumanModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.head = this.root.getChild("head");
		this.right_arm = this.root.getChild("right_arm");
		this.left_arm = this.root.getChild("left_arm");
		this.left_leg = this.root.getChild("left_leg");
		this.right_leg = this.root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -1.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 24).addBox(-3.5F, 5.0F, -1.5F, 7.0F, 7.0F, 3.0F, cubeDeformation.extend(0.1F))
				.texOffs(16, 42).addBox(-3.5F, 5.0F, -1.5F, 7.0F, 7.0F, 3.0F, cubeDeformation.extend(0.35F))
				.texOffs(16, 16).addBox(-4.0F, 0.0F, -1.5F, 8.0F, 5.0F, 3.0F, cubeDeformation)
				.texOffs(16, 34).addBox(-4.0F, 0.0F, -1.5F, 8.0F, 5.0F, 3.0F, cubeDeformation.extend(0.25F)), PartPose.offset(0.0F, -24.0F, 1.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(78, 9).addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 3.0F, cubeDeformation.extend(0.25F))
				.texOffs(64, 9).addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 3.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, 1.0F, -1.5F, -0.436F, -0.0184F, -0.0395F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(78, 2).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 4.0F, 3.0F, cubeDeformation.extend(0.25F))
				.texOffs(64, 2).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 4.0F, 3.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, 1.0F, -1.5F, -0.436F, 0.0184F, 0.0395F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation)
				.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(-2.0F, -12.0F, 1.0F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 52).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation)
				.texOffs(0, 52).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(2.0F, -12.0F, 1.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation)
				.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation.extend(0.5F)), PartPose.offset(0.0F, -24.0F, 1.0F));

		PartDefinition right_arm2 = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(38, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cubeDeformation)
				.texOffs(38, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(-5.0F, -22.0F, 1.0F));

		PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 52).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cubeDeformation)
				.texOffs(46, 52).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cubeDeformation.extend  (0.25F)), PartPose.offset(5.0F, -22.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setModelProperties(Player pClientPlayer) {
		if (pClientPlayer.isSpectator()) {
			root.getAllParts().forEach(modelPart -> modelPart.visible = false);
			head.visible = true;
		} else {
			root.getAllParts().forEach(modelPart -> modelPart.visible = true);
		}
		super.setModelProperties(pClientPlayer);
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
	public ModelPart getHeadPart() {
		return head;
	}
}
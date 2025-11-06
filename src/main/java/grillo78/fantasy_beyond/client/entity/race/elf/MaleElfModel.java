package grillo78.fantasy_beyond.client.entity.race.elf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import grillo78.fantasy_beyond.client.entity.race.human.FemaleHumanModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class MaleElfModel<T extends Entity> extends CustomizationModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart head;
	private final ModelPart right_arm;
	private final ModelPart left_arm;

	public MaleElfModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.left_leg = this.root.getChild("left_leg");
		this.right_leg = this.root.getChild("right_leg");
		this.head = this.root.getChild("head");
		this.right_arm = this.root.getChild("right_arm");
		this.left_arm = this.root.getChild("left_arm");
	}

	@Override
	public void copyFrom(CustomizationModel model) {
		root.copyFrom(((MaleElfModel) model).root);
		body.copyFrom(((MaleElfModel) model).body);
		right_leg.copyFrom(((MaleElfModel) model).right_leg);
		left_leg.copyFrom(((MaleElfModel) model).left_leg);
		head.copyFrom(((MaleElfModel) model).head);
		left_arm.copyFrom(((MaleElfModel) model).left_arm);
		right_arm.copyFrom(((MaleElfModel) model).right_arm);
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

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 7.0F, 4.0F, cubeDeformation)
				.texOffs(16, 35).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 7.0F, 4.0F, cubeDeformation.extend(0.25F))
				.texOffs(16, 27).addBox(-3.5F, 7.0F, -1.5F, 7.0F, 5.0F, 3.0F, cubeDeformation.extend(0.1F))
				.texOffs(16, 46).addBox(-3.5F, 7.0F, -1.5F, 7.0F, 5.0F, 3.0F, cubeDeformation.extend(0.35F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 54).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, cubeDeformation)
				.texOffs(0, 54).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(2.0F, 11.0F, 0.0F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 34).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, cubeDeformation.extend(0.25F))
				.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, cubeDeformation), PartPose.offset(-2.0F, 11.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation)
				.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation.extend(0.5F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(56, 26).addBox(-4.0F, -3.0F, 0.0F, 4.0F, 5.0F, 0.0F, cubeDeformation), PartPose.offsetAndRotation(-4.0F, -3.0F, 0.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(56, 20).addBox(0.0F, -3.0F, 0.0F, 4.0F, 5.0F, 0.0F, cubeDeformation), PartPose.offsetAndRotation(4.0F, -3.0F, 0.0F, 0.0F, -0.8727F, 0.0F));

		PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 13.0F, 4.0F, cubeDeformation)
				.texOffs(40, 33).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 13.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(-5.0F, 1.0F, 0.0F));

		PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 54).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 13.0F, 4.0F, cubeDeformation)
				.texOffs(48, 54).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 13.0F, 4.0F, cubeDeformation.extend(0.25F)), PartPose.offset(5.0F, 1.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	public void setModelProperties(LivingEntity pClientPlayer) {
		if (pClientPlayer.isSpectator()) {
			root.getAllParts().forEach(modelPart -> modelPart.visible = false);
			head.visible = true;
		} else {
			root.getAllParts().forEach(modelPart -> modelPart.visible = true);
		}
		super.setModelProperties(pClientPlayer);
	}

	@Override
	public ModelPart getHeadPart() {
		return head;
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
}
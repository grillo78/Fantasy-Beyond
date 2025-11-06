package grillo78.fantasy_beyond.client.entity.race.tiefling.horns;

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

public class TallHornsModel<T extends Entity> extends CustomizationModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart right_leg;
	private final ModelPart left_leg;
	private final ModelPart head;
	private final ModelPart root2;
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart left_arm;
	private final ModelPart right_arm;

	public TallHornsModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.right_leg = this.root.getChild("right_leg");
		this.left_leg = this.root.getChild("left_leg");
		this.head = this.root.getChild("head");
		this.root2 = this.head.getChild("root2");
		this.bone = this.root2.getChild("bone");
		this.bone2 = this.root2.getChild("bone2");
		this.left_arm = this.root.getChild("left_arm");
		this.right_arm = this.root.getChild("right_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-2.0F, 11.0F, 0.0F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(2.0F, 11.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition root2 = head.addOrReplaceChild("root2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone = root2.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(124, 13).addBox(0.0F, -3.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.75F, -13.0F, -4.25F, -0.2237F, 0.0978F, -0.469F));

		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(120, 7).addBox(0.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -10.0F, -4.0F, 0.3149F, -0.0088F, -0.1361F));

		PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0F, -3.0F, -1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -7.0F, -4.0F, 0.1554F, -0.0992F, 0.3806F));

		PartDefinition bone2 = root2.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r4 = bone2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(120, 13).mirror().addBox(-1.0F, -3.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.75F, -13.0F, -4.25F, -0.2237F, -0.0978F, 0.469F));

		PartDefinition cube_r5 = bone2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(104, 0).mirror().addBox(-2.0F, -3.0F, -1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -7.0F, -4.0F, 0.1554F, 0.0992F, -0.3806F));

		PartDefinition cube_r6 = bone2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(112, 7).mirror().addBox(-2.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, -10.0F, -4.0F, 0.3149F, 0.0088F, 0.1361F));

		PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 1.0F, 0.0F));

		PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 1.0F, 0.0F));

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
    public ModelPart getArmPart(HumanoidArm arm) {
        return arm == HumanoidArm.RIGHT? right_arm : left_arm;
    }


    @Override
    public ModelPart getRoot() {
        return root;
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
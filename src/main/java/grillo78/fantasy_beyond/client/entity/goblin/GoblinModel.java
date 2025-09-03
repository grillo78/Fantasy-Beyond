package grillo78.fantasy_beyond.client.entity.goblin;
// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class GoblinModel<T extends Entity> extends EntityModel<T> {
	private final ModelPart armorHead;
	private final ModelPart bone;
	private final ModelPart bone5;
	private final ModelPart bone6;
	private final ModelPart armorBody;
	private final ModelPart bone2;
	private final ModelPart armorRightArm;
	private final ModelPart armorLeftArm;
	private final ModelPart armorRightLeg;
	private final ModelPart bone3;
	private final ModelPart armorLeftLeg;
	private final ModelPart bone4;

	public GoblinModel(ModelPart root) {
		this.armorHead = root.getChild("armorHead");
		this.bone = this.armorHead.getChild("bone");
		this.bone5 = this.armorHead.getChild("bone5");
		this.bone6 = this.armorHead.getChild("bone6");
		this.armorBody = root.getChild("armorBody");
		this.bone2 = this.armorBody.getChild("bone2");
		this.armorRightArm = root.getChild("armorRightArm");
		this.armorLeftArm = root.getChild("armorLeftArm");
		this.armorRightLeg = root.getChild("armorRightLeg");
		this.bone3 = this.armorRightLeg.getChild("bone3");
		this.armorLeftLeg = root.getChild("armorLeftLeg");
		this.bone4 = this.armorLeftLeg.getChild("bone4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition armorHead = partdefinition.addOrReplaceChild("armorHead", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 6.7913F, 0.4573F, 0.2182F, 0.0F, 0.0F));

		PartDefinition Head_r1 = armorHead.addOrReplaceChild("Head_r1", CubeListBuilder.create().texOffs(52, 38).addBox(-1.0F, -3.0F, -3.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F))
				.texOffs(24, 51).addBox(-1.0F, -3.0F, -3.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -1.6378F, -3.1665F, 0.1309F, 0.0F, 0.0F));

		PartDefinition Head_r2 = armorHead.addOrReplaceChild("Head_r2", CubeListBuilder.create().texOffs(0, 22).addBox(-3.0F, -3.0F, -2.5F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -3.1378F, -1.8665F, -0.0873F, 0.0F, 0.0F));

		PartDefinition Head_r3 = armorHead.addOrReplaceChild("Head_r3", CubeListBuilder.create().texOffs(0, 11).addBox(-3.0F, -3.0F, -2.5F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.25F))
				.texOffs(0, 0).addBox(-3.0F, -3.0F, -2.5F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.3378F, -2.0665F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone = armorHead.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(22, 16).addBox(-3.5F, -0.6957F, -2.1679F, 7.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(22, 23).addBox(-3.5F, -0.6957F, -2.1679F, 7.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -1.492F, -2.9986F));

		PartDefinition Head_r4 = bone.addOrReplaceChild("Head_r4", CubeListBuilder.create().texOffs(20, 38).addBox(-3.5F, -1.0F, -2.5F, 7.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, -0.6957F, 0.3321F, 0.0873F, 0.0F, 0.0F));

		PartDefinition bone5 = armorHead.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(-4.1644F, -4.7535F, 1.016F));

		PartDefinition Head_r5 = bone5.addOrReplaceChild("Head_r5", CubeListBuilder.create().texOffs(0, 41).addBox(3.0F, 1.0F, -2.5F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4356F, -1.9843F, -3.0825F, 0.4044F, -0.735F, -0.0871F));

		PartDefinition bone6 = armorHead.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(4.1644F, -4.7535F, 1.016F));

		PartDefinition Head_r6 = bone6.addOrReplaceChild("Head_r6", CubeListBuilder.create().texOffs(40, 38).addBox(-3.0F, 1.0F, -2.5F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4356F, -1.9843F, -3.0825F, 0.4044F, 0.735F, 0.0871F));

		PartDefinition armorBody = partdefinition.addOrReplaceChild("armorBody", CubeListBuilder.create().texOffs(22, 30).addBox(-3.5F, 1.5F, -1.5F, 7.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 33).addBox(-3.5F, 1.5F, -1.5F, 7.0F, 5.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 9.0F, 0.0F));

		PartDefinition bone2 = armorBody.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, -0.25F, -0.2F));

		PartDefinition Body_r1 = bone2.addOrReplaceChild("Body_r1", CubeListBuilder.create().texOffs(22, 0).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(22, 8).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition bone7 = bone2.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition armorRightArm = partdefinition.addOrReplaceChild("armorRightArm", CubeListBuilder.create().texOffs(12, 43).addBox(-2.5F, -2.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.25F))
				.texOffs(42, 30).addBox(-2.5F, -2.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 8.5F, -0.3F, 0.0875F, -0.0915F, 0.0445F));

		PartDefinition RightArm_r1 = armorRightArm.addOrReplaceChild("RightArm_r1", CubeListBuilder.create().texOffs(48, 47).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F))
				.texOffs(0, 50).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.45F)), PartPose.offsetAndRotation(-1.0F, 4.0F, -0.1F, -0.1309F, 0.0F, 0.0F));

		PartDefinition armorLeftArm = partdefinition.addOrReplaceChild("armorLeftArm", CubeListBuilder.create().texOffs(24, 43).addBox(-0.5F, -2.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(46, 0).addBox(-0.5F, -2.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(4.5F, 8.5F, -0.3F, 0.0875F, 0.0915F, -0.0445F));

		PartDefinition LeftArm_r1 = armorLeftArm.addOrReplaceChild("LeftArm_r1", CubeListBuilder.create().texOffs(16, 51).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.45F))
				.texOffs(8, 51).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(1.0F, 4.0F, -0.1F, -0.1309F, 0.0F, 0.0F));

		PartDefinition armorRightLeg = partdefinition.addOrReplaceChild("armorRightLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0283F, 15.5091F, -0.3233F, 0.0F, 0.0873F, 0.0F));

		PartDefinition RightLeg_r1 = armorRightLeg.addOrReplaceChild("RightLeg_r1", CubeListBuilder.create().texOffs(46, 15).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.25F))
				.texOffs(46, 8).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition bone3 = armorRightLeg.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 5.8413F, -0.2169F));

		PartDefinition RightLeg_r2 = bone3.addOrReplaceChild("RightLeg_r2", CubeListBuilder.create().texOffs(50, 55).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.35F))
				.texOffs(54, 29).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.4995F, 0.0218F, 0.0436F, 0.0F, 0.0F));

		PartDefinition armorLeftLeg = partdefinition.addOrReplaceChild("armorLeftLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0283F, 15.5091F, -0.3233F, 0.0F, -0.0873F, 0.0F));

		PartDefinition LeftLeg_r1 = armorLeftLeg.addOrReplaceChild("LeftLeg_r1", CubeListBuilder.create().texOffs(36, 47).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.25F))
				.texOffs(46, 22).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition bone4 = armorLeftLeg.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, 5.8413F, -0.2169F));

		PartDefinition LeftLeg_r2 = bone4.addOrReplaceChild("LeftLeg_r2", CubeListBuilder.create().texOffs(42, 55).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.35F))
				.texOffs(34, 54).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.4995F, 0.0218F, 0.0436F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.armorHead.yRot = (float) Math.toRadians(netHeadYaw);
		this.armorHead.xRot = (float) Math.toRadians(headPitch+12.5);
		this.armorHead.zRot = 0;

		this.armorRightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.armorLeftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.armorRightArm.zRot = 0.0F;
		this.armorLeftArm.zRot = 0.0F;
		this.armorRightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.armorLeftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.armorRightLeg.yRot = 0.005F;
		this.armorLeftLeg.yRot = -0.005F;
		this.armorRightLeg.zRot = 0.005F;
		this.armorLeftLeg.zRot = -0.005F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		armorHead.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorRightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorLeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorRightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorLeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
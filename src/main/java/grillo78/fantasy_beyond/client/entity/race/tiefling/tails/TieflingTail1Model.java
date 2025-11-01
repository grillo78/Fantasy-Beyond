package grillo78.fantasy_beyond.client.entity.race.tiefling.tails;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class TieflingTail1Model<T extends Entity> extends CustomizationModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone3;
    private final ModelPart bone4;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart head;
    private final ModelPart left_arm;
    private final ModelPart right_arm;


    public TieflingTail1Model(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.bone = this.body.getChild("bone");
        this.bone2 = this.bone.getChild("bone2");
        this.bone3 = this.bone2.getChild("bone3");
        this.bone4 = this.bone3.getChild("bone4");
        this.right_leg = this.root.getChild("right_leg");
        this.left_leg = this.root.getChild("left_leg");
        this.head = this.root.getChild("head");
        this.left_arm = this.root.getChild("left_arm");
        this.right_arm = this.root.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition bone = body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(110, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 1.75F));

        PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(108, 9).addBox(-1.5F, -1.25F, 0.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 5.0F));

        PartDefinition bone3 = bone2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(110, 19).addBox(-1.0F, -1.25F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, 7.0F));

        PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(112, 28).addBox(-0.5F, -0.5F, 0.25F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 6.75F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-2.0F, 11.0F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(2.0F, 11.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

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
    public void setupModel(HumanoidModel bipedModel, Player player, float partialTick) {
        super.setupModel(bipedModel, player, partialTick);
        bone.setRotation((float) Math.toRadians((player.isVisuallySwimming()?-60:0)+Math.cos(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 5) * 7 - 7), (float) Math.toRadians(Math.sin(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 2) * 3), (float) Math.toRadians(Math.sin(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 5) * 20));
        bone2.setRotation((float) Math.toRadians(Math.cos(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 5) * 7 - 7), (float) Math.toRadians(Math.sin(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 2) * 3), (float) Math.toRadians(Math.sin(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 5) * 20));
        bone3.setRotation((float) Math.toRadians(Math.cos(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 5) * 7 - 7), (float) Math.toRadians(Math.sin(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 2) * 3), (float) Math.toRadians(Math.sin(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 5) * 20));
        bone4.setRotation((float) Math.toRadians(Math.cos(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 5) * 7 - 7), (float) Math.toRadians(Math.sin(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 2) * 3), (float) Math.toRadians(Math.sin(-Mth.lerp(partialTick, player.tickCount - 1, player.tickCount) / 5) * 20));
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
    public ModelPart getHeadPart() {
        return head;
    }
}

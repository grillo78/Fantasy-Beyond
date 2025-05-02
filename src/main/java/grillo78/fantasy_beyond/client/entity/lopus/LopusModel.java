package grillo78.fantasy_beyond.client.entity.lopus;// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.entities.Lopus;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class LopusModel<T extends Lopus> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart head1;
    private final ModelPart head2;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart topBody;
    private final ModelPart rightArm;
    private final ModelPart rightForeArm;
    private final ModelPart leftArm;
    private final ModelPart leftForeArm;
    private final ModelPart rightLeg;
    private final ModelPart rightForeLeg;
    private final ModelPart leftLeg;
    private final ModelPart leftForeLeg;

    public LopusModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head1 = this.root.getChild("head1");
        this.head2 = this.root.getChild("head2");
        this.body = this.root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.topBody = this.body.getChild("topBody");
        this.rightArm = this.topBody.getChild("rightArm");
        this.rightForeArm = this.rightArm.getChild("rightForeArm");
        this.leftArm = this.topBody.getChild("leftArm");
        this.leftForeArm = this.leftArm.getChild("leftForeArm");
        this.rightLeg = this.root.getChild("rightLeg");
        this.rightForeLeg = this.rightLeg.getChild("rightForeLeg");
        this.leftLeg = this.root.getChild("leftLeg");
        this.leftForeLeg = this.leftLeg.getChild("leftForeLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head1 = root.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(0, 78).addBox(-20.0F, -18.0F, -15.0F, 20.0F, 18.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 133).addBox(-15.0F, -10.0F, -26.0F, 10.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(136, 87).addBox(-14.5F, -4.0F, -25.0F, 9.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(80, 68).addBox(-5.0F, -22.0F, -10.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(92, 32).addBox(-19.0F, -22.0F, -10.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8F, -87.4F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition head2 = root.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(0, 78).mirror().addBox(0.0F, -18.0F, -15.0F, 20.0F, 18.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 133).mirror().addBox(5.0F, -10.0F, -26.0F, 10.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(136, 87).mirror().addBox(5.5F, -4.0F, -25.0F, 9.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(80, 68).mirror().addBox(1.0F, -22.0F, -10.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(92, 32).mirror().addBox(15.0F, -22.0F, -10.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.8F, -87.4F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 38).addBox(-13.0F, -1.0F, -7.0F, 26.0F, 26.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -64.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(70, 78).addBox(-2.0F, -1.0F, -2.4F, 4.0F, 3.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 23.5F, 7.5F, -1.0036F, 0.0F, 0.0F));

        PartDefinition topBody = body.addOrReplaceChild("topBody", CubeListBuilder.create(), PartPose.offset(-13.0F, -1.0F, 7.0F));

        PartDefinition cube_r1 = topBody.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -22.0F, -16.0F, 30.0F, 22.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition rightArm = topBody.addOrReplaceChild("rightArm", CubeListBuilder.create(), PartPose.offset(-2.0F, -12.0F, -13.0F));

        PartDefinition cube_r2 = rightArm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(110, 110).addBox(-9.0F, -2.0F, -5.0F, 10.0F, 21.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition rightForeArm = rightArm.addOrReplaceChild("rightForeArm", CubeListBuilder.create(), PartPose.offset(-14.6109F, 16.0232F, 5.0F));

        PartDefinition cube_r3 = rightForeArm.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(70, 110).addBox(0.0F, 0.0F, -10.0F, 10.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1342F, -0.0316F, 0.247F));

        PartDefinition leftArm = topBody.addOrReplaceChild("leftArm", CubeListBuilder.create(), PartPose.offset(28.0F, -12.0F, -13.0F));

        PartDefinition cube_r4 = leftArm.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(110, 110).mirror().addBox(-1.0F, -2.0F, -5.0F, 10.0F, 21.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition leftForeArm = leftArm.addOrReplaceChild("leftForeArm", CubeListBuilder.create(), PartPose.offset(14.6109F, 16.0232F, 5.0F));

        PartDefinition cube_r5 = leftForeArm.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(70, 110).mirror().addBox(-10.0F, 0.0F, -10.0F, 10.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1342F, 0.0316F, -0.247F));

        PartDefinition rightLeg = root.addOrReplaceChild("rightLeg", CubeListBuilder.create(), PartPose.offset(-13.0F, -43.9315F, -4.9541F));

        PartDefinition cube_r6 = rightLeg.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(92, 0).addBox(0.0F, 0.0F, -12.0F, 12.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.1042F, 11.2763F, -0.3491F, 0.0F, 0.0436F));

        PartDefinition rightForeLeg = rightLeg.addOrReplaceChild("rightForeLeg", CubeListBuilder.create().texOffs(0, 111).addBox(-0.0633F, 17.5494F, -9.9664F, 8.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0599F, 21.5531F, 0.6771F));

        PartDefinition cube_r7 = rightForeLeg.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(136, 68).addBox(2.0F, 30.0F, 13.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0599F, -21.5531F, -0.6771F, -0.3491F, 0.0F, 0.0436F));

        PartDefinition cube_r8 = rightForeLeg.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(80, 38).addBox(0.0F, 0.0F, -7.0F, 10.0F, 10.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0436F));

        PartDefinition leftLeg = root.addOrReplaceChild("leftLeg", CubeListBuilder.create(), PartPose.offset(13.0F, -43.9315F, -4.9541F));

        PartDefinition cube_r9 = leftLeg.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(92, 0).mirror().addBox(-12.0F, 0.0F, -12.0F, 12.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.1042F, 11.2763F, -0.3491F, 0.0F, -0.0436F));

        PartDefinition leftForeLeg = leftLeg.addOrReplaceChild("leftForeLeg", CubeListBuilder.create().texOffs(0, 111).mirror().addBox(-7.9218F, 17.8911F, -9.0267F, 8.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.0748F, 21.2114F, -0.2626F));

        PartDefinition cube_r10 = leftForeLeg.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(136, 68).mirror().addBox(-10.0F, 30.0F, 13.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0748F, -21.2114F, 0.2626F, -0.3491F, 0.0F, -0.0436F));

        PartDefinition cube_r11 = leftForeLeg.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(80, 38).mirror().addBox(-10.0F, 0.0F, -6.0F, 10.0F, 10.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, -0.0436F));

        return LayerDefinition.create(meshdefinition, 176, 176);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entity.tickCount >300 && false){
            root().getAllParts().forEach(part->part.resetPose());
            this.head1.yRot = (float) Math.toRadians(netHeadYaw);
            this.head1.xRot = (float) Math.toRadians(headPitch + 12.5);

            this.head2.yRot = (float) Math.toRadians(netHeadYaw);
            this.head2.xRot = (float) Math.toRadians(headPitch + 12.5);

            this.rightArm.xRot = Mth.cos(limbSwing / 2 * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
            this.leftArm.xRot = Mth.cos(limbSwing / 2 * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
            this.rightForeArm.xRot = Mth.cos(limbSwing / 2 * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
            this.leftForeArm.xRot = Mth.cos(limbSwing / 2 * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            float speed = (float) entity.getDeltaMovement().horizontalDistance();
            this.rightLeg.xRot = Mth.cos(limbSwing * 0.062F) * speed;
            this.leftLeg.xRot = Mth.cos(limbSwing * 0.062F + (float) Math.PI) * speed;
            this.rightForeLeg.xRot = Mth.cos(limbSwing / 2 * 0.6662F) * speed;
            this.leftForeLeg.xRot = Mth.cos(limbSwing / 2 * 0.6662F + (float) Math.PI) * speed;
            this.rightLeg.yRot = 0.005F;
            this.leftLeg.yRot = -0.005F;
            this.rightLeg.zRot = 0.005F;
            this.leftLeg.zRot = -0.005F;
        } else{
            root().getAllParts().forEach(part->part.resetPose());
            animate(entity.getSpawnAnimationState(),LopusAnimations.SPAWN, ageInTicks);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
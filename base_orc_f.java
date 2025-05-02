// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class base_orc_f<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "base_orc_f"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart right_leg;
	private final ModelPart left_leg;
	private final ModelPart head;
	private final ModelPart right_arm;
	private final ModelPart left_arm;

	public base_orc_f(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.right_leg = this.root.getChild("right_leg");
		this.left_leg = this.root.getChild("left_leg");
		this.head = this.root.getChild("head");
		this.right_arm = this.root.getChild("right_arm");
		this.left_arm = this.root.getChild("left_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, -1.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 30).addBox(-5.0F, 7.0F, -1.5F, 10.0F, 10.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(20, 55).addBox(-5.0F, 7.0F, -1.5F, 10.0F, 10.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(20, 19).addBox(-5.0F, 0.0F, -1.5F, 10.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(20, 44).addBox(-5.0F, 0.0F, -1.5F, 10.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(92, 10).addBox(0.0F, 0.0F, 0.0F, 5.0F, 6.0F, 3.0F, new CubeDeformation(0.25F))
		.texOffs(76, 10).addBox(0.0F, 0.0F, 0.0F, 5.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -1.5F, -0.436F, -0.0184F, -0.0395F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(92, 1).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 6.0F, 3.0F, new CubeDeformation(0.25F))
		.texOffs(76, 1).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -1.5F, -0.436F, 0.0184F, 0.0395F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 19).addBox(-3.0F, 0.0F, -2.0F, 5.0F, 17.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 42).addBox(-3.0F, 0.0F, -2.0F, 5.0F, 17.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 16.0F, 0.0F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(20, 69).addBox(-2.0F, 0.0F, -2.0F, 5.0F, 17.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 69).addBox(-2.0F, 0.0F, -2.0F, 5.0F, 17.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, 16.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -4.0F, 10.0F, 10.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(38, 0).addBox(-5.0F, -10.0F, -4.0F, 10.0F, 10.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(66, 24).addBox(-7.0F, -3.0F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -5.0F, 0.0F, 0.0F, 0.2182F, 0.0F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(66, 29).addBox(0.0F, -3.0F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -5.0F, 0.0F, 0.0F, -0.2182F, 0.0F));

		PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(48, 19).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 18.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(48, 42).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 18.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(-6.0F, 1.0F, 0.0F));

		PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 69).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 18.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(58, 69).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 18.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(6.0F, 1.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
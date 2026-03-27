package grillo78.fantasy_beyond.client.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.block_entities.ForgeBlockEntity;
import grillo78.fantasy_beyond.client.FantasyBeyondClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class ForgeBlockEntityRenderer implements BlockEntityRenderer<ForgeBlockEntity> {

    private static final ResourceLocation COAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/block/forge_coal.png");
    private static final ResourceLocation LIT_COAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/block/forge_coal_lit.png");

    public ForgeBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ForgeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(!blockEntity.isLit()?COAL_TEXTURE:LIT_COAL_TEXTURE));
        Matrix4f matrix4f = poseStack.last().pose();
        float fuelTime = blockEntity.getFuelTime();
        float minYOffset = 2 / 16F;
        float maxYOffset = 6 / 16F;
        float coalPercentage = fuelTime / ForgeBlockEntity.MAX_FUEL_TIME;
        float yOffset = coalPercentage * (maxYOffset - minYOffset) + minYOffset;
        if (fuelTime > 0) {
            vertexConsumer.addVertex(matrix4f, 0, yOffset, 0).setUv(0, 0).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 0, yOffset, 1).setUv(0, 1).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 1, yOffset, 1).setUv(1, 1).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 1, yOffset, 0).setUv(1, 0).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);

            vertexConsumer.addVertex(matrix4f, 0.001f, minYOffset, 0).setUv(0, 0).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 0.001f, minYOffset, 1).setUv(0, 1).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 0.001f, yOffset, 1).setUv(coalPercentage/4, 1).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 0.001f, yOffset, 0).setUv(coalPercentage/4, 0).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);

            vertexConsumer.addVertex(matrix4f, 0.999f, minYOffset, 0).setUv(0, 0).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 0.999f, yOffset, 0).setUv(coalPercentage/4, 0).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 0.999f, yOffset, 1).setUv(coalPercentage/4, 1).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 0.999f, minYOffset, 1).setUv(0, 1).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);

            vertexConsumer.addVertex(matrix4f, 0, minYOffset, 0.999f).setUv(0, 0).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 1, minYOffset, 0.999f).setUv(0, 1).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 1, yOffset, 0.999f).setUv(coalPercentage/4, 1).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 0, yOffset, 0.999f).setUv(coalPercentage/4, 0).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);

            vertexConsumer.addVertex(matrix4f, 0, minYOffset, 0.001f).setUv(0, 0).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 0, yOffset, 0.001f).setUv(coalPercentage/4, 0).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 1, yOffset, 0.001f).setUv(coalPercentage/4, 1).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);
            vertexConsumer.addVertex(matrix4f, 1, minYOffset, 0.001f).setUv(0, 1).setColor(255, 255, 255, 255).setNormal(poseStack.last(), 0, 1, 0).setLight(packedLight).setOverlay(OverlayTexture.NO_OVERLAY);

        }
        poseStack.pushPose();
        poseStack.translate(0.5,yOffset + 0.5/16,0.5);
        poseStack.mulPose(new Quaternionf().rotateX((float) Math.toRadians(-90)));
        poseStack.scale(0.999F,0.999F,0.999F);
        Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.getHeatingItem(), ItemDisplayContext.NONE,packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();
    }
}

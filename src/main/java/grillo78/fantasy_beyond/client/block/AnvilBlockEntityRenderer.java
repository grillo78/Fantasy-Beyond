package grillo78.fantasy_beyond.client.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.block_entities.AnvilBlockEntity;
import grillo78.fantasy_beyond.block_entities.ForgeBlockEntity;
import grillo78.fantasy_beyond.blocks.AnvilBlock;
import grillo78.fantasy_beyond.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class AnvilBlockEntityRenderer implements BlockEntityRenderer<AnvilBlockEntity> {

    public AnvilBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(AnvilBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5,1+0.5/16F,0.5);
        poseStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(90)));
        poseStack.mulPose(blockEntity.getBlockState().getValue(AnvilBlock.FACING).getRotation());
        poseStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(180)));
        if(blockEntity.getPiece().is(ModItems.FORGING_HAMMER)) {
            poseStack.translate(0,2/16F,1.25/16F);
            poseStack.mulPose(new Quaternionf().rotateZ((float) Math.toRadians(45)).rotateY((float) Math.toRadians(90)).rotateZ((float) Math.toRadians(5)));
        }
        if(blockEntity.getPiece().is(ModItems.FORGING_TONGS)) {
            poseStack.translate(0, 0, 7.5 / 16F);
            poseStack.mulPose(new Quaternionf().rotateX((float) Math.toRadians(90)).rotateY((float) Math.toRadians(-30)));
        }
        Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.getPiece(), ItemDisplayContext.NONE, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();
    }
}

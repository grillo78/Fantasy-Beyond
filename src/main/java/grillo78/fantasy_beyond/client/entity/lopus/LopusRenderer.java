package grillo78.fantasy_beyond.client.entity.lopus;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.client.RenderTypes;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.entities.Lopus;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class LopusRenderer<T extends Lopus> extends MobRenderer<T, LopusModel<T>> {

    public LopusRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new LopusModel<>(pContext.bakeLayer(ModModelLayers.LOPUS)), 3F);
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {


        VertexConsumer consumer = pBuffer.getBuffer(RenderTypes.LOPUS_PORTAL);
        pPoseStack.pushPose();
        float scale = (float) Mth.clamp(Mth.lerp(pPartialTicks, pEntity.tickCount, pEntity.tickCount+1)/200,0, 0.5);
        if(pEntity.tickCount >150)
            scale = 0.5F-(float) Mth.clamp((Mth.lerp(pPartialTicks, pEntity.tickCount, pEntity.tickCount+1)-150)/200,0, 0.5);
        pPoseStack.scale(scale, scale, scale);
        for (int i = 0; i < 8; i++) {

            pPoseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(45)));
            consumer.vertex(pPoseStack.last().pose(), -2.5F, 0.01F, 0).endVertex();
            consumer.vertex(pPoseStack.last().pose(), -5, 0.01F, 15).endVertex();
            consumer.vertex(pPoseStack.last().pose(), 2.5F, 0.01F, 0).endVertex();

            consumer.vertex(pPoseStack.last().pose(), -2.5F, 0.01F, 0).endVertex();
            consumer.vertex(pPoseStack.last().pose(), 5, 0.01F, 20).endVertex();
            consumer.vertex(pPoseStack.last().pose(), 2.5F, 0.01F, 0).endVertex();

            consumer.vertex(pPoseStack.last().pose(), 5, 0.01F, 20).endVertex();
            consumer.vertex(pPoseStack.last().pose(), 10, 0.01F, 30).endVertex();
            consumer.vertex(pPoseStack.last().pose(), 3.5F, 0.01F, 10).endVertex();

            consumer.vertex(pPoseStack.last().pose(), -2.5F, 0.01F, 0).endVertex();
            consumer.vertex(pPoseStack.last().pose(), 0, 0.01F, 25).endVertex();
            consumer.vertex(pPoseStack.last().pose(), 2.5F, 0.01F, 0).endVertex();

            consumer.vertex(pPoseStack.last().pose(), -0, 0.01F, 10).endVertex();
            consumer.vertex(pPoseStack.last().pose(), -5, 0.01F, 20).endVertex();
            consumer.vertex(pPoseStack.last().pose(), 0, 0.01F, 15).endVertex();

            consumer.vertex(pPoseStack.last().pose(), 0, 0.01F, -1).endVertex();
            consumer.vertex(pPoseStack.last().pose(), -10, 0.01F, 5).endVertex();
            consumer.vertex(pPoseStack.last().pose(), -0, 0.01F, 2).endVertex();

            consumer.vertex(pPoseStack.last().pose(), 0, 0.01F, -1).endVertex();
            consumer.vertex(pPoseStack.last().pose(), -15, 0.01F, 1).endVertex();
            consumer.vertex(pPoseStack.last().pose(), -0, 0.01F, 2).endVertex();
        }
        pPoseStack.popPose();
        pPoseStack.pushPose();
        if(pEntity.tickCount < 100){
            pPoseStack.translate(0, -6.7, 0);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(Lopus pEntity) {
        return new ResourceLocation(FantasyBeyond.MOD_ID, "textures/entity/lopus.png");
    }
}

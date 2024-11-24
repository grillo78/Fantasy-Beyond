package grillo78.fantasy_beyond.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public class RenderUtil {

    public static boolean renderingFirstPersonModel = false;

    public static void renderFirstPersonModel(RenderLevelStageEvent event) {
        PoseStack matrixStack = event.getPoseStack();
        matrixStack.pushPose();
        MultiBufferSource.BufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();

        Vec3 view = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        matrixStack.translate(-view.x(), -view.y(), -view.z());
        if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            AbstractClientPlayer player = Minecraft.getInstance().player;
            double playerRot = Math.toRadians(player.yBodyRot);
            Vec3 lookVector = new Vec3(-Math.sin(playerRot), 0, Math.cos(playerRot));
            Vec3 playerPosition = player.getPosition(event.getPartialTick()).add(-lookVector.x() * (player.getPose() != Pose.SWIMMING ? 0.1 : .5), 0, -lookVector.z() * (player.getPose() != Pose.SWIMMING ? 0.1 : .5));
            matrixStack.pushPose();

            renderingFirstPersonModel = true;
            Minecraft.getInstance().getEntityRenderDispatcher().render(player, playerPosition.x, playerPosition.y, playerPosition.z, Minecraft.getInstance().player.yBodyRot, event.getPartialTick(), event.getPoseStack(), buffers, Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(Minecraft.getInstance().player, event.getPartialTick()));
            renderingFirstPersonModel = false;

            matrixStack.popPose();
        }
        matrixStack.popPose();
        buffers.endBatch();
        RenderSystem.disableDepthTest();
    }
}

package grillo78.fantasy_beyond.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.client.entity.race.RaceCharacteristicRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4f;

import java.awt.*;
import java.math.BigDecimal;


public class RenderUtils {

    public static boolean renderingFirstPersonModel = false;
    public static Vec3 headPosition = null;

    @OnlyIn(Dist.CLIENT)
    public static void renderHealth(RenderGuiLayerEvent.Pre event) {

        CharacterData playerData = Minecraft.getInstance().player.getData(ModAttachments.CHARACTER_DATA);
        GuiGraphics graphics = event.getGuiGraphics();
        Player player = Minecraft.getInstance().player;

        int offset = 10;
        double factor = Minecraft.getInstance().getWindow().getGuiScale();

        graphics.blit(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/screen/hud/health_indicator.png"), offset - 8, 5, 0, 0, 140, 60, 140, 60);

        RenderSystem.enableScissor((int) ((offset + 10) * factor), (int) (Minecraft.getInstance().getWindow().getHeight() - 60 * factor), (int) (factor * (49)), (int) (49 * factor));
        InventoryScreen.renderEntityInInventory(graphics, offset + 35, 60, playerData.getPlayerCustomization().getRace().getHUDViewerScale(), Vec3.ZERO.toVector3f(), new Quaternionf().rotateX((float) Math.PI).rotateY((float) Math.toRadians(player.yBodyRot)), new Quaternionf().rotationX((float) 0), Minecraft.getInstance().player);
        RenderSystem.disableScissor();

        graphics.fillGradient(offset + 70, 11, offset + (int) (70 + (56 * Minecraft.getInstance().player.getHealth() / Minecraft.getInstance().player.getMaxHealth())), 16, new Color(250, 100, 100).hashCode(), Color.RED.hashCode());

        graphics.fillGradient(offset + 70, 19, offset + (int) (70 + (56 * Minecraft.getInstance().player.getArmorCoverPercentage())), 24, Color.LIGHT_GRAY.hashCode(), Color.GRAY.hashCode());

        graphics.fillGradient(offset + 70, 27, offset + 70 + (56 * Minecraft.getInstance().player.getFoodData().getFoodLevel() / 20), 32, Color.GREEN.hashCode(), new Color(0, 200, 0).hashCode());

        graphics.fillGradient(offset + 70, 35, offset + 70 + Mth.clamp((56 * Minecraft.getInstance().player.getAirSupply() / Minecraft.getInstance().player.getMaxAirSupply()), 0, 56), 40, new Color(100, 100, 250).hashCode(), Color.BLUE.hashCode());

        graphics.fillGradient(offset, (int) (61 - (50 * Minecraft.getInstance().player.experienceProgress)), offset + 7, 60, new Color(0, 150, 10).hashCode(), new Color(0, 200, 10).hashCode());

        Component text = Component.literal(String.valueOf(Minecraft.getInstance().player.experienceLevel));
        graphics.drawString(Minecraft.getInstance().font, Component.translatable(FantasyBeyond.MOD_ID + ".hud.level"), offset + 63, 42, new Color(0, 150, 10).hashCode());
        graphics.drawString(Minecraft.getInstance().font, text, offset + 63, 52, new Color(0, 150, 10).hashCode());

        graphics.pose().pushPose();
        graphics.pose().scale(0.5F, 0.5F, 0.5F);

        text = Component.literal(round(Minecraft.getInstance().player.getHealth(), 2) + " / " + Minecraft.getInstance().player.getMaxHealth());

        graphics.drawString(Minecraft.getInstance().font, text, (offset + 96) * 2 - (Minecraft.getInstance().font.width(text) / 2), 24, Color.WHITE.hashCode(), false);
        text = Component.literal(String.valueOf(Minecraft.getInstance().player.getArmorCoverPercentage()));
        graphics.drawString(Minecraft.getInstance().font, text, (offset + 96) * 2 - (Minecraft.getInstance().font.width(text) / 2), 40, Color.WHITE.hashCode(), false);
        text = Component.literal(Minecraft.getInstance().player.getFoodData().getFoodLevel() + "/ 20");
        graphics.drawString(Minecraft.getInstance().font, text, (int) ((offset + 93.5) * 2 - (Minecraft.getInstance().font.width(text) / 2)), 56, Color.WHITE.hashCode(), false);
        text = Component.literal(Minecraft.getInstance().player.getAirSupply() + " / " + Minecraft.getInstance().player.getMaxAirSupply());
        graphics.drawString(Minecraft.getInstance().font, text, (int) ((offset + 93.5) * 2 - (Minecraft.getInstance().font.width(text) / 2)), 72, Color.WHITE.hashCode(), false);
        graphics.pose().popPose();
    }

    public static float round(float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static void renderFilledBox(PoseStack matrixStack, VertexConsumer builder, AABB box, float red, float green, float blue, float alpha, int combinedLightIn) {
        Matrix4f poseStack = matrixStack.last().pose();
        builder.addVertex(poseStack, (float) box.minX, (float) box.maxY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.minX, (float) box.maxY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.maxX, (float) box.maxY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);

        builder.addVertex(poseStack, (float) box.minX, (float) box.minY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.maxX, (float) box.minY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.maxX, (float) box.minY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.minX, (float) box.minY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);

        builder.addVertex(poseStack, (float) box.minX, (float) box.minY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.minX, (float) box.maxY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.maxX, (float) box.maxY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.maxX, (float) box.minY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);

        builder.addVertex(poseStack, (float) box.minX, (float) box.minY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.maxX, (float) box.minY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.minX, (float) box.maxY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);

        builder.addVertex(poseStack, (float) box.maxX, (float) box.minY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.maxX, (float) box.maxY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.maxX, (float) box.minY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);

        builder.addVertex(poseStack, (float) box.minX, (float) box.minY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.minX, (float) box.minY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.minX, (float) box.maxY, (float) box.maxZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
        builder.addVertex(poseStack, (float) box.minX, (float) box.maxY, (float) box.minZ).setColor(red, green, blue, alpha).setLight(combinedLightIn);
    }


    public static void renderFirstPersonModel(RenderLevelStageEvent event) {
            PoseStack matrixStack = event.getPoseStack();
            matrixStack.pushPose();
            MultiBufferSource.BufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();

            Vec3 view = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            matrixStack.translate(-view.x(), -view.y(), -view.z());
            AbstractClientPlayer player = Minecraft.getInstance().player;
            Vec3 lookVector = new Vec3(0,0,-0.25).yRot((float) Math.toRadians(-player.getViewYRot(Minecraft.getInstance().gameRenderer.getMainCamera().getPartialTickTime())));
            Vec3 playerPosition = player.getPosition(Minecraft.getInstance().gameRenderer.getMainCamera().getPartialTickTime()).add(lookVector);

            renderingFirstPersonModel = true;
            Minecraft.getInstance().getEntityRenderDispatcher().render(player, playerPosition.x, playerPosition.y, playerPosition.z, Minecraft.getInstance().player.yBodyRot, Minecraft.getInstance().gameRenderer.getMainCamera().getPartialTickTime(), event.getPoseStack(), buffers, Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(Minecraft.getInstance().player, Minecraft.getInstance().gameRenderer.getMainCamera().getPartialTickTime()));
            renderingFirstPersonModel = false;

            matrixStack.popPose();
            buffers.endBatch();
            RenderSystem.disableDepthTest();
    }

    public static void moveCamera(Camera instance, double x, double y, double z, float partialTickTime, Entity entity) {
        Vec3 position = new Vec3(
                Mth.lerp(partialTickTime, entity.xo, entity.getX()),
                Mth.lerp(partialTickTime, entity.yo, entity.getY()),
                Mth.lerp(partialTickTime, entity.zo, entity.getZ())
        );

        if (entity.hasData(ModAttachments.CHARACTER_DATA) && !instance.isDetached() && !entity.isSpectator()) {
            CharacterData data = entity.getData(ModAttachments.CHARACTER_DATA);
            if (data.getPlayerCustomization().isFinished() && RenderUtils.headPosition != null) {
                position = new Vec3(headPosition.x, headPosition.y+0.25, headPosition.z).add(new Vec3(0,0,0.25).yRot((float) Math.toRadians(-entity.getViewYRot(partialTickTime))));
            }
        } else {
            position = position.add(0, (double) Mth.lerp(partialTickTime, instance.eyeHeightOld, instance.eyeHeight), 0);
        }
        instance.setPosition(position.x, position.y, position.z);

    }

    public static void getHeadPos(PoseStack poseStack, ModelPart part) {
        if (renderingFirstPersonModel && Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasData(ModAttachments.CHARACTER_DATA) && RaceCharacteristicRenderer.getRenderer(Minecraft.getInstance().player.getData(ModAttachments.CHARACTER_DATA).getPlayerCustomization().getRace().getCharacteristics().get(0).getClass()).getModel(Minecraft.getInstance().player.getData(ModAttachments.CHARACTER_DATA).getPlayerCustomization().getRace().getCharacteristics().get(0)).getHeadPart() == part) {
            poseStack.pushPose();
            part.translateAndRotate(poseStack);
            Matrix4f matrix = poseStack.last().pose();
//            matrix.invert();
//            Vector4f vector4f = new Vector4f(0, 0, 0, 1);
//            vector4f.mulProject(matrix);
//            System.out.println(vector4f);
            RenderUtils.headPosition = new Vec3(matrix.m30(), matrix.m31(), matrix.m32()).add(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition());
//            RenderUtils.headPosition = new Vec3(vector4f.x(), vector4f.y(), vector4f.z()).subtract(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition());
            poseStack.popPose();
        }
    }
}

package grillo78.fantasy_beyond.magic.magic_circles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.MagicProvider;
import grillo78.fantasy_beyond.client.RenderUtils;
import grillo78.fantasy_beyond.network.PacketHandler;
import grillo78.fantasy_beyond.network.messages.SetEffect;
import grillo78.fantasy_beyond.util.ProjectilesUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import org.joml.Quaternionf;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

public class FallingCannonMagicCircleType extends MagicCircleType {

    public static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/beacon_beam.png");

    @Override
    public void render(MagicCircle instance, PoseStack poseStack, MultiBufferSource bufferSource, int packetLight, float partialTick) {
        super.render(instance, poseStack, bufferSource, packetLight, partialTick);
        poseStack.pushPose();
        poseStack.translate(instance.getPos().x, instance.getPos().y + 0.1, instance.getPos().z);
        if (instance.getTickCount() >= 440 && instance.getTickCount() <= 455)
            drawBeam(instance, poseStack, partialTick, new Color(255, 0, 0, 255), bufferSource);
        float scale = 2.5F;
        if (instance.getTickCount() < 15) {
            scale = 2.5F * Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1) / 15;
        }
        if (instance.getTickCount() > 485) {
            scale = 2.5F * (500 - Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1)) / 15;
        }
        poseStack.scale(scale, scale, scale);
        drawCircle(instance, poseStack, partialTick, new Color(255, 0, 0, 255), bufferSource);
        poseStack.pushPose();
        poseStack.translate(0, -2, 0);
        scale = 0.75F;
        if (instance.getTickCount() < 30) {
            scale = 0.75F * Mth.clamp((Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1) - 15) / 15, 0F, 1F);
        }
        if (instance.getTickCount() > 470) {
            scale = 0.75F * Mth.clamp((485 - Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1)) / 15, 0, 1);
        }
        poseStack.scale(scale, scale, scale);
        drawCircle(instance, poseStack, partialTick, new Color(148, 234, 253, 255), bufferSource);
        poseStack.popPose();
        poseStack.pushPose();
        poseStack.translate(0, -4, 0);
        scale = 0.5F;
        if (instance.getTickCount() < 45) {
            scale = 0.5F * Mth.clamp((Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1) - 30) / 15, 0F, 1F);
        }
        if (instance.getTickCount() > 455) {
            scale = 0.5F * Mth.clamp((470 - Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1)) / 15, 0, 1);
        }
        poseStack.scale(scale, scale, scale);
        drawCircle(instance, poseStack, partialTick, new Color(255, 255, 63, 255), bufferSource);
        poseStack.popPose();

        poseStack.pushPose();

        poseStack.popPose();
        poseStack.popPose();
    }

    public void drawBeam(MagicCircle instance, PoseStack poseStack, float partialTick, Color color, MultiBufferSource bufferSource) {
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.lightning());
        HitResult result = ProjectilesUtil.getHitResult(instance.position, Objects::nonNull, new Vec3(0, -200, 0), Minecraft.getInstance().level, null);
        double distance = result.getLocation().distanceTo(instance.position);

        RenderUtils.renderFilledBox(poseStack, consumer, new AABB(-0.25, 0, -0.25, 0.25, (1 - (455 - Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1)) / 15) * (result == null ? 50 : -distance), 0.25), 1, 1, 1, 1, 15728880);
        RenderUtils.renderFilledBox(poseStack, consumer, new AABB(-0.5, 0, -0.5, 0.5, (1 - (455 - Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1)) / 15) * (result == null ? 50 : -distance), 0.5), color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F, 15728880);
    }

    public void drawCircle(MagicCircle instance, PoseStack poseStack, float partialTick, Color color, MultiBufferSource bufferSource) {

        double radius = 5;
        poseStack.pushPose();
        poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(2 * Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1))));

        String text = "falling";
        for (int i = 0; i < 5; i++) {
            poseStack.pushPose();
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(i * 360 / 5)));
            for (int j = 0; j < text.length(); j++) {

                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(-j * 40 / text.length() - 360 / 20)));
                poseStack.translate(radius - 0.25, 0, 0);
                drawText(String.valueOf(text.charAt(j)), color, poseStack, bufferSource, false, 0.025F);
                poseStack.popPose();
            }
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(360 / 10)));
            poseStack.translate(radius - 1.25, 0, 0);
            drawText("cannon", color, poseStack, bufferSource, false, 0.025F);
            poseStack.popPose();
        }

        drawCircle(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 360);
        drawCircle(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 5);
        draw5Star(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose());
        radius = 1.55;
        for (int i = 0; i < 10; i++) {
            poseStack.pushPose();
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(i * 360 / 40)));

            drawCircle(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 5);
            poseStack.popPose();
        }
        radius = 0.5;
        poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(360 / 10)));
        for (int i = 0; i < 5; i++) {
            poseStack.pushPose();
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(i * 360 / 5)));
            poseStack.translate(3, 0, 0);
            draw6Star(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack);
            drawCircle(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 360);
            poseStack.popPose();
        }
        radius = 0.25;
        for (int i = 0; i < 5; i++) {
            poseStack.pushPose();
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians((i) * 360 / 5)));
            poseStack.translate(3, 0, 0);
            draw6Star(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack);
            drawCircle(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 360);
            poseStack.popPose();
        }
        poseStack.popPose();

        radius = 1;
        poseStack.pushPose();
        poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(Math.pow(Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1), 1.4))));
        drawCircle(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 360);
        draw6Star(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack);
        draw5Star(new Vec3(0.5F, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose());
        draw5Star(new Vec3(-0.5F, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose());
        drawCircle(new Vec3(0.5F, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 10);
        poseStack.popPose();

        radius = 6.18;
        poseStack.pushPose();
        poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(-2 * Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1))));
        drawCircle(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 360);
        drawCircle(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 5);
        poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(90)));
        drawCircle(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 5);
        poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(90)));
        drawCircle(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 5);
        poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(90)));
        drawCircle(new Vec3(radius, 0, 0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), poseStack.last().pose(), 5);
        poseStack.popPose();
    }

    public static void createInstanceStatic(Level level, Player player) {
        level.getCapability(MagicProvider.MAGIC).ifPresent(magic -> {
            HitResult hitResult = ProjectilesUtil.getHitResult(player.getEyePosition(), entity -> player != entity, player.getViewVector(0).multiply(50, 50, 50), level, player);
            hitResult = ProjectilesUtil.getHitResult(hitResult.getLocation(), entity -> player != entity,new Vec3(0,60,0),level, player);
            if(hitResult.distanceTo(player)>= 60){
                magic.getMagicCircles().add(MagicCircleType.FALLING_CANNON_CIRCLE.get().createInstance((ServerLevel) level, hitResult.getLocation().add(0, 60, 0), new ArrayList()));
            }
        });
    }

    @Override
    public void tick(MagicCircle magicCircle, Level level) {
        super.tick(magicCircle, level);
        if (magicCircle.getTickCount() >= 455 && !level.isClientSide) {
            HitResult result = ProjectilesUtil.getHitResult(magicCircle.position, Objects::nonNull, new Vec3(0, -200, 0), level, null);
            if (result != null) {
                if (magicCircle.getTickCount() == 465)
                    level.explode(null, result.getLocation().x, result.getLocation().y, result.getLocation().z, 20, Level.ExplosionInteraction.TNT);
                if (magicCircle.getTickCount() == 455)
                    level.players().forEach(serverPlayer -> {
                        PacketHandler.INSTANCE.sendTo(new SetEffect(new BlockPos((int) result.getLocation().x, (int) result.getLocation().y, (int) result.getLocation().z), new ResourceLocation(FantasyBeyond.MOD_ID, "explsion")), ((ServerPlayer) serverPlayer).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                    });
            }
        }
        if (magicCircle.getTickCount() > 500)
            level.getCapability(MagicProvider.MAGIC).ifPresent(magic -> magic.removeMagicCircle(magicCircle));
    }
}

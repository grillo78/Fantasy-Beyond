package grillo78.fantasy_beyond.magic.magic_circles;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.capabilities.MagicProvider;
import grillo78.fantasy_beyond.entities.Fireball;
import grillo78.fantasy_beyond.entities.ModEntities;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public class FireballMagicCircleType extends MagicCircleType {

    @Override
    public void render(MagicCircle instance, PoseStack poseStack, MultiBufferSource bufferSource, int packetLight, float partialTick) {
        super.render(instance, poseStack, bufferSource, packetLight, partialTick);
        FireballMagicCircle fireballMagicCircle = (FireballMagicCircle) instance;
        if (fireballMagicCircle.player != null) {
            poseStack.pushPose();
            Vec3 position = fireballMagicCircle.player.getEyePosition(partialTick).add(fireballMagicCircle.player.getViewVector(partialTick));
            poseStack.translate(position.x, position.y, position.z);
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(-fireballMagicCircle.player.getViewYRot(partialTick))));
            poseStack.mulPose(new Quaternionf().rotationX((float) Math.toRadians(90 + fireballMagicCircle.player.getViewXRot(partialTick))));
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(360 / 20)));
            poseStack.pushPose();
            poseStack.mulPose(new Quaternionf().rotationY(Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1) / 30));
            float scale = 1;
            if (instance.getTickCount() < 10) {
                scale = Mth.clamp((Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1)) / 10, 0F, 1F);
            }
            if (instance.getTickCount() > 130) {
                scale = Mth.clamp(1 - (Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1) - 130) / 10, 0, 1);
            }
            poseStack.scale(scale, scale, scale);
            drawCircle(new Vec3(1, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
            String text = "fireball";
            for (int i = 0; i < text.length(); i++) {
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(i * 360 / 5)));
                for (int j = 0; j < text.length(); j++) {

                    poseStack.pushPose();
                    poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(j * 40 / text.length() - 360 / 20)));
                    poseStack.translate(1.2F - 0.25, 0, 0);
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                    drawText(String.valueOf(text.charAt(j)), Color.RED, poseStack, bufferSource, true, 0.025F);
                    poseStack.popPose();
                }
                poseStack.popPose();
            }
            drawCircle(new Vec3(0.91, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
            poseStack.popPose();

            poseStack.pushPose();
            if (instance.getTickCount() < 20) {
                scale = Mth.clamp((Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1) - 10) / 10, 0F, 1F);
            }
            if (instance.getTickCount() > 120) {
                scale = Mth.clamp(1 - (Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1) - 120) / 10, 0, 1);
            }
            poseStack.scale(scale, scale, scale);
            poseStack.mulPose(new Quaternionf().rotationY(-Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1) / 20));
            drawCircle(new Vec3(0.91, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 5);
            drawCircle(new Vec3(-0.91, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 5);
            drawCircle(new Vec3(0.735, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);

            text = "Qemos";
            for (int i = 0; i < text.length(); i++) {
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(i * 360 / 5)));
                for (int j = 0; j < text.length(); j++) {

                    poseStack.pushPose();
                    poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(j * 40 / text.length() - 360 / 20)));
                    poseStack.translate(0.68, 0, 0);
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                    drawText(String.valueOf(text.charAt(j)), Color.RED, poseStack, bufferSource, true, 0.025F);
                    poseStack.popPose();
                }
                poseStack.popPose();
            }
            drawCircle(new Vec3(0.64, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
            poseStack.popPose();


            poseStack.pushPose();
            if (instance.getTickCount() < 30) {
                scale = Mth.clamp((Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1) - 20) / 10, 0F, 1F);
            }
            if (instance.getTickCount() > 110) {
                scale = Mth.clamp(1 - (Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1) - 110) / 10, 0, 1);
            }
            poseStack.scale(scale, scale, scale);
            poseStack.mulPose(new Quaternionf().rotationY(Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1) / 10));
            drawCircle(new Vec3(0.5, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
            draw5Star(new Vec3(0.5, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
            for (int i = 0; i < 5; i++) {
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(i * 360 / 5)));
                poseStack.translate(0.655, 0, -0.48);
                drawCircle(new Vec3(-0.5, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360 / 5, 360 / 5);
                poseStack.popPose();
            }
            poseStack.popPose();

            poseStack.popPose();
        }
    }

    @Override
    public MagicCircle createInstance(ServerLevel level, Vec3 position, List parameters) {
        FireballMagicCircle magicCircle = (FireballMagicCircle) super.createInstance(level, position, parameters);
        magicCircle.player = (Player) parameters.get(0);
        return magicCircle;
    }

    @Override
    public MagicCircle createInstance(Level level) {
        return new FireballMagicCircle(level, this);
    }

    public static class FireballMagicCircle extends MagicCircle {

        private Player player;
        private Fireball fireball;

        public FireballMagicCircle(Level level, MagicCircleType type) {
            super(level, type);
        }

        @Override
        public void tick() {
            super.tick();
            if (player == null || tickCount > 140)
                level.getCapability(MagicProvider.MAGIC).ifPresent(magic -> magic.removeMagicCircle(this));
            if (player != null && !level.isClientSide) {
                position = player.getEyePosition(0).add(player.getViewVector(0));
                if (tickCount == 30) {
                    fireball = new Fireball(ModEntities.FIREBALL.get(), level);
                    fireball.setPos(player.position().add(0, player.getEyeHeight(), 0).add(player.getViewVector(0).multiply(2, 2, 2)));
                    level.addFreshEntity(fireball);
                }

                if (fireball != null) {
                    if (tickCount == 110) {
                        fireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2, 0);
                    } else {
                        if (tickCount < 110)
                            fireball.setPos(player.position().add(0, player.getEyeHeight(), 0).add(player.getViewVector(0).multiply(2, 2, 2)));
                    }
                }
            }
        }

        @Override
        public CompoundTag serializeNBT() {

            CompoundTag compoundTag = super.serializeNBT();
            compoundTag.putString("player", player.getStringUUID());
            return compoundTag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            super.deserializeNBT(nbt);
            player = level.getPlayerByUUID(UUID.fromString(nbt.getString("player")));
        }
    }
}

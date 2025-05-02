package grillo78.fantasy_beyond.magic.magic_circles;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import com.lowdragmc.shimmer.client.light.LightManager;
import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.blocks.ModBlocks;
import grillo78.fantasy_beyond.capabilities.MagicProvider;
import grillo78.fantasy_beyond.entities.Lopus;
import grillo78.fantasy_beyond.entities.ModEntities;
import grillo78.fantasy_beyond.network.PacketHandler;
import grillo78.fantasy_beyond.network.messages.SetEffect;
import grillo78.fantasy_beyond.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import org.joml.Quaternionf;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class InvocationCircleType extends MagicCircleType {
    private Random random = new Random();

    @Override
    public MagicCircle createInstance(ServerLevel level, Vec3 position, List parameters) {
        BlockPos pos = new BlockPos((int) (position.x - 0.5), (int) position.y, (int) (position.z - 0.5));
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        level.playSound(null, BlockPos.containing(position), ModSounds.LOPUS_INVOCATION_SFX_1.get(), SoundSource.AMBIENT, 0.5F, 1);
        level.players().forEach(serverPlayer -> {
            PacketHandler.INSTANCE.sendTo(new SetEffect(new BlockPos((int) (position.x - 0.5), (int) position.y, (int) (position.z - 0.5)), new ResourceLocation(FantasyBeyond.MOD_ID, "invocation_circle")), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        });
        return super.createInstance(level, position, parameters);
    }

    @Override
    public MagicCircle createInstance(Level level) {
        return new InvocationCircle(level, this);
    }

    @Override
    public void tick(MagicCircle magicCircle, Level level) {
        if (!level.isClientSide) {
            BlockPos pos = new BlockPos((int) magicCircle.position.x, (int) (magicCircle.position.y - 0.5 + magicCircle.tickCount / 30F), (int) magicCircle.position.z);
            BlockPos.betweenClosed(pos.north(4).east(3), pos.west(4).south(3)).forEach(posAux -> {
                if (level.getBlockState(posAux).getBlock() instanceof CandleBlock) {
                    level.setBlock(posAux, Blocks.AIR.defaultBlockState(), 3);
                }
            });
            if (magicCircle.getTickCount() == 300)
                level.getCapability(MagicProvider.MAGIC).ifPresent(magic -> {
                    magic.removeMagicCircle(magicCircle);
                });
            if (magicCircle.getTickCount() == 225) {
                Lopus entity = ModEntities.LOPUS.get().create(level);
                entity.setPos(magicCircle.getPos());
                level.addFreshEntity(entity);
            }
            if (random.nextInt(100) < 20) {
                level.playSound(null, BlockPos.containing(magicCircle.position), ModSounds.LOPUS_INVOCATION_SFX_2.get(), SoundSource.AMBIENT, .025F, 1);
            }
        } else {
            Vec3 particlePosition = magicCircle.getPos();
            particlePosition = particlePosition.add(new Vec3(random.nextDouble() * 3, 5, 0).yRot((float) Math.toRadians(random.nextDouble() * 360)));

            if (magicCircle.getTickCount() < 230)
                level.addAlwaysVisibleParticle(ParticleTypes.ENCHANT, true, particlePosition.x, particlePosition.y, particlePosition.z, particlePosition.subtract(magicCircle.position).x / (random.nextDouble() * 10), -5, particlePosition.subtract(magicCircle.position).z / (random.nextDouble() * 10));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(MagicCircle instance, PoseStack poseStack, MultiBufferSource bufferSource, int packetLight, float partialTick) {
        super.render(instance, poseStack, bufferSource, packetLight, partialTick);

        poseStack.pushPose();
        poseStack.translate(instance.getPos().x, instance.getPos().y, instance.getPos().z);

        poseStack.pushPose();
        poseStack.translate(0, Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1) / 30, 0);
        poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(instance.tickCount)));
        poseStack.mulPose(new Quaternionf().rotationX((float) Math.toRadians(90)));
        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(ModBlocks.INVOCATION_BOOK.get()), ItemDisplayContext.FIXED, packetLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, Minecraft.getInstance().level, 0);
        poseStack.popPose();

        poseStack.translate(0, (instance.getTickCount() < 32 ? Mth.clamp(Math.sin(Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1) / 10), 0.1, 1) : 0.1), 0);
        if (instance.getTickCount() < 240) {
            //center
            if (instance.getTickCount() > 39) {
                float scale = Mth.clamp((Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1) - 40F) / 20F, 0, 1);
                poseStack.pushPose();
                poseStack.scale(scale, 1, scale);
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1))));
                drawCircle(new Vec3(2, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 5);
                draw5Star(new Vec3(2, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                poseStack.popPose();
            }

            //middle
            if (instance.getTickCount() > 19) {
                float scale = Mth.clamp((Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1) - 20F) / 20F, 0, 1);
                poseStack.pushPose();
                poseStack.scale(scale, 1, scale);
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1)) / 2));
                drawWaveCircle(new Vec3(1.55, 0, 0).yRot((float) Math.toRadians(45 / 2)), 0.01, 1.5, 255, 0, 0, 255, poseStack.last().pose(), 360);
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) -Math.toRadians(Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1))));
                drawCircle(new Vec3(3, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 5);
                drawCircle(new Vec3(2, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);

                poseStack.pushPose();
                poseStack.translate(2.5, 0, 0);
                draw5Star(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                drawCircle(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(360 / 5)));
                poseStack.translate(2.5, 0, 0);
                drawCircle(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                draw5Star(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(2 * 360 / 5)));
                poseStack.translate(2.5, 0, 0);
                draw5Star(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                drawCircle(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(3 * 360 / 5)));
                poseStack.translate(2.5, 0, 0);
                draw5Star(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                drawCircle(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(4 * 360 / 5)));
                poseStack.translate(2.5, 0, 0);
                draw5Star(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                drawCircle(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                poseStack.popPose();
                poseStack.popPose();
                poseStack.popPose();
            }

            //exterior
            float scale = Mth.clamp((Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1)) / 20F, 0, 1);
            poseStack.pushPose();
            poseStack.scale(scale, 1, scale);
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1))));
            drawCircle(new Vec3(3, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
            drawCircle(new Vec3(3.5, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
            drawCircle(new Vec3(3.47, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 6);
            poseStack.pushPose();
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(45)));
            drawCircle(new Vec3(3.47, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 6);
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(45)));
            drawCircle(new Vec3(3.47, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 6);
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(45)));
            drawCircle(new Vec3(3.47, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 6);
            poseStack.popPose();
            poseStack.popPose();
        } else {

            //center
            if (instance.getTickCount() < 259) {
                float scale = Mth.clamp(((260 - Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1)) / 20F), 0, 1F);
                poseStack.pushPose();
                poseStack.scale(scale, 1, scale);
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1))));
                drawCircle(new Vec3(2, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 5);
                draw5Star(new Vec3(2, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                poseStack.popPose();
            }

//            //middle
            if (instance.getTickCount() < 278) {
                float scale = Mth.clamp(((280 - Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1)) / 20F), 0, 1F);
                poseStack.pushPose();
                poseStack.scale(scale, 1, scale);
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1)) / 2));
                drawWaveCircle(new Vec3(1.55, 0, 0).yRot((float) Math.toRadians(45 / 2)), 0.01, 1.5, 255, 0, 0, 255, poseStack.last().pose(), 360);
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) -Math.toRadians(Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1))));
                drawCircle(new Vec3(3, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 5);
                drawCircle(new Vec3(2, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);

                poseStack.pushPose();
                poseStack.translate(2.5, 0, 0);
                draw5Star(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                drawCircle(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(360 / 5)));
                poseStack.translate(2.5, 0, 0);
                drawCircle(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                draw5Star(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(2 * 360 / 5)));
                poseStack.translate(2.5, 0, 0);
                draw5Star(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                drawCircle(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(3 * 360 / 5)));
                poseStack.translate(2.5, 0, 0);
                draw5Star(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                drawCircle(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(4 * 360 / 5)));
                poseStack.translate(2.5, 0, 0);
                draw5Star(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose());
                drawCircle(new Vec3(0.25, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                poseStack.popPose();
                poseStack.popPose();
                poseStack.popPose();
            }

            //exterior
            {
                float scale = Mth.clamp(((300 - Mth.lerp(partialTick, instance.getTickCount(), instance.getTickCount() + 1)) / 20F), 0, 1F);
                poseStack.pushPose();
                poseStack.scale(scale, 1, scale);
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(Mth.lerp(partialTick, instance.tickCount, instance.tickCount + 1))));
                drawCircle(new Vec3(3, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                drawCircle(new Vec3(3.5, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 360);
                drawCircle(new Vec3(3.47, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 6);
                poseStack.pushPose();
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(45)));
                drawCircle(new Vec3(3.47, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 6);
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(45)));
                drawCircle(new Vec3(3.47, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 6);
                poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(45)));
                drawCircle(new Vec3(3.47, 0, 0), 255, 0, 0, 255, poseStack.last().pose(), 6);
                poseStack.popPose();
                poseStack.popPose();
            }

        }


        poseStack.popPose();
    }

    public static class InvocationCircle extends MagicCircle {

        private ColorPointLight light;

        public InvocationCircle(Level level, MagicCircleType type) {
            super(level, type);
        }

        @Override
        public void tick() {
            if (!level.isClientSide) {
                if (light == null)
                    light = LightManager.INSTANCE.addLight(position.toVector3f(), Color.RED.hashCode(), 0);
                if(tickCount<278)
                    light.radius = Mth.clamp(10 * tickCount / 19F, 0, 10);
                else
                    light.radius = Mth.clamp(10 * (1-(tickCount-278) / 19F), 0, 10);
                light.update();
            }

            super.tick();
        }

        @Override
        public void onRemove() {
            super.onRemove();
            if (light != null) {
                light.remove();
                light = null;
            }
        }
    }
}

package grillo78.fantasy_beyond.magic.magic_circles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.client.RenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class MagicCircleType {
    public static final DeferredRegister<MagicCircleType> MAGIC_CIRCLES = DeferredRegister.create(new ResourceLocation(FantasyBeyond.MOD_ID, "magic_circles"), FantasyBeyond.MOD_ID);
    public static final Supplier<IForgeRegistry<MagicCircleType>> CIRCLES_REGISTRY = MAGIC_CIRCLES.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<MagicCircleType> INVOCATION_CIRCLE = register("invocation_circle", InvocationCircleType::new);
    public static final RegistryObject<MagicCircleType> FALLING_CANNON_CIRCLE = register("falling_cannon_circle", FallingCannonMagicCircleType::new);
    public static final RegistryObject<MagicCircleType> FIREBALL_CIRCLE = register("fireball_circle", FireballMagicCircleType::new);

    private static final ResourceLocation MAGIC_LANGUAGE_FONT = new ResourceLocation("alt");
    public static final Style STYLE = Style.EMPTY.withFont(MAGIC_LANGUAGE_FONT);

    private static RegistryObject<MagicCircleType> register(String name, Supplier<MagicCircleType> alien) {
        return MAGIC_CIRCLES.register(name, alien);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(MagicCircle instance, PoseStack poseStack, MultiBufferSource bufferSource, int packetLight, float partialTick) {
    }

    @OnlyIn(Dist.CLIENT)
    protected void drawLine(Vec3 start, Vec3 end, int red, int green, int blue, int alpha, Matrix4f matrix) {
        VertexConsumer vertexConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderTypes.lines());
        vertexConsumer.vertex(matrix, (float) start.x, (float) start.y, (float) start.z).color(red, green, blue, alpha).normal(0, 0, 0).endVertex();
        vertexConsumer.vertex(matrix, (float) end.x, (float) end.y, (float) end.z).color(red, green, blue, alpha).normal(0, 0, 0).endVertex();
    }

    @OnlyIn(Dist.CLIENT)
    protected void drawText(String text, Color color, PoseStack pose, MultiBufferSource buffer, boolean fromUp, float scale) {
        pose.pushPose();
        pose.scale(scale, scale, scale);
        pose.mulPose(new Quaternionf().rotationY((float) Math.toRadians(90)));
        pose.mulPose(new Quaternionf().rotationX((float) Math.toRadians(-90)));
        if(!fromUp)
            pose.mulPose(new Quaternionf().rotationX((float) Math.toRadians(180)));
        Component component = Component.literal(text).withStyle(STYLE);
        Minecraft.getInstance().font.drawInBatch(component, -Minecraft.getInstance().font.width(component) / 2, -Minecraft.getInstance().font.lineHeight / 2, color.hashCode(), false, pose.last().pose(), buffer, Font.DisplayMode.POLYGON_OFFSET, 0, 200);
        pose.mulPose(new Quaternionf().rotationX((float) Math.PI));
        pose.scale(-1,-1,-1);
        Minecraft.getInstance().font.drawInBatch(component, -Minecraft.getInstance().font.width(component) / 2, -Minecraft.getInstance().font.lineHeight / 2, color.hashCode(), false, pose.last().pose(), buffer, Font.DisplayMode.POLYGON_OFFSET, 0, 200);
        pose.popPose();
    }

    @OnlyIn(Dist.CLIENT)
    protected void drawCircle(Vec3 radius, int red, int green, int blue, int alpha, Matrix4f matrix, int steps) {
        drawCircle(radius,red,green,blue,alpha,matrix,steps,360);
    }

    @OnlyIn(Dist.CLIENT)
    protected void drawCircle(Vec3 radius, int red, int green, int blue, int alpha, Matrix4f matrix, int steps, float angle) {
        for (int i = 0; i < steps; i++) {
            Vec3 radius1 = radius;
            radius = radius.yRot((float) Math.toRadians(angle / steps));
            drawLine(radius1, radius, red, green, blue, alpha, matrix);
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void drawWaveCircle(Vec3 radius, double waveAperture, double frequency, int red, int green, int blue, int alpha, Matrix4f matrix, int steps) {
        for (int i = 0; i <= steps; i++) {
            double sin1 = radius.x + Math.sin((i - 1) * frequency * 360 / steps) * waveAperture;
            double sin = radius.x + Math.sin(i * frequency * 360 / steps) * waveAperture;
            Vec3 radius1 = radius.multiply(sin1, 0, 0).yRot((float) Math.toRadians((i - 1) * 360 / steps));
            Vec3 radius2 = radius.multiply(sin, 0, 0).yRot((float) Math.toRadians(i * 360 / steps));
            drawLine(radius1, radius2, red, green, blue, alpha, matrix);
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void draw5Star(Vec3 radius, int red, int green, int blue, int alpha, Matrix4f matrix) {
        drawStar(radius, red, green, blue, alpha, matrix, 5);
    }

    @OnlyIn(Dist.CLIENT)
    protected void draw6Star(Vec3 radius, int red, int green, int blue, int alpha, PoseStack poseStack) {
        drawCircle(radius, red, green, blue, alpha, poseStack.last().pose(), 3);
        poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(180)));
        drawCircle(radius, red, green, blue, alpha, poseStack.last().pose(), 3);
    }

    @OnlyIn(Dist.CLIENT)
    protected void drawStar(Vec3 radius, int red, int green, int blue, int alpha, Matrix4f matrix, int spikes) {
        for (int i = 0; i < spikes; i++) {
            Vec3 radius1 = radius;
            radius = radius.yRot((float) Math.toRadians(2 * 360 / spikes));
            drawLine(radius1, radius, red, green, blue, alpha, matrix);
        }
    }

    public MagicCircle createInstance(ServerLevel level, Vec3 position, List parameters) {
        MagicCircle magicCircle = createInstance(level);
        magicCircle.setPos(position);
        return magicCircle;
    }

    public void tick(MagicCircle magicCircle, Level level) {
    }

    public MagicCircle createInstance(Level level) {
        return new MagicCircle(level, this);
    }

    public static class MagicCircle implements INBTSerializable<CompoundTag> {

        protected int tickCount = 0;
        protected MagicCircleType type;
        protected Vec3 position;
        protected Level level;

        public MagicCircle(Level level, MagicCircleType type) {
            this.level = level;
            this.type = type;
        }

        public void onRemove(){}

        public void tick() {
            tickCount++;
            type.tick(this, level);
        }

        public Vec3 getPos() {
            return position;
        }

        public void setPos(Vec3 position) {
            this.position = position;
        }

        public int getTickCount() {
            return tickCount;
        }

        @OnlyIn(Dist.CLIENT)
        public void render(PoseStack poseStack, MultiBufferSource bufferSource, Level level, float partialTick) {
            type.render(this, poseStack, bufferSource, LevelRenderer.getLightColor(level, new BlockPos((int) position.x, (int) position.y, (int) position.z)), partialTick);
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("type", MagicCircleType.CIRCLES_REGISTRY.get().getKey(type).toString());
            compoundTag.putDouble("posX", position.x);
            compoundTag.putDouble("posY", position.y);
            compoundTag.putDouble("posZ", position.z);
            compoundTag.putInt("tickCount", tickCount);
            return compoundTag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            position = new Vec3(nbt.getFloat("posX"), nbt.getFloat("posY"), nbt.getFloat("posZ"));
            tickCount = nbt.getInt("tickCount");
        }
    }
}

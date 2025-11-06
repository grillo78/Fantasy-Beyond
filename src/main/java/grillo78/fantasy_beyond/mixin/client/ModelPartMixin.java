package grillo78.fantasy_beyond.mixin.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.client.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.class)
public abstract class ModelPartMixin {

    @Shadow public abstract void translateAndRotate(PoseStack poseStack);

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V", at = @At(value = "HEAD"))
    public void translate(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color, CallbackInfo ci) {
        RenderUtils.getHeadPos(poseStack, (ModelPart) (Object) this);
    }
}
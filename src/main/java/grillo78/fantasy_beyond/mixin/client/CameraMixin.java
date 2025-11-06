package grillo78.fantasy_beyond.mixin.client;


import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.client.RenderUtils;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow private float partialTickTime;

    @Shadow private Entity entity;

    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setPosition(DDD)V"))
    public void translate(Camera instance, double x, double y, double z) {
        RenderUtils.moveCamera(instance, x, y,z, partialTickTime, entity);
    }
}
package grillo78.fantasy_beyond.mixin.client;


import grillo78.fantasy_beyond.client.ClientUtils;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
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
        ClientUtils.moveCamera(instance, x, y,z, partialTickTime, entity);
    }
}
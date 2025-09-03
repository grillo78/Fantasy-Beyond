package grillo78.fantasy_beyond.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.NearPlane.class)
public class CameraNearPlaneMixin {

    @Mutable
    @Shadow @Final private Vec3 forward;

    @Mutable
    @Shadow @Final private Vec3 up;

    @Mutable
    @Shadow @Final private Vec3 left;

    @Inject(method = "<init>(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
    private void onInit(Vec3 pForward, Vec3 pLeft, Vec3 pUp, CallbackInfo ci){
        this.forward = this.forward.scale(0.0005);
        this.up = this.up.scale(0.0005);
        this.left = this.left.scale(0.0005);
    }
}

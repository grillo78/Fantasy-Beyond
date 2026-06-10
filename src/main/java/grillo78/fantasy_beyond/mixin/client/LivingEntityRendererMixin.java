package grillo78.fantasy_beyond.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Shadow
    public abstract EntityModel getModel();

//    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/world/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;"))
//    public void render(LivingEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
//        if (pEntity instanceof Player) {
//            ((PlayerModel) getModel()).setAllVisible(DebugUtils.shouldRenderDefaultPlayerModel());
//            if (pEntity.getOffhandItem().getItem() instanceof SpellsBook) {
//                if (pEntity.getMainArm() == HumanoidArm.RIGHT) {
//                    ((PlayerModel) getModel()).leftArm.xRot = (float) -Math.PI / 2;
//                    ((PlayerModel) getModel()).leftSleeve.xRot = (float) -Math.PI / 2;
//                    ((PlayerModel) getModel()).leftArm.yRot = 0;
//                    ((PlayerModel) getModel()).leftSleeve.yRot = 0;
//                    ((PlayerModel) getModel()).leftArm.zRot = 0;
//                    ((PlayerModel) getModel()).leftSleeve.zRot = 0;
//                } else {
//                    ((PlayerModel) getModel()).rightArm.xRot = (float) -Math.PI / 2;
//                    ((PlayerModel) getModel()).rightSleeve.xRot = (float) -Math.PI / 2;
//                    ((PlayerModel) getModel()).rightArm.yRot = 0;
//                    ((PlayerModel) getModel()).rightSleeve.yRot = 0;
//                    ((PlayerModel) getModel()).rightArm.zRot = 0;
//                    ((PlayerModel) getModel()).rightSleeve.zRot = 0;
//                }
//            }
//        }
//    }

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void getRenderType(LivingEntity pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing, CallbackInfoReturnable<RenderType> cir){
        if (pLivingEntity instanceof Player) {
            cir.setReturnValue(null);
        }
    }
}

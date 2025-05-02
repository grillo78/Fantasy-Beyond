package grillo78.fantasy_beyond.mixin.client;

import grillo78.fantasy_beyond.util.ClientUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {


    @Inject(method = "setPartVisibility", at = @At("RETURN"))
    public void render(A pModel, EquipmentSlot pSlot, CallbackInfo ci) {
        if (ClientUtil.renderingFirstPersonModel) {
            pModel.hat.visible = false;
            pModel.head.visible = false;
        }
    }
}

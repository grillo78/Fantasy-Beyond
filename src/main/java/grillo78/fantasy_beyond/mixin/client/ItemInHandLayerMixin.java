package grillo78.fantasy_beyond.mixin.client;


import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin {

    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ArmedModel;translateToHand(Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;)V"))
    public void translateToHand(ArmedModel instance, HumanoidArm humanoidArm, PoseStack poseStack, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player) {
            CharacterData data = pLivingEntity.getData(ModAttachments.CHARACTER_DATA);
            data.getPlayerCustomization().getRace().getCharacteristics().get(0).translateToArm(poseStack, humanoidArm);
        }
    }
    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    public void translate(PoseStack instance, float x, float y, float z) {
    }
}
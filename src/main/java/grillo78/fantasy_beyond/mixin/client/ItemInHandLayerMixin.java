package grillo78.fantasy_beyond.mixin.client;


import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.customization.race.Characteristic;
import grillo78.fantasy_beyond.client.entity.race.RaceCharacteristicRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin {

    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ArmedModel;translateToHand(Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;)V"))
    public void translateToHand(ArmedModel instance, HumanoidArm humanoidArm, PoseStack poseStack, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player) {
            CharacterData data = pLivingEntity.getData(ModAttachments.CHARACTER_DATA);
            Characteristic characteristic = data.getPlayerCustomization().getRace().getCharacteristics().get(0);
            RaceCharacteristicRenderer.getRenderer(characteristic.getClass()).translateToArm(poseStack, humanoidArm, characteristic, instance, pLivingEntity);
        }
    }

    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    public void translate(PoseStack instance, float x, float y, float z) {
    }
}
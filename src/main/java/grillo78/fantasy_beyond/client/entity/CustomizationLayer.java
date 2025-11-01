package grillo78.fantasy_beyond.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import grillo78.fantasy_beyond.client.entity.race.human.MaleHumanModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

public class CustomizationLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private CustomizationModel model;

    public CustomizationLayer(PlayerRenderer renderer) {
        super(renderer);
        this.model = new MaleHumanModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_HUMAN));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, AbstractClientPlayer pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        pPoseStack.pushPose();
        PlayerModel bipedModel = getParentModel();
        CharacterData data = pLivingEntity.getData(ModAttachments.CHARACTER_DATA);
        if (data.getPlayerCustomization() != null && !pLivingEntity.isInvisible())
            data.getPlayerCustomization().getRace().render(bipedModel, pPoseStack, pBuffer, pPackedLight, pLivingEntity);
        pPoseStack.popPose();
    }

}

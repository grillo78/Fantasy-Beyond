package grillo78.fantasy_beyond.client.entity.race;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import grillo78.fantasy_beyond.character.customization.race.Characteristic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import oshi.util.tuples.Pair;

import java.awt.*;
import java.util.HashMap;

public class RaceEyesCharacteristicRenderer<T extends CustomizationModel, R extends CustomizationModel> extends RaceCharacteristicRenderer<T,R> {

    public RaceEyesCharacteristicRenderer(Pair<T, R> models) {
        super(models);
    }

    @Override
    public void render(HumanoidModel model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, LivingEntity player, Characteristic characteristic) {
        CustomizationModel customizationModel = getModel(characteristic);
        setupModel(customizationModel, model, player, characteristic);
        customizationModel.renderToBuffer(poseStack, pBuffer.getBuffer(RenderType.entityTranslucent(characteristic.getTexture(player, false))), pPackedLight, OverlayTexture.NO_OVERLAY, characteristic.getColor().hashCode());
        customizationModel.renderToBuffer(poseStack, pBuffer.getBuffer(RenderType.entityTranslucent(characteristic.getTexture(player, true))), pPackedLight, OverlayTexture.NO_OVERLAY, Color.WHITE.hashCode());
    }
}

package grillo78.fantasy_beyond.client.entity.race;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.character.customization.race.Characteristic;
import grillo78.fantasy_beyond.character.customization.race.Race;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import oshi.util.tuples.Pair;

import java.util.HashMap;

public class RaceCharacteristicRenderer<T extends CustomizationModel, R extends CustomizationModel> {
    private final static HashMap<Class, RaceCharacteristicRenderer> RENDERERS = new HashMap<>();

    public static void registerRenderer(Class aClass, RaceCharacteristicRenderer renderer) {
        RENDERERS.put(aClass, renderer);
    }

    private Pair<T, R> models;

    public RaceCharacteristicRenderer(Pair<T, R> models) {
        this.models = models;
    }

    public static RaceCharacteristicRenderer<CustomizationModel, CustomizationModel> getRenderer(Class<? extends Characteristic> aClass) {
        return RENDERERS.get(aClass);
    }

    @OnlyIn(Dist.CLIENT)
    public void translateToArm(PoseStack poseStack, HumanoidArm arm, Characteristic characteristic, ArmedModel model, LivingEntity entity) {
        Race race = characteristic.getPlayerCustomization().getRace();
        setupModel(getModel(characteristic), (HumanoidModel<Player>) model, entity, characteristic);
        if(getModel(characteristic).getRoot() != null)
        getModel(characteristic).getRoot().translateAndRotate(poseStack);
        getModel(characteristic).getArmPart(arm).translateAndRotate(poseStack);
        boolean flag = arm == HumanoidArm.LEFT;
        poseStack.translate((float) (flag ? -race.getArmOffset().x : race.getArmOffset().x) , race.getArmOffset().y, race.getArmOffset().z);
        poseStack.scale(1.1F,1.1F,1.1F);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(HumanoidModel model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, LivingEntity player, Characteristic characteristic) {
        CustomizationModel customizationModel = getModel(characteristic);
        setupModel(customizationModel, model, player, characteristic);
        customizationModel.renderToBuffer(poseStack, pBuffer.getBuffer(RenderType.entityTranslucent(characteristic.getTexture(player))), pPackedLight, OverlayTexture.NO_OVERLAY, characteristic.getColor().hashCode());
    }

    public void setupModel(CustomizationModel customizationModel, HumanoidModel<Player> model, LivingEntity player, Characteristic characteristic) {
        customizationModel.young = characteristic.getPlayerCustomization().isYoung();
        customizationModel.setModelProperties(player);
        customizationModel.setupModel(model, player, Minecraft.getInstance().getTimer().getGameTimeDeltaTicks());
    }

    public CustomizationModel getModel(Characteristic characteristic) {
            return characteristic.getPlayerCustomization().isMale() ? models.getA() : models.getB();
    }
}

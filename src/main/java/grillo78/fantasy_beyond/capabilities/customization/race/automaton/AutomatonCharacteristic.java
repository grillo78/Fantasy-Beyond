package grillo78.fantasy_beyond.capabilities.customization.race.automaton;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Characteristic;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import grillo78.fantasy_beyond.client.entity.race.automaton.AutomatonModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

public class AutomatonCharacteristic extends Characteristic {

    @OnlyIn(Dist.CLIENT)
    private CustomizationModel maleModel;

    public AutomatonCharacteristic(PlayerCustomization playerCustomization, String name, int maxVariant) {
        super(playerCustomization, name, maxVariant);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            this.maleModel = new AutomatonModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.AUTOMATON));
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void translateToArm(PoseStack poseStack, HumanoidArm arm) {
        poseStack.translate(0,1.55,0);
        maleModel.getArmPart(arm).translateAndRotate(poseStack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public CustomizationModel getModel() {
        return maleModel;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(PlayerModel<Player> model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, Player player) {
        CustomizationModel customizationModel = maleModel;
        customizationModel.young = getPlayerCustomization().isYoung();
        customizationModel.setModelProperties(player);
        customizationModel.setupModel(model);
        customizationModel.renderToBuffer(poseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTexture())), pPackedLight, OverlayTexture.NO_OVERLAY, getColor().getRed() / 255F, getColor().getGreen() / 255F, getColor().getBlue() / 255F, 1);
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(FantasyBeyond.MOD_ID, "textures/entity/customization/race/automaton/" + name + "/" + getVariant() + ".png");
    }
}

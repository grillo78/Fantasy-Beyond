package grillo78.fantasy_beyond.capabilities.customization.race.elf;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Characteristic;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.*;
import grillo78.fantasy_beyond.client.entity.race.elf.FemaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.MaleElfModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

public class ElfCharacteristic extends Characteristic {

    @OnlyIn(Dist.CLIENT)
    private CustomizationModel maleModel;
    @OnlyIn(Dist.CLIENT)
    private CustomizationModel femaleModel;

    public ElfCharacteristic(PlayerCustomization playerCustomization, String name) {
        super(playerCustomization,name);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->{
            this.maleModel = new MaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_ELF));
            this.femaleModel = new FemaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_ELF));
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(PlayerModel<Player> model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, Player player) {
        CustomizationModel customizationModel = getPlayerCustomization().isMale()? maleModel : femaleModel;
        customizationModel.young = getPlayerCustomization().isYoung();
        customizationModel.setModelProperties(player);
        customizationModel.setupModel(model);
        customizationModel.renderToBuffer(poseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTexture())),pPackedLight, OverlayTexture.NO_OVERLAY, 1,1,1,1);
    }

    public ResourceLocation getTexture(){
        return new ResourceLocation(FantasyBeyond.MOD_ID, "textures/entity/customization/race/elf/"+name+"/"+((getPlayerCustomization().isMale()? "male":"female")+"_")+getVariant()+".png");
    }
}

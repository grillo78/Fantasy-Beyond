package grillo78.fantasy_beyond.capabilities.customization.race.pixy;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.client.texture.AlphaMaskTexture;
import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.items.Cloth;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Characteristic;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import grillo78.fantasy_beyond.client.entity.race.elf.FemaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.MaleElfModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PixyCharacteristic extends Characteristic {

    @OnlyIn(Dist.CLIENT)
    private CustomizationModel maleModel;
    @OnlyIn(Dist.CLIENT)
    private CustomizationModel femaleModel;

    public PixyCharacteristic(PlayerCustomization playerCustomization, String name, int maxVariants) {
        super(playerCustomization,name, maxVariants);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->{
            this.maleModel = new MaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_ELF));
            this.femaleModel = new FemaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_ELF));
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public CustomizationModel getModel() {
        return getPlayerCustomization().isMale()? maleModel : femaleModel;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void translateToArm(PoseStack poseStack, HumanoidArm arm) {
        if(getPlayerCustomization().isMale())
            maleModel.getArmPart(arm).translateAndRotate(poseStack);
        else
            femaleModel.getArmPart(arm).translateAndRotate(poseStack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(PlayerModel<Player> model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, Player player) {
        CustomizationModel customizationModel = getModel();
        customizationModel.young = getPlayerCustomization().isYoung();
        customizationModel.setModelProperties(player);
        customizationModel.setupModel(model);
//        customizationModel.renderToBuffer(poseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTexture(player))), pPackedLight, OverlayTexture.NO_OVERLAY, getColor().getRed() / 255F, getColor().getGreen() / 255F, getColor().getBlue() / 255F, 1);
    }

    public ResourceLocation getTexture(Player player){
        ResourceLocation baseTexture = new ResourceLocation(FantasyBeyond.MOD_ID, "textures/entity/customization/race/elf/"+name+"/"+((getPlayerCustomization().isMale()? "male":"female")+"_")+getVariant()+".png");

        List<ResourceLocation> masks = new ArrayList<>();
        AtomicReference<String> append = new AtomicReference<>("");
        player.getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(clothes->{
            for (int i = 0; i < clothes.getInventory().getSlots(); i++) {
                Item item = clothes.getInventory().getStackInSlot(i).getItem();
                if(item instanceof Cloth) {
                    masks.add(((Cloth) item).getAlphaMask(player));
                    append.set(append.get() + ForgeRegistries.ITEMS.getKey(item).toString().replace(":", "_"));
                }
            }
        });

        return AlphaMaskTexture.getTexture(baseTexture,  new ResourceLocation(ClothesMod.MOD_ID, baseTexture.getPath() + append), masks);
    }
}

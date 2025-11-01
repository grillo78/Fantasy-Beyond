package grillo78.fantasy_beyond.character.customization.race.elf;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Characteristic;
import grillo78.fantasy_beyond.client.clothes.RacistClothItemRenderer;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import grillo78.fantasy_beyond.client.entity.race.elf.FemaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.MaleElfModel;
import grillo78.fantasy_beyond.client.texture.AlphaMaskTexture;
import grillo78.fantasy_beyond.items.RacistClothItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLLoader;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ElfCharacteristic extends Characteristic {

    @OnlyIn(Dist.CLIENT)
    private CustomizationModel maleModel;
    @OnlyIn(Dist.CLIENT)
    private CustomizationModel femaleModel;

    public ElfCharacteristic(PlayerCustomization playerCustomization, String name, int maxVariants) {
        super(playerCustomization,name, maxVariants);
        if (FMLLoader.getDist() == Dist.CLIENT){
            this.maleModel = new MaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_ELF));
            this.femaleModel = new FemaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_ELF));
        }
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
        CustomizationModel customizationModel = getModel();;
        customizationModel.young = getPlayerCustomization().isYoung();
        customizationModel.setModelProperties(player);
        customizationModel.setupModel(model);
        customizationModel.renderToBuffer(poseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTexture(player))), pPackedLight, OverlayTexture.NO_OVERLAY, getColor().hashCode());
    }

    public ResourceLocation getTexture(Player player){
        ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/entity/customization/race/elf/"+name+"/"+((getPlayerCustomization().isMale()? "male":"female")+"_")+getVariant()+".png");

        List<ResourceLocation> masks = new ArrayList<>();
        AtomicReference<String> append = new AtomicReference<>("");

        ICuriosItemHandler inventory = CuriosApi.getCuriosInventory(player).get();
        for (SlotResult slot : inventory.findCurios(itemStack -> itemStack.getItem() instanceof RacistClothItem)) {
            masks.add(RacistClothItemRenderer.getAlphaMask(player, (RacistClothItem) slot.stack().getItem()));
            append.set(append.get() + BuiltInRegistries.ITEM.getKey(slot.stack().getItem()).toString().replace(":", "_"));
        }

        return AlphaMaskTexture.getTexture(baseTexture, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, baseTexture.getPath() + append), masks);
    }
}

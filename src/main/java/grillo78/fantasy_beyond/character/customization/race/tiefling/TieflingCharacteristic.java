package grillo78.fantasy_beyond.character.customization.race.tiefling;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Characteristic;
import grillo78.fantasy_beyond.client.clothes.RacistClothItemRenderer;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import grillo78.fantasy_beyond.client.entity.race.orc.FemaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.orc.MaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.FemaleTieflingModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.MaleTieflingModel;
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

public class TieflingCharacteristic extends Characteristic {

    @OnlyIn(Dist.CLIENT)
    private CustomizationModel femaleModel;
    @OnlyIn(Dist.CLIENT)
    private CustomizationModel maleModel;

    public TieflingCharacteristic(PlayerCustomization playerCustomization, String name, int maxVariant) {
        super(playerCustomization, name, maxVariant);
        if (FMLLoader.getDist() == Dist.CLIENT) {
            this.maleModel = new MaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_TIEFLING));
            this.femaleModel = new FemaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_TIEFLING));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public CustomizationModel getModel() {
        return getPlayerCustomization().isMale() ? maleModel : femaleModel;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void translateToArm(PoseStack poseStack, HumanoidArm arm) {
        if (getPlayerCustomization().isMale())
            maleModel.getArmPart(arm).translateAndRotate(poseStack);
        else
            femaleModel.getArmPart(arm).translateAndRotate(poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        boolean flag = arm == HumanoidArm.LEFT;
        poseStack.translate((float) (flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(PlayerModel<Player> model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, Player player) {
        CustomizationModel customizationModel = getModel();
        customizationModel.young = getPlayerCustomization().isYoung();
        customizationModel.setModelProperties(player);
        customizationModel.setupModel(model, player, Minecraft.getInstance().getTimer().getGameTimeDeltaTicks());
        customizationModel.renderToBuffer(poseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTexture(player))), pPackedLight, OverlayTexture.NO_OVERLAY, getColor().hashCode());
    }

    public ResourceLocation getTexture(Player player) {
        ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/entity/customization/race/tiefling/" + name + "/" + (getPlayerCustomization().isMale() ? "male_" : "female_") + getVariant() + ".png");

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

package grillo78.fantasy_beyond.client.clothes;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.customization.race.Characteristic;
import grillo78.fantasy_beyond.character.customization.race.Race;
import grillo78.fantasy_beyond.character.customization.race.RaceType;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import grillo78.fantasy_beyond.client.entity.race.RaceCharacteristicRenderer;
import grillo78.fantasy_beyond.client.entity.race.dwarf.FemaleDwarfModel;
import grillo78.fantasy_beyond.client.entity.race.dwarf.MaleDwarfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.FemaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.MaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.human.FemaleHumanModel;
import grillo78.fantasy_beyond.client.entity.race.human.MaleHumanModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.FemaleMerfolkModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.MaleMerfolkModel;
import grillo78.fantasy_beyond.client.entity.race.orc.FemaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.orc.MaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.FemaleTieflingModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.MaleTieflingModel;
import grillo78.fantasy_beyond.client.texture.AlphaMaskTexture;
import grillo78.fantasy_beyond.items.RacistClothItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import oshi.util.tuples.Pair;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class QuiverRenderer implements ICurioRenderer {
    private final static HashMap<RaceType, Pair<CustomizationModel, CustomizationModel>> RACE_MODELS = new HashMap<>();

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack pPoseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource pBuffer, int pPackedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        pPoseStack.pushPose();
        if (slotContext.entity().hasData(ModAttachments.CHARACTER_DATA.get())) {
            Vec3 offset = slotContext.entity().getData(ModAttachments.CHARACTER_DATA.get()).getPlayerCustomization().getRace().quiverOffset();
            Vec3 angles = slotContext.entity().getData(ModAttachments.CHARACTER_DATA.get()).getPlayerCustomization().getRace().quiverAngles();
            pPoseStack.translate(offset.x,offset.y, offset.z);
            pPoseStack.mulPose(new Quaternionf().rotateX((float) Math.toRadians(180)).rotateXYZ((float) angles.x, (float) angles.y, (float) angles.z));
        } else {

            pPoseStack.translate(0, 0, 4F / 16F);
            pPoseStack.mulPose(new Quaternionf().rotateX((float) Math.toRadians(180)));
        }
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.NONE, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, slotContext.entity().level(), 0);
        pPoseStack.popPose();
    }
}

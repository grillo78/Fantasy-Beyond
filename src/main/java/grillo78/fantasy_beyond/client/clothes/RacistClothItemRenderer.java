package grillo78.fantasy_beyond.client.clothes;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.customization.race.Race;
import grillo78.fantasy_beyond.character.customization.race.RaceType;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
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
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

public class RacistClothItemRenderer implements ICurioRenderer {
    private final static HashMap<RaceType, Pair<CustomizationModel, CustomizationModel>> RACE_MODELS = new HashMap<>();

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack pPoseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource pBuffer, int pPackedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (RACE_MODELS.size() == 0)
            registerModels();
        if (slotContext.entity().hasData(ModAttachments.CHARACTER_DATA)) {
            CharacterData data = slotContext.entity().getData(ModAttachments.CHARACTER_DATA);
            Race race = data.getPlayerCustomization().getRace();
            if (RACE_MODELS.containsKey(race.getType())) {
                CustomizationModel model = data.getPlayerCustomization().isMale() ? RACE_MODELS.get(race.getType()).getA() : RACE_MODELS.get(race.getType()).getB();
                model.young = data.getPlayerCustomization().isYoung();
                model.setModelProperties(slotContext.entity());
                if (data.getPlayerCustomization().getRace().getCharacteristics().get(0).getModel().getClass() == model.getClass())
                    model.copyFrom(data.getPlayerCustomization().getRace().getCharacteristics().get(0).getModel());
                model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTexture(slotContext.entity(), (RacistClothItem) stack.getItem(), slotContext.identifier()))), pPackedLight, OverlayTexture.NO_OVERLAY, Color.WHITE.hashCode());
            }
        }
    }

    protected ResourceLocation getTexture(LivingEntity entity, RacistClothItem item, String indentifier) {
        CharacterData data = entity.getData(ModAttachments.CHARACTER_DATA);
        ResourceLocation baseTexture = getBaseTexture(data, item);

        List<ResourceLocation> masks = new ArrayList<>();
        AtomicReference<String> append = new AtomicReference<>("");

        ICuriosItemHandler inventory = CuriosApi.getCuriosInventory(entity).get();

        switch (indentifier) {
            case "shirt":
                addMasks(inventory.findCurios("head"), append, masks, entity);
                addMasks(inventory.findCurios("wrist"), append, masks, entity);
                addMasks(inventory.findCurios("pants"), append, masks, entity);
            case "pants":
                addMasks(inventory.findCurios("belt"), append, masks, entity);
            case "wrist":
            case "head":
                addMasks(inventory.findCurios("jacket"), append, masks, entity);
                break;
        }

        return AlphaMaskTexture.getTexture(baseTexture, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, baseTexture.getPath() + append), masks);
    }

    private void addMasks(List<SlotResult> results, AtomicReference<String> append, List<ResourceLocation> masks, Entity player) {
        for (int i = 0; i < results.size(); i++) {
            Item item = results.get(i).stack().getItem();
            if (item instanceof RacistClothItem) {
                masks.add(getAlphaMask(player, (RacistClothItem) item));
                append.set(append.get() + BuiltInRegistries.ITEM.getKey(item).toString().replace(":", "_"));
            }
        }
    }

    private static ResourceLocation getBaseTexture(CharacterData data, RacistClothItem item) {
        boolean isForMale = item.isForMale();
        if (item.isGenderless()) {
            isForMale = data.getPlayerCustomization().isMale();
        }
        return ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.ITEM.getKey(item).getNamespace(), "textures/entity/clothes/" + (isForMale ? "male" : "female") + "/" + BuiltInRegistries.ITEM.getKey(item).getPath() + ".png");
    }

    public static ResourceLocation getAlphaMask(Entity entity, RacistClothItem item) {
        CharacterData data = entity.getData(ModAttachments.CHARACTER_DATA);

        ResourceLocation texture = getBaseTexture(data, item);

        return !item.hasMask() ? null : ResourceLocation.fromNamespaceAndPath(texture.getNamespace(), texture.getPath().replace(".png", "_a.png"));
    }

    public static void registerRaceModels(RaceType race, CustomizationModel maleModel, CustomizationModel femaleModel) {
        RACE_MODELS.put(race, new Pair<>(maleModel, femaleModel));
    }

    private void registerModels() {
        RacistClothItemRenderer.registerRaceModels(RaceType.HUMAN, new MaleHumanModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_HUMAN)),
                new FemaleHumanModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_HUMAN)));
        RacistClothItemRenderer.registerRaceModels(RaceType.ELF, new MaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_ELF)),
                new FemaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_ELF)));
        RacistClothItemRenderer.registerRaceModels(RaceType.ORC, new MaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_ORC)),
                new FemaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_ORC)));
        RacistClothItemRenderer.registerRaceModels(RaceType.DWARF, new MaleDwarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_DWARF)),
                new FemaleDwarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_DWARF)));
        RacistClothItemRenderer.registerRaceModels(RaceType.MERFOLKF, new MaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_MERFLOK)),
                new FemaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_MERFLOK)));
        RacistClothItemRenderer.registerRaceModels(RaceType.TIEFLING, new MaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_TIEFLING)),
                new FemaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_TIEFLING)));
    }
}

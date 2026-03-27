package grillo78.fantasy_beyond.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.customization.race.dwarf.DwarfCharacteristic;
import grillo78.fantasy_beyond.character.customization.race.dwarf.DwarfColoreableCharacteristic;
import grillo78.fantasy_beyond.character.customization.race.dwarf.DwarfEyes;
import grillo78.fantasy_beyond.character.customization.race.elf.ElfCharacteristic;
import grillo78.fantasy_beyond.character.customization.race.elf.ElfColoreableCharacteristic;
import grillo78.fantasy_beyond.character.customization.race.elf.ElfEyes;
import grillo78.fantasy_beyond.character.customization.race.human.HumanCharacteristic;
import grillo78.fantasy_beyond.character.customization.race.human.HumanColoreableCharacteristic;
import grillo78.fantasy_beyond.character.customization.race.human.HumanEyes;
import grillo78.fantasy_beyond.character.customization.race.merfolk.MerfolkCharacteristic;
import grillo78.fantasy_beyond.character.customization.race.merfolk.MerfolkColoreableCharacteristic;
import grillo78.fantasy_beyond.character.customization.race.merfolk.MerfolkEyes;
import grillo78.fantasy_beyond.character.customization.race.merfolk.MerfolkTailCharacteristic;
import grillo78.fantasy_beyond.character.customization.race.orc.OrcCharacteristic;
import grillo78.fantasy_beyond.character.customization.race.orc.OrcColoreableCharacteristic;
import grillo78.fantasy_beyond.character.customization.race.orc.OrcEyes;
import grillo78.fantasy_beyond.character.customization.race.tiefling.*;
import grillo78.fantasy_beyond.client.ClientUtils;
import grillo78.fantasy_beyond.client.entity.race.RaceCharacteristicRenderer;
import grillo78.fantasy_beyond.client.entity.race.RaceEyesCharacteristicRenderer;
import grillo78.fantasy_beyond.client.entity.race.dwarf.FemaleDwarfModel;
import grillo78.fantasy_beyond.client.entity.race.dwarf.MaleDwarfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.FemaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.MaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.human.FemaleHumanModel;
import grillo78.fantasy_beyond.client.entity.race.human.MaleHumanModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.FemaleMerfolkModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.MaleMerfolkModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.tails.MerfolkTailCharacteristicRenderer;
import grillo78.fantasy_beyond.client.entity.race.orc.FemaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.orc.MaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.FemaleTieflingModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.MaleTieflingModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.TieflingHornsCharacteristicRenderer;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTailCharacteristicRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import oshi.util.tuples.Pair;

public class CustomizationLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public CustomizationLayer(PlayerRenderer renderer) {
        super(renderer);
        registerRenderers();
    }

    private void registerRenderers() {
        //Tiefling
        RaceCharacteristicRenderer.registerRenderer(TieflingCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_TIEFLING)),
                new FemaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_TIEFLING)))));
        RaceCharacteristicRenderer.registerRenderer(TieflingColoreableCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_TIEFLING)),
                new FemaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_TIEFLING)))));
        RaceCharacteristicRenderer.registerRenderer(TieflingEyes.class, new RaceEyesCharacteristicRenderer(new Pair<>(
                new MaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_SECOND_TIEFLING)),
                new FemaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_SECOND_TIEFLING)))));
        RaceCharacteristicRenderer.registerRenderer(TieflingHornsCharacteristic.class, new TieflingHornsCharacteristicRenderer());
        RaceCharacteristicRenderer.registerRenderer(TieflingTailCharacteristic.class, new TieflingTailCharacteristicRenderer());

        //Human
        RaceCharacteristicRenderer.registerRenderer(HumanCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleHumanModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_HUMAN)),
                new FemaleHumanModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_HUMAN)))));
        RaceCharacteristicRenderer.registerRenderer(HumanColoreableCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleHumanModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_HUMAN)),
                new FemaleHumanModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_HUMAN)))));
        RaceCharacteristicRenderer.registerRenderer(HumanEyes.class, new RaceEyesCharacteristicRenderer(new Pair<>(
                new MaleHumanModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_SECOND_HUMAN)),
                new FemaleHumanModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_SECOND_HUMAN)))));

        //Orc
        RaceCharacteristicRenderer.registerRenderer(OrcCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_ORC)),
                new FemaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_ORC)))));
        RaceCharacteristicRenderer.registerRenderer(OrcColoreableCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_ORC)),
                new FemaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_ORC)))));
        RaceCharacteristicRenderer.registerRenderer(OrcEyes.class, new RaceEyesCharacteristicRenderer(new Pair<>(
                new MaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_SECOND_ORC)),
                new FemaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_SECOND_ORC)))));

        //Dwarf
        RaceCharacteristicRenderer.registerRenderer(DwarfCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleDwarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_DWARF)),
                new FemaleDwarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_DWARF)))));
        RaceCharacteristicRenderer.registerRenderer(DwarfColoreableCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleDwarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_DWARF)),
                new FemaleDwarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_DWARF)))));
        RaceCharacteristicRenderer.registerRenderer(DwarfEyes.class, new RaceEyesCharacteristicRenderer(new Pair<>(
                new MaleDwarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_SECOND_DWARF)),
                new FemaleDwarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_SECOND_DWARF)))));

        //Elf
        RaceCharacteristicRenderer.registerRenderer(ElfCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_ELF)),
                new FemaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_ELF)))));
        RaceCharacteristicRenderer.registerRenderer(ElfColoreableCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_ELF)),
                new FemaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_ELF)))));
        RaceCharacteristicRenderer.registerRenderer(ElfEyes.class, new RaceEyesCharacteristicRenderer(new Pair<>(
                new MaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_SECOND_ELF)),
                new FemaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_SECOND_ELF)))));

        //Merfolk
        RaceCharacteristicRenderer.registerRenderer(MerfolkCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_MERFOLK)),
                new FemaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_MERFOLK)))));
        RaceCharacteristicRenderer.registerRenderer(MerfolkColoreableCharacteristic.class, new RaceCharacteristicRenderer(new Pair<>(
                new MaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_MERFOLK)),
                new FemaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_MERFOLK)))));
        RaceCharacteristicRenderer.registerRenderer(MerfolkEyes.class, new RaceEyesCharacteristicRenderer(new Pair<>(
                new MaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MALE_SECOND_MERFOLK)),
                new FemaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FEMALE_SECOND_MERFOLK)))));
        RaceCharacteristicRenderer.registerRenderer(MerfolkTailCharacteristic.class, new MerfolkTailCharacteristicRenderer());
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, AbstractClientPlayer pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        pPoseStack.pushPose();
        PlayerModel bipedModel = getParentModel();
        CharacterData data = pLivingEntity.getData(ModAttachments.CHARACTER_DATA);
        if (data.getPlayerCustomization() != null && !pLivingEntity.isInvisible())
            data.getPlayerCustomization().getRace().getCharacteristics().forEach(characteristic -> RaceCharacteristicRenderer.getRenderer(characteristic.getClass()).render(bipedModel, pPoseStack, pBuffer, pPackedLight, pLivingEntity, characteristic));
        pPoseStack.popPose();
    }

}

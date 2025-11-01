package grillo78.fantasy_beyond.client;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.race.RaceType;
import grillo78.fantasy_beyond.client.clothes.RacistClothItemRenderer;
import grillo78.fantasy_beyond.client.entity.CustomizationLayer;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.automaton.AutomatonModel;
import grillo78.fantasy_beyond.client.entity.race.dwarf.FemaleDwarfModel;
import grillo78.fantasy_beyond.client.entity.race.dwarf.MaleDwarfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.FemaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.MaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.human.FemaleHumanModel;
import grillo78.fantasy_beyond.client.entity.race.human.MaleHumanModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.FemaleMerfolkModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.MaleMerfolkModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.tails.MerfolkTail1Model;
import grillo78.fantasy_beyond.client.entity.race.orc.FemaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.orc.MaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.FemaleTieflingModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.MaleTieflingModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.BigHornsModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.MediumHornsModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.TallHornsModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail1Model;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail2Model;
import grillo78.fantasy_beyond.items.ModItems;
import grillo78.fantasy_beyond.items.RacistClothItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import oshi.util.tuples.Pair;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import static net.neoforged.neoforge.client.event.EntityRenderersEvent.*;

@Mod(value = FantasyBeyond.MOD_ID, dist = Dist.CLIENT)
public class FantasyBeyondClient {
    public FantasyBeyondClient(ModContainer container) {
        container.getEventBus().addListener(this::clientSetup);
        container.getEventBus().addListener(this::registerLayerDefinitions);
        container.getEventBus().addListener(this::addLayers);
        NeoForge.EVENT_BUS.addListener(this::renderFirstPersonHand);
        NeoForge.EVENT_BUS.addListener(this::renderLevelLast);
        NeoForge.EVENT_BUS.addListener(this::renderHUD);
    }

    @OnlyIn(Dist.CLIENT)
    private void renderLevelLast(final RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            RenderUtils.renderFirstPersonModel(event);
        }
    }
    @OnlyIn(Dist.CLIENT)
    private void renderHUD(RenderGuiLayerEvent.Pre event) {
        if (!Minecraft.getInstance().player.isCreative() && !Minecraft.getInstance().player.isSpectator()) {
            ResourceLocation type = event.getName();
            switch (type.getPath()) {
                case "player_health":
                    RenderUtils.renderHealth(event);
//                case "crosshair":
                case "boss_event_progress":
                case "armor_level":
                case "experience_bar":
                case "experience_level":
                case "food_level":
                case "air_level":
                case "potion_icons":
                    event.setCanceled(true);
                    break;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void renderFirstPersonHand(RenderHandEvent event) {
        event.setCanceled(true);
    }

    private void clientSetup(FMLClientSetupEvent  event) {
        for (int i = 0; i < RacistClothItem.CLOTHES.size(); i++) {
            CuriosRendererRegistry.register(RacistClothItem.CLOTHES.get(i), () -> new RacistClothItemRenderer());
        }
    }

    private void addLayers(EntityRenderersEvent.AddLayers event) {
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().forEach((s, renderer) -> {
            ((PlayerRenderer) renderer).addLayer(new CustomizationLayer((PlayerRenderer) renderer));
        });
    }

    @OnlyIn(Dist.CLIENT)
    public void registerLayerDefinitions(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.MALE_HUMAN, () -> MaleHumanModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_HUMAN, () -> MaleHumanModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_HUMAN, () -> FemaleHumanModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_HUMAN, () -> FemaleHumanModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.MALE_ELF, () -> MaleElfModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_ELF, () -> MaleElfModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_ELF, () -> FemaleElfModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_ELF, () -> FemaleElfModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.MALE_MERFLOK, () -> MaleMerfolkModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_MERFLOK, () -> MaleMerfolkModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_MERFLOK, () -> FemaleMerfolkModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_MERFLOK, () -> FemaleMerfolkModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.MALE_ORC, () -> MaleOrcModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_ORC, () -> MaleOrcModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_ORC, () -> FemaleOrcModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_ORC, () -> FemaleOrcModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.MALE_DWARF, () -> MaleDwarfModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_DWARF, () -> MaleDwarfModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_DWARF, () -> FemaleDwarfModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_DWARF, () -> FemaleDwarfModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.AUTOMATON, () -> AutomatonModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_AUTOMATON, () -> AutomatonModel.createBodyLayer(new CubeDeformation(0.1F)));

        event.registerLayerDefinition(ModModelLayers.MALE_TIEFLING, () -> MaleTieflingModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_TIEFLING, () -> MaleTieflingModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_TIEFLING, () -> FemaleTieflingModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_TIEFLING, () -> FemaleTieflingModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.BIG_HORNS, () -> BigHornsModel.createBodyLayer());
        event.registerLayerDefinition(ModModelLayers.MEDIUM_HORNS, () -> MediumHornsModel.createBodyLayer());
        event.registerLayerDefinition(ModModelLayers.TALL_HORNS, () -> TallHornsModel.createBodyLayer());
        event.registerLayerDefinition(ModModelLayers.TIEFLING_TAIL_1, () -> TieflingTail1Model.createBodyLayer());
        event.registerLayerDefinition(ModModelLayers.TIEFLING_TAIL_2, () -> TieflingTail2Model.createBodyLayer());
        event.registerLayerDefinition(ModModelLayers.MERFOLK_TAIL_1, () -> MerfolkTail1Model.createBodyLayer());

//        event.registerLayerDefinition(ModModelLayers.GOBLIN, GoblinModel::createBodyLayer);
//        event.registerLayerDefinition(ModModelLayers.LOPUS, LopusModel::createBodyLayer);
//        event.registerLayerDefinition(ModModelLayers.MIMIC, MimicModel::createBodyLayer);
    }
}

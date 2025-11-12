package grillo78.fantasy_beyond.client;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
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
import grillo78.fantasy_beyond.client.screen.CharacterScreen;
import grillo78.fantasy_beyond.items.ItemContainer;
import grillo78.fantasy_beyond.items.QuiverItem;
import grillo78.fantasy_beyond.items.RacistClothItem;
import grillo78.fantasy_beyond.items.components.ItemContents;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.items.components.QuiverContents;
import grillo78.fantasy_beyond.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.List;

import static net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

@Mod(value = FantasyBeyond.MOD_ID, dist = Dist.CLIENT)
public class FantasyBeyondClient {
    public FantasyBeyondClient(ModContainer container) {
        container.getEventBus().addListener(this::clientSetup);
        container.getEventBus().addListener(this::registerLayerDefinitions);
        container.getEventBus().addListener(this::registerRenderers);
        container.getEventBus().addListener(this::registerBindings);
        container.getEventBus().addListener(this::addLayers);
        NeoForge.EVENT_BUS.addListener(this::renderFirstPersonHand);
        NeoForge.EVENT_BUS.addListener(this::onClientTick);
        NeoForge.EVENT_BUS.addListener(this::renderLevelLast);
        NeoForge.EVENT_BUS.addListener(this::renderHUD);
        NeoForge.EVENT_BUS.addListener(this::scrollMouseScreen);
    }

    private void scrollMouseScreen(ScreenEvent.MouseScrolled.Pre event) {
        if (event.getScreen() instanceof AbstractContainerScreen && ((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse() != null && ((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse().hasItem()) {
            if (((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse().getItem().getItem() instanceof ItemContainer) {
                event.setCanceled(true);
                ItemContents itemContents = ((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse().getItem().get(ModDataComponents.ITEM_CONTENTS);
                List<Item> containedItems = itemContents.getContainedItems();
                if (!containedItems.isEmpty()) {
                    int index = containedItems.contains(itemContents.getSelectedItem()) ? getIndex(containedItems, itemContents, (int) -(event.getScrollDeltaY())) : containedItems.size() - 1;
                    itemContents.setSelectedItem(containedItems.get(index));
                }
                ((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse().getItem().set(ModDataComponents.ITEM_CONTENTS, new ItemContents(List.copyOf(itemContents.getItems()), itemContents.getSelectedItem()));
                ((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse().setChanged();
                boolean otherContainer = ((AbstractContainerScreen<?>) event.getScreen()).getSlotUnderMouse().container == Minecraft.getInstance().player.containerMenu.slots.get(0).container;
                PacketDistributor.sendToServer(new UpdateItemContainerItem(((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse().getContainerSlot(), otherContainer, BuiltInRegistries.ITEM.getKey(itemContents.getSelectedItem()).toString()));
            }
            if (((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse().getItem().getItem() instanceof QuiverItem) {
                event.setCanceled(true);
                QuiverContents itemContents = ((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse().getItem().get(ModDataComponents.QUIVER_CONTENTS);
                int index = itemContents.getIndex() >= itemContents.getItems().size() ? itemContents.getItems().size() - 1 : getIndex(itemContents, (int) -(event.getScrollDeltaY()));
                ((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse().getItem().set(ModDataComponents.QUIVER_CONTENTS, new QuiverContents(List.copyOf(itemContents.getItems()), index));
                ((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse().setChanged();
                boolean otherContainer = ((AbstractContainerScreen<?>) event.getScreen()).getSlotUnderMouse().container == Minecraft.getInstance().player.containerMenu.slots.get(0).container;
                PacketDistributor.sendToServer(new UpdateQuiverIndex(((AbstractContainerScreen) event.getScreen()).getSlotUnderMouse().getContainerSlot(), otherContainer, index));
            }
        }
    }

    private static int getIndex(List<Item> containedItems, ItemContents itemContents, int direction) {
        int index = containedItems.indexOf(itemContents.getSelectedItem());
        for (int i = 0; i < Math.abs(direction); i++) {
            if (direction > 0) {
                if (index < containedItems.size() - 1)
                    index++;
                else
                    index = 0;
            } else {
                if (index > 0)
                    index--;
                else
                    index = containedItems.size() - 1;
            }
        }

        return index;
    }

    private static int getIndex(QuiverContents itemContents, int direction) {
        int index = itemContents.getIndex();
        for (int i = 0; i < Math.abs(direction); i++) {
            if (direction > 0) {
                if (index < itemContents.getItems().size() - 1)
                    index++;
                else
                    index = 0;
            } else {
                if (index > 0)
                    index--;
                else
                    index = itemContents.getItems().size() - 1;
            }
        }

        return index;
    }

    private void onClientTick(ClientTickEvent.Post event) {
        if (ModKeybinds.OPEN_CHARACTER_SCREEN.get().consumeClick())
            Minecraft.getInstance().setScreen(new CharacterScreen());
        if (ModKeybinds.NEXT_QUIVER_INDEX.get().consumeClick())
            PacketDistributor.sendToServer(new ChangeQuiverIndex(1));
        if (ModKeybinds.PREVIOUS_QUIVER_INDEX.get().consumeClick())
            PacketDistributor.sendToServer(new ChangeQuiverIndex(-1));
        if (ModKeybinds.NEXT_ABILITY_INDEX.get().consumeClick())
            PacketDistributor.sendToServer(new ChangeAbilityIndex(1));
        if (ModKeybinds.PREVIOUS_ABILITY_INDEX.get().consumeClick())
            PacketDistributor.sendToServer(new ChangeAbilityIndex(-1));
        if (ModKeybinds.ACTIVATE_ABILITY.get().consumeClick())
            PacketDistributor.sendToServer(new ActivateAbility(Minecraft.getInstance().player.getId()));
    }

    @OnlyIn(Dist.CLIENT)
    private void renderLevelLast(final RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES && Minecraft.getInstance().options.getCameraType().isFirstPerson() && Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasData(ModAttachments.CHARACTER_DATA)) {
            ClientUtils.renderFirstPersonModel(event);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void renderHUD(RenderGuiLayerEvent.Pre event) {
        if (!Minecraft.getInstance().player.isCreative() && !Minecraft.getInstance().player.isSpectator()) {
            ResourceLocation type = event.getName();
            switch (type.getPath()) {
                case "player_health":
                    ClientUtils.renderHealth(event);
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

    private void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(ModKeybinds.OPEN_CHARACTER_SCREEN.get());
        event.register(ModKeybinds.NEXT_QUIVER_INDEX.get());
        event.register(ModKeybinds.PREVIOUS_QUIVER_INDEX.get());
        event.register(ModKeybinds.NEXT_ABILITY_INDEX.get());
        event.register(ModKeybinds.PREVIOUS_ABILITY_INDEX.get());
        event.register(ModKeybinds.ACTIVATE_ABILITY.get());
    }

    private void renderFirstPersonHand(RenderHandEvent event) {
        event.setCanceled(true);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        for (int i = 0; i < RacistClothItem.CLOTHES.size(); i++) {
            CuriosRendererRegistry.register(RacistClothItem.CLOTHES.get(i), () -> new RacistClothItemRenderer());
        }
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
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
        event.registerLayerDefinition(ModModelLayers.MALE_MERFOLK, () -> MaleMerfolkModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_MERFOLK, () -> MaleMerfolkModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_MERFOLK, () -> FemaleMerfolkModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_MERFOLK, () -> FemaleMerfolkModel.createBodyLayer(new CubeDeformation(0.1F)));
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

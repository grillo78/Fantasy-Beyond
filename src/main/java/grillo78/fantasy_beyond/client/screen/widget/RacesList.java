package grillo78.fantasy_beyond.client.screen.widget;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.customization.race.Race;
import grillo78.fantasy_beyond.character.customization.race.RaceType;
import grillo78.fantasy_beyond.character.customization.race.Race;
import grillo78.fantasy_beyond.client.screen.CustomizationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class RacesList extends ObjectSelectionList<RacesList.RaceEntry> {

    private CustomizationScreen parent;
    private CharacteristicsList characteristicsList;

    //Minecraft pMinecraft, int pWidth, int pHeight, int pY0, int pY1, int pItemHeight
    public RacesList(CustomizationScreen parent, CharacteristicsList characteristicsList, Race initialRace) {
        super(parent.getMinecraft(),
                parent.width / 3, parent.height - 56, 28, parent.getMinecraft().font.lineHeight * 2 + 8);
        this.parent = parent;
        this.setX(parent.width / 3 - 10);
        this.characteristicsList = characteristicsList;
        this.characteristicsList.fillCharacteristics(initialRace);
        RaceType.RACE_TYPES_REGISTRY.keySet().forEach(key -> {
            RaceEntry entry = new RaceEntry(key);
            addEntry(entry);
            if (initialRace.getType() == RaceType.RACE_TYPES_REGISTRY.get(key))
                setSelected(entry);
        });
    }

    @Override
    public int getRowLeft() {
        return this.getX();
    }

    @Override
    public int getRowWidth() {
        return width - 22;
    }

    @Override
    public int getBottom() {
        return super.getBottom();
    }

    @Override
    protected int getScrollbarPosition() {
        return getX() + width - 15;
    }

    @Override
    protected void renderSelection(GuiGraphics pGuiGraphics, int pTop, int pWidth, int pHeight, int pOuterColor, int pInnerColor) {
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {

        boolean canClick = parent.getColorPicker() == null;
        if (canClick) {
            canClick = super.mouseClicked(pMouseX, pMouseY, pButton);
        }
        return canClick;
    }

    @Override
    protected void renderListBackground(GuiGraphics guiGraphics) {
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/scrolls.png"), getX() - 5, getY() - 3, 0, 0, width, height + 6, width, height + 6);
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {

    }

    public class RaceEntry extends ObjectSelectionList.Entry<RaceEntry> {

        private Component text;
        private ResourceLocation raceKey;

        public RaceEntry(ResourceLocation raceKey) {
            this.raceKey = raceKey;
            this.text = Component.translatable("race.name." + raceKey.getPath());
        }

        @Override
        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            boolean canClick = parent.getColorPicker() == null;
            if (canClick) {
                RaceType raceType = RaceType.RACE_TYPES_REGISTRY.get(raceKey);
                CharacterData data = Minecraft.getInstance().player.getData(ModAttachments.CHARACTER_DATA);
                    data.getPlayerCustomization().setRace(raceType.createRace(data.getPlayerCustomization()));
                    if (!RacesList.this.parent.children().contains(RacesList.this.characteristicsList)) {
                        boolean removed = false;
                        for (int i = 0; i < RacesList.this.parent.children().size() && !removed; i++) {
                            if (RacesList.this.parent.children().get(i) instanceof VariantsList) {
                                VariantsList variantsList = (VariantsList)RacesList.this.parent.children().get(i);
                                RacesList.this.parent.removeRenderable(variantsList);
                                RacesList.this.parent.removeRenderable(RacesList.this.characteristicsList.getBackButton());
                                removed = true;
                            }
                        }
                        RacesList.this.parent.addRenderableWidgetWrap(RacesList.this.characteristicsList);
                    }
                    RacesList.this.characteristicsList.fillCharacteristics(data.getPlayerCustomization().getRace());
            }
            return canClick;
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {
            pGuiGraphics.blit(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/scroll_entry" + (pHovering || isSelectedItem(pIndex) ? "_selected" : "") + ".png"), pLeft + 5, pTop, 0, 0, pWidth, pHeight, pWidth, pHeight);
            pGuiGraphics.drawString(Minecraft.getInstance().font, text, pLeft + 5 + pWidth / 2 - Minecraft.getInstance().font.width(text) / 2, pTop + pHeight / 2 - Minecraft.getInstance().font.lineHeight / 2, pHovering ? Color.GRAY.hashCode() : Color.WHITE.hashCode());
        }
    }
}

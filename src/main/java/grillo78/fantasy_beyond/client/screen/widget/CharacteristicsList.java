package grillo78.fantasy_beyond.client.screen.widget;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.client.screen.CustomizationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class CharacteristicsList extends ObjectSelectionList<CharacteristicsList.CharacteristicEntry> {

    private CustomizationScreen parent;

    //Minecraft pMinecraft, int pWidth, int pHeight, int pY0, int pY1, int pItemHeight
    public CharacteristicsList(CustomizationScreen parent) {
        super(parent.getMinecraft(), parent.width / 3 - 20, parent.height,
                28, parent.height - 28, parent.getMinecraft().font.lineHeight * 2 + 8);
        this.parent = parent;
        this.x0 = 2 * parent.width / 3;
        this.x1 = parent.width;
        setRenderTopAndBottom(false);
        setRenderBackground(false);
    }

    @Override
    protected void renderSelection(GuiGraphics pGuiGraphics, int pTop, int pWidth, int pHeight, int pOuterColor, int pInnerColor) {
    }

    @Override
    public int getRowLeft() {
        return this.x0;
    }

    @Override
    public int getRowWidth() {
        return width - 10;
    }

    @Override
    protected int getScrollbarPosition() {
        return x1 - 22;
    }

    @Override
    protected void renderBackground(GuiGraphics pGuiGraphics) {
        super.renderBackground(pGuiGraphics);
        pGuiGraphics.blit(new ResourceLocation(FantasyBeyond.MOD_ID, "textures/screen/customization/scrolls.png"), x0 - 5, y0 - 3, 0, 0, x1 - x0, y1 - 22, x1 - x0, y1 - 22);
    }

    public void fillCharacteristics(Race race) {
        clearEntries();
        for (int i = 0; i < race.getCharacteristics().size(); i++) {
            addEntry(new CharacteristicEntry(i, race.getCharacteristics().get(i).getName(),parent));
        }
    }

    public class CharacteristicEntry extends ObjectSelectionList.Entry<CharacteristicEntry> {
        private Component text;
        private int index;
        private CustomizationScreen screen;

        public CharacteristicEntry(int index, String name, CustomizationScreen screen) {
            this.index = index;
            this.text = Component.translatable("characteristic.name." + name);
            this.screen = screen;
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            screen.children().remove(list);
            screen.renderables.remove(list);
            screen.addRenderableWidgetWrap(new VariantsList(screen, index));
            return true;
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {
            pGuiGraphics.blit(new ResourceLocation(FantasyBeyond.MOD_ID, "textures/screen/customization/scroll_entry" + (pHovering || list.getSelected() == this ? "_selected" : "") + ".png"), pLeft + 5, pTop, 0, 0, pWidth, pHeight, pWidth, pHeight);
            pGuiGraphics.drawString(Minecraft.getInstance().font, text, pLeft + 5 + pWidth / 2 - Minecraft.getInstance().font.width(text) / 2, pTop + pHeight / 2 - Minecraft.getInstance().font.lineHeight / 2, pHovering ? Color.GRAY.hashCode() : Color.WHITE.hashCode());
        }
    }
}

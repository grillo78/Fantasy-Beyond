package grillo78.fantasy_beyond.client.screen.widget;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.customization.race.Characteristic;
import grillo78.fantasy_beyond.capabilities.customization.race.Coloreable;
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
                28, parent.height - 60, parent.getMinecraft().font.lineHeight * 2 + 8);
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

    public void fillCharacteristics(Race race) {
        clearEntries();
        for (int i = 0; i < race.getCharacteristics().size(); i++) {
            addEntry(new CharacteristicEntry(race.getCharacteristics().get(i), race.getCharacteristics().get(i).getName(), parent));
        }
    }

    public class CharacteristicEntry extends ObjectSelectionList.Entry<CharacteristicEntry> {
        private Component text;
        private Characteristic characteristic;
        private CustomizationScreen screen;

        public CharacteristicEntry(Characteristic characteristic, String name, CustomizationScreen screen) {
            this.characteristic = characteristic;
            this.text = Component.translatable("characteristic.name." + name);
            this.screen = screen;
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            if (pMouseX > getRowRight() - 16) {
                if(screen.getColorPicker() != null)
                    screen.removeRenderable(screen.getColorPicker());
                screen.setColorPicker(new ColorPicker(screen.width/2, screen.height/2,50,65, (Coloreable) characteristic));
                screen.addRenderableWidgetWrap(screen.getColorPicker());
            } else {
                screen.removeRenderable(list);
                VariantsList variantsList = new VariantsList(screen, characteristic);
                screen.addRenderableWidgetWrap(variantsList);
                //int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Button.OnPress pOnPress, Component pMessage
                screen.addRenderableWidgetWrap(new ImageButtonWithText(2 * parent.width / 3 + 5, 30, parent.width / 3 - 30, 22, 0, 0, 22, new ResourceLocation(FantasyBeyond.MOD_ID, "textures/screen/customization/scroll_button.png"), parent.width / 3 - 30, 66, (button) -> {
                    screen.removeRenderable(button);
                    screen.removeRenderable(variantsList);
                    screen.addRenderableWidgetWrap(list);
                }, Component.translatable("gui.back")));
            }
            return true;
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {
            pGuiGraphics.blit(new ResourceLocation(FantasyBeyond.MOD_ID, "textures/screen/customization/scroll_entry" + ((pHovering && pMouseX < getRowRight() - 20) ? "_selected" : "") + ".png"), pLeft + 5, pTop, 0, 0, pWidth - (characteristic instanceof Coloreable ? 24 : 0), pHeight, pWidth - (characteristic instanceof Coloreable ? 24 : 0), pHeight);
            pGuiGraphics.drawString(Minecraft.getInstance().font, text, pLeft + 5 + pWidth / 2 - Minecraft.getInstance().font.width(text) / 2, pTop + pHeight / 2 - Minecraft.getInstance().font.lineHeight / 2, (pHovering && pMouseX < getRowRight() - 20) ? Color.GRAY.hashCode() : Color.WHITE.hashCode());
            if (characteristic instanceof Coloreable)
                pGuiGraphics.blit(new ResourceLocation(FantasyBeyond.MOD_ID, "textures/screen/customization/color_picker_icon.png"), getRowRight() - 16, pTop, 0, 0, 22, 22, 22, 22);
        }
    }
}

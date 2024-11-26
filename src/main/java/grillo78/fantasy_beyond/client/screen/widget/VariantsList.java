package grillo78.fantasy_beyond.client.screen.widget;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.customization.race.Characteristic;
import grillo78.fantasy_beyond.client.screen.CustomizationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class VariantsList extends ObjectSelectionList<VariantsList.VariantEntry> {

    private Characteristic characteristic;

    public VariantsList(CustomizationScreen parent, Characteristic characteristic) {
        super(parent.getMinecraft(), parent.width / 3 - 20, parent.height,
                58, parent.height - 60, parent.getMinecraft().font.lineHeight * 2 + 8);
        this.x0 = 2 * parent.width / 3;
        this.x1 = parent.width;
        this.characteristic = characteristic;
        setRenderTopAndBottom(false);
        setRenderBackground(false);

        for (int i = 0; i < characteristic.getMaxVariant(); i++) {
            VariantEntry entry = new VariantEntry(i, Component.literal(Component.translatable("characteristic.name." + characteristic.getName()).getString() + ": " + (i + 1)));
            addEntry(entry);
            if (characteristic.getVariant() == i)
                setSelected(entry);
        }
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

    public class VariantEntry extends ObjectSelectionList.Entry<VariantEntry> {

        private Component text;
        private int index;

        public VariantEntry(int index, Component text) {
            this.index = index;
            this.text = text;
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            characteristic.setVariant(index);
            return true;
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {
            pGuiGraphics.blit(new ResourceLocation(FantasyBeyond.MOD_ID, "textures/screen/customization/scroll_entry" + (pHovering || list.getSelected() == this ? "_selected" : "") + ".png"), pLeft + 5, pTop, 0, 0, pWidth, pHeight, pWidth, pHeight);
            pGuiGraphics.drawString(Minecraft.getInstance().font, text, pLeft + 5 + pWidth / 2 - Minecraft.getInstance().font.width(text) / 2, pTop + pHeight / 2 - Minecraft.getInstance().font.lineHeight / 2, pHovering ? Color.GRAY.hashCode() : Color.WHITE.hashCode());
        }
    }
}

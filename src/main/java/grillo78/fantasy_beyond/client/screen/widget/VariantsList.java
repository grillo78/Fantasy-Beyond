package grillo78.fantasy_beyond.client.screen.widget;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.client.screen.CustomizationScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class VariantsList extends ObjectSelectionList<VariantsList.VariantEntry> {

    private int index;

    public VariantsList(CustomizationScreen parent, int index) {
        super(parent.getMinecraft(), parent.width / 3 - 20, parent.height,
                28, parent.height - 28, parent.getMinecraft().font.lineHeight * 2 + 8);
        this.x0 = 2 * parent.width / 3;
        this.x1 = parent.width;
        this.index = index;
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

    public class VariantEntry extends ObjectSelectionList.Entry<VariantEntry> {

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            if(list.children().indexOf(this) == 0){

            }
            return true;
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {

        }
    }
}

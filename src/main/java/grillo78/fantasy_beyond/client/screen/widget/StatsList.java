package grillo78.fantasy_beyond.client.screen.widget;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.customization.race.Coloreable;
import grillo78.fantasy_beyond.character.level.stats.Stat;
import grillo78.fantasy_beyond.client.screen.CharacterScreen;
import grillo78.fantasy_beyond.network.IncreaseStat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

import java.awt.*;

public class StatsList extends ObjectSelectionList<StatsList.StatEntry> {

    private final CharacterData data;

    //Minecraft pMinecraft, int pWidth, int pHeight, int pY0, int pY1, int pItemHeight
    public StatsList(CharacterScreen parent, CharacterData data) {
        super(parent.getMinecraft(),
        parent.width/3-6, parent.height - 60,
                35, parent.getMinecraft().font.lineHeight * 2 + 8);
        this.setX(2 * parent.width / 3);
        this.data = data;
    }

    @Override
    protected void renderSelection(GuiGraphics pGuiGraphics, int pTop, int pWidth, int pHeight, int pOuterColor, int pInnerColor) {
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
    protected int getScrollbarPosition() {
        return getX() + width - 15;
    }

    public void fillCharacteristics() {
        clearEntries();
        data.getStats().forEach((key, stat) -> {
            addEntry(new StatEntry(stat, key));
        });
    }


    @Override
    protected void renderListBackground(GuiGraphics guiGraphics) {
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/scrolls.png"), getX(), getY()-15, 0, 0, width, height+20, width, height+20);
        Component text = Component.translatable("fantasy_beyond.character.points", data.getStatPoints());
        guiGraphics.drawString(minecraft.font, text, getX() + width / 2 - minecraft.font.width(text) / 2, 25, Color.WHITE.hashCode());
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {

    }

    public class StatEntry extends Entry<StatEntry> {
        private Stat stat;
        private String name;

        public StatEntry(Stat stat, String name) {
            this.stat = stat;
            this.name = name;
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            if(data.getStatPoints() > 0){
                PacketDistributor.sendToServer(new IncreaseStat(name));
                stat.increase();
            }
            return true;
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {
            pGuiGraphics.blit(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/scroll_entry" + ((pHovering && pMouseX < getRowRight() - (stat instanceof Coloreable? 20 : -6)) ? "_selected" : "") + ".png"), pLeft + 5, pTop, 0, 0, pWidth - (stat instanceof Coloreable ? 24 : 0), pHeight, pWidth - (stat instanceof Coloreable ? 24 : 0), pHeight);
            Component text = Component.translatable("stat.name." + name.toLowerCase(), stat.getLevel());
            pGuiGraphics.drawString(Minecraft.getInstance().font, text, pLeft + 5 + pWidth / 2 - Minecraft.getInstance().font.width(text) / 2, pTop + pHeight / 2 - Minecraft.getInstance().font.lineHeight / 2, (pHovering && pMouseX < getRowRight() - 20) ? Color.GRAY.hashCode() : Color.WHITE.hashCode());
        }
    }
}

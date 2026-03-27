package grillo78.fantasy_beyond.client.screen.widget;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.classes.PlayerClass;
import grillo78.fantasy_beyond.character.classes.PlayerClassType;
import grillo78.fantasy_beyond.character.customization.race.Coloreable;
import grillo78.fantasy_beyond.client.screen.ChooseClassScreen;
import grillo78.fantasy_beyond.network.IncreaseStat;
import grillo78.fantasy_beyond.network.SyncCharacterData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

import java.awt.*;

public class ClassList extends ObjectSelectionList<ClassList.ClassEntry> {

    private final CharacterData data;

    //Minecraft pMinecraft, int pWidth, int pHeight, int pY0, int pY1, int pItemHeight
    public ClassList(ChooseClassScreen parent, CharacterData data) {
        super(parent.getMinecraft(), parent.width/3-6, parent.height - 60,
                35, parent.getMinecraft().font.lineHeight * 2 + 8);
        this.setX(parent.width / 3);
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

    public void fillClasses() {
        clearEntries();
        PlayerClassType.PLAYER_CLASS_TYPES_REGISTRY.forEach((playerClassType) -> {
            addEntry(new ClassEntry(playerClassType, PlayerClassType.PLAYER_CLASS_TYPES_REGISTRY.getKey(playerClassType).getPath()));
        });
    }


    @Override
    protected void renderListBackground(GuiGraphics guiGraphics) {
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/scrolls.png"), getX(), getY() - 15, 0, 0, width, height + 20, width, height + 20);
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {

    }

    public class ClassEntry extends Entry<ClassEntry> {
        private PlayerClassType playerClassType;
        private String name;

        public ClassEntry(PlayerClassType playerClassType, String name) {
            this.playerClassType = playerClassType;
            this.name = name;
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            data.setPlayerClass(playerClassType.createPlayerClass());
            data.getPlayerClass().increaseAbilityPoints(data.getLevel().getLevel() - PlayerClass.UNLOCK_LEVEL + 1);
            PacketDistributor.sendToServer(new SyncCharacterData(Minecraft.getInstance().player.getId(), data.serializeNBT(null)));
            Minecraft.getInstance().screen.onClose();
            return true;
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {
            pGuiGraphics.blit(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/scroll_entry" + ((pHovering && pMouseX < getRowRight() - (playerClassType instanceof Coloreable ? 20 : -6)) ? "_selected" : "") + ".png"), pLeft + 5, pTop, 0, 0, pWidth - (playerClassType instanceof Coloreable ? 24 : 0), pHeight, pWidth - (playerClassType instanceof Coloreable ? 24 : 0), pHeight);
            Component text = Component.translatable("player_class.name." + name.toLowerCase());
            pGuiGraphics.drawString(Minecraft.getInstance().font, text, pLeft + 5 + pWidth / 2 - Minecraft.getInstance().font.width(text) / 2, pTop + pHeight / 2 - Minecraft.getInstance().font.lineHeight / 2, (pHovering && pMouseX < getRowRight() - 20) ? Color.GRAY.hashCode() : Color.WHITE.hashCode());
        }
    }
}

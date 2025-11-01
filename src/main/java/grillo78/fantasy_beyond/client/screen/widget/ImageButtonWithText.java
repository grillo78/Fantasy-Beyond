package grillo78.fantasy_beyond.client.screen.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.awt.*;

public class ImageButtonWithText extends ImageButton {
    public ImageButtonWithText(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, WidgetSprites sprites, int pTextureWidth, int pTextureHeight, OnPress pOnPress, Component pMessage) {
        super(pX, pY, pWidth, pHeight, sprites, pOnPress);
        this.setMessage(pMessage);
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderString(pGuiGraphics, Minecraft.getInstance().font, isHovered? Color.GRAY.hashCode() : Color.WHITE.hashCode());
    }
}

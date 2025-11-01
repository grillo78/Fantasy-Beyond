package grillo78.fantasy_beyond.client.screen.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class CustomImageButton extends ImageButton {
    public CustomImageButton(int x, int y, int width, int height, WidgetSprites sprites, OnPress onPress) {
        super(x, y, width, height, sprites, onPress);
    }

    public CustomImageButton(int x, int y, int width, int height, WidgetSprites sprites, OnPress onPress, Component message) {
        super(x, y, width, height, sprites, onPress, message);
    }

    public CustomImageButton(int width, int height, WidgetSprites sprites, OnPress onPress, Component message) {
        super(width, height, sprites, onPress, message);
    }

    public void setSprites(WidgetSprites sprites){
        this.sprites = sprites;
    }
}

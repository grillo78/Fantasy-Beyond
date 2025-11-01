package grillo78.fantasy_beyond.client.screen.widget;


import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.race.Characteristic;
import grillo78.fantasy_beyond.character.customization.race.Coloreable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

import java.awt.*;

public class ColorPicker extends AbstractWidget {

    private static ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/color_picker_background.png");
    private static ResourceLocation COLOR_PICKER_SLIDER_TEXTURE = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/color_picker.png");
    private static ResourceLocation COLOR_PICKER_POINT_TEXTURE = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/color_picker_point.png");
    private static ResourceLocation COLOR_PICKER_SLIDER_SELECT_TEXTURE = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/color_picker_slider.png");

    private float hue = 0;
    private float saturation = 0;
    private float lightness = 0;
    private Coloreable characteristic;

    public ColorPicker(int pX, int pY, int pWidth, int pHeight, Coloreable characteristic) {
        super(pX - pWidth / 2, pY - pHeight / 2, pWidth, pHeight, Component.empty());
        this.characteristic = characteristic;
        Color originalColor = ((Characteristic) characteristic).getColor();
        float[] hsbColor = Color.RGBtoHSB(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), null);
        hue = hsbColor[0];
        saturation = hsbColor[1];
        lightness = hsbColor[2];
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(0,0,10);
        pGuiGraphics.blit(BACKGROUND_TEXTURE, getX(), getY(), 0, 0, width, height, width, height);
        pGuiGraphics.blit(COLOR_PICKER_SLIDER_TEXTURE, getX() + 5, getY() - 15 + height, 0, 0, width - 10, 10, width - 10, 10);
        pGuiGraphics.fill(getX() + 5, getY() + 5, getX() + width - 5, getY() + height - 20, Color.WHITE.hashCode());
        pGuiGraphics.fillGradient(getX() + 5, getY() + 5, getX() + width - 5, getY() + height - 20, Color.getHSBColor(hue, 1, 1).hashCode(), new Color(0, 0, 0, 0).hashCode());
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(getX() + width - 5, getY() + 5, 0);
        pGuiGraphics.pose().mulPose(new Quaternionf().rotationZ((float) Math.toRadians(90)));
        pGuiGraphics.fillGradient(0, 0, height - 25, width - 15, Color.BLACK.hashCode(), new Color(0, 0, 0, 0).hashCode());
        pGuiGraphics.pose().popPose();
        pGuiGraphics.blit(COLOR_PICKER_POINT_TEXTURE, (int) (getX() + (width -2.5F - 5) * (1-lightness)), (int) (getY() -2 + (height - 20) * (1-saturation)), 0, 0, 5, 5, 5, 5);
        pGuiGraphics.pose().popPose();
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        if (pMouseX - getX() > 5 && pMouseX - getX() < width - 5) {
            if (pMouseY - getY() > 5 && pMouseY - getY() < height - 20) {
                lightness = (float) ((getX() + width - 5 - pMouseX) / (width - 5));
                saturation = (float) ((getY() + height - 20 - pMouseY) / (height - 20));
            }
            if (pMouseY - getY() > height - 15 && pMouseY - getY() < height - 5)
                hue = -0.3F- (float) ((getX() +  width - 10 - pMouseX) / width - 10);
        }
        characteristic.setColor(Color.getHSBColor(hue, saturation, lightness));
    }


    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}

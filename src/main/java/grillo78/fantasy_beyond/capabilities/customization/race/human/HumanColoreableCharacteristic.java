package grillo78.fantasy_beyond.capabilities.customization.race.human;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Coloreable;

import java.awt.*;

public class HumanColoreableCharacteristic extends HumanCharacteristic implements Coloreable {

    private Color color = new Color(105, 18, 18,255);

    public HumanColoreableCharacteristic(PlayerCustomization playerCustomization, String name) {
        super(playerCustomization, name);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }
}

package grillo78.fantasy_beyond.capabilities.customization.race.dwarf;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Coloreable;

import java.awt.*;

public class DwarfColoreableCharacteristic extends DwarfCharacteristic implements Coloreable {

    private Color color = new Color(105, 18, 18,255);

    public DwarfColoreableCharacteristic(PlayerCustomization playerCustomization, String name, int maxVariants) {
        super(playerCustomization,name, maxVariants);
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

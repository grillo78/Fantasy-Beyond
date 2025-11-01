package grillo78.fantasy_beyond.character.customization.race.merfolk;

import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Coloreable;

import java.awt.*;

public class MerfolkColoreableCharacteristic extends MerfolkCharacteristic implements Coloreable {

    private Color color = new Color(105, 18, 18,255);

    public MerfolkColoreableCharacteristic(PlayerCustomization playerCustomization, String name, int maxVariants) {
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

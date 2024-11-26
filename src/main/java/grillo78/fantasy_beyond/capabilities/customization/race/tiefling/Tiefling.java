package grillo78.fantasy_beyond.capabilities.customization.race.tiefling;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;

public class Tiefling extends Race {
    public Tiefling(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new TieflingCharacteristic(playerCustomization, "body", 1));
        getCharacteristics().add(new TieflingColoreableCharacteristic(playerCustomization, "hair", 3));
        getCharacteristics().add(new TieflingEyes(playerCustomization, "eyes", 4));
    }

    @Override
    public EntityDimensions getNewSize(Pose pose, EntityDimensions newSize) {
        return newSize;
    }

    @Override
    public float getNewEyeHeight(Pose pose, float oldEyeHeight) {
        return oldEyeHeight;
    }
}

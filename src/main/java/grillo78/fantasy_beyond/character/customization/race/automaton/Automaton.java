package grillo78.fantasy_beyond.character.customization.race.automaton;

import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Race;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;

public class Automaton extends Race {
    public Automaton(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new AutomatonCharacteristic(playerCustomization, "body", 1));
    }

    @Override
    public EntityDimensions getNewSize(Pose pose, EntityDimensions newSize) {
        return newSize.scale(1.05F);
    }

    @Override
    public float getNewEyeHeight(Pose pose, float oldEyeHeight) {
        return oldEyeHeight;
    }
}

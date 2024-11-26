package grillo78.fantasy_beyond.capabilities.customization.race.elf;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;

public class Elf extends Race {
    public Elf(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        this.getCharacteristics().add(new ElfCharacteristic(playerCustomization, "body", 1));
        this.getCharacteristics().add(new ElfColoreableCharacteristic(playerCustomization, "hair", 3));
        this.getCharacteristics().add(new ElfEyes(playerCustomization, "eyes", 4));
    }

    @Override
    public EntityDimensions getNewSize(Pose pose, EntityDimensions newSize) {
        return newSize.scale(1.2F);
    }

    @Override
    public float getNewEyeHeight(Pose pose, float oldEyeHeight) {
        return oldEyeHeight * 1.1F;
    }
}

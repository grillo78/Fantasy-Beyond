package grillo78.fantasy_beyond.character.customization.race.tiefling;

import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Race;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class Tiefling extends Race {
    public Tiefling(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new TieflingCharacteristic(playerCustomization, "body", 1));
        getCharacteristics().add(new TieflingColoreableCharacteristic(playerCustomization, "hair", 3));
        getCharacteristics().add(new TieflingEyes(playerCustomization, "eyes", 4));
        getCharacteristics().add(new TieflingHornsCharacteristic(playerCustomization, "horns",3));
        getCharacteristics().add(new TieflingTailCharacteristic(playerCustomization, "tail", 2));
    }

    @Override
    public void onHurt(LivingIncomingDamageEvent event) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE))
            event.setCanceled(true);
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

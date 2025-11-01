package grillo78.fantasy_beyond.character.customization.race.tiefling;

import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.tiefling.FemaleTieflingModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.MaleTieflingModel;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class Tiefling extends Race {
    public Tiefling(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new TieflingCharacteristic(playerCustomization, "body", 1));
        getCharacteristics().add(new TieflingColoreableCharacteristic(playerCustomization, "hair", 3));
        getCharacteristics().add(new TieflingEyes(playerCustomization, "eyes", 4));
        getCharacteristics().add(new TieflingHornsCharacteristic(playerCustomization, "horns"));
        getCharacteristics().add(new TieflingTailCharacteristic(playerCustomization, "tail"));
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

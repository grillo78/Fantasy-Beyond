package grillo78.fantasy_beyond.capabilities.customization.race.automaton;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.automaton.AutomatonModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class Automaton extends Race {
    public Automaton(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new AutomatonCharacteristic(playerCustomization, "body", 1));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setMaleClothesModel(new AutomatonModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_AUTOMATON)));
            setFemaleClothesModel(new AutomatonModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_AUTOMATON)));
        });
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

package grillo78.fantasy_beyond.capabilities.customization.race.orc;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.orc.FemaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.orc.MaleOrcModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class Orc extends Race {
    public Orc(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new OrcCharacteristic(playerCustomization, "body", 1));
        getCharacteristics().add(new OrcColoreableCharacteristic(playerCustomization, "hair", 3));
        getCharacteristics().add(new OrcEyes(playerCustomization, "eyes", 4));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setMaleClothesModel(new MaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_ORC)));
            setFemaleClothesModel(new FemaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_ORC)));
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

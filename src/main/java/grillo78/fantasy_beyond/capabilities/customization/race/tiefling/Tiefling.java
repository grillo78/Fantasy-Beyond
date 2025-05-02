package grillo78.fantasy_beyond.capabilities.customization.race.tiefling;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.tiefling.FemaleTieflingModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.MaleTieflingModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class Tiefling extends Race {
    public Tiefling(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new TieflingCharacteristic(playerCustomization, "body", 1));
        getCharacteristics().add(new TieflingColoreableCharacteristic(playerCustomization, "hair", 3));
        getCharacteristics().add(new TieflingEyes(playerCustomization, "eyes", 4));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setMaleClothesModel(new MaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_TIEFLING)));
            setFemaleClothesModel(new FemaleTieflingModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_TIEFLING)));
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

package grillo78.fantasy_beyond.capabilities.customization.race.merfolk;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.merfolk.FemaleMerfolkModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.MaleMerfolkModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class Merfolk extends Race {
    public Merfolk(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        this.getCharacteristics().add(new MerfolkCharacteristic(playerCustomization, "body", 1));
        this.getCharacteristics().add(new MerfolkColoreableCharacteristic(playerCustomization, "hair", 3));
        this.getCharacteristics().add(new MerfolkEyes(playerCustomization, "eyes", 4));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setMaleClothesModel(new MaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_MERFLOK)));
            setFemaleClothesModel(new FemaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_MERFLOK)));
        });
    }

    @Override
    public EntityDimensions getNewSize(Pose pose, EntityDimensions newSize) {
        return newSize.scale(1.05F);
    }

    @Override
    public float getNewEyeHeight(Pose pose, float oldEyeHeight) {
        return oldEyeHeight * 1.1F;
    }
}

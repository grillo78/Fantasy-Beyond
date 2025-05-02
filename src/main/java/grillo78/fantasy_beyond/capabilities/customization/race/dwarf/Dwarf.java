package grillo78.fantasy_beyond.capabilities.customization.race.dwarf;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.dwarf.FemaleDwarfModel;
import grillo78.fantasy_beyond.client.entity.race.dwarf.MaleDwarfModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class Dwarf extends Race {
    public Dwarf(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new DwarfCharacteristic(playerCustomization, "body", 1));
        getCharacteristics().add(new DwarfColoreableCharacteristic(playerCustomization, "hair", 3));
        getCharacteristics().add(new DwarfEyes(playerCustomization, "eyes", 4));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setMaleClothesModel(new MaleDwarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_DWARF)));
            setFemaleClothesModel(new FemaleDwarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_DWARF)));
        });
    }

    @Override
    public EntityDimensions getNewSize(Pose pose, EntityDimensions newSize) {
        return newSize.scale(0.95F);
    }

    @Override
    public float getNewEyeHeight(Pose pose, float oldEyeHeight) {
        return oldEyeHeight*0.8F;
    }
}

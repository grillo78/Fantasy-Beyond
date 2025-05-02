package grillo78.fantasy_beyond.capabilities.customization.race.elf;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.elf.FemaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.MaleElfModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class Elf extends Race {
    public Elf(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        this.getCharacteristics().add(new ElfCharacteristic(playerCustomization, "body", 1));
        this.getCharacteristics().add(new ElfColoreableCharacteristic(playerCustomization, "hair", 3));
        this.getCharacteristics().add(new ElfEyes(playerCustomization, "eyes", 4));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setMaleClothesModel(new MaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_ELF)));
            setFemaleClothesModel(new FemaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_ELF)));
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

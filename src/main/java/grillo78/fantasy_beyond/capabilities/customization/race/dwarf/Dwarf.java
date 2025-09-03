package grillo78.fantasy_beyond.capabilities.customization.race.dwarf;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.dwarf.FemaleDwarfModel;
import grillo78.fantasy_beyond.client.entity.race.dwarf.MaleDwarfModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
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
    public void applyAttributes(Player player) {
        super.applyAttributes(player);
        setAttribute(player, "health", Attributes.MAX_HEALTH, ATTRIBUTE_UUID, 6, AttributeModifier.Operation.ADDITION);
        setAttribute(player, "speed", Attributes.MOVEMENT_SPEED, ATTRIBUTE_UUID, -0.015, AttributeModifier.Operation.ADDITION);
        setAttribute(player, "knockback_resistance", Attributes.KNOCKBACK_RESISTANCE, ATTRIBUTE_UUID, 3, AttributeModifier.Operation.MULTIPLY_BASE);
        player.setHealth(player.getHealth()*player.getMaxHealth()/20);
    }

    @Override
    public EntityDimensions getNewSize(Pose pose, EntityDimensions newSize) {
        return newSize.scale(0.95F);
    }

    @Override
    public float getNewEyeHeight(Pose pose, float oldEyeHeight) {
        return oldEyeHeight*0.8F;
    }

    @Override
    public int getHUDViewerScale() {
        return 25;
    }
}

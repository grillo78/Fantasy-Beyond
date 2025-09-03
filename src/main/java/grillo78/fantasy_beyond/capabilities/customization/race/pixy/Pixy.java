package grillo78.fantasy_beyond.capabilities.customization.race.pixy;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.elf.FemaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.MaleElfModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.DistExecutor;
import org.w3c.dom.Attr;

public class Pixy extends Race {
    public Pixy(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        this.getCharacteristics().add(new PixyCharacteristic(playerCustomization, "body", 1));
        this.getCharacteristics().add(new PixyColoreableCharacteristic(playerCustomization, "hair", 3));
        this.getCharacteristics().add(new PixyEyes(playerCustomization, "eyes", 4));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setMaleClothesModel(new MaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_ELF)));
            setFemaleClothesModel(new FemaleElfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_ELF)));
        });
    }

    @Override
    public EntityDimensions getNewSize(Pose pose, EntityDimensions newSize) {
        return newSize.scale(0.05F);
    }

    @Override
    public float getNewEyeHeight(Pose pose, float oldEyeHeight) {
        return oldEyeHeight * 0.05F;
    }

    @Override
    public float getJumpScale() {
        return 0.1F;
    }

    @Override
    public void applyAttributes(Player player) {
        super.applyAttributes(player);
        setAttribute(player, "speed", Attributes.MOVEMENT_SPEED, ATTRIBUTE_UUID, -0.08, AttributeModifier.Operation.ADDITION);
        setAttribute(player, "step_height_addition", ForgeMod.STEP_HEIGHT_ADDITION.get(), ATTRIBUTE_UUID, -0.55, AttributeModifier.Operation.ADDITION);
        setAttribute(player, "gravity", ForgeMod.ENTITY_GRAVITY.get(), ATTRIBUTE_UUID, -0.065, AttributeModifier.Operation.ADDITION);
        setAttribute(player, "attack_speed", Attributes.ATTACK_SPEED, ATTRIBUTE_UUID, 0.015, AttributeModifier.Operation.ADDITION);
    }
}

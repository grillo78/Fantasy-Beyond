package grillo78.fantasy_beyond.character.customization.race.elf;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.Util;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Race;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;

public class Elf extends Race {
    public Elf(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        this.getCharacteristics().add(new ElfCharacteristic(playerCustomization, "body", 1));
        this.getCharacteristics().add(new ElfColoreableCharacteristic(playerCustomization, "hair", 3));
        this.getCharacteristics().add(new ElfEyes(playerCustomization, "eyes", 4));
    }

    @Override
    public EntityDimensions getNewSize(Pose pose, EntityDimensions newSize) {
        return newSize.scale(1.05F);
    }

    @Override
    public float getNewEyeHeight(Pose pose, float oldEyeHeight) {
        return oldEyeHeight * 1.1F;
    }

    @Override
    public void applyAttributes(LivingEntity player) {
        super.applyAttributes(player);
        Util.setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID,"speed"), Attributes.MOVEMENT_SPEED, 0.015, AttributeModifier.Operation.ADD_VALUE);
        Util.setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID,"attack_speed"), Attributes.ATTACK_SPEED, 0.015, AttributeModifier.Operation.ADD_VALUE);
    }
}

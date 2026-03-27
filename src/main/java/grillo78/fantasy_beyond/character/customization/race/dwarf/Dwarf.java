package grillo78.fantasy_beyond.character.customization.race.dwarf;

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
import net.minecraft.world.phys.Vec3;

public class Dwarf extends Race {
    public Dwarf(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new DwarfCharacteristic(playerCustomization, "body", 1));
        getCharacteristics().add(new DwarfColoreableCharacteristic(playerCustomization, "hair", 3));
        getCharacteristics().add(new DwarfEyes(playerCustomization, "eyes", 4));
    }

    @Override
    public void applyAttributes(LivingEntity player) {
        super.applyAttributes(player);
        Util.setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "health"), Attributes.MAX_HEALTH, 6, AttributeModifier.Operation.ADD_VALUE);
        Util.setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID,"speed"), Attributes.MOVEMENT_SPEED, -0.015, AttributeModifier.Operation.ADD_VALUE);
        Util.setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID,"knockback_resistance"), Attributes.KNOCKBACK_RESISTANCE, 3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        player.setHealth(player.getHealth()*player.getMaxHealth()/20);
    }

    @Override
    public Vec3 getArmOffset() {
        return new Vec3(-1/16F,0.6,-0.13);
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

    @Override
    public Vec3 quiverOffset() {
        return super.quiverOffset().add(0,8/16F,0);
    }

    @Override
    public Vec3 quiverAngles() {
        return super.quiverAngles();
    }
}

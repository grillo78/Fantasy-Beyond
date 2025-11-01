package grillo78.fantasy_beyond.character.customization.race.dwarf;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.dwarf.FemaleDwarfModel;
import grillo78.fantasy_beyond.client.entity.race.dwarf.MaleDwarfModel;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;

public class Dwarf extends Race {
    public Dwarf(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new DwarfCharacteristic(playerCustomization, "body", 1));
        getCharacteristics().add(new DwarfColoreableCharacteristic(playerCustomization, "hair", 3));
        getCharacteristics().add(new DwarfEyes(playerCustomization, "eyes", 4));
    }

    @Override
    public void applyAttributes(Player player) {
        super.applyAttributes(player);
        setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "health"), Attributes.MAX_HEALTH, 6, AttributeModifier.Operation.ADD_VALUE);
        setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID,"speed"), Attributes.MOVEMENT_SPEED, -0.015, AttributeModifier.Operation.ADD_VALUE);
        setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID,"knockback_resistance"), Attributes.KNOCKBACK_RESISTANCE, 3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
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

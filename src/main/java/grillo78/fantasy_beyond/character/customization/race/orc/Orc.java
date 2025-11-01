package grillo78.fantasy_beyond.character.customization.race.orc;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.orc.FemaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.orc.MaleOrcModel;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

public class Orc extends Race {
    public Orc(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new OrcCharacteristic(playerCustomization, "body", 1));
        getCharacteristics().add(new OrcColoreableCharacteristic(playerCustomization, "hair", 3));
        getCharacteristics().add(new OrcEyes(playerCustomization, "eyes", 4));
    }

    @Override
    public EntityDimensions getNewSize(Pose pose, EntityDimensions newSize) {
        return newSize.scale(1.25F);
    }

    @Override
    public float getNewEyeHeight(Pose pose, float oldEyeHeight) {
        return oldEyeHeight*1.45F;
    }

    @Override
    public void livingFall(LivingFallEvent event) {
        super.livingFall(event);
        event.setDistance(Math.max(0,event.getDistance()-3.5F));
    }

    @Override
    public float getJumpScale() {
        return 2;
    }

    @Override
    public void applyAttributes(Player player) {
        super.applyAttributes(player);
        setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "health"), Attributes.MAX_HEALTH, 20, AttributeModifier.Operation.ADD_VALUE);
        setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "speed"), Attributes.KNOCKBACK_RESISTANCE, 3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        player.setHealth(player.getHealth()*player.getMaxHealth()/20);
    }

    @Override
    public int getHUDViewerScale() {
        return 15;
    }
}

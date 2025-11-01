package grillo78.fantasy_beyond.character.customization.race.merfolk;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.merfolk.FemaleMerfolkModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.MaleMerfolkModel;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;

public class Merfolk extends Race {
    public Merfolk(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        this.getCharacteristics().add(new MerfolkCharacteristic(playerCustomization, "body", 1));
        this.getCharacteristics().add(new MerfolkColoreableCharacteristic(playerCustomization, "hair", 3));
        this.getCharacteristics().add(new MerfolkEyes(playerCustomization, "eyes", 4));
        this.getCharacteristics().add(new MerfolkTailCharacteristic(playerCustomization, "tail"));
    }

    @Override
    public void canBreath(LivingBreatheEvent event) {
        event.setCanBreathe(event.getEntity().isUnderWater());
        event.setRefillAirAmount(event.getEntity().isUnderWater()? 1 : 0);
        if (event.getEntity().isUnderWater())
            event.setConsumeAirAmount(0);
        else
            event.setConsumeAirAmount(event.getEntity().getAirSupply() <= 0 || event.getEntity().tickCount % 80 == 0?1:0);
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
    public void applyAttributes(Player player) {
        super.applyAttributes(player);
        setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID,"health"), Attributes.MAX_HEALTH, 6, AttributeModifier.Operation.ADD_VALUE);
        setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID,"swim_speed"), NeoForgeMod.SWIM_SPEED, 2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        player.setHealth(player.getHealth()*player.getMaxHealth()/20);
    }
}

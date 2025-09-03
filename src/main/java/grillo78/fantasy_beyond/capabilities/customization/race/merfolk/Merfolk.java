package grillo78.fantasy_beyond.capabilities.customization.race.merfolk;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.merfolk.FemaleMerfolkModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.MaleMerfolkModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;

public class Merfolk extends Race {
    public Merfolk(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        this.getCharacteristics().add(new MerfolkCharacteristic(playerCustomization, "body", 1));
        this.getCharacteristics().add(new MerfolkColoreableCharacteristic(playerCustomization, "hair", 3));
        this.getCharacteristics().add(new MerfolkEyes(playerCustomization, "eyes", 4));
        this.getCharacteristics().add(new MerfolkTailCharacteristic(playerCustomization, "tail"));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setMaleClothesModel(new MaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_MERFLOK)));
            setFemaleClothesModel(new FemaleMerfolkModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_MERFLOK)));
        });
    }

    @Override
    public void canBreath(LivingBreatheEvent event) {
        event.setCanBreathe(event.getEntity().isUnderWater());
        event.setCanRefillAir(event.getEntity().isUnderWater());
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
        setAttribute(player, "health", Attributes.MAX_HEALTH, ATTRIBUTE_UUID, 6, AttributeModifier.Operation.ADDITION);
        setAttribute(player, "swim_speed", ForgeMod.SWIM_SPEED.get(), ATTRIBUTE_UUID, 2, AttributeModifier.Operation.MULTIPLY_BASE);
        player.setHealth(player.getHealth()*player.getMaxHealth()/20);
    }
}

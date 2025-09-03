package grillo78.fantasy_beyond.capabilities.customization.race.orc;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.orc.FemaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.orc.MaleOrcModel;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.DistExecutor;

public class Orc extends Race {
    public Orc(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        getCharacteristics().add(new OrcCharacteristic(playerCustomization, "body", 1));
        getCharacteristics().add(new OrcColoreableCharacteristic(playerCustomization, "hair", 3));
        getCharacteristics().add(new OrcEyes(playerCustomization, "eyes", 4));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setMaleClothesModel(new MaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_MALE_ORC)));
            setFemaleClothesModel(new FemaleOrcModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CLOTH_FEMALE_ORC)));
        });
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
        setAttribute(player, "health", Attributes.MAX_HEALTH, ATTRIBUTE_UUID, 20, AttributeModifier.Operation.ADDITION);
        setAttribute(player, "speed", Attributes.KNOCKBACK_RESISTANCE, ATTRIBUTE_UUID, 3, AttributeModifier.Operation.MULTIPLY_BASE);
        player.setHealth(player.getHealth()*player.getMaxHealth()/20);
    }

    @Override
    public int getHUDViewerScale() {
        return 15;
    }
}

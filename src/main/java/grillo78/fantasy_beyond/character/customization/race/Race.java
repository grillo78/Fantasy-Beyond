package grillo78.fantasy_beyond.character.customization.race;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.client.entity.race.RaceCharacteristicRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Race implements INBTSerializable<CompoundTag> {

    public static final UUID ATTRIBUTE_UUID = UUID.fromString("931d9762-4e36-46a6-ab4f-7a2e38d80d3b");

    private List<Characteristic> characteristics = new ArrayList<>();
    private PlayerCustomization playerCustomization;
    private RaceType type;

    public Race(PlayerCustomization playerCustomization) {
        this.playerCustomization = playerCustomization;
    }

    public void setType(RaceType type) {
        this.type = type;
    }

    public RaceType getType() {
        return type;
    }

    public PlayerCustomization getPlayerCustomization() {
        return playerCustomization;
    }

    public Vec3 getArmOffset() {
        return new Vec3(-1/ 16.0F,11/16F,-2/16F);
    }

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        CompoundTag characteristics = new CompoundTag();
        for (int i = 0; i < this.characteristics.size(); i++) {
            characteristics.put(String.valueOf(i), this.characteristics.get(i).serializeNBT(provider));
        }
        compoundTag.put("characteristics", characteristics);
        compoundTag.putString("type", RaceType.RACE_TYPES_REGISTRY.getKey(type).toString());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt ) {
        CompoundTag characteristics = nbt.getCompound("characteristics");
        for (int i = 0; i < this.characteristics.size(); i++) {
            this.characteristics.get(i).deserializeNBT(provider, characteristics.getCompound(String.valueOf(i)));
        }
    }

    public void tick(EntityTickEvent event){}

    public void onHurt(LivingIncomingDamageEvent event){}

    public void canBreath(LivingBreatheEvent event) {}

    public abstract EntityDimensions getNewSize(Pose pose, EntityDimensions newSize);

    public abstract float getNewEyeHeight(Pose pose, float oldEyeHeight);

    public int getMaxBreathAmount() {
        return 300;
    }

    public void applyAttributes(LivingEntity player) {
    }

    public int getHUDViewerScale() {
        return 20;
    }

    public float getJumpScale() {
        return 1;
    }

    public void livingFall(LivingFallEvent event) {

    }

    public Vec3 quiverOffset() {
        return new Vec3(0,0,4F/16F);
    }

    public Vec3 quiverAngles() {
        return new Vec3(0,0, Math.toRadians(45));
    }
}

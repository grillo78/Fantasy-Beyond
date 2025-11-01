package grillo78.fantasy_beyond.character.customization.race;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
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

    @OnlyIn(Dist.CLIENT)
    public void render(PlayerModel<Player> model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, Player player) {
        poseStack.pushPose();
        characteristics.forEach(characteristic -> characteristic.render(model, poseStack, pBuffer, pPackedLight, player));
        poseStack.popPose();
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

    public void applyAttributes(Player player) {
    }

    public static void setAttribute(Player entity, ResourceLocation id, Holder<Attribute> attribute, double amount, AttributeModifier.Operation operation) {
        AttributeInstance instance = entity.getAttribute(attribute);

        if (instance == null || entity.level().isClientSide) {
            return;
        }

        AttributeModifier modifier = instance.getModifier(id);

        if (amount == 0 || modifier != null && (modifier.amount() != amount || modifier.operation() != operation)) {
            instance.removeModifier(id);
            if(amount == 0)
                return;
        }

        modifier = instance.getModifier(id);

        if (modifier == null) {
            modifier = new AttributeModifier(id, amount, operation);
            instance.addTransientModifier(modifier);
        }
    }

    public int getHUDViewerScale() {
        return 20;
    }

    public float getJumpScale() {
        return 1;
    }

    public void livingFall(LivingFallEvent event) {

    }
}

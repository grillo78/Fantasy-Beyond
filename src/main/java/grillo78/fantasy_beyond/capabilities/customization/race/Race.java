package grillo78.fantasy_beyond.capabilities.customization.race;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Race implements INBTSerializable<CompoundTag> {

    private List<Characteristic> characteristics = new ArrayList<>();
    private PlayerCustomization playerCustomization;
    private RaceType type;
    @OnlyIn(Dist.CLIENT)
    private CustomizationModel maleClothesModel;
    @OnlyIn(Dist.CLIENT)
    private CustomizationModel femaleClothesModel;

    public Race(PlayerCustomization playerCustomization) {
        this.playerCustomization = playerCustomization;
    }

    public void onDamageEvent(LivingDamageEvent event) {

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
    public void setMaleClothesModel(CustomizationModel maleClothesModel) {
        this.maleClothesModel = maleClothesModel;
    }

    @OnlyIn(Dist.CLIENT)
    public void setFemaleClothesModel(CustomizationModel femaleClothesModel) {
        this.femaleClothesModel = femaleClothesModel;
    }

    @OnlyIn(Dist.CLIENT)
    public CustomizationModel getModel() {
        return playerCustomization.isMale() ? maleClothesModel : femaleClothesModel;
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
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        CompoundTag characteristics = new CompoundTag();
        for (int i = 0; i < this.characteristics.size(); i++) {
            characteristics.put(String.valueOf(i), this.characteristics.get(i).serializeNBT());
        }
        compoundTag.put("characteristics", characteristics);
        compoundTag.putString("type", RaceType.RACE_TYPES_REGISTRY.get().getKey(type).toString());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag characteristics = nbt.getCompound("characteristics");
        for (int i = 0; i < this.characteristics.size(); i++) {
            this.characteristics.get(i).deserializeNBT(characteristics.getCompound(String.valueOf(i)));
        }
    }

    public abstract EntityDimensions getNewSize(Pose pose, EntityDimensions newSize);

    public abstract float getNewEyeHeight(Pose pose, float oldEyeHeight);
}

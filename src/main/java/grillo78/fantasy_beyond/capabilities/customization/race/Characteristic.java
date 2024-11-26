package grillo78.fantasy_beyond.capabilities.customization.race;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

import java.awt.*;

public abstract class Characteristic implements INBTSerializable<CompoundTag> {

    private PlayerCustomization playerCustomization;
    private int variant = 0;
    private int maxVariant;
    protected String name;

    public Characteristic(PlayerCustomization playerCustomization,String name, int maxVariant) {
        this.playerCustomization = playerCustomization;
        this.name = name;
        this.maxVariant = maxVariant;
    }

    public String getName() {
        return name;
    }

    public Color getColor(){
        return Color.WHITE;
    }

    public int getMaxVariant() {
        return maxVariant;
    }

    public void setMaxVariant(int maxVariant) {
        this.maxVariant = maxVariant;
    }

    public int getVariant() {
        return variant;
    }

    public void setVariant(int variant) {
        this.variant = variant;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("variant", variant);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        variant = nbt.getInt("variant");
    }

    public PlayerCustomization getPlayerCustomization() {
        return playerCustomization;
    }

    public abstract void render(PlayerModel<Player> model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, Player player);
}

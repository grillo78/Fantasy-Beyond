package grillo78.fantasy_beyond.character.customization.race;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.util.INBTSerializable;

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
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("variant", variant);
        if(this instanceof Coloreable)
            compoundTag.putInt("color", getColor().hashCode());
        return compoundTag;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void translateToArm(PoseStack poseStack, HumanoidArm arm);

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt ) {
        variant = nbt.getInt("variant");
        if (this instanceof Coloreable)
            ((Coloreable)this).setColor(new Color(nbt.getInt("color")));
    }

    public PlayerCustomization getPlayerCustomization() {
        return playerCustomization;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract CustomizationModel getModel();

    @OnlyIn(Dist.CLIENT)
    public abstract void render(PlayerModel<Player> model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, Player player);
}

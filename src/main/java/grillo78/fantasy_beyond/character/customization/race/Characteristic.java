package grillo78.fantasy_beyond.character.customization.race;

import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LivingEntity;
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

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt ) {
        variant = nbt.getInt("variant");
        if (this instanceof Coloreable)
            ((Coloreable)this).setColor(new Color(nbt.getInt("color")));
    }

    public PlayerCustomization getPlayerCustomization() {
        return playerCustomization;
    }

    public ResourceLocation getTexture(LivingEntity player) {
        return ResourceLocation.parse("");
    }
    public ResourceLocation getTexture(LivingEntity player, boolean renderingIris) {
        return getTexture(player);
    }
}

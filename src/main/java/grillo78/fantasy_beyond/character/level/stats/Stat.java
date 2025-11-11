package grillo78.fantasy_beyond.character.level.stats;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.Util;
import grillo78.fantasy_beyond.character.level.Level;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.HashMap;

public class Stat implements INBTSerializable<CompoundTag> {

    private HashMap<Holder<Attribute>, Float> attributesMultipliers;
    private int level = 0;

    public Stat(HashMap<Holder<Attribute>, Float> attributesMultipliers) {
        this.attributesMultipliers = attributesMultipliers;
    }

    public void increase() {
        level++;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compound = new CompoundTag();
        compound.putInt("level", level);
        return compound;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        level = nbt.getInt("level");
    }

    public int getLevel() {
        return level;
    }

    public void apply(LivingEntity player, Level level) {
        attributesMultipliers.forEach((attribute, value) -> {
            float percentage = 1;
            if (attribute == Attributes.MAX_HEALTH)
                percentage = player.getHealth()/player.getMaxHealth();
            Util.setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, attribute.getRegisteredName().split(":")[0]), attribute,value * this.level, AttributeModifier.Operation.ADD_VALUE);
            if (attribute == Attributes.MAX_HEALTH)
                player.setHealth(player.getMaxHealth() * percentage);
        });
    }
}

package grillo78.fantasy_beyond.character.level.stats;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.Util;
import grillo78.fantasy_beyond.character.level.Level;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.UUID;

public class Stat implements INBTSerializable<CompoundTag> {

    private Holder<Attribute> attribute;
    private UUID modifierID;
    private float multiplier;
    private int level = 0;

    public Stat(Holder<Attribute> attribute, float multiplier) {
        this.attribute = attribute;
        this.modifierID = UUID.randomUUID();
        this.multiplier = multiplier;
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
        Util.setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, attribute.getRegisteredName().split(":")[0]), attribute,(attribute == Attributes.MAX_HEALTH ? 25 * (level.getLevel()-1) : 0) + this.multiplier * this.level, AttributeModifier.Operation.ADD_VALUE);
    }
}

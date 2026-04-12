package grillo78.fantasy_beyond.magic.spell;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;

public abstract class Spell implements INBTSerializable<CompoundTag> {

    private SpellType type;

    public Spell() {}

    public SpellType getType() {
        return type;
    }

    public void setType(SpellType type) {
        this.type = type;
    }
    public abstract void tick(LivingEntity owner);

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("type", SpellType.SPELL_TYPES_REGISTRY.getKey(type).toString());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {

    }
}

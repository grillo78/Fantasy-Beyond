package grillo78.fantasy_beyond.character.level.classes;

import grillo78.fantasy_beyond.character.customization.race.RaceType;
import grillo78.fantasy_beyond.character.level.Level;
import grillo78.fantasy_beyond.items.CustomWeapon;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public abstract class PlayerClass implements INBTSerializable<CompoundTag> {
    private Level level = new Level(this);
    private PlayerClassType type;

    public PlayerClass() {
    }

    public abstract Component getDisplayName();

    public void onIncreaseLevel(){
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();

        tag.putString("type", PlayerClassType.PLAYER_CLASS_TYPES_REGISTRY.getKey(type).toString());
        tag.put("level", level.serializeNBT(provider));

        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        level.deserializeNBT(provider, nbt.getCompound("level"));
    }

    public PlayerClassType getType() {
        return type;
    }

    public abstract float getAttackDamageBonus(CustomWeapon customWeapon);

    public void setType(PlayerClassType type) {
        this.type = type;
    }
}

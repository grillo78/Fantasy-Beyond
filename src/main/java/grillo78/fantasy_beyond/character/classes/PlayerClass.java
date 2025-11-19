package grillo78.fantasy_beyond.character.classes;

import grillo78.fantasy_beyond.character.level.Level;
import grillo78.fantasy_beyond.character.classes.abilities.Ability;
import grillo78.fantasy_beyond.items.CustomWeapon;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerClass implements INBTSerializable<CompoundTag> {
    public static final int UNLOCK_LEVEL = 5;

//    private Level level = new Level(this);
    private PlayerClassType type;
    private List<Ability> abilities = new ArrayList<>();
    private int abilityPoints = 0;
    private int selectedAbilityIndex = 0;

    public PlayerClass() {
        this.addAbilities();
    }

    public List<Ability> getUnlockedAbilities(){
        List<Ability> unlockedAbilities = new ArrayList<>();

        for (int i = 0; i < abilities.size(); i++) {
            if (abilities.get(i).isUnlocked())
                unlockedAbilities.add(abilities.get(i));
        }

        return unlockedAbilities;
    }

    protected abstract void addAbilities();

    public abstract Component getDisplayName();

    public void onIncreaseLevel(){
        abilityPoints++;
    }

//    public Level getLevel() {
//        return level;
//    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();

        tag.putString("type", PlayerClassType.PLAYER_CLASS_TYPES_REGISTRY.getKey(type).toString());
//        tag.put("level", level.serializeNBT(provider));
        tag.putInt("abilityPoints", abilityPoints);

        CompoundTag abilities = new CompoundTag();
        for (int i = 0; i < this.abilities.size(); i++) {
            abilities.put(String.valueOf(i), this.abilities.get(i).serializeNBT(provider));
        }
        tag.put("abilities", abilities);

        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
//        level.deserializeNBT(provider, nbt.getCompound("level"));
        abilityPoints = nbt.getInt("abilityPoints");
        CompoundTag abilities = nbt.getCompound("abilities");

        for (int i = 0; i < abilities.size(); i++) {
            this.abilities.get(i).deserializeNBT(provider, abilities.getCompound(String.valueOf(i)));
        }
    }

    public Ability addAbility(Ability ability){
        abilities.add(ability);
        return ability;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public int getAbilityPoints() {
        return abilityPoints;
    }

    public void increaseAbilityPoints(int amount){
        abilityPoints += amount;
    }

    public void decreaseAbilityPoints(int amount){
        abilityPoints -= amount;
    }

    public PlayerClassType getType() {
        return type;
    }

    public abstract float getAttackDamageBonus(CustomWeapon customWeapon);

    public void setType(PlayerClassType type) {
        this.type = type;
    }

    public int getSelectedAbilityIndex() {
        return selectedAbilityIndex;
    }

    public void setSelectedAbilityIndex(int selectedAbilityIndex) {
        this.selectedAbilityIndex = selectedAbilityIndex;
    }
}

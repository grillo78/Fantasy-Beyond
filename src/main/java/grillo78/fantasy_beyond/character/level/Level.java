package grillo78.fantasy_beyond.character.level;

import grillo78.fantasy_beyond.character.CharacterData;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class Level implements INBTSerializable<CompoundTag> {

    private int level = 1;
    private double experience = 0;
    private double expToNextLevel = calcExpToNextLevel();
    private CharacterData data;

    public Level(CharacterData data) {
        this.data = data;
    }

    public int getLevel() {
        return level;
    }

    private double calcExpToNextLevel() {
//        return 100+Math.pow(1.005, level);
        return (level * 2 - 1) * 400;
    }

    public void increaseXP(LivingEntity player, double exp) {
        experience += exp;
        updateXP(player);
    }

    private void updateXP(LivingEntity player){
        if (experience >= expToNextLevel) {
            experience = experience - expToNextLevel;
            level++;
            expToNextLevel = calcExpToNextLevel();
            data.increaseStatPoints();
            data.applyStats(player);
            updateXP(player);
        }
    }

    public double getExperience() {
        return experience;
    }

    public double getExpToNextLevel() {
        return expToNextLevel;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compound = new CompoundTag();
        compound.putDouble("experience", experience);
        compound.putDouble("expToNextLevel", expToNextLevel);
        compound.putInt("level", level);
        return compound;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider,CompoundTag nbt) {
        level = nbt.getInt("level");
        experience = nbt.getDouble("experience");
        if (nbt.contains("expToNextLevel")) expToNextLevel = nbt.getDouble("expToNextLevel");
    }
}

package grillo78.fantasy_beyond.character.customization;

import grillo78.fantasy_beyond.character.customization.race.Race;
import grillo78.fantasy_beyond.character.customization.race.RaceType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class PlayerCustomization implements INBTSerializable<CompoundTag> {

    private Race race;
    private boolean young = false;
    private boolean male = true;
    private boolean finished = false;

    public PlayerCustomization() {
        race = RaceType.RACE_TYPES_REGISTRY.byId(0).createRace(this);
    }

    public boolean isFinished() {
        return finished;
    }

    public void finish() {
        finished = true;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
        race.getCharacteristics().forEach(characteristic -> {
            while (characteristic.getVariant() >= characteristic.getMaxVariant())
                characteristic.setVariant(characteristic.getMaxVariant() - 1);
        });
    }

    public boolean isYoung() {
        return young;
    }

    public void setYoung(boolean young) {
        this.young = young;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.put("race", race.serializeNBT(provider));
        tag.putBoolean("finished", finished);
        tag.putBoolean("male", male);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        finished = nbt.getBoolean("finished");
        if (nbt.contains("male"))
            male = nbt.getBoolean("male");
        if (nbt.contains("race")) {
            CompoundTag raceCompound = nbt.getCompound("race");
            if (raceCompound.contains("type")) {
                if (race == null || race.getType() != RaceType.RACE_TYPES_REGISTRY.get(ResourceLocation.parse(raceCompound.getString("type"))))
                    race = RaceType.RACE_TYPES_REGISTRY.get(ResourceLocation.parse(raceCompound.getString("type"))).createRace(this);
            }
            race.deserializeNBT(provider, raceCompound);
        }
    }
}

package grillo78.fantasy_beyond.capabilities.customization;

import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.capabilities.customization.race.RaceType;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerCustomization implements INBTSerializable<CompoundTag> {

    private Race race;
    private boolean young = false;
    private boolean male = true;
    private boolean finished = false;

    public PlayerCustomization() {
        race = ((RaceType)RaceType.RACE_TYPES_REGISTRY.get().getValues().toArray()[0]).createRace(this);
    }

    public boolean isFinished() {
        return finished;
    }

    public void finish(){
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
            while(characteristic.getVariant()>=characteristic.getMaxVariant())
                characteristic.decreaseVariant();
        });
    }

    public boolean isYoung() {
        return young;
    }

    public void setYoung(boolean young) {
        this.young = young;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("race", race.serializeNBT());
        tag.putBoolean("finished", finished);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}

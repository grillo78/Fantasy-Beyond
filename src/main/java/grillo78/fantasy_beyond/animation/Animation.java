package grillo78.fantasy_beyond.animation;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class Animation implements INBTSerializable<CompoundTag> {
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
    }
}

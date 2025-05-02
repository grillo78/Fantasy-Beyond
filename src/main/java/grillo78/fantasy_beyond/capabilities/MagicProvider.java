package grillo78.fantasy_beyond.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class MagicProvider  implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<Magic> MAGIC = CapabilityManager.get(new CapabilityToken<>(){});
    private final LazyOptional<Magic> magicOptional;

    public MagicProvider(Level level) {
        this.magicOptional = LazyOptional.of(() -> new Magic(level));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == MAGIC) {
            return magicOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return magicOptional.orElse(null).serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        magicOptional.ifPresent(magic -> magic.deserializeNBT(nbt));
    }
}

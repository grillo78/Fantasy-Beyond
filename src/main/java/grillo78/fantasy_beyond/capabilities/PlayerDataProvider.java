package grillo78.fantasy_beyond.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerDataProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<PlayerData> DATA = CapabilityManager.get(new CapabilityToken<>() {
    });
    private final LazyOptional<PlayerData> PlayerDataOptional;

    public PlayerDataProvider(Player player) {
        this.PlayerDataOptional = LazyOptional.of(() -> new PlayerData(player));
    }

    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == DATA) {
            return PlayerDataOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return PlayerDataOptional.orElse(null).serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        PlayerDataOptional.ifPresent(data -> data.deserializeNBT(nbt));
    }
}
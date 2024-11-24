package grillo78.fantasy_beyond.capabilities;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerData implements INBTSerializable<CompoundTag> {
    private Player player;
    private PlayerCustomization playerCustomization = new PlayerCustomization();

    public PlayerData(Player player) {
        this.player = player;
    }

    public PlayerCustomization getPlayerCustomization() {
        return playerCustomization;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}

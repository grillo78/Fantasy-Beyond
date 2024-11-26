package grillo78.fantasy_beyond.capabilities;

import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.network.PacketHandler;
import grillo78.fantasy_beyond.network.messages.SyncPlayerData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.NetworkDirection;

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
        compoundTag.put("customization", playerCustomization.serializeNBT());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        playerCustomization.deserializeNBT(nbt.getCompound("customization"));
    }

    public void sync() {
        ((ServerLevel) player.level()).players().forEach(playerAux->{
            PacketHandler.INSTANCE.sendTo(new SyncPlayerData(serializeNBT(), player.getId()), playerAux.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        });
    }
}

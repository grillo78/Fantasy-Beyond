package grillo78.fantasy_beyond.network.messages;

import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import grillo78.fantasy_beyond.util.ClientUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncPlayerData implements IMessage<SyncPlayerData> {

    private CompoundTag playerData;
    private int id;

    public SyncPlayerData() {
    }

    public SyncPlayerData(CompoundTag playerData, int id) {
        this.playerData = playerData;
    }

    @Override
    public void encode(SyncPlayerData message, FriendlyByteBuf buffer) {
        buffer.writeNbt(message.playerData);
        buffer.writeInt(message.id);
    }

    @Override
    public SyncPlayerData decode(FriendlyByteBuf buffer) {
        return new SyncPlayerData(buffer.readNbt(), buffer.readInt());
    }

    @Override
    public void handle(SyncPlayerData message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT,()->()-> ClientUtil.setPlayerData(message.id, message.playerData));
        });
        supplier.get().setPacketHandled(true);
    }
}

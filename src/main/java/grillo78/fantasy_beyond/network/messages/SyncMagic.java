package grillo78.fantasy_beyond.network.messages;

import grillo78.fantasy_beyond.capabilities.MagicProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncMagic implements IMessage<SyncMagic> {

    private CompoundTag nbt;

    public SyncMagic() {
    }

    public SyncMagic(CompoundTag nbt) {
        this.nbt = nbt;
    }

    @Override
    public void encode(SyncMagic message, FriendlyByteBuf buffer) {
        buffer.writeNbt(message.nbt);
    }

    @Override
    public SyncMagic decode(FriendlyByteBuf buffer) {
        return new SyncMagic(buffer.readNbt());
    }

    @Override
    public void handle(SyncMagic message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(()->{
            Minecraft.getInstance().level.getCapability(MagicProvider.MAGIC).ifPresent(magic -> magic.deserializeNBT(message.nbt));
        });
        supplier.get().setPacketHandled(true);
    }
}

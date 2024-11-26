package grillo78.fantasy_beyond.network.messages;

import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetPlayerCustomizationOnServer implements IMessage<SetPlayerCustomizationOnServer> {

    private CompoundTag playerCustomization;

    public SetPlayerCustomizationOnServer() {
    }

    public SetPlayerCustomizationOnServer(CompoundTag playerCustomization) {
        this.playerCustomization = playerCustomization;
    }

    @Override
    public void encode(SetPlayerCustomizationOnServer message, FriendlyByteBuf buffer) {
        buffer.writeNbt(message.playerCustomization);
    }

    @Override
    public SetPlayerCustomizationOnServer decode(FriendlyByteBuf buffer) {
        return new SetPlayerCustomizationOnServer(buffer.readNbt());
    }

    @Override
    public void handle(SetPlayerCustomizationOnServer message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            supplier.get().getSender().getCapability(PlayerDataProvider.DATA).ifPresent(playerData->{
                playerData.getPlayerCustomization().deserializeNBT(message.playerCustomization);
                playerData.sync();
            });
            supplier.get().getSender().refreshDimensions();
        });
        supplier.get().setPacketHandled(true);
    }
}

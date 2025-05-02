package grillo78.fantasy_beyond.network.messages;

import grillo78.fantasy_beyond.capabilities.PlayerData;
import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetAnimation implements IMessage<SetAnimation>{

    private String animation;
    private int entityID;

    public SetAnimation() {
    }

    public SetAnimation(String animation, int entityID) {
        this.animation = animation;
        this.entityID = entityID;
    }

    @Override
    public void encode(SetAnimation message, FriendlyByteBuf buffer) {
        buffer.writeUtf(message.animation);
        buffer.writeInt(message.entityID);
    }

    @Override
    public SetAnimation decode(FriendlyByteBuf buffer) {
        return new SetAnimation(buffer.readUtf(), buffer.readInt());
    }

    @Override
    public void handle(SetAnimation message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(()->{
            PlayerData data = Minecraft.getInstance().level.getEntity(message.entityID).getCapability(PlayerDataProvider.DATA).orElse(null);
            data.applyAnimation(message.animation);
        });
        supplier.get().setPacketHandled(true);
    }
}

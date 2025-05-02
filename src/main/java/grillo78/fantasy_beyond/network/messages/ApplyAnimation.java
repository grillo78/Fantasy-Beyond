package grillo78.fantasy_beyond.network.messages;

import grillo78.fantasy_beyond.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ApplyAnimation implements IMessage<ApplyAnimation>{

    private String animation;

    public ApplyAnimation() {
    }

    public ApplyAnimation(String animation) {
        this.animation = animation;
    }

    @Override
    public void encode(ApplyAnimation message, FriendlyByteBuf buffer) {
        buffer.writeUtf(message.animation);
    }

    @Override
    public ApplyAnimation decode(FriendlyByteBuf buffer) {
        return new ApplyAnimation(buffer.readUtf());
    }

    @Override
    public void handle(ApplyAnimation message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(()->{
            ServerPlayer player = supplier.get().getSender();
            for (ServerPlayer auxPlayer : player.serverLevel().players()) {
                PacketHandler.INSTANCE.sendTo(new SetAnimation(message.animation, player.getId()), auxPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}

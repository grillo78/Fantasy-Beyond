package grillo78.fantasy_beyond.network.messages;

import grillo78.fantasy_beyond.client.screen.CustomizationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenCustomizationScreen implements IMessage<OpenCustomizationScreen> {
    @Override
    public void encode(OpenCustomizationScreen message, FriendlyByteBuf buffer) {

    }

    @Override
    public OpenCustomizationScreen decode(FriendlyByteBuf buffer) {
        return new OpenCustomizationScreen();
    }

    @Override
    public void handle(OpenCustomizationScreen message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft.getInstance().setScreen(new CustomizationScreen());
        });
        supplier.get().setPacketHandled(true);
    }
}

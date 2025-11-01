package grillo78.fantasy_beyond.network;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.client.screen.CustomizationScreen;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenScreen() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<OpenScreen> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "open_screen"));
    public static final StreamCodec<ByteBuf, OpenScreen> STREAM_CODEC = StreamCodec.of(((buffer, value) -> {}), (byteBuffer)->new OpenScreen());
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleServer(final OpenScreen data, final IPayloadContext context) {}

    @OnlyIn(Dist.CLIENT)
    public static void handle(final OpenScreen data, final IPayloadContext context) {
        if (FMLLoader.getDist().isClient())
            Minecraft.getInstance().setScreen(new CustomizationScreen());
    }
}

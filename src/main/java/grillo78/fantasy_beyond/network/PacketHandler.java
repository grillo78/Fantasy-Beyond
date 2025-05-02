package grillo78.fantasy_beyond.network;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.network.messages.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";

    public static SimpleChannel INSTANCE;
    private static int nextId = 0;

    /**
     * create the network channel and register the packets
     */
    public static void init() {
        // Create the Network channel
        INSTANCE = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(FantasyBeyond.MOD_ID, "network"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();

        // Register packets
        register(OpenCustomizationScreen.class, new OpenCustomizationScreen());
        register(SetPlayerCustomizationOnServer.class, new SetPlayerCustomizationOnServer());
        register(SyncPlayerData.class, new SyncPlayerData());
        register(SetAnimation.class, new SetAnimation());
        register(ApplyAnimation.class, new ApplyAnimation());
        register(SyncMagic.class, new SyncMagic());
        register(SetEffect.class, new SetEffect());
        register(ScrollSpell.class, new ScrollSpell());
    }

    /**
     * Method to register a packet
     *
     * @param clazz   Class of the packet
     * @param message Message object
     * @param <T>
     */
    private static <T> void register(Class<T> clazz, IMessage<T> message) {
        INSTANCE.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle);
    }
}

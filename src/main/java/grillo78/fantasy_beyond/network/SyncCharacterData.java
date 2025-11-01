package grillo78.fantasy_beyond.network;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncCharacterData(int id, CompoundTag nbt) implements CustomPacketPayload {

    public static final Type<SyncCharacterData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "sync_character_data"));
    public static final StreamCodec<ByteBuf, SyncCharacterData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SyncCharacterData::id,
            ByteBufCodecs.COMPOUND_TAG,
            SyncCharacterData::nbt,
            SyncCharacterData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SyncCharacterData data, final IPayloadContext context) {
        Level level = context.player().level();
        Entity entity = level.getEntity(data.id);
        entity.getData(ModAttachments.CHARACTER_DATA).deserializeNBT(null, data.nbt);
        entity.refreshDimensions();
        if(!level.isClientSide)
            PacketDistributor.sendToAllPlayers(new SyncCharacterData(data.id, data.nbt));
    }
}

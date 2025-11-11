package grillo78.fantasy_beyond.network;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record IncreaseStat(String name) implements CustomPacketPayload {

    public static final Type<IncreaseStat> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "increase_stat"));
    public static final StreamCodec<ByteBuf, IncreaseStat> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            IncreaseStat::name,
            IncreaseStat::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final IncreaseStat data, final IPayloadContext context) {
        Entity entity = context.player();
        if(entity instanceof LivingEntity){
            entity.getData(ModAttachments.CHARACTER_DATA).getStats().get(data.name).increase();
            entity.getData(ModAttachments.CHARACTER_DATA).decreaseStatPoint();
            entity.getData(ModAttachments.CHARACTER_DATA).applyStats((LivingEntity) entity);
            if (!entity.level().isClientSide)
                PacketDistributor.sendToAllPlayers(new SyncCharacterData(entity.getId(), entity.getData(ModAttachments.CHARACTER_DATA).serializeNBT(null)));
        }
    }
}

package grillo78.fantasy_beyond.network;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateQuiverIndex(int slot, boolean container, int index) implements CustomPacketPayload {

    public static final Type<UpdateQuiverIndex> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "update_quiver_index"));
    public static final StreamCodec<ByteBuf, UpdateQuiverIndex> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            UpdateQuiverIndex::slot,
            ByteBufCodecs.BOOL,
            UpdateQuiverIndex::container,
            ByteBufCodecs.INT,
            UpdateQuiverIndex::index,
            UpdateQuiverIndex::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final UpdateQuiverIndex data, final IPayloadContext context) {
        Player entity = context.player();
        if(data.container){
            entity.containerMenu.getSlot(data.slot).getItem().get(ModDataComponents.QUIVER_CONTENTS).setIndex(data.index);
            entity.containerMenu.getSlot(data.slot).setChanged();
        } else
            entity.getSlot(data.slot).get().get(ModDataComponents.QUIVER_CONTENTS).setIndex(data.index);
        entity.containerMenu.sendAllDataToRemote();
    }
}

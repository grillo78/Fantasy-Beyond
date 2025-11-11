package grillo78.fantasy_beyond.network;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateItemContainerItem(int slot, boolean container, String item) implements CustomPacketPayload {

    public static final Type<UpdateItemContainerItem> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "update_item_container_item"));
    public static final StreamCodec<ByteBuf, UpdateItemContainerItem> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            UpdateItemContainerItem::slot,
            ByteBufCodecs.BOOL,
            UpdateItemContainerItem::container,
            ByteBufCodecs.STRING_UTF8,
            UpdateItemContainerItem::item,
            UpdateItemContainerItem::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final UpdateItemContainerItem data, final IPayloadContext context) {
        Player entity = context.player();
        if(data.container){
            entity.containerMenu.getSlot(data.slot).getItem().get(ModDataComponents.ITEM_CONTENTS).setSelectedItem(BuiltInRegistries.ITEM.get(ResourceLocation.parse(data.item)));
            entity.containerMenu.getSlot(data.slot).setChanged();
        } else
            entity.getSlot(data.slot).get().get(ModDataComponents.ITEM_CONTENTS).setSelectedItem(BuiltInRegistries.ITEM.get(ResourceLocation.parse(data.item)));
        entity.containerMenu.sendAllDataToRemote();
    }
}

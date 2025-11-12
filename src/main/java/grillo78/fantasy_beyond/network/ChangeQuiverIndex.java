package grillo78.fantasy_beyond.network;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.items.QuiverItem;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.items.components.QuiverContents;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.awt.*;
import java.util.List;

public record ChangeQuiverIndex(int amount) implements CustomPacketPayload {

    public static final Type<ChangeQuiverIndex> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "change_quiver_index"));
    public static final StreamCodec<ByteBuf, ChangeQuiverIndex> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ChangeQuiverIndex::amount,
            ChangeQuiverIndex::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final ChangeQuiverIndex data, final IPayloadContext context) {
        Player entity = context.player();
        List<SlotResult> slots = CuriosApi.getCuriosInventory(entity).get().findCurios("back");
        boolean notObtained = true;
        for (int i = 0; i < slots.size() && notObtained; i++) {
            SlotResult slot = slots.get(i);
            ItemStack stack = slot.stack();
            if (stack.getItem() instanceof QuiverItem) {
                notObtained = false;
                QuiverContents quiverContents = stack.get(ModDataComponents.QUIVER_CONTENTS);
                int index = quiverContents.getIndex();
                for (int j = 0; j < Math.abs(data.amount); j++) {
                    index += data.amount < 0 ? 1 : -1;
                    if (index < 0)
                        index = quiverContents.getItems().size() - 1;
                    if (index >= quiverContents.getItems().size())
                        index = 0;
                }
                stack.set(ModDataComponents.QUIVER_CONTENTS, new QuiverContents(List.copyOf(quiverContents.getItems()), index));
                PotionContents potionContents = quiverContents.getItems().get(index).getItems().getFirst().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                MutableComponent arrowComponent = Component.literal(quiverContents.getItems().get(index).getItems().getFirst().getHoverName().getString());
                arrowComponent.withColor(potionContents.potion().isPresent() ? potionContents.getColor() : Color.WHITE.hashCode());
                entity.displayClientMessage(Component.translatable("set_arrow.message", arrowComponent), true);
            }
        }
    }
}

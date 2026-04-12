package grillo78.fantasy_beyond.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Objects;

public class TongsContent implements TooltipComponent {
    public static final TongsContent EMPTY = new TongsContent(ItemStack.EMPTY);
    public static final Codec<TongsContent> CODEC = RecordCodecBuilder.create(
            itemContentsInstance -> itemContentsInstance.group(
                            ItemStack.CODEC.fieldOf("item").forGetter(TongsContent::getItem)
                    )
                    .apply(itemContentsInstance, TongsContent::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, TongsContent> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            TongsContent::getItem,
            TongsContent::new);

    private ItemStack item;

    public TongsContent(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            if (item == null)
                return false;
            else
                return !(other instanceof TongsContent tongsContent) ? false : item.equals(tongsContent.item);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

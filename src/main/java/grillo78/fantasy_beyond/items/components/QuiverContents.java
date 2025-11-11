package grillo78.fantasy_beyond.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class QuiverContents implements TooltipComponent {
    public static final QuiverContents EMPTY = new QuiverContents(List.of(), 0);
    public static final Codec<QuiverContents> CODEC = RecordCodecBuilder.create(
            itemContentsInstance -> itemContentsInstance.group(
                            Codec.list(ArrowItemCodec.CODEC).fieldOf("items").forGetter(QuiverContents::getItems),
                            Codec.INT.fieldOf("index").forGetter(QuiverContents::getIndex)
                    )
                    .apply(itemContentsInstance, QuiverContents::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, QuiverContents> STREAM_CODEC = StreamCodec.composite(
            ArrowItemCodec.STREAM_CODEC.apply(ByteBufCodecs.list()),
            QuiverContents::getItems,
            ByteBufCodecs.INT,
            QuiverContents::getIndex,
            QuiverContents::new);

    private List<ArrowItemCodec> items;
    private int index;

    public QuiverContents(List<ArrowItemCodec> items, int index) {
        this.items = items;
        this.index = index;
    }

    public List<ArrowItemCodec> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return !(other instanceof QuiverContents quiverContents) ? false : items.equals(quiverContents.items);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.items, this.index);
    }

    public int getAmount() {
        int amount = 0;
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < items.get(i).getItems().size(); j++) {
                amount += items.get(i).getItems().get(j).getCount();
            }
        }
        return amount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

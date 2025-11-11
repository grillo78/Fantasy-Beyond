package grillo78.fantasy_beyond.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import grillo78.fantasy_beyond.character.level.stats.Stat;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ArrowItemCodec {
    public static Codec<ArrowItemCodec> CODEC = RecordCodecBuilder.create(
            itemContentsInstance -> itemContentsInstance.group(
                            Codec.list(ItemStack.CODEC).fieldOf("items").forGetter(ArrowItemCodec::getItems),
                            Codec.STRING.fieldOf("item_name").forGetter(ArrowItemCodec::getItemName),
                            Potion.CODEC.optionalFieldOf("potion").forGetter(ArrowItemCodec::getPotion)
                    )
                    .apply(itemContentsInstance, ArrowItemCodec::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ArrowItemCodec> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
            ArrowItemCodec::getItems,
            ByteBufCodecs.STRING_UTF8,
            ArrowItemCodec::getItemName,
            Potion.STREAM_CODEC.apply(ByteBufCodecs::optional),
            ArrowItemCodec::getPotion,
            ArrowItemCodec::new);

    private Item item;
    private List<ItemStack> items;
    private Optional<Holder<Potion>> potion;

    public ArrowItemCodec(List<ItemStack> items, String itemName, Optional<Holder<Potion>> potion) {
        this.item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName));
        this.items = items;
        this.potion = potion;
    }

    public ArrowItemCodec(List<ItemStack> items, Item item, Optional<Holder<Potion>> potion) {
        this.item = item;
        this.items = items;
        this.potion = potion;
    }

    private String getItemName() {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    private Optional<Holder<Potion>> getPotion() {
        return Optional.empty();
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return !(other instanceof ArrowItemCodec arrowItemCodec) ? false : ItemStack.listMatches(this.items, arrowItemCodec.items) && arrowItemCodec.item.equals(this.item) && this.potion.equals(arrowItemCodec.potion);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(ItemStack.hashStackList(this.items), this.potion, this.item);
    }

    public boolean validStack(ItemStack other) {
        return other.getItem() == item && other.get(DataComponents.POTION_CONTENTS).potion().equals(potion);
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }
}

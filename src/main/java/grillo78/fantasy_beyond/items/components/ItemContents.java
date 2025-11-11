package grillo78.fantasy_beyond.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemContents implements TooltipComponent {
    public static final ItemContents EMPTY = new ItemContents(List.of(), "");
    public static final Codec<ItemContents> CODEC = RecordCodecBuilder.create(
            itemContentsInstance -> itemContentsInstance.group(
                            Codec.list(ItemStack.CODEC).fieldOf("items").forGetter(ItemContents::getItems),
                            Codec.STRING.fieldOf("selectedItem").forGetter((itemContents -> itemContents.selectedItem != null ? BuiltInRegistries.ITEM.getKey(itemContents.selectedItem).toString() : ""))
                    )
                    .apply(itemContentsInstance, ItemContents::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemContents> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
            ItemContents::getItems,
            ByteBufCodecs.STRING_UTF8,
            (itemContents -> BuiltInRegistries.ITEM.getKey(itemContents.selectedItem).toString()),
            ItemContents::new);

    private List<ItemStack> items;
    private Item selectedItem;

    public ItemContents(List<ItemStack> items, String location) {
        this.items = items;
        if (!location.equals(""))
            this.selectedItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(location));
    }

    public ItemContents(List<ItemStack> items, Item selectedItem) {
        this.items = items;
        if (selectedItem != null)
            this.selectedItem = selectedItem;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return !(other instanceof ItemContents bundleContents) ? false : ItemStack.listMatches(this.items, bundleContents.items);
        }
    }

    @Override
    public int hashCode() {
        return ItemStack.hashStackList(this.items);
    }

    public int getAmount() {
        int amount = 0;
        for (int i = 0; i < items.size(); i++) {
            amount += items.get(i).getCount();
        }
        return amount;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<Item> getContainedItems() {
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < this.items.size(); i++) {
            ItemStack stack = this.items.get(i);
            if (!items.contains(stack.getItem()))
                items.add(stack.getItem());
        }

        return items;
    }
}

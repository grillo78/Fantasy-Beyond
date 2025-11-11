package grillo78.fantasy_beyond.items;

import grillo78.fantasy_beyond.items.components.ArrowItemCodec;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.items.components.QuiverContents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuiverItem extends Item {
    public QuiverItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack other = slot.getItem();
        if (stack.getCount() != 1 || (!(isValid(other)) && !other.isEmpty())) return false;
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            QuiverContents quiverContents = stack.get(ModDataComponents.QUIVER_CONTENTS.get());
            if (quiverContents != null) {
                List<ArrowItemCodec> arrowItems = new ArrayList<>(quiverContents.getItems());
                if (!other.isEmpty()) {
                    int finalIndex = -1;
                    for (int i = 0; i < arrowItems.size() && finalIndex == -1; i++) {
                        ArrowItemCodec arrowItem = arrowItems.get(i);
                        if (arrowItem.validStack(other)) {
                            finalIndex = i;
                        }
                    }
                    if (finalIndex == -1) {
                        arrowItems.add(new ArrowItemCodec(List.of(other.copy()), other.getItem(), other.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion()));
                        other.setCount(0);
                    } else {
                        List<ItemStack> items = arrowItems.get(finalIndex).getItems();
                        ItemStack stackInside = items.getLast();
                        if (stackInside.getCount() != stackInside.getMaxStackSize()) {
                            int amount = Math.min(stackInside.getMaxStackSize() - stackInside.getCount(), other.getCount());
                            if (quiverContents.getAmount() + amount > getMaxContent())
                                amount = getMaxContent() - quiverContents.getAmount();
                            stackInside.grow(amount);
                            other.shrink(amount);
                        }
                        if (!other.isEmpty() && quiverContents.getAmount() != getMaxContent()) {
                            items.add(other.copy());
                            other.setCount(0);
                        }
                    }
                    stack.set(ModDataComponents.QUIVER_CONTENTS, new QuiverContents(List.copyOf(arrowItems), quiverContents.getIndex()));
                } else {
                    if (quiverContents.getAmount() > 0) {
                        int index = quiverContents.getIndex();
                        List<ArrowItemCodec> list = new ArrayList<>(quiverContents.getItems());
                        if(index>=list.size())
                            index = list.size()-1;
                        List<ItemStack> items = new ArrayList<>(list.get(index).getItems());
                        slot.set(items.getLast());
                        items.removeLast();
                        list.get(index).setItems(List.copyOf(items));
                        if(items.isEmpty())
                            list.remove(index);
                        stack.set(ModDataComponents.QUIVER_CONTENTS, new QuiverContents(List.copyOf(list), index));
                    } else
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.getCount() != 1 || (!(isValid(other)) && !other.isEmpty())) return false;
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            QuiverContents quiverContents = stack.get(ModDataComponents.QUIVER_CONTENTS.get());
            if (quiverContents != null) {
                List<ArrowItemCodec> arrowItems = new ArrayList<>(quiverContents.getItems());
                if (!other.isEmpty()) {
                    int finalIndex = -1;
                    for (int i = 0; i < arrowItems.size() && finalIndex == -1; i++) {
                        ArrowItemCodec arrowItem = arrowItems.get(i);
                        if (arrowItem.validStack(other)) {
                            finalIndex = i;
                        }
                    }
                    if (finalIndex == -1) {
                        arrowItems.add(new ArrowItemCodec(List.of(other.copy()), other.getItem(), other.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion()));
                        other.setCount(0);
                    } else {
                        List<ItemStack> items = arrowItems.get(finalIndex).getItems();
                        ItemStack stackInside = items.getLast();
                        if (stackInside.getCount() != stackInside.getMaxStackSize()) {
                            int amount = Math.min(stackInside.getMaxStackSize() - stackInside.getCount(), other.getCount());
                            if (quiverContents.getAmount() + amount > getMaxContent())
                                amount = getMaxContent() - quiverContents.getAmount();
                            stackInside.grow(amount);
                            other.shrink(amount);
                        }
                        if (!other.isEmpty() && quiverContents.getAmount() != getMaxContent()) {
                            items.add(other.copy());
                            other.setCount(0);
                        }
                    }
                    stack.set(ModDataComponents.QUIVER_CONTENTS, new QuiverContents(List.copyOf(arrowItems), quiverContents.getIndex()));
                } else {
                    if (quiverContents.getAmount() > 0) {
                        int index = quiverContents.getIndex();
                        List<ArrowItemCodec> list = new ArrayList<>(quiverContents.getItems());
                        if(index>=list.size())
                            index = list.size()-1;
                        List<ItemStack> items = new ArrayList<>(list.get(index).getItems());
                        access.set(items.getLast());
                        items.removeLast();
                        list.get(index).setItems(List.copyOf(items));
                        if(items.isEmpty())
                            list.remove(index);
                        stack.set(ModDataComponents.QUIVER_CONTENTS, new QuiverContents(List.copyOf(list), index));
                    } else
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    protected boolean isValid(ItemStack stack) {
        return stack.getItem() instanceof ArrowItem;
    }

    protected int getMaxContent() {
        return 512;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        QuiverContents bundleContents = stack.get(ModDataComponents.QUIVER_CONTENTS.get());
        if (bundleContents != null) {
            tooltipComponents.add(Component.translatable(getContentTranslatable(), bundleContents.getAmount()));
        }
        for (int i = 0; i < bundleContents.getItems().size(); i++) {
            ItemStack firstItemStack = bundleContents.getItems().get(i).getItems().getFirst();
            PotionContents potionContents = firstItemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            int amount = 0;
            for (int j = 0; j < bundleContents.getItems().get(i).getItems().size(); j++) {
                amount += bundleContents.getItems().get(i).getItems().get(j).getCount();
            }
            tooltipComponents.add(Component.literal(bundleContents.getIndex() == i? "> " : "").append(firstItemStack.getHoverName()).append(Component.literal(": " + amount)).withColor(firstItemStack.has(DataComponents.POTION_CONTENTS) ? potionContents.getColor() : Color.WHITE.hashCode()));
        }
    }

    protected String getContentTranslatable() {
        return "fantasy_beyond.tooltip.total_arrows";
    }
}

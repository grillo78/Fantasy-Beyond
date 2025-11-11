package grillo78.fantasy_beyond.items;

import grillo78.fantasy_beyond.items.components.ItemContents;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemContainer extends Item {
    public ItemContainer(Properties properties) {
        super(properties);
    }

    protected abstract boolean isValid(ItemStack stack);

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack other = slot.getItem();
        if (stack.getCount() != 1 || (!(isValid(other)) && !other.isEmpty())) return false;
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            ItemContents bundleContents = stack.get(ModDataComponents.ITEM_CONTENTS.get());
            if (bundleContents != null) {
                List<ItemStack> items = new ArrayList<>(bundleContents.getItems());
                if (!other.isEmpty()) {
                    if (bundleContents.getAmount() != getMaxContent()) {
                        if (items.size() == 0) {
                            items.add(other.copy());
                            other.setCount(0);
                        } else {
                            ItemStack stackInside = items.getLast();
                            if (stackInside.getCount() != stackInside.getMaxStackSize()) {
                                int amount = Math.min(stackInside.getMaxStackSize() - stackInside.getCount(), other.getCount());
                                if (bundleContents.getAmount() + amount > getMaxContent())
                                    amount = getMaxContent() - bundleContents.getAmount();
                                stackInside.grow(amount);
                                other.shrink(amount);
                            }
                            if (!other.isEmpty() && bundleContents.getAmount() != getMaxContent()) {
                                items.add(other.copy());
                                other.setCount(0);
                            }
                        }
                        stack.set(ModDataComponents.ITEM_CONTENTS, new ItemContents(List.copyOf(items), bundleContents.getSelectedItem()));
                    }
                } else {
                    if (bundleContents.getAmount() > 0) {
                        int selectedStack = getStackIndex(bundleContents, items);
                        slot.set(items.get(selectedStack));
                        items.remove(selectedStack);
                        stack.set(ModDataComponents.ITEM_CONTENTS, new ItemContents(List.copyOf(items), bundleContents.getSelectedItem()));
                    } else
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    private int getStackIndex(ItemContents bundleContents, List<ItemStack> items) {
        int index = items.size() - 1;

        if (bundleContents.getSelectedItem() != null)
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getItem() == bundleContents.getSelectedItem())
                    index = i;
            }

        return index;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.getCount() != 1 || (!(isValid(other)) && !other.isEmpty())) return false;
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            ItemContents bundleContents = stack.get(ModDataComponents.ITEM_CONTENTS.get());
            if (bundleContents != null) {
                List<ItemStack> items = new ArrayList<>(bundleContents.getItems());
                if (!other.isEmpty()) {
                    if (bundleContents.getAmount() != getMaxContent()) {
                        if (items.size() == 0) {
                            items.add(other.copy());
                            other.setCount(0);
                        } else {
                            ItemStack stackInside = items.getLast();
                            if (stackInside.getCount() != stackInside.getMaxStackSize()) {
                                int amount = Math.min(stackInside.getMaxStackSize() - stackInside.getCount(), other.getCount());
                                if (bundleContents.getAmount() + amount > getMaxContent())
                                    amount = getMaxContent() - bundleContents.getAmount();
                                stackInside.grow(amount);
                                other.shrink(amount);
                            }
                            if (!other.isEmpty() && bundleContents.getAmount() != getMaxContent()) {
                                items.add(other.copy());
                                other.setCount(0);
                            }
                        }
                        stack.set(ModDataComponents.ITEM_CONTENTS, new ItemContents(List.copyOf(items), bundleContents.getSelectedItem()));
                    }
                } else {
                    if (bundleContents.getAmount() > 0) {
                        int selectedStack = getStackIndex(bundleContents, items);
                        access.set(items.get(selectedStack));
                        items.remove(selectedStack);
                        stack.set(ModDataComponents.ITEM_CONTENTS, new ItemContents(List.copyOf(items), bundleContents.getSelectedItem()));
                    } else
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    protected int getMaxContent() {
        return 10240;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        ItemContents bundleContents = stack.get(ModDataComponents.ITEM_CONTENTS.get());
        if (bundleContents != null) {
            tooltipComponents.add(Component.translatable(getContentTranslatable(), bundleContents.getAmount()));
        }
    }

    protected String getContentTranslatable() {
        return "fantasy_beyond.tooltip.total_coins";
    }
}

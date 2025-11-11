package grillo78.fantasy_beyond.items;

import grillo78.fantasy_beyond.items.components.ItemContents;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class CoinBundleItem extends ItemContainer {
    public CoinBundleItem(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isValid(ItemStack stack) {
        return stack.getItem() instanceof CoinItem;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        ItemContents bundleContents = stack.get(ModDataComponents.ITEM_CONTENTS.get());
        for (int i = 0; i < bundleContents.getContainedItems().size(); i++) {
            MutableComponent component = null;
            int amount = 0;
            for (int j = 0; j < bundleContents.getItems().size(); j++) {
                if(bundleContents.getItems().get(j).getItem() == bundleContents.getContainedItems().get(i)){
                    if(component == null)
                        component = Component.literal(bundleContents.getItems().get(j).getItem() == bundleContents.getSelectedItem()? "> " : "").append(bundleContents.getItems().get(j).getHoverName().getString());
                    amount += bundleContents.getItems().get(j).getCount();
                }
            }
            tooltipComponents.add(component.append(": " + amount));
        }
    }
}

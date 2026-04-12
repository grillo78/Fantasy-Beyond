package grillo78.fantasy_beyond.items;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.magic.spell.SpellType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GrimoireItem extends Item {

    public GrimoireItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!player.level().isClientSide && player.getData(ModAttachments.CHARACTER_DATA).getActiveSpell() == null)
            player.getData(ModAttachments.CHARACTER_DATA).setActiveSpell(SpellType.HEALING.createSpell());
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}

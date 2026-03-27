package grillo78.fantasy_beyond.items;

import grillo78.fantasy_beyond.block_entities.AnvilBlockEntity;
import grillo78.fantasy_beyond.block_entities.ForgeBlockEntity;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.items.components.TongsContent;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TongsItem extends Item {
    public TongsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = super.useOn(context);
        BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
        ItemStack tongs = context.getPlayer().getItemInHand(context.getHand());
        TongsContent tongsContent;
        if (!tongs.has(ModDataComponents.TONGS_CONTENT))
            tongsContent = TongsContent.EMPTY;
        else
            tongsContent = tongs.get(ModDataComponents.TONGS_CONTENT);
        if (blockEntity instanceof ForgeBlockEntity) {
            if (!context.getLevel().isClientSide) {
                ItemStack oldForgeItem = ((ForgeBlockEntity) blockEntity).getHeatingItem();
                ((ForgeBlockEntity) blockEntity).setHeatingItem(tongsContent.getItem().copy(), false);
                tongsContent = oldForgeItem.isEmpty()? null : new TongsContent(oldForgeItem);
            }
            result = InteractionResult.SUCCESS;
        }else{
            if (blockEntity instanceof AnvilBlockEntity) {
                if (!context.getLevel().isClientSide) {
                    ItemStack oldAnvilItem = ((AnvilBlockEntity) blockEntity).getPiece();
                    ((AnvilBlockEntity) blockEntity).setPiece(tongsContent.getItem().copy(), false);
                    tongsContent = oldAnvilItem.isEmpty()? null : new TongsContent(oldAnvilItem);
                }
                result = InteractionResult.SUCCESS;
            }else{
                if (!context.getLevel().isClientSide) {
                    ItemEntity itemEntity;
                    if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof LayeredCauldronBlock)
                        itemEntity = new ItemEntity(context.getLevel(), context.getClickedPos().getX()+0.5, context.getClickedPos().getY() + 1, context.getClickedPos().getZ()+0.5, tongsContent.getItem());
                    else
                        itemEntity = new ItemEntity(context.getLevel(), context.getClickLocation().x, context.getClickLocation().y, context.getClickLocation().z, tongsContent.getItem());
                    itemEntity.setThrower(context.getPlayer());
                    itemEntity.setDefaultPickUpDelay();
                    itemEntity.setDeltaMovement(Vec3.ZERO);
                    context.getLevel().addFreshEntity(itemEntity);
                    tongsContent = null;
                }
                result = InteractionResult.SUCCESS;
            }
        }
        if (tongsContent == null) {
            tongs.remove(ModDataComponents.TONGS_CONTENT);
        } else
            tongs.set(ModDataComponents.TONGS_CONTENT, tongsContent);
        return result;
    }
}

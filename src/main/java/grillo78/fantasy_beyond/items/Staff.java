package grillo78.fantasy_beyond.items;

import grillo78.fantasy_beyond.blocks.Book;
import grillo78.fantasy_beyond.capabilities.MagicProvider;
import grillo78.fantasy_beyond.capabilities.spells_book.SpellsBookProvider;
import grillo78.fantasy_beyond.magic.magic_circles.MagicCircleType;
import grillo78.fantasy_beyond.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class Staff extends Item {
    public Staff(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        BlockPos clickedPos = ((BlockHitResult) pPlayer.pick(pPlayer.getEntityReach(), 0, false)).getBlockPos();
        BlockState blockState = pLevel.getBlockState(clickedPos);
        if (!pLevel.isClientSide) {
            if ((blockState.getBlock() instanceof Book))
                pLevel.getCapability(MagicProvider.MAGIC).ifPresent(magic -> {
                    pLevel.playSound(null, pPlayer.getOnPos(), ModSounds.LOPUS_INVOCATION.get(), SoundSource.PLAYERS, 1, 1);
                    magic.getMagicCircles().add(MagicCircleType.INVOCATION_CIRCLE.get().createInstance((ServerLevel) pLevel, new Vec3(clickedPos.getX() + 0.5, clickedPos.getY(), clickedPos.getZ() + 0.5), new ArrayList<>()));
                });
            else if (pPlayer.getOffhandItem().getItem() instanceof SpellsBook)
                pPlayer.getOffhandItem().getCapability(SpellsBookProvider.SPELLS_BOOK).ifPresent(spellsBook -> {
                    spellsBook.castSpell(pLevel, pPlayer);
                });
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}

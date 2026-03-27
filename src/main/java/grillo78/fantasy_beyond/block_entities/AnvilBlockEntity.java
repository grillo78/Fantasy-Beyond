package grillo78.fantasy_beyond.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AnvilBlockEntity extends BlockEntity {
    private ItemStack piece = ItemStack.EMPTY;

    public AnvilBlockEntity(BlockPos pos, BlockState blockState) {
        this(ModBlockEntities.ANVIL.get(), pos, blockState);
    }

    public AnvilBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        piece = ItemStack.parseOptional(registries, tag.getCompound("piece"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("piece", piece.equals(ItemStack.EMPTY) ? new CompoundTag() : piece.save(registries));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries) {
        super.onDataPacket(connection, packet, registries);
        loadAdditional(packet.getTag(), registries);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AnvilBlockEntity blockEntity) {
        if (!blockEntity.piece.isEmpty()) {
            blockEntity.setChanged();
        }
    }

    public ItemStack getPiece() {
        return piece;
    }

    public void setPiece(ItemStack piece) {
        setPiece(piece, true);
    }

    public void setPiece(ItemStack piece, boolean dropItem) {
        if (!this.piece.isEmpty() && dropItem) {
            Containers.dropItemStack(level, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5, this.piece);
        }
        this.piece = piece;
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    public void hitWithHammer(Player entity) {
        if (!entity.level().isClientSide) {
            level.playSound(null, worldPosition, SoundEvents.MACE_SMASH_GROUND, SoundSource.BLOCKS, 1, 10);
        }
    }
}

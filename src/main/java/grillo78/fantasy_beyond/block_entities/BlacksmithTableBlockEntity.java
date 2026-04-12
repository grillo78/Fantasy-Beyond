package grillo78.fantasy_beyond.block_entities;

import grillo78.fantasy_beyond.data_map.ModDataMaps;
import grillo78.fantasy_beyond.data_map.forging.HeatableMaterial;
import grillo78.fantasy_beyond.items.components.CurrentRecipe;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.items.components.TemperatureManager;
import grillo78.fantasy_beyond.recipes.ModRecipes;
import grillo78.fantasy_beyond.recipes.SmashingRecipe;
import grillo78.fantasy_beyond.recipes.inputs.SmashingItemInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class BlacksmithTableBlockEntity extends BlockEntity {
    private ItemStack piece = ItemStack.EMPTY;
    private int recipeIndex = -1;

    public BlacksmithTableBlockEntity(BlockPos pos, BlockState blockState) {
        this(ModBlockEntities.BLACKSMITH_TABLE.get(), pos, blockState);
    }

    public BlacksmithTableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        piece = ItemStack.parseOptional(registries, tag.getCompound("piece"));
        recipeIndex = tag.getInt("recipeIndex");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("piece", piece.isEmpty() ? new CompoundTag() : piece.save(registries));
        tag.putInt("recipeIndex", recipeIndex);
    }

    public int getRecipeIndex() {
        return recipeIndex;
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

    public static void tick(Level level, BlockPos pos, BlockState state, BlacksmithTableBlockEntity blockEntity) {
        if (!blockEntity.piece.isEmpty() && blockEntity.piece.has(ModDataComponents.TEMPERATURE_MANAGER)) {
            blockEntity.piece.get(ModDataComponents.TEMPERATURE_MANAGER).decreaseTemperature(blockEntity.piece);
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
        if (level.getRecipeManager().getRecipesFor(ModRecipes.SMASHING.get(), new SmashingItemInput(piece, Optional.empty()), level).size() > 0)
            recipeIndex = 0;
        else
            recipeIndex = -1;
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    public void rightPageClick() {
        if (!piece.isEmpty()) {
            if (recipeIndex < level.getRecipeManager().getRecipesFor(ModRecipes.SMASHING.get(), new SmashingItemInput(piece, Optional.empty()), level).size() - 1)
                recipeIndex++;
            else
                recipeIndex = 0;
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public void leftPageClick() {
        if (!piece.isEmpty()) {
            if (recipeIndex > 0)
                recipeIndex--;
            else
                recipeIndex = level.getRecipeManager().getRecipesFor(ModRecipes.SMASHING.get(), new SmashingItemInput(piece, Optional.empty()), level).size() - 1;
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public void setPieceRecipe(ItemStack paper, ItemStack blackDye) {
        if (!piece.isEmpty() && recipeIndex != -1) {
            paper.shrink(1);
            blackDye.shrink(1);
            this.piece.set(ModDataComponents.CURRENT_RECIPE, new CurrentRecipe(level.getRecipeManager().getRecipesFor(ModRecipes.SMASHING.get(), new SmashingItemInput(piece, Optional.empty()), level).get(recipeIndex).id().toString(), 0));
            Containers.dropItemStack(level, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5, this.piece);
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

}

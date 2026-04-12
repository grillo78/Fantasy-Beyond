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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.units.qual.C;

import java.util.Optional;

public class AnvilBlockEntity extends BlockEntity {
    private ItemStack piece = ItemStack.EMPTY;
    private int ticksSinceLastHammering = 0;

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
        if (!blockEntity.piece.isEmpty() && blockEntity.piece.has(ModDataComponents.TEMPERATURE_MANAGER)) {
            blockEntity.piece.get(ModDataComponents.TEMPERATURE_MANAGER).decreaseTemperature(blockEntity.piece);
            blockEntity.setChanged();
            if (blockEntity.ticksSinceLastHammering < 1000)
                blockEntity.ticksSinceLastHammering++;
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
//        if (!piece.has(ModDataComponents.CURRENT_RECIPE) && level.getRecipeManager().getRecipeFor(ModRecipes.SMASHING.get(), new SmashingItemInput(piece), level).isPresent()) {
//            piece.set(ModDataComponents.CURRENT_RECIPE, new CurrentRecipe(level.getRecipeManager().getRecipeFor(ModRecipes.SMASHING.get(), new SmashingItemInput(piece), level).get().id().toString(), 0));
//        }
        this.piece = piece;
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    public void hitWithHammer(Player entity) {
        if (!entity.level().isClientSide && ticksSinceLastHammering > 5) {
            ticksSinceLastHammering = 0;
            level.playSound(null, worldPosition, SoundEvents.MACE_SMASH_GROUND, SoundSource.BLOCKS, 1, 10);
            if (piece.has(ModDataComponents.CURRENT_RECIPE) && piece.getItemHolder().getData(ModDataMaps.HEATABLE_MATERIALS) != null && piece.has(ModDataComponents.TEMPERATURE_MANAGER)) {
                HeatableMaterial heatableMaterial = piece.getItemHolder().getData(ModDataMaps.HEATABLE_MATERIALS);
                TemperatureManager temperatureManager = piece.get(ModDataComponents.TEMPERATURE_MANAGER);
                if (temperatureManager.getTemperature() >= heatableMaterial.getWorkableAt()) {
                    CurrentRecipe currentRecipe = piece.get(ModDataComponents.CURRENT_RECIPE);
                    Optional<RecipeHolder<?>> recipeOptional = level.getRecipeManager().byKey(ResourceLocation.parse(currentRecipe.getRecipe()));
                    if (recipeOptional.isPresent() && recipeOptional.get().value() instanceof SmashingRecipe) {
                        SmashingRecipe smashingRecipe = (SmashingRecipe) recipeOptional.get().value();
                        currentRecipe = currentRecipe.copy();
                        currentRecipe.hit();
                        if (currentRecipe.getHits() == smashingRecipe.getHits()) {
                            piece = smashingRecipe.assemble(new SmashingItemInput(piece, Optional.of(entity.getOffhandItem())), level.registryAccess());
                            piece.set(ModDataComponents.TEMPERATURE_MANAGER, new TemperatureManager(temperatureManager.getTemperature()));
                            level.playSound(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(),SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS);
                        } else
                            piece.set(ModDataComponents.CURRENT_RECIPE, currentRecipe);
                    }
                } else {
                    piece.remove(ModDataComponents.CURRENT_RECIPE);
                }
                setChanged();
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    }
}

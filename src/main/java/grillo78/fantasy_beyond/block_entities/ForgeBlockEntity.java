package grillo78.fantasy_beyond.block_entities;

import grillo78.fantasy_beyond.data_map.ModDataMaps;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.data_map.forging.HeatableMaterial;
import grillo78.fantasy_beyond.items.components.TemperatureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ForgeBlockEntity extends BlockEntity {

    private boolean lit = false;
    private int fuelTime = 0;
    private ItemStack heatingItem = ItemStack.EMPTY;
    public static int MAX_FUEL_TIME = 100000;

    public ForgeBlockEntity(BlockPos pos, BlockState blockState) {
        this(ModBlockEntities.FORGE.get(), pos, blockState);
    }

    public ForgeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        lit = tag.getBoolean("lit");
        fuelTime = tag.getInt("fuelTime");
        heatingItem = ItemStack.parseOptional(registries, tag.getCompound("heatingItem"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("lit", lit);
        tag.putInt("fuelTime", fuelTime);
        tag.put("heatingItem", heatingItem.equals(ItemStack.EMPTY) ? new CompoundTag() : heatingItem.save(registries));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ForgeBlockEntity blockEntity) {
        if (!level.isClientSide && blockEntity.lit) {
            ItemStack item = blockEntity.heatingItem;
            if(item.getItemHolder().getData(ModDataMaps.HEATABLE_MATERIALS) != null){
                if (!item.has(ModDataComponents.TEMPERATURE_MANAGER))
                    item.set(ModDataComponents.TEMPERATURE_MANAGER, new TemperatureManager());
                TemperatureManager temperatureManager = item.get(ModDataComponents.TEMPERATURE_MANAGER);
                temperatureManager.increaseTemperature(item);
                blockEntity.fuelTime -= RandomSource.create().nextInt(1, 10);
            }
            if (blockEntity.fuelTime <= 0) {
                blockEntity.setLit(false);
                blockEntity.fuelTime = 0;
            }
            level.sendBlockUpdated(pos, state, state, 3);
            blockEntity.setChanged();
        }
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

    public void setLitRecursively(boolean lit) {
        if (!lit || canLit()) {
            setLit(lit);
            setLitRecursively(getBlockPos().north(), lit);
            setLitRecursively(getBlockPos().south(), lit);
            setLitRecursively(getBlockPos().west(), lit);
            setLitRecursively(getBlockPos().east(), lit);
        }
    }

    public void setLit(boolean lit) {
        this.lit = lit && fuelTime > 0;
        if (!this.lit)
            level.playSound(null, worldPosition, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS);
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        setChanged();
    }

    public boolean canLit() {
        return fuelTime > 0;
    }

    public void setHeatingItem(ItemStack heatingItem) {
        setHeatingItem(heatingItem, true);
    }
    public void setHeatingItem(ItemStack heatingItem, boolean dropItem) {
        if(!this.heatingItem.isEmpty() && dropItem)
            Containers.dropItemStack(level,worldPosition.getX(), worldPosition.getY()+0.5F, worldPosition.getZ(), this.heatingItem);
        this.heatingItem = heatingItem;
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    public ItemStack getHeatingItem() {
        return heatingItem;
    }

    private void setLitRecursively(BlockPos pos, boolean lit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ForgeBlockEntity && ((ForgeBlockEntity) blockEntity).lit != lit) {
            ((ForgeBlockEntity) blockEntity).setLitRecursively(lit);
        }
    }

    public boolean isLit() {
        return lit;
    }

    public void setFuelTime(int fuelTime) {
        this.fuelTime = fuelTime;
        if (level.getBlockEntity(getBlockPos().north()) instanceof ForgeBlockEntity && ((ForgeBlockEntity) level.getBlockEntity(getBlockPos().north())).lit)
            lit = true;
        if (level.getBlockEntity(getBlockPos().south()) instanceof ForgeBlockEntity && ((ForgeBlockEntity) level.getBlockEntity(getBlockPos().south())).lit)
            lit = true;
        if (level.getBlockEntity(getBlockPos().west()) instanceof ForgeBlockEntity && ((ForgeBlockEntity) level.getBlockEntity(getBlockPos().west())).lit)
            lit = true;
        if (level.getBlockEntity(getBlockPos().east()) instanceof ForgeBlockEntity && ((ForgeBlockEntity) level.getBlockEntity(getBlockPos().east())).lit)
            lit = true;
        if (lit) {
            if (level.getBlockEntity(getBlockPos().north()) instanceof ForgeBlockEntity && !((ForgeBlockEntity) level.getBlockEntity(getBlockPos().north())).lit)
                ((ForgeBlockEntity) level.getBlockEntity(getBlockPos().north())).lit = true;
            if (level.getBlockEntity(getBlockPos().south()) instanceof ForgeBlockEntity && !((ForgeBlockEntity) level.getBlockEntity(getBlockPos().south())).lit)
                ((ForgeBlockEntity) level.getBlockEntity(getBlockPos().south())).lit = true;
            if (level.getBlockEntity(getBlockPos().west()) instanceof ForgeBlockEntity && !((ForgeBlockEntity) level.getBlockEntity(getBlockPos().west())).lit)
                ((ForgeBlockEntity) level.getBlockEntity(getBlockPos().west())).lit = true;
            if (level.getBlockEntity(getBlockPos().east()) instanceof ForgeBlockEntity && !((ForgeBlockEntity) level.getBlockEntity(getBlockPos().east())).lit)
                ((ForgeBlockEntity) level.getBlockEntity(getBlockPos().east())).lit = true;
        }
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    public int getFuelTime() {
        return fuelTime;
    }
}

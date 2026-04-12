package grillo78.fantasy_beyond.magic.spell;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.network.SyncCharacterData;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.PacketDistributor;

public class HealingSpell extends Spell {

    private int tickCount = 0;
    private static int MAX_TICKS = 10000;

    @Override
    public void tick(LivingEntity owner) {
        if(!owner.level().isClientSide){
            if (tickCount < MAX_TICKS) {

                tickCount++;
            } else
                owner.getData(ModAttachments.CHARACTER_DATA).setActiveSpell(null);
            PacketDistributor.sendToAllPlayers(new SyncCharacterData(owner.getId(), owner.getData(ModAttachments.CHARACTER_DATA).serializeNBT(owner.level().registryAccess())));
        }
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compound = super.serializeNBT(provider);
        compound.putInt("tickCount",tickCount);
        return compound;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        super.deserializeNBT(provider, nbt);
        tickCount = nbt.getInt("tickCount");
    }
}

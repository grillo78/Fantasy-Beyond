package grillo78.fantasy_beyond.character;

import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.UnknownNullability;

public class CharacterData implements INBTSerializable<CompoundTag> {
    private PlayerCustomization playerCustomization = new PlayerCustomization();

    public PlayerCustomization getPlayerCustomization() {
        return playerCustomization;
    }

    public void tick(EntityTickEvent event) {
        if (event.getEntity().level().isClientSide)
            this.clientTick();
        if (playerCustomization.isFinished())
            playerCustomization.getRace().tick(event);
    }

    @OnlyIn(Dist.CLIENT)
    private void clientTick() {
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("customization", playerCustomization.serializeNBT(provider));
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        playerCustomization.deserializeNBT(provider, nbt.getCompound("customization"));
    }
}

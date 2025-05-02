package grillo78.fantasy_beyond.capabilities.spells_book;

import grillo78.fantasy_beyond.magic.spells.SpellType;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class SpellsBookProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<SpellsBookCap> SPELLS_BOOK = CapabilityManager.get(new CapabilityToken<>(){});
    private final LazyOptional<SpellsBookCap> spellsBookOptional;

    public SpellsBookProvider(ItemStack stack, List<RegistryObject<SpellType>> spellTypes) {
        this.spellsBookOptional = LazyOptional.of(() -> new SpellsBookCap(stack, spellTypes));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == SPELLS_BOOK) {
            return spellsBookOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return spellsBookOptional.orElse(null).serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        spellsBookOptional.ifPresent(magic -> magic.deserializeNBT(nbt));
    }
}

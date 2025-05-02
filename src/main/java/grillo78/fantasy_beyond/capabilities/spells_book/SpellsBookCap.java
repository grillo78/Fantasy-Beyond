package grillo78.fantasy_beyond.capabilities.spells_book;

import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import grillo78.fantasy_beyond.magic.spells.SpellType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class SpellsBookCap implements INBTSerializable<CompoundTag> {

    private ItemStack stack;
    private List<SpellType> spellTypes = new ArrayList<>();
    private int currentIndex = 0;

    public SpellsBookCap(ItemStack stack, List<RegistryObject<SpellType>> spellTypes) {
        this.stack = stack;
        spellTypes.forEach(spellTypeRegistryObject -> this.spellTypes.add(spellTypeRegistryObject.get()));
    }

    public void increaseSpellIndex(){
        if(currentIndex<spellTypes.size()-1)
            currentIndex++;
        else
            currentIndex = 0;
    }

    public void decreaseSpellIndex(){
        if(currentIndex>0)
            currentIndex--;
        else
            currentIndex = spellTypes.size()-1;
    }

    public SpellType getCurrentSpell(){
        return spellTypes.get(currentIndex);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        CompoundTag magicCirclesCompound = new CompoundTag();
        for (int i = 0; i < spellTypes.size(); i++) {
            magicCirclesCompound.putString(String.valueOf(i), spellTypes.get(i).getRegistryName().toString());
        }
        compound.put("spellTypes", magicCirclesCompound);
        compound.putInt("index", currentIndex);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        spellTypes.clear();
        CompoundTag magicCirclesCompound = nbt.getCompound("spellTypes");

        for (int i = 0; i < magicCirclesCompound.size(); i++) {
            SpellType circle = SpellType.SPELLS_REGISTRY.get().getValue(new ResourceLocation(magicCirclesCompound.getString(String.valueOf(i))));
            spellTypes.add(i, circle);
        }
        currentIndex = Mth.clamp(nbt.getInt("index"), 0, spellTypes.size()-1);
    }

    public void castSpell(Level level, Player player) {
        player.getCapability(PlayerDataProvider.DATA).ifPresent(playerMagic -> {
            if(playerMagic.getSpell() == null)
                playerMagic.setSpell(spellTypes.get(currentIndex).cast(level, player));
        });
    }
}

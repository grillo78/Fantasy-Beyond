package grillo78.fantasy_beyond.magic.spells;

import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Supplier;

public class SpellType {
    public static final DeferredRegister<SpellType> SPELLS = DeferredRegister.create(new ResourceLocation(FantasyBeyond.MOD_ID, "spell_types"), FantasyBeyond.MOD_ID);
    public static final Supplier<IForgeRegistry<SpellType>> SPELLS_REGISTRY = SPELLS.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<SpellType> FALLING_CANNON_SPELL = register("falling_cannon_spell", FallingCannonMagicSpell::new);
    public static final RegistryObject<SpellType> SHIELD_SPELL = register("shield_spell", ShieldSpell::new);
    public static final RegistryObject<SpellType> SWORD_INVOKE_SPELL = register("sword_invoke_spell", SwordsInvokeSpell::new);
    public static final RegistryObject<SpellType> FIREBALL_SPELL = register("fireball_spell", FireballSpell::new);
    public static final RegistryObject<SpellType> LIGHT_SPELL = register("light_spell", LightSpell::new);

    private static RegistryObject<SpellType> register(String name, TriFunction<Player, Level, SpellType, Spell> spellSupplier) {
        return SPELLS.register(name, ()-> new SpellType(spellSupplier));
    }

    public ResourceLocation getRegistryName() {
        return SPELLS_REGISTRY.get().getKey(this);
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }

    private TriFunction<Player, Level, SpellType, Spell> spellFunction;

    public SpellType(TriFunction<Player, Level, SpellType, Spell> spellFunction) {
        this.spellFunction = spellFunction;
    }

    public Spell cast(Level level, Player player) {
        Spell spell = spellFunction.apply(player, level, this);
        spell.cast();
        return spell;
    }

    public Spell createInstance(Player player, Level level) {
        return spellFunction.apply(player, level, this);
    }
}


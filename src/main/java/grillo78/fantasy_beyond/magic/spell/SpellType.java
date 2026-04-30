package grillo78.fantasy_beyond.magic.spell;

import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class SpellType {

    public static DeferredRegister<SpellType> SPELL_TYPES = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "spell_type"), FantasyBeyond.MOD_ID);
    public static Registry<SpellType> SPELL_TYPES_REGISTRY = SPELL_TYPES.makeRegistry(SpellTypeRegistryBuilder -> {});

    public static SpellType HEALING = register("healing", new SpellType(HealingSpell::new));

    private Supplier<Spell> spellFunction;

    public SpellType(Supplier<Spell> spellfunction) {
        this.spellFunction = spellfunction;
    }

    private static <T extends SpellType> T register(String name, T abilityType) {
        SPELL_TYPES.register(name, () -> abilityType);
        return abilityType;
    }

    public Spell createSpell() {
        Spell Spell = spellFunction.get();
        Spell.setType(this);
        return Spell;
    }
}

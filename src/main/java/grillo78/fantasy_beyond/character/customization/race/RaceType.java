package grillo78.fantasy_beyond.character.customization.race;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.dwarf.Dwarf;
import grillo78.fantasy_beyond.character.customization.race.elf.Elf;
import grillo78.fantasy_beyond.character.customization.race.human.Human;
import grillo78.fantasy_beyond.character.customization.race.merfolk.Merfolk;
import grillo78.fantasy_beyond.character.customization.race.orc.Orc;
import grillo78.fantasy_beyond.character.customization.race.tiefling.Tiefling;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class RaceType {

    public static DeferredRegister<RaceType> RACE_TYPES = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "race_type"), FantasyBeyond.MOD_ID);
    public static Registry<RaceType> RACE_TYPES_REGISTRY = RACE_TYPES.makeRegistry(raceTypeRegistryBuilder -> {});

    public static RaceType HUMAN = register("human", new RaceType(Human::new));
    public static RaceType ELF = register("elf", new RaceType(Elf::new));
    public static RaceType TIEFLING = register("tiefling", new RaceType(Tiefling::new));
    public static RaceType MERFOLKF = register("merfolk", new RaceType(Merfolk::new));
    public static RaceType ORC = register("orc", new RaceType(Orc::new));
    public static RaceType DWARF = register("dwarf", new RaceType(Dwarf::new));
//    public static RaceType PIXY = register("pixy", new RaceType(Pixy::new));
//    public static RaceType AUTOMATON = register("automaton", new RaceType(Automaton::new));

    private Function<PlayerCustomization, Race> raceFunction;

    public RaceType(Function<PlayerCustomization, Race> raceFunction) {
        this.raceFunction = raceFunction;
    }

    private static <T extends RaceType> T register(String name, T abilityType) {
        RACE_TYPES.register(name, () -> abilityType);
        return abilityType;
    }

    public Race createRace(PlayerCustomization playerCustomization) {
        Race race = raceFunction.apply(playerCustomization);
        race.setType(this);
        return race;
    }
}

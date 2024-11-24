package grillo78.fantasy_beyond.capabilities.customization.race;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.elf.Elf;
import grillo78.fantasy_beyond.capabilities.customization.race.human.Human;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Function;
import java.util.function.Supplier;

public class RaceType {

    public static DeferredRegister<RaceType> RACE_TYPES = DeferredRegister.create(new ResourceLocation(FantasyBeyond.MOD_ID, "race_type"), FantasyBeyond.MOD_ID);
    public static Supplier<IForgeRegistry<RaceType>> RACE_TYPES_REGISTRY = RACE_TYPES.makeRegistry(RegistryBuilder::new);

    public static RaceType HUMAN = register("human",new RaceType(Human::new));
    public static RaceType ELF = register("elf",new RaceType(Elf::new));

    private Function<PlayerCustomization, Race> raceFunction;

    public RaceType(Function<PlayerCustomization, Race> raceFunction) {
        this.raceFunction = raceFunction;
    }

    private static <T extends RaceType> T register(String name, T abilityType) {
        RACE_TYPES.register(name, () -> abilityType);
        return abilityType;
    }

    public Race createRace(PlayerCustomization playerCustomization) {
        return raceFunction.apply(playerCustomization);
    }
}

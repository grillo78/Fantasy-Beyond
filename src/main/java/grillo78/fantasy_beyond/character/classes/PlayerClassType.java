package grillo78.fantasy_beyond.character.classes;

import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class PlayerClassType {

    public static DeferredRegister<PlayerClassType> PLAYER_CLASS_TYPES = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "player_class_type"), FantasyBeyond.MOD_ID);
    public static Registry<PlayerClassType> PLAYER_CLASS_TYPES_REGISTRY = PLAYER_CLASS_TYPES.makeRegistry(raceTypeRegistryBuilder -> {});

    public static PlayerClassType TANK = register("tank", new PlayerClassType(Tank::new));

    private Supplier<PlayerClass> playerClassSupplier;

    public PlayerClassType(Supplier<PlayerClass> playerClassSupplier) {
        this.playerClassSupplier = playerClassSupplier;
    }

    private static <T extends PlayerClassType> T register(String name, T abilityType) {
        PLAYER_CLASS_TYPES.register(name, () -> abilityType);
        return abilityType;
    }

    public PlayerClass createPlayerClass() {
        PlayerClass race = playerClassSupplier.get();
        race.setType(this);
        return race;
    }
}

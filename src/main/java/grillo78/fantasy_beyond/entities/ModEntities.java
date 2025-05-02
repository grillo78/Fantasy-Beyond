package grillo78.fantasy_beyond.entities;


import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FantasyBeyond.MOD_ID);

    public static final RegistryObject<EntityType<Goblin>> GOBLIN = register("goblin", ()->EntityType.Builder.<Goblin>of(Goblin::new, MobCategory.MISC).sized(0.5F, 1.5F).build(FantasyBeyond.MOD_ID + ":goblin"));
    public static final RegistryObject<EntityType<Lopus>> LOPUS = register("lopus", ()->EntityType.Builder.<Lopus>of(Lopus::new, MobCategory.MISC).sized(2.5F, 7).build(FantasyBeyond.MOD_ID + ":goblin"));
    public static final RegistryObject<EntityType<Fireball>> FIREBALL = register("fireball", ()->EntityType.Builder.<Fireball>of(Fireball::new, MobCategory.MISC).sized(0.5F, 0.5F).build(FantasyBeyond.MOD_ID + ":flying_book"));
    public static final RegistryObject<EntityType<LightSource>> LIGHT_SOURCE = register("light_source", ()->EntityType.Builder.<LightSource>of(LightSource::new, MobCategory.MISC).sized(0.5F, 0.5F).build(FantasyBeyond.MOD_ID + ":flying_book"));

    private static <T extends EntityType<?>> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return ENTITIES.register(name, supplier);
    }
}
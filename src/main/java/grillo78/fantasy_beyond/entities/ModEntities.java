package grillo78.fantasy_beyond.entities;

import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, FantasyBeyond.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<DemonLord>> DEMON_LORD =
            ENTITY_TYPES.register("demon_lord", () ->
                    EntityType.Builder.of(DemonLord::new, MobCategory.MONSTER).sized(2f, 3f)
                            .build(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "demon_lord").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<Goblin>> GOBLIN =
            ENTITY_TYPES.register("goblin", () ->
                    EntityType.Builder.of(Goblin::new, MobCategory.MONSTER).sized(1f, 1.5f)
                            .build(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "goblin").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<Direwolf>> DIREWOLF =
            ENTITY_TYPES.register("direwolf", () ->
                    EntityType.Builder.of(Direwolf::new, MobCategory.MONSTER).sized(1f, 1.5f)
                            .build(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "direwolf").toString()));

}

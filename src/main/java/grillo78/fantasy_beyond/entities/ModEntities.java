package grillo78.fantasy_beyond.entities;

import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, FantasyBeyond.MOD_ID);

    public static final RegistryObject<EntityType<DemonLord>> DEMON_LORD =
            ENTITY_TYPES.register("guardian_scout", () ->
                    EntityType.Builder.of(DemonLord::new, MobCategory.MONSTER).sized(2f, 2f)
                            .build(new ResourceLocation(FantasyBeyond.MOD_ID, "demon_lord").toString()));

}

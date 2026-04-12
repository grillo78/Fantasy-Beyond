package grillo78.fantasy_beyond.recipes;

import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, FantasyBeyond.MOD_ID);

    public static final Supplier<RecipeType<SmashingRecipe>> SMASHING =
            RECIPE_TYPES.register(
                    "smashing",
                    () -> RecipeType.<SmashingRecipe>simple(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "smashing"))
            );
}

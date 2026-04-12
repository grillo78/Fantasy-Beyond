package grillo78.fantasy_beyond.recipes.serializers;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.recipes.SmashingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, FantasyBeyond.MOD_ID);

    public static final Supplier<RecipeSerializer<SmashingRecipe>> SMASHING =
            RECIPE_SERIALIZERS.register("smashing", SmashingSerializer::new);
}

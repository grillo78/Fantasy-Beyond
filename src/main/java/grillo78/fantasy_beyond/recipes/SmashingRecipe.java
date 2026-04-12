package grillo78.fantasy_beyond.recipes;

import grillo78.fantasy_beyond.items.components.CurrentRecipe;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.recipes.inputs.SmashingItemInput;
import grillo78.fantasy_beyond.recipes.serializers.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class SmashingRecipe implements Recipe<SmashingItemInput> {

    private Ingredient ingredient;
    private ItemStack result;
    private int hits;
    private Optional<Ingredient> requiredTool;

    public SmashingRecipe(Ingredient ingredient, ItemStack result, int hits, Optional<Ingredient> requiredTool) {
        this.ingredient = ingredient;
        this.result = result;
        this.hits = hits;
        this.requiredTool = requiredTool;
    }

    @Override
    public boolean matches(SmashingItemInput input, Level level) {
        boolean ingredientTest = ingredient.test(input.stack());
        boolean offHandPresent = input.offHandItem().isPresent();
        boolean requiredToolPresent = requiredTool.isPresent();
        boolean requiredToolTest = requiredTool.orElse(Ingredient.of(ItemStack.EMPTY)).test(input.offHandItem().orElse(ItemStack.EMPTY));
        return ingredientTest && (!offHandPresent || !requiredToolPresent || requiredToolTest);
    }

    @Override
    public ItemStack assemble(SmashingItemInput input, HolderLookup.Provider registries) {
        ItemStack stack = result.copy();
        return stack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SMASHING.get();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(ingredient);
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SMASHING.get();
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public ItemStack getResult() {
        return result;
    }

    public int getHits() {
        return hits;
    }

    public Optional<Ingredient> getRequiredTool() {
        return requiredTool;
    }
}

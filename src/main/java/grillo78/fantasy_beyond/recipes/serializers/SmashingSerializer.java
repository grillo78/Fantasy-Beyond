package grillo78.fantasy_beyond.recipes.serializers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import grillo78.fantasy_beyond.recipes.SmashingRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SmashingSerializer implements RecipeSerializer<SmashingRecipe> {
    public static final MapCodec<SmashingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(SmashingRecipe::getIngredient),
            ItemStack.CODEC.fieldOf("result").forGetter(SmashingRecipe::getResult),
            Codec.INT.fieldOf("hits").forGetter(SmashingRecipe::getHits),
            Ingredient.CODEC.optionalFieldOf("required_tool").forGetter(SmashingRecipe::getRequiredTool)
            ).apply(inst, SmashingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SmashingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, SmashingRecipe::getIngredient,
                    ItemStack.STREAM_CODEC, SmashingRecipe::getResult,
                    ByteBufCodecs.INT, SmashingRecipe::getHits,
                    ByteBufCodecs.optional(Ingredient.CONTENTS_STREAM_CODEC), SmashingRecipe::getRequiredTool,
                    SmashingRecipe::new
            );

    // Return our map codec.
    @Override
    public MapCodec<SmashingRecipe> codec() {
        return CODEC;
    }

    // Return our stream codec.
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SmashingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}

package grillo78.fantasy_beyond.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.checkerframework.checker.units.qual.C;

import java.util.List;
import java.util.Objects;

public class CurrentRecipe implements TooltipComponent {
    public static final CurrentRecipe EMPTY = new CurrentRecipe("", 0);
    public static final Codec<CurrentRecipe> CODEC = RecordCodecBuilder.create(
            itemContentsInstance -> itemContentsInstance.group(
                            Codec.STRING.fieldOf("recipe").forGetter(CurrentRecipe::getRecipe),
                            Codec.INT.fieldOf("hits").forGetter(CurrentRecipe::getHits)
                    )
                    .apply(itemContentsInstance, CurrentRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, CurrentRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            CurrentRecipe::getRecipe,
            ByteBufCodecs.INT,
            CurrentRecipe::getHits,
            CurrentRecipe::new);

    private String recipe;
    private int hits;

    public CurrentRecipe(String recipe, int hits) {
        this.recipe = recipe;
        this.hits = hits;
    }

    public int getHits() {
        return hits;
    }

    public String getRecipe() {
        return recipe;
    }

    public CurrentRecipe copy(){
        return new CurrentRecipe(recipe, hits);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return !(other instanceof CurrentRecipe currentRecipe) ? false : (recipe.equals(currentRecipe.recipe) && hits == currentRecipe.hits);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void hit() {
        hits++;
    }
}

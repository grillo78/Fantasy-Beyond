package grillo78.fantasy_beyond.items.components;

import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, FantasyBeyond.MOD_ID);


    public static final Supplier<DataComponentType<ItemContents>> ITEM_CONTENTS = DATA_COMPONENTS.registerComponentType(
            "item_contents", p_341857_ -> p_341857_.persistent(ItemContents.CODEC).networkSynchronized(ItemContents.STREAM_CODEC).cacheEncoding()
    );
    public static final Supplier<DataComponentType<QuiverContents>> QUIVER_CONTENTS = DATA_COMPONENTS.registerComponentType(
            "quiver_contents", p_341857_ -> p_341857_.persistent(QuiverContents.CODEC).networkSynchronized(QuiverContents.STREAM_CODEC).cacheEncoding()
    );
    public static final Supplier<DataComponentType<TemperatureManager>> TEMPERATURE_MANAGER = DATA_COMPONENTS.registerComponentType(
            "temperature_manager", p_341857_ -> p_341857_.persistent(TemperatureManager.CODEC).networkSynchronized(TemperatureManager.STREAM_CODEC).cacheEncoding()
    );
    public static final Supplier<DataComponentType<TongsContent>> TONGS_CONTENT = DATA_COMPONENTS.registerComponentType(
            "tongs_content", p_341857_ -> p_341857_.persistent(TongsContent.CODEC).networkSynchronized(TongsContent.STREAM_CODEC).cacheEncoding()
    );
    public static final Supplier<DataComponentType<CurrentRecipe>> CURRENT_RECIPE = DATA_COMPONENTS.registerComponentType(
            "current_recipe", p_341857_ -> p_341857_.persistent(CurrentRecipe.CODEC).networkSynchronized(CurrentRecipe.STREAM_CODEC).cacheEncoding()
    );
}

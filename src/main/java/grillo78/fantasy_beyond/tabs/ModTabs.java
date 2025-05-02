package grillo78.fantasy_beyond.tabs;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.blocks.ModBlocks;
import grillo78.fantasy_beyond.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FantasyBeyond.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder().title(Component.literal("Wizarding Magic")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> ModItems.STAFF.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(ModItems.STAFF.get());
        output.accept(ModItems.SPELLS_BOOK.get());
        output.accept(ModItems.HUMAN_SHIRT_1.get());
        output.accept(ModItems.HUMAN_BELT_1.get());
        output.accept(ModItems.HUMAN_PANTS_1.get());
        output.accept(ModItems.HUMAN_BOOTS_1.get());
        output.accept(ModItems.GOBLIN_SPAWN_EGG.get());
        output.accept(ModBlocks.INVOCATION_BOOK.get());
        output.accept(ModBlocks.RESEARCH_TABLE.get());
    }).build());
}

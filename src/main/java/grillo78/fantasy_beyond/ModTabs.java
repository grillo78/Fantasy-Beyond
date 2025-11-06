package grillo78.fantasy_beyond;

import grillo78.fantasy_beyond.blocks.ModBlocks;
import grillo78.fantasy_beyond.items.ModItems;
import grillo78.fantasy_beyond.items.RacistClothItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FantasyBeyond.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CLOTHES = CREATIVE_MODE_TABS.register(FantasyBeyond.MOD_ID + "_clothes", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + FantasyBeyond.MOD_ID + "_clothes")).icon(() -> ModItems.HUMAN_HOOD_2.get().getDefaultInstance()).displayItems((parameters, output) -> {
        RacistClothItem.CLOTHES.forEach(racistClothItem -> {
            output.accept(racistClothItem);
        });
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> WEAPONS = CREATIVE_MODE_TABS.register(FantasyBeyond.MOD_ID + "_weapons", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + FantasyBeyond.MOD_ID + "_weapons")).icon(() -> ModItems.KATANA_1.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(ModItems.KATANA_1.get());
        output.accept(ModItems.SWORD_1.get());
        output.accept(ModItems.SPEAR_1.get());
        output.accept(ModItems.SCYTHE_1.get());
        output.accept(ModItems.GREATSWORD_1.get());
        output.accept(ModItems.GAUNTLET_1.get());
        output.accept(ModItems.GREATHAMMER_1.get());
        output.accept(ModItems.BATTLEAXE_1.get());
        output.accept(ModItems.STAFF_1.get());
        output.accept(ModItems.GRIMOIRE_1.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCKS = CREATIVE_MODE_TABS.register(FantasyBeyond.MOD_ID + "_blocks", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + FantasyBeyond.MOD_ID + "_blocks")).icon(() -> ModBlocks.END_PILAR.get().asItem().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(ModBlocks.BASE_PILAR.get());
        output.accept(ModBlocks.PILAR.get());
        output.accept(ModBlocks.END_PILAR.get());
    }).build());

}

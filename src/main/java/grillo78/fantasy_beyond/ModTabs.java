package grillo78.fantasy_beyond;

import grillo78.fantasy_beyond.blocks.ModBlocks;
import grillo78.fantasy_beyond.items.ModItems;
import grillo78.fantasy_beyond.items.RacistClothItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FantasyBeyond.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FORGING = CREATIVE_MODE_TABS.register(FantasyBeyond.MOD_ID + "_forging", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + FantasyBeyond.MOD_ID + "_forging")).icon(() -> ModItems.FORGING_HAMMER.get().asItem().getDefaultInstance()).displayItems((parameters, output) -> {
            output.accept(ModBlocks.FORGE.get());
            output.accept(ModBlocks.ANVIL.get());
            output.accept(ModBlocks.BLACKSMITH_TABLE.get());
            output.accept(ModItems.FORGING_HAMMER.get());
            output.accept(ModItems.FORGING_TONGS.get());
            output.accept(ModItems.IRON_STICK.get());
            output.accept(ModItems.BORAX.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FORGING_PARTS = CREATIVE_MODE_TABS.register(FantasyBeyond.MOD_ID + "_forging_parts", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + FantasyBeyond.MOD_ID + "_forging_parts")).icon(() -> ModItems.IRON_AXE_HEAD.get().asItem().getDefaultInstance()).displayItems((parameters, output) -> {
            output.accept(ModItems.IRON_AXE_EYE.get());
            output.accept(ModItems.IRON_AXE_EDGE.get());
            output.accept(ModItems.UNWELD_IRON_AXE_HEAD.get());
            output.accept(ModItems.IRON_AXE_HEAD.get());
            output.accept(ModItems.IRON_SHOVEL_HEAD.get());
            output.accept(ModItems.UNFINISHED_IRON_SHOVEL_HEAD.get());
            output.accept(ModItems.IRON_PICKAXE_HEAD.get());
            output.accept(ModItems.UNFINISHED_IRON_PICKAXE_HEAD.get());
            output.accept(ModItems.KATANA_1_BLADE.get());
            output.accept(ModItems.KATANA_1_GUARD.get());
            output.accept(ModItems.KATANA_1_HANDLE.get());
    }).build());

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
        output.accept(ModItems.HALBERD_1.get());
        output.accept(ModItems.STAFF_1.get());
        output.accept(ModItems.GRIMOIRE_1.get());
        output.accept(ModItems.QUIVER.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCKS = CREATIVE_MODE_TABS.register(FantasyBeyond.MOD_ID + "_blocks", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + FantasyBeyond.MOD_ID + "_blocks")).icon(() -> ModBlocks.END_PILAR.get().asItem().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(ModBlocks.BASE_PILAR.get());
        output.accept(ModBlocks.PILAR.get());
        output.accept(ModBlocks.END_PILAR.get());
        output.accept(ModBlocks.MOSSY_DEEPSLATE_BRICKS.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register(FantasyBeyond.MOD_ID + "_items", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + FantasyBeyond.MOD_ID + "_items")).icon(() -> ModItems.GOLD_COIN.get().asItem().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(ModItems.GOLD_COIN.get());
        output.accept(ModItems.SILVER_COIN.get());
        output.accept(ModItems.BRONZE_COIN.get());
        output.accept(ModItems.COIN_BUNDLE.get());
    }).build());

}

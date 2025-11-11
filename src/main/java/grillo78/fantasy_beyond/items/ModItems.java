package grillo78.fantasy_beyond.items;

import com.google.common.collect.Lists;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.race.RaceType;
import grillo78.fantasy_beyond.items.components.ItemContents;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.items.components.QuiverContents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, FantasyBeyond.MOD_ID);

//    public static final DeferredHolder<Item,Item> STAFF = register("staff", ()-> new Staff(new Item.Properties().stacksTo(1)));
//    public static final DeferredHolder<Item,Item> SPELLS_BOOK = register("spells_book", ()-> new SpellsBook(new Item.Properties().stacksTo(1), Arrays.asList(SpellType.FALLING_CANNON_SPELL,
////            SpellType.SHIELD_SPELL, SpellType.SWORD_INVOKE_SPELL,
//            SpellType.FIREBALL_SPELL, SpellType.LIGHT_SPELL)));

    public static final DeferredHolder<Item,Item> KATANA_1 = register("katana_1", ()-> new CustomWeapon(CustomWeapon.WeaponType.KATANA, Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 2, -2F))));
    public static final DeferredHolder<Item,Item> SWORD_1 = register("sword_1", ()-> new CustomWeapon(CustomWeapon.WeaponType.SWORD, Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F))));
    public static final DeferredHolder<Item,Item> SPEAR_1 = register("spear_1", ()-> new CustomWeapon(CustomWeapon.WeaponType.SPEAR, Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 2, -1F))));
    public static final DeferredHolder<Item,Item> SCYTHE_1 = register("scythe_1", ()-> new CustomWeapon(CustomWeapon.WeaponType.SCYTHE, Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F))));
    public static final DeferredHolder<Item,Item> GREATSWORD_1 = register("greatsword_1", ()-> new CustomWeapon(CustomWeapon.WeaponType.GREAT_SWORD, Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F))));
    public static final DeferredHolder<Item,Item> GAUNTLET_1 = register("gauntlet_1", ()-> new CustomWeapon(CustomWeapon.WeaponType.GAUNTLET, Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 1.5F, -1F))));
    public static final DeferredHolder<Item,Item> GREATHAMMER_1 = register("greathammer_1", ()-> new CustomWeapon(CustomWeapon.WeaponType.GREAT_HAMMER, Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 6, -3F))));
    public static final DeferredHolder<Item,Item> BATTLEAXE_1 = register("battleaxe_1", ()-> new CustomWeapon(CustomWeapon.WeaponType.BATTLE_AXE, Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 6, -3F))));
    public static final DeferredHolder<Item,Item> HALBERD_1 = register("halberd_1", ()-> new CustomWeapon(CustomWeapon.WeaponType.HALBERD, Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 6, -3F))));

    public static final DeferredHolder<Item,Item> STAFF_1 = register("staff_1", ()-> new Item(new Item.Properties().stacksTo(1).attributes(SwordItem.createAttributes(Tiers.WOOD, 2, -2.4F))));
    public static final DeferredHolder<Item,Item> GRIMOIRE_1 = register("grimoire_1", ()-> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredHolder<Item,Item> GOLD_COIN = register("gold_coin", ()-> new CoinItem(new Item.Properties()));
    public static final DeferredHolder<Item,Item> SILVER_COIN = register("silver_coin", ()-> new CoinItem(new Item.Properties()));
    public static final DeferredHolder<Item,Item> BRONZE_COIN = register("bronze_coin", ()-> new CoinItem(new Item.Properties()));
    public static final DeferredHolder<Item,Item> COIN_BUNDLE = register("coin_bundle", ()-> new CoinBundleItem(new Item.Properties().stacksTo(1).component(ModDataComponents.ITEM_CONTENTS.get(), ItemContents.EMPTY)));
    public static final DeferredHolder<Item,Item> QUIVER = register("quiver", ()-> new QuiverItem(new Item.Properties().stacksTo(1).component(ModDataComponents.QUIVER_CONTENTS.get(), QuiverContents.EMPTY)));

    public static final DeferredHolder<Item,Item> DWARF_SHIRT_1 = register("dwarf_shirt_1", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));
    public static final DeferredHolder<Item,Item> DWARF_PANTS_1 = register("dwarf_pants_1", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));
    public static final DeferredHolder<Item,Item> DWARF_BOOTS_1 = register("dwarf_boots_1", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));

    public static final DeferredHolder<Item,Item> DWARF_HOOD_2 = register("dwarf_hood_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF), true));
    public static final DeferredHolder<Item,Item> DWARF_SHIRT_2 = register("dwarf_shirt_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));
    public static final DeferredHolder<Item,Item> DWARF_BELT_2 = register("dwarf_belt_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));
    public static final DeferredHolder<Item,Item> DWARF_PANTS_2 = register("dwarf_pants_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));
    public static final DeferredHolder<Item,Item> DWARF_BOOTS_2 = register("dwarf_boots_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));

    public static final DeferredHolder<Item,Item> DWARF_SHIRT_3 = register("dwarf_shirt_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));
    public static final DeferredHolder<Item,Item> DWARF_BELT_3 = register("dwarf_belt_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));
    public static final DeferredHolder<Item,Item> DWARF_PANTS_3 = register("dwarf_pants_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));
    public static final DeferredHolder<Item,Item> DWARF_BOOTS_3 = register("dwarf_boots_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));

    public static final DeferredHolder<Item,Item> DWARF_COAT_4 = register("dwarf_coat_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));
    public static final DeferredHolder<Item,Item> DWARF_SHIRT_4 = register("dwarf_shirt_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));
    public static final DeferredHolder<Item,Item> DWARF_PANTS_4 = register("dwarf_pants_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));
    public static final DeferredHolder<Item,Item> DWARF_BOOTS_4 = register("dwarf_boots_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.DWARF)));

    public static final DeferredHolder<Item,Item>HUMAN_SHIRT_1 = register("human_shirt_1", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));
    public static final DeferredHolder<Item,Item>HUMAN_PANTS_1 = register("human_pants_1", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));
    public static final DeferredHolder<Item,Item>HUMAN_BOOTS_1 = register("human_boots_1", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));

    public static final DeferredHolder<Item,Item>HUMAN_HOOD_2 = register("human_hood_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN), true));
    public static final DeferredHolder<Item,Item>HUMAN_SHIRT_2 = register("human_shirt_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));
    public static final DeferredHolder<Item,Item>HUMAN_BELT_2 = register("human_belt_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));
    public static final DeferredHolder<Item,Item>HUMAN_PANTS_2 = register("human_pants_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));
    public static final DeferredHolder<Item,Item>HUMAN_BOOTS_2 = register("human_boots_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));

    public static final DeferredHolder<Item,Item>HUMAN_SHIRT_3 = register("human_shirt_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));
    public static final DeferredHolder<Item,Item>HUMAN_BELT_3 = register("human_belt_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));
    public static final DeferredHolder<Item,Item>HUMAN_PANTS_3 = register("human_pants_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));
    public static final DeferredHolder<Item,Item>HUMAN_BOOTS_3 = register("human_boots_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));

    public static final DeferredHolder<Item,Item>HUMAN_COAT_4 = register("human_coat_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN),true));
    public static final DeferredHolder<Item,Item>HUMAN_SHIRT_4 = register("human_shirt_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));
    public static final DeferredHolder<Item,Item>HUMAN_PANTS_4 = register("human_pants_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));
    public static final DeferredHolder<Item,Item>HUMAN_BOOTS_4 = register("human_boots_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.HUMAN)));

    public static final DeferredHolder<Item,Item> MULTI_SHIRT_1 = register("multi_shirt_1", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));
    public static final DeferredHolder<Item,Item> MULTI_PANTS_1 = register("multi_pants_1", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));
    public static final DeferredHolder<Item,Item> MULTI_BOOTS_1 = register("multi_boots_1", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));

    public static final DeferredHolder<Item,Item> MULTI_HOOD_2 = register("multi_hood_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF), true));
    public static final DeferredHolder<Item,Item> MULTI_SHIRT_2 = register("multi_shirt_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));
    public static final DeferredHolder<Item,Item> MULTI_BELT_2 = register("multi_belt_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));
    public static final DeferredHolder<Item,Item> MULTI_PANTS_2 = register("multi_pants_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));
    public static final DeferredHolder<Item,Item> MULTI_BOOTS_2 = register("multi_boots_2", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));

    public static final DeferredHolder<Item,Item> MULTI_SHIRT_3 = register("multi_shirt_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));
    public static final DeferredHolder<Item,Item> MULTI_BELT_3 = register("multi_belt_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));
    public static final DeferredHolder<Item,Item> MULTI_PANTS_3 = register("multi_pants_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));
    public static final DeferredHolder<Item,Item> MULTI_BOOTS_3 = register("multi_boots_3", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));

    public static final DeferredHolder<Item,Item> MULTI_COAT_4 = register("multi_coat_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF), false, true, true));
    public static final DeferredHolder<Item,Item> MULTI_SHIRT_4 = register("multi_shirt_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));
    public static final DeferredHolder<Item,Item> MULTI_PANTS_4 = register("multi_pants_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));
    public static final DeferredHolder<Item,Item> MULTI_BOOTS_4 = register("multi_boots_4", ()-> new RacistClothItem(new Item.Properties(), Lists.newArrayList(RaceType.ELF, RaceType.TIEFLING, RaceType.MERFOLKF)));

//    public static final DeferredHolder<Item, Item> GOBLIN_SPAWN_EGG = register("goblin_spawn_egg", ()-> new ForgeSpawnEggItem(()-> ModEntities.GOBLIN.get(), Color.GREEN.hashCode(), Color.GRAY.hashCode(),new Item.Properties()));

    public static <T extends Item, V extends Supplier<T>> DeferredHolder<Item, T> register(String name, V itemSupplier) {
        return ITEMS.register(name, itemSupplier);
    }
}

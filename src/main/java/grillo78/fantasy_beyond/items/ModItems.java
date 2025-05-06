package grillo78.fantasy_beyond.items;

import grillo78.clothes_mod.common.items.ClothesSlot;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.customization.race.RaceType;
import grillo78.fantasy_beyond.entities.ModEntities;
import grillo78.fantasy_beyond.magic.spells.SpellType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.Arrays;
import java.util.function.Supplier;

public class ModItems {
    // Create a Deferred Register to hold Items which will all be registered under the "wizarding_magic" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FantasyBeyond.MOD_ID);

    public static final RegistryObject<Item> STAFF = register("staff", ()-> new Staff(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SPELLS_BOOK = register("spells_book", ()-> new SpellsBook(new Item.Properties().stacksTo(1), Arrays.asList(SpellType.FALLING_CANNON_SPELL,
//            SpellType.SHIELD_SPELL, SpellType.SWORD_INVOKE_SPELL,
            SpellType.FIREBALL_SPELL, SpellType.LIGHT_SPELL)));
    public static final RegistryObject<Item> HUMAN_SHIRT_1 = register("human_shirt_1", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.SHIRT, RaceType.HUMAN));
    public static final RegistryObject<Item> HUMAN_BELT_1 = register("human_belt_1", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.BELT, RaceType.HUMAN));
    public static final RegistryObject<Item> HUMAN_PANTS_1 = register("human_pants_1", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.PANTS, RaceType.HUMAN));
    public static final RegistryObject<Item> HUMAN_BOOTS_1 = register("human_boots_1", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.SHOES, RaceType.HUMAN));
    public static final RegistryObject<Item> HUMAN_SHIRT_2 = register("human_shirt_2", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.SHIRT, RaceType.HUMAN));
    public static final RegistryObject<Item> HUMAN_PANTS_2 = register("human_pants_2", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.PANTS, RaceType.HUMAN));
    public static final RegistryObject<Item> HUMAN_BOOTS_2 = register("human_boots_2", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.SHOES, RaceType.HUMAN));
    public static final RegistryObject<Item> HUMAN_HOOD_1 = register("human_hood_1", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.HEAD, RaceType.HUMAN));
    public static final RegistryObject<Item> HUMAN_SHIRT_3 = register("human_shirt_3", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.SHIRT, RaceType.HUMAN));
    public static final RegistryObject<Item> HUMAN_BELT_2 = register("human_belt_2", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.BELT, RaceType.HUMAN));
    public static final RegistryObject<Item> HUMAN_PANTS_3 = register("human_pants_3", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.PANTS, RaceType.HUMAN));
    public static final RegistryObject<Item> HUMAN_BOOTS_3 = register("human_boots_3", ()-> new RacistClothItem(new Item.Properties(), ClothesSlot.SHOES, RaceType.HUMAN));
    public static final RegistryObject<Item> GOBLIN_SPAWN_EGG = register("goblin_spawn_egg", ()-> new ForgeSpawnEggItem(()-> ModEntities.GOBLIN.get(), Color.GREEN.hashCode(), Color.GRAY.hashCode(),new Item.Properties()));

    public static <T extends Item, V extends Supplier<T>> RegistryObject<T> register(String name, V itemSupplier) {
        return ITEMS.register(name, itemSupplier);
    }
}

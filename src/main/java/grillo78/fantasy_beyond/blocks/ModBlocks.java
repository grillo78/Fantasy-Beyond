package grillo78.fantasy_beyond.blocks;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "wizarding_magic" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FantasyBeyond.MOD_ID);

    public static RegistryObject<Block> INVOCATION_BOOK = register("invocation_book", () -> new Book(BlockBehaviour.Properties.of()
            .strength(-1.0F, 3600000.0F)
            .noLootTable()
            .isValidSpawn(ModBlocks::never)), new Item.Properties());
    public static RegistryObject<Block> RESEARCH_TABLE = register("research_table", () -> new ResearchTable(BlockBehaviour.Properties.of()
            .strength(-1.0F, 3600000.0F)
            .noLootTable()
            .isValidSpawn(ModBlocks::never)), new Item.Properties());

    private static Boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    public static <T extends Block, V extends Supplier<T>> RegistryObject<T> register(String name, V blockSupplier, Item.Properties properties) {
        RegistryObject<T> registryObject = BLOCKS.register(name, blockSupplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(registryObject.get(), properties));
        return registryObject;
    }

    public static <T extends Block, V extends Supplier<T>> RegistryObject<T> register(String name, V blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
    }
}

package grillo78.fantasy_beyond.blocks;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "wizarding_magic" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, FantasyBeyond.MOD_ID);

    public static final DeferredHolder<Block, Block> FORGE = register("forge", () -> new ForgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), new Item.Properties());
    public static final DeferredHolder<Block, Block> BLACKSMITH_TABLE = register("blacksmith_table", () -> new BlacksmithTable(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.0F, 3.0F)), new Item.Properties());
    public static final DeferredHolder<Block, Block> ANVIL = register("anvil", () -> new AnvilBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 1200.0F)
                    .sound(SoundType.ANVIL)
                    .pushReaction(PushReaction.BLOCK)), new Item.Properties());
    public static final DeferredHolder<Block, Block> IRON_BUCKET = register("iron_bucket", () -> new BucketBlock(BlockBehaviour.Properties.of()));
    public static DeferredHolder<Block, Block> BASE_PILAR = register("base_pilar", () -> new BasePilar(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)
                    .sound(SoundType.ANVIL)
                    .pushReaction(PushReaction.BLOCK)), new Item.Properties());
    public static DeferredHolder<Block, Block> PILAR = register("pilar", () -> new Pilar(BlockBehaviour.Properties.of()), new Item.Properties());
    public static DeferredHolder<Block, Block> END_PILAR = register("end_pilar", () -> new EndPilar(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)
                    .sound(SoundType.ANVIL)
                    .pushReaction(PushReaction.BLOCK)), new Item.Properties());


    public static final DeferredHolder<Block, Block> MOSSY_DEEPSLATE_BRICKS = register(
            "mossy_deepslate_bricks", ()-> new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.COBBLED_DEEPSLATE).sound(SoundType.DEEPSLATE_BRICKS)),
            new Item.Properties()
    );

    private static Boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    public static <T extends Block, V extends Supplier<T>> DeferredHolder<Block, T> register(String name, V blockSupplier, Item.Properties properties) {
        DeferredHolder<Block, T> registryObject = BLOCKS.register(name, blockSupplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(registryObject.get(), properties));
        return registryObject;
    }

    public static <T extends Block, V extends Supplier<T>> DeferredHolder<Block, T> register(String name, V blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
    }
}

package grillo78.fantasy_beyond.block_entities;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.blocks.AnvilBlock;
import grillo78.fantasy_beyond.blocks.ForgeBlock;
import grillo78.fantasy_beyond.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    // Create a Deferred Register to hold Blocks which will all be registered under the "wizarding_magic" namespace
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, FantasyBeyond.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ForgeBlockEntity>> FORGE = BLOCK_ENTITIES.register("forge",
            () -> BlockEntityType.Builder.of(ForgeBlockEntity::new, ModBlocks.FORGE.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AnvilBlockEntity>> ANVIL = BLOCK_ENTITIES.register("anvil",
            () -> BlockEntityType.Builder.of(AnvilBlockEntity::new, ModBlocks.ANVIL.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BucketBlockEntity>> BUCKET = BLOCK_ENTITIES.register("bucket",
            () -> BlockEntityType.Builder.of(BucketBlockEntity::new, ModBlocks.IRON_BUCKET.get()).build(null));

}

package grillo78.fantasy_beyond.blockentities;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FantasyBeyond.MOD_ID);

    public static final RegistryObject<BlockEntityType> RESEARCH_TABLE = register("research_table", ()->BlockEntityType.Builder.of(ResearchTableBlockEntity::new, ModBlocks.RESEARCH_TABLE.get()).build(null));

    public static <T extends BlockEntityType<?>, V extends Supplier<T>> RegistryObject<T> register(String name, V blockSupplier) {
        return BLOCK_ENTITY_TYPES.register(name, blockSupplier);
    }
}

package grillo78.fantasy_beyond.structures;

import com.mojang.serialization.Codec;
import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, FantasyBeyond.MOD_ID);

    public static final RegistryObject<StructureType<BiggerStructure>> BIGGER_STRUCTURE = STRUCTURE_TYPES.register("bigger_structure",()-> explicitStructureTypeTyping(BiggerStructure.CODEC));
    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(Codec<T> structureCodec) {
        return () -> structureCodec;
    }
}

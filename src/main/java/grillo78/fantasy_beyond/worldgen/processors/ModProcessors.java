package grillo78.fantasy_beyond.worldgen.processors;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.recipes.SmashingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, FantasyBeyond.MOD_ID);

    public static final Supplier<StructureProcessorType<LeveledSpawners>> LEVELED_SPAWNERS =PROCESSORS.register("leveled_spawners", ()->()->LeveledSpawners.CODEC);
}

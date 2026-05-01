package grillo78.fantasy_beyond.worldgen.processors;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LeveledSpawners extends StructureProcessor {

    public static final MapCodec<LeveledSpawners> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.list(Codec.mapPair(
                    Codec.INT.fieldOf("level"),
                    Codec.list(ResourceLocation.CODEC).fieldOf("ids")
            ).codec()).optionalFieldOf("entities", new ArrayList<>()).forGetter((leveledSpawners) -> leveledSpawners.entities)
    ).apply(instance, LeveledSpawners::new));

    private final List<Pair<Integer, List<ResourceLocation>>> entities;
    private Random random = new Random();

    public LeveledSpawners(List<Pair<Integer, List<ResourceLocation>>> entities) {
        this.entities = entities;
    }

    @Override
    public @Nullable StructureTemplate.StructureBlockInfo process(
            @NotNull LevelReader level,
            @NotNull BlockPos offset,
            @NotNull BlockPos pos,
            @NotNull StructureTemplate.StructureBlockInfo blockInfo,
            @NotNull StructureTemplate.StructureBlockInfo relativeBlockInfo,
            @NotNull StructurePlaceSettings settings,
            StructureTemplate template) {

        if (blockInfo.state().is(Blocks.SPAWNER) && blockInfo.nbt() != null && relativeBlockInfo.nbt() != null) {
            System.out.println((pos.getY() - relativeBlockInfo.pos().getY()) / 10);
            ListTag tags = getEntitiesAsTags((pos.getY() - relativeBlockInfo.pos().getY()) / 10);
            relativeBlockInfo.nbt().put("SpawnPotentials", tags);
            CompoundTag randomSpawnData = tags.getCompound(random.nextInt(tags.size())).getCompound("data");
            relativeBlockInfo.nbt().put("SpawnData", randomSpawnData);
        }

        return super.process(level, offset, pos, blockInfo, relativeBlockInfo, settings, template);
    }

    private @NotNull ListTag getEntitiesAsTags(int spawnLevel) {
        ListTag tags = new ListTag();

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getFirst() <= spawnLevel)
                for (int j = 0; j < entities.get(i).getSecond().size(); j++) {
                    CompoundTag spawnChance = new CompoundTag();
                    spawnChance.putInt("weight", 1);
                    CompoundTag spawnData = new CompoundTag();
                    CompoundTag entity = new CompoundTag();

                    entity.putString("id", entities.get(i).getSecond().get(j).toString());

                    spawnData.put("entity", entity);

                    spawnChance.put("data", spawnData);

                    tags.add(spawnChance);
                }
        }
        return tags;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.LEVELED_SPAWNERS.get();
    }
}

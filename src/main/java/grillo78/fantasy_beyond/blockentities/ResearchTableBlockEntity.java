package grillo78.fantasy_beyond.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ResearchTableBlockEntity extends BlockEntity {

    public ResearchTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.RESEARCH_TABLE.get(), pPos, pBlockState);
    }
}

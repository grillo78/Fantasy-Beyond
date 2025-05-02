package grillo78.fantasy_beyond.blocks;

import grillo78.fantasy_beyond.blockentities.ResearchTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ResearchTable extends HorizontalDirectionalBlock implements EntityBlock {

    public static final BooleanProperty LEFT = BooleanProperty.create("left");

    private static final VoxelShape NORTH_SHAPE = makeNorthShape();
    private static final VoxelShape SOUTH_SHAPE = makeSouthShape();
    private static final VoxelShape WEST_SHAPE = makeWestShape();
    private static final VoxelShape EAST_SHAPE = makeEastShape();

    public ResearchTable(Properties pProperties) {
        super(pProperties);
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LEFT, false));

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape shape = Shapes.block();
        switch (pState.getValue(FACING)) {
            case NORTH:
                shape = NORTH_SHAPE;
                if (pState.getValue(LEFT))
                    shape = shape.move(1, 0, 0);
                break;
            case EAST:
                shape = EAST_SHAPE;
                if (pState.getValue(LEFT))
                    shape = shape.move(0, 0, 1);
                break;
            case SOUTH:
                shape = SOUTH_SHAPE;
                if (pState.getValue(LEFT))
                    if (pState.getValue(LEFT))
                        shape = shape.move(-1, 0, 0);
                break;
            case WEST:
                shape = WEST_SHAPE;
                if (pState.getValue(LEFT))
                    shape = shape.move(0, 0, -1);
                break;
        }

        return shape;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING).add(LEFT);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        boolean canPlace = true;

        switch (pState.getValue(FACING)){
            case NORTH:
                canPlace = pLevel.getBlockState(pPos.west()).canBeReplaced();
                break;
            case EAST:
                canPlace = pLevel.getBlockState(pPos.north()).canBeReplaced();
                break;
            case SOUTH:
                canPlace = pLevel.getBlockState(pPos.east()).canBeReplaced();
                break;
            case WEST:
                canPlace = pLevel.getBlockState(pPos.south()).canBeReplaced();
                break;
            default:
                canPlace = false;
                break;
        }

        return canPlace;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);

        switch (pState.getValue(FACING)) {
            case NORTH:
                pLevel.setBlock(pState.getValue(LEFT) ? pPos.east() : pPos.west(), pNewState, 3);
                break;
            case EAST:
                pLevel.setBlock(pState.getValue(LEFT) ? pPos.south() : pPos.north(), pNewState, 3);
                break;
            case SOUTH:
                pLevel.setBlock(pState.getValue(LEFT) ? pPos.west() : pPos.east(), pNewState, 3);
                break;
            case WEST:
                pLevel.setBlock(pState.getValue(LEFT) ? pPos.north() : pPos.south(), pNewState, 3);
                break;
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        switch (pState.getValue(FACING)) {
            case NORTH:
                pLevel.setBlock(pPos.west(), pState.setValue(LEFT, true), 3);
                break;
            case EAST:
                pLevel.setBlock(pPos.north(), pState.setValue(LEFT, true), 3);
                break;
            case SOUTH:
                pLevel.setBlock(pPos.east(), pState.setValue(LEFT, true), 3);
                break;
            case WEST:
                pLevel.setBlock(pPos.south(), pState.setValue(LEFT, true), 3);
                break;
        }
    }

    public static VoxelShape makeWestShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.03125, 0, 0.03125, 0.21875, 0.1875, 0.21875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.1875, 0.0625, 0.1875, 0.75, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.78125, 0, 0.03125, 0.96875, 0.1875, 0.21875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.1875, 0.0625, 0.9375, 0.75, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.1875, 1.8125, 0.1875, 0.75, 1.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.03125, 0, 1.78125, 0.21875, 0.1875, 1.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.78125, 0, 1.78125, 0.96875, 0.1875, 1.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.1875, 1.8125, 0.9375, 0.75, 1.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 2), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 1, 0, 1, 1.25, 2), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeSouthShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.03125, 0, 0.78125, 0.21875, 0.1875, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.1875, 0.8125, 0.1875, 0.75, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.03125, 0, 0.03125, 0.21875, 0.1875, 0.21875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.1875, 0.0625, 0.1875, 0.75, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1.8125, 0.1875, 0.8125, 1.9375, 0.75, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1.78125, 0, 0.78125, 1.96875, 0.1875, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1.78125, 0, 0.03125, 1.96875, 0.1875, 0.21875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1.8125, 0.1875, 0.0625, 1.9375, 0.75, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 2, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 1, 0, 2, 1.25, 0.1875), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeEastShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.78125, 0, 0.78125, 0.96875, 0.1875, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.1875, 0.8125, 0.9375, 0.75, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.03125, 0, 0.78125, 0.21875, 0.1875, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.1875, 0.8125, 0.1875, 0.75, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.1875, -0.9375, 0.9375, 0.75, -0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.78125, 0, -0.96875, 0.96875, 0.1875, -0.78125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.03125, 0, -0.96875, 0.21875, 0.1875, -0.78125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.1875, -0.9375, 0.1875, 0.75, -0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.75, -1, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 1, -1, 0.1875, 1.25, 1), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeNorthShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.78125, 0, 0.03125, 0.96875, 0.1875, 0.21875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.1875, 0.0625, 0.9375, 0.75, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.78125, 0, 0.78125, 0.96875, 0.1875, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.1875, 0.8125, 0.9375, 0.75, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.9375, 0.1875, 0.0625, -0.8125, 0.75, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.96875, 0, 0.03125, -0.78125, 0.1875, 0.21875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.96875, 0, 0.78125, -0.78125, 0.1875, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.9375, 0.1875, 0.8125, -0.8125, 0.75, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.75, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 1, 0.8125, 1, 1.25, 1), BooleanOp.OR);

        return shape;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(LEFT) ? null : new ResearchTableBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return EntityBlock.super.getTicker(pLevel, pState, pBlockEntityType);
    }
}

package grillo78.fantasy_beyond.blocks;

import grillo78.fantasy_beyond.block_entities.AnvilBlockEntity;
import grillo78.fantasy_beyond.block_entities.ForgeBlockEntity;
import grillo78.fantasy_beyond.block_entities.ModBlockEntities;
import grillo78.fantasy_beyond.data_map.ModDataMaps;
import grillo78.fantasy_beyond.data_map.forging.HeatableMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AnvilBlock extends Block implements EntityBlock {
    private static VoxelShape X_SHAPE = Shapes.join(Shapes.box(6F / 16, 0, 6F / 16, 10F / 16, 1, 10F / 16).move(1, 0, 0),
            Shapes.box(6F / 16, 0, 6F / 16, 10F / 16, 1, 10F / 16).move(-1, 0, 0), BooleanOp.OR);
    ;
    private static VoxelShape Z_SHAPE = Shapes.join(Shapes.box(6F / 16, 0, 6F / 16, 10F / 16, 1, 10F / 16).move(0, 0, 1),
            Shapes.box(6F / 16, 0, 6F / 16, 10F / 16, 1, 10F / 16).move(0, 0, -1), BooleanOp.OR);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public AnvilBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
//        return facing.getAxis() == Direction.Axis.X ? X_SHAPE : Z_SHAPE;
        return Shapes.join(Shapes.join(
                        Shapes.box(6F / 16, 0, 6F / 16, 10F / 16, 1, 10F / 16),
                        Shapes.box(0, 10 / 16F, 2F / 16, 1, 1, 14F / 16), BooleanOp.OR),
                Shapes.box(0, 10 / 16F, 2F / 16, 1, 1, 14F / 16), BooleanOp.OR);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(level.getBlockEntity(pos) instanceof AnvilBlockEntity){
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), ((AnvilBlockEntity) level.getBlockEntity(pos)).getPiece());
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemInteractionResult result = super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        HeatableMaterial heatableMaterial = stack.getItemHolder().getData(ModDataMaps.HEATABLE_MATERIALS);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if ((heatableMaterial != null || stack.isEmpty()) && blockEntity instanceof AnvilBlockEntity) {
            if (!level.isClientSide) {
                AnvilBlockEntity anvilBlockEntity = (AnvilBlockEntity) blockEntity;
                anvilBlockEntity.setPiece(stack.copyWithCount(1));
                stack.shrink(1);
            }
            result = ItemInteractionResult.SUCCESS;
        }

        return result;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getClockWise());
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AnvilBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.ANVIL.get(), AnvilBlockEntity::tick);
    }

    private static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker
    ) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }
}

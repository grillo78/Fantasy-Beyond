package grillo78.fantasy_beyond.blocks;

import grillo78.fantasy_beyond.block_entities.AnvilBlockEntity;
import grillo78.fantasy_beyond.block_entities.BlacksmithTableBlockEntity;
import grillo78.fantasy_beyond.block_entities.ModBlockEntities;
import grillo78.fantasy_beyond.data_map.ModDataMaps;
import grillo78.fantasy_beyond.data_map.forging.HeatableMaterial;
import grillo78.fantasy_beyond.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlacksmithTable extends Block implements EntityBlock {
    private static VoxelShape BASE_Z_SHAPE =
            Shapes.join(Shapes.box(0, 12F / 16, 0, 1, 1, 2),
                    Shapes.join(Shapes.join(Shapes.box(1F / 16, 0, 1F / 16, 3F / 16, 1, 3F / 16),
                                    Shapes.join(Shapes.box(1F / 16, 0, 1F / 16, 3F / 16, 1, 3F / 16).move(0, 0, 28 / 16F),
                                            Shapes.join(Shapes.box(1F / 16, 0, 1F / 16, 3F / 16, 1, 3F / 16).move(12 / 16F, 0, 0),
                                                    Shapes.box(1F / 16, 0, 1F / 16, 3F / 16, 1, 3F / 16).move(12 / 16F, 0, 28 / 16F), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR),
                            Shapes.join(Shapes.box(0.5 / 16, 0, 0.5 / 16, 3.5F / 16, 3F / 16, 3.5F / 16),
                                    Shapes.join(Shapes.box(0.5 / 16, 0, 0.5 / 16, 3.5F / 16, 3F / 16, 3.5F / 16).move(0, 0, 28 / 16F),
                                            Shapes.join(Shapes.box(0.5 / 16, 0, 0.5 / 16, 3.5F / 16, 3F / 16, 3.5F / 16).move(12 / 16F, 0, 0),
                                                    Shapes.box(0.5 / 16, 0, 0.5 / 16, 3.5F / 16, 3F / 16, 3.5F / 16).move(12 / 16F, 0, 28 / 16F), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR);
    private static VoxelShape EAST_SHAPE = Shapes.join(Shapes.box(13F / 16, 12F / 16, 0, 1, 20F / 16, 2), BASE_Z_SHAPE, BooleanOp.OR);
    private static VoxelShape WEST_SHAPE = Shapes.join(Shapes.box(0, 1, 0, 3F / 16, 20F / 16, 2), BASE_Z_SHAPE, BooleanOp.OR);
    private static VoxelShape BASE_X_SHAPE =
            Shapes.join(Shapes.box(0, 12F / 16, 0, 2, 1, 1),
                    Shapes.join(Shapes.join(Shapes.box(1F / 16, 0, 1F / 16, 3F / 16, 1, 3F / 16),
                                    Shapes.join(Shapes.box(1F / 16, 0, 1F / 16, 3F / 16, 1, 3F / 16).move(28 / 16F, 0, 0),
                                            Shapes.join(Shapes.box(1F / 16, 0, 1F / 16, 3F / 16, 1, 3F / 16).move(0, 0, 12 / 16F),
                                                    Shapes.box(1F / 16, 0, 1F / 16, 3F / 16, 1, 3F / 16).move(28 / 16F, 0, 12 / 16F), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR),
                            Shapes.join(Shapes.box(0.5 / 16, 0, 0.5 / 16, 3.5F / 16, 3F / 16, 3.5F / 16),
                                    Shapes.join(Shapes.box(0.5 / 16, 0, 0.5 / 16, 3.5F / 16, 3F / 16, 3.5F / 16).move(28 / 16F, 0, 0),
                                            Shapes.join(Shapes.box(0.5 / 16, 0, 0.5 / 16, 3.5F / 16, 3F / 16, 3.5F / 16).move(0, 0, 12 / 16F),
                                                    Shapes.box(0.5 / 16, 0, 0.5 / 16, 3.5F / 16, 3F / 16, 3.5F / 16).move(28 / 16F, 0, 12 / 16F), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR);
    private static VoxelShape NORTH_SHAPE = Shapes.join(Shapes.box(0, 12F / 16, 0, 2, 20F / 16, 3F / 16), BASE_X_SHAPE, BooleanOp.OR);
    private static VoxelShape SOUTH_SHAPE = Shapes.join(Shapes.box(0, 12F / 16, 13F / 16, 2, 20F / 16, 1), BASE_X_SHAPE, BooleanOp.OR);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final EnumProperty<EnumPartType> PART = EnumProperty.create(
            "part", BlacksmithTable.EnumPartType.class);

    public BlacksmithTable(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART, EnumPartType.LEFT));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        switch (direction) {
            default:
            case NORTH:
                return NORTH_SHAPE.move(state.getValue(PART) == EnumPartType.RIGHT ? -1 : 0, 0, 0);
            case SOUTH:
                return SOUTH_SHAPE.move(state.getValue(PART) == EnumPartType.LEFT ? -1 : 0, 0, 0);
            case EAST:
                return EAST_SHAPE.move(0, 0, state.getValue(PART) == EnumPartType.RIGHT ? -1 : 0);
            case WEST:
                return WEST_SHAPE.move(0, 0, state.getValue(PART) == EnumPartType.LEFT ? -1 : 0);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        switch (state.getValue(FACING)) {
            default:
            case NORTH:
                level.setBlock(pos.east(), state.setValue(PART, EnumPartType.RIGHT), 3);
                break;
            case SOUTH:
                level.setBlock(pos.west(), state.setValue(PART, EnumPartType.RIGHT), 3);
                break;
            case EAST:
                level.setBlock(pos.south(), state.setValue(PART, EnumPartType.RIGHT), 3);
                break;
            case WEST:
                level.setBlock(pos.north(), state.setValue(PART, EnumPartType.RIGHT), 3);
                break;
        }
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        EnumPartType part = state.getValue(PART);
        switch (state.getValue(FACING)) {
            default:
            case NORTH:
                if (part == EnumPartType.RIGHT)
                    level.destroyBlock(pos.west(), true);
                else
                    level.destroyBlock(pos.east(), true);
                break;
            case SOUTH:
                if (part == EnumPartType.RIGHT)
                    level.destroyBlock(pos.east(), true);
                else
                    level.destroyBlock(pos.west(), true);
                break;
            case EAST:
                if (part == EnumPartType.RIGHT)
                    level.destroyBlock(pos.north(), true);
                else
                    level.destroyBlock(pos.south(), true);
                break;
            case WEST:
                if (part == EnumPartType.RIGHT)
                    level.destroyBlock(pos.south(), true);
                else
                    level.destroyBlock(pos.north(), true);
                break;
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (level.getBlockEntity(pos) instanceof BlacksmithTableBlockEntity) {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), ((BlacksmithTableBlockEntity) level.getBlockEntity(pos)).getPiece());
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockEntity blockEntity = getBlockEntity(state, pos, level);
        InteractionResult result = super.useWithoutItem(state, level, pos, player, hitResult);

        if (blockEntity instanceof BlacksmithTableBlockEntity) {
            if (!level.isClientSide) {
                BlacksmithTableBlockEntity blacksmithTableBlockEntity = (BlacksmithTableBlockEntity) blockEntity;
                boolean leftPage = false;
                boolean rightPage = false;
                Vec3 hitOffset = hitResult.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
                if (state.getValue(PART) == EnumPartType.RIGHT) {
                    switch (state.getValue(FACING)) {
                        case NORTH:
                            rightPage = hitOffset.x > 8.75 / 16F && hitOffset.x < 14.75 / 16F && hitOffset.z > 6.75 / 16F && hitOffset.z < 14.75 / 16F;
                            leftPage = hitOffset.x > 2.75 / 16F && hitOffset.x < 8.75 / 16F && hitOffset.z > 6.75 / 16F && hitOffset.z < 14.75 / 16F;
                            break;
                        case SOUTH:
                            rightPage = hitOffset.x > 1.25 / 16F && hitOffset.x < 7.25 / 16F && hitOffset.z > 1.75 / 16 && hitOffset.z < 9.25 / 16F;
                            leftPage = hitOffset.x > 7.25 / 16F && hitOffset.x < 13.25 / 16F && hitOffset.z > 1.75 / 16 && hitOffset.z < 9.25 / 16F;
                            break;
                        case EAST:
                            leftPage = hitOffset.z > 1.25 / 16F && hitOffset.z < 7.25 / 16F && hitOffset.x > 1.75 / 16 && hitOffset.x < 9.25 / 16F;
                            rightPage = hitOffset.z > 7.25 / 16F && hitOffset.z < 13.25 / 16F && hitOffset.x > 1.75 / 16 && hitOffset.x < 9.25 / 16F;
                            break;
                        case WEST:
                            leftPage = hitOffset.z > 8.75 / 16F && hitOffset.z < 14.75 / 16F && hitOffset.x > 6.75 / 16F && hitOffset.x < 14.75 / 16F;
                            rightPage = hitOffset.z > 2.75 / 16F && hitOffset.z < 8.75 / 16F && hitOffset.x > 6.75 / 16F && hitOffset.x < 14.75 / 16F;
                            break;
                    }
                }
                if (!leftPage && !rightPage) {
                    blacksmithTableBlockEntity.setPiece(ItemStack.EMPTY);
                } else {
                    if (rightPage)
                        blacksmithTableBlockEntity.rightPageClick();
                    else
                        blacksmithTableBlockEntity.leftPageClick();
                }
            }
            result = InteractionResult.SUCCESS;
        }
        return result;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemInteractionResult result = super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        BlockEntity blockEntity = getBlockEntity(state, pos, level);

        if (blockEntity instanceof BlacksmithTableBlockEntity && !stack.isEmpty()) {
            if (!level.isClientSide) {
                BlacksmithTableBlockEntity blacksmithTableBlockEntity = (BlacksmithTableBlockEntity) blockEntity;
                if (stack.is(Items.BLACK_DYE) && hand == InteractionHand.MAIN_HAND && player.getItemInHand(InteractionHand.OFF_HAND).is(Items.PAPER)) {
                    blacksmithTableBlockEntity.setPieceRecipe(player.getItemInHand(InteractionHand.OFF_HAND), stack);
                } else {
                    blacksmithTableBlockEntity.setPiece(stack.copyWithCount(1));
                    stack.shrink(1);
                }
            }
            result = ItemInteractionResult.SUCCESS;
        }

        return result;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        boolean canPlace = true;

        switch (pState.getValue(FACING)) {
            case NORTH:
                canPlace = pLevel.getBlockState(pPos.east()).canBeReplaced();
                break;
            case EAST:
                canPlace = pLevel.getBlockState(pPos.south()).canBeReplaced();
                break;
            case SOUTH:
                canPlace = pLevel.getBlockState(pPos.west()).canBeReplaced();
                break;
            case WEST:
                canPlace = pLevel.getBlockState(pPos.north()).canBeReplaced();
                break;
            default:
                canPlace = false;
                break;
        }

        return canPlace;
    }

    private BlockEntity getBlockEntity(BlockState state, BlockPos pos, Level level) {
        BlockEntity blockEntity = null;

        if (state.getValue(PART) == EnumPartType.LEFT) {
            blockEntity = level.getBlockEntity(pos);
        } else {
            switch (state.getValue(FACING)) {
                case NORTH:
                    blockEntity = level.getBlockEntity(pos.west());
                    break;
                case SOUTH:
                    blockEntity = level.getBlockEntity(pos.east());
                    break;
                case EAST:
                    blockEntity = level.getBlockEntity(pos.north());
                    break;
                case WEST:
                    blockEntity = level.getBlockEntity(pos.south());
                    break;
            }
        }

        return blockEntity;
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(PART);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART) == EnumPartType.LEFT ? new BlacksmithTableBlockEntity(pos, state) : null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.BLACKSMITH_TABLE.get(), BlacksmithTableBlockEntity::tick);
    }

    private static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker
    ) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    public enum EnumPartType implements StringRepresentable {
        LEFT("left"), RIGHT("right");

        private String name;

        EnumPartType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}

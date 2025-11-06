package grillo78.fantasy_beyond.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class EndPilar extends Block {
    private static VoxelShape X_SHAPE = Shapes.join(Shapes.join(Shapes.join(Shapes.box(6F / 16, 0, 6F / 16, 10F / 16, 1, 10F / 16).move(1, 0, 0),
                            Shapes.box(6F / 16, 0, 6F / 16, 10F / 16, 1, 10F / 16).move(-1, 0, 0), BooleanOp.OR),
                    Shapes.box(6F / 16, 0, 6F / 16, 10F / 16, 1, 10F / 16), BooleanOp.OR),
            Shapes.box(-1, 11F / 16, 5F / 16, 2, 1, 11F / 16), BooleanOp.OR);;
    private static VoxelShape Z_SHAPE =
            Z_SHAPE = Shapes.join(Shapes.join(Shapes.join(Shapes.box(6F / 16, 0, 6F / 16, 10F / 16, 1, 10F / 16).move(0, 0, 1),
                                    Shapes.box(6F / 16, 0, 6F / 16, 10F / 16, 1, 10F / 16).move(0, 0, -1), BooleanOp.OR),
                            Shapes.box(6F / 16, 0, 6F / 16, 10F / 16, 1, 10F / 16), BooleanOp.OR),
                    Shapes.box(5F/16, 11F / 16, -1, 11F / 16, 1, 2), BooleanOp.OR);
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final EnumProperty<EnumPartType> PART = EnumProperty.create(
            "part", EndPilar.EnumPartType.class);

    public EndPilar(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X).setValue(PART, EnumPartType.CENTER));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction.Axis axis = state.getValue(AXIS);
        switch (state.getValue(PART)) {
            case RIGHT:
                return axis == Direction.Axis.X ? X_SHAPE.move(1, 0, 0) : Z_SHAPE.move(0,0,1);
            case CENTER:
                return axis == Direction.Axis.X ? X_SHAPE : Z_SHAPE;
            case LEFT:
            default:
                return axis == Direction.Axis.X ? X_SHAPE.move(-1, 0, 0) : Z_SHAPE.move(0,0,-1);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(AXIS, context.getHorizontalDirection().getClockWise().getAxis());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        switch (state.getValue(AXIS)) {
            case X:
                level.setBlock(pos.west(), defaultBlockState().setValue(AXIS, Direction.Axis.X).setValue(PART, EnumPartType.RIGHT), 2);
                level.setBlock(pos.east(), defaultBlockState().setValue(AXIS, Direction.Axis.X).setValue(PART, EnumPartType.LEFT), 2);
                break;
            case Z:
                level.setBlock(pos.north(), defaultBlockState().setValue(AXIS, Direction.Axis.Z).setValue(PART, EnumPartType.RIGHT), 2);
                level.setBlock(pos.south(), defaultBlockState().setValue(AXIS, Direction.Axis.Z).setValue(PART, EnumPartType.LEFT), 2);
                break;
        }
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        Direction.Axis axis = state.getValue(AXIS);
        switch (state.getValue(PART)) {
            case RIGHT:
                if (axis == Direction.Axis.X) {
                    level.destroyBlock(pos.east(), true);
                    level.destroyBlock(pos.east(2), true);
                } else {
                    level.destroyBlock(pos.south(), true);
                    level.destroyBlock(pos.south(2), true);
                }
                break;
            case CENTER:
                if (axis == Direction.Axis.X) {
                    level.destroyBlock(pos.west(), true);
                    level.destroyBlock(pos.east(), true);
                } else {
                    level.destroyBlock(pos.north(), true);
                    level.destroyBlock(pos.south(), true);
                }
                break;
            case LEFT:
            default:
                if (axis == Direction.Axis.X) {
                    level.destroyBlock(pos.west(), true);
                    level.destroyBlock(pos.west(2), true);
                } else {
                    level.destroyBlock(pos.north(), true);
                    level.destroyBlock(pos.north(2), true);
                }
                break;
        }
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        switch (rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch (state.getValue(AXIS)) {
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X);
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS).add(PART);
    }

    public enum EnumPartType implements StringRepresentable {
        LEFT("left"), CENTER("center"), RIGHT("right");

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

package grillo78.fantasy_beyond.blocks;

import grillo78.fantasy_beyond.block_entities.ForgeBlockEntity;
import grillo78.fantasy_beyond.block_entities.ModBlockEntities;
import grillo78.fantasy_beyond.data_map.ModDataMaps;
import grillo78.fantasy_beyond.data_map.forging.HeatableMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ForgeBlock extends Block implements EntityBlock {

    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty NORTH_WEST = BooleanProperty.create("north_west");
    public static final BooleanProperty NORTH_EAST = BooleanProperty.create("north_east");
    public static final BooleanProperty SOUTH_WEST = BooleanProperty.create("south_west");
    public static final BooleanProperty SOUTH_EAST = BooleanProperty.create("south_east");


    public ForgeBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH).add(SOUTH).add(WEST).add(EAST).add(NORTH_WEST).add(NORTH_EAST).add(SOUTH_WEST).add(SOUTH_EAST);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.create(0, 0, 0, 1, 0.5, 1);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        return getState(blockgetter, blockpos, super.getStateForPlacement(context));
    }

    private boolean connectsTo(BlockState blockstate) {
        return blockstate.is(this);
    }

    private BlockState getState(BlockGetter blockgetter, BlockPos blockpos, BlockState startState) {
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();

        BlockPos blockpos5 = blockpos.north().west();
        BlockPos blockpos6 = blockpos.north().east();
        BlockPos blockpos7 = blockpos.south().west();
        BlockPos blockpos8 = blockpos.south().east();

        BlockState blockstate = blockgetter.getBlockState(blockpos1);
        BlockState blockstate1 = blockgetter.getBlockState(blockpos2);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos3);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos4);

        BlockState blockstate4 = blockgetter.getBlockState(blockpos5);
        BlockState blockstate5 = blockgetter.getBlockState(blockpos6);
        BlockState blockstate6 = blockgetter.getBlockState(blockpos7);
        BlockState blockstate7 = blockgetter.getBlockState(blockpos8);
        return startState
                .setValue(NORTH, this.connectsTo(blockstate))
                .setValue(EAST, this.connectsTo(blockstate1))
                .setValue(SOUTH, this.connectsTo(blockstate2))
                .setValue(WEST, this.connectsTo(blockstate3))
                .setValue(NORTH_WEST, connectsTo(blockstate) && connectsTo(blockstate3) && connectsTo(blockstate4))
                .setValue(NORTH_EAST, connectsTo(blockstate) && connectsTo(blockstate1) && connectsTo(blockstate5))
                .setValue(SOUTH_WEST, connectsTo(blockstate2) && connectsTo(blockstate3) && connectsTo(blockstate6))
                .setValue(SOUTH_EAST, connectsTo(blockstate2) && connectsTo(blockstate1) && connectsTo(blockstate7));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        level.setBlock(pos, getState(level, pos, state), 3);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeBlockEntity(pos, state);
    }

    private static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker
    ) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemInteractionResult result = super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ForgeBlockEntity) {
            if (stack.is(Items.FLINT_AND_STEEL) && !((ForgeBlockEntity) blockEntity).isLit() && ((ForgeBlockEntity) blockEntity).canLit()) {

                if (!level.isClientSide) {
                    stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                    ((ForgeBlockEntity) blockEntity).setLitRecursively(true);
                }
                result = ItemInteractionResult.SUCCESS;
            }
            if (stack.is(ItemTags.SHOVELS) && ((ForgeBlockEntity) blockEntity).isLit()) {
                if (!level.isClientSide)
                    ((ForgeBlockEntity) blockEntity).setLitRecursively(false);
                result = ItemInteractionResult.SUCCESS;
            }
            if ((stack.is(Items.COAL) || stack.is(Items.CHARCOAL)) && ((ForgeBlockEntity) blockEntity).getFuelTime() + ForgeBlockEntity.MAX_FUEL_TIME / 4 <= ForgeBlockEntity.MAX_FUEL_TIME) {
                if (!level.isClientSide) {
                    ((ForgeBlockEntity) blockEntity).setFuelTime(((ForgeBlockEntity) blockEntity).getFuelTime() + ForgeBlockEntity.MAX_FUEL_TIME / 4);
                    stack.shrink(1);
                }
                result = ItemInteractionResult.SUCCESS;
            }

            HeatableMaterial heatableMaterial = stack.getItemHolder().getData(ModDataMaps.HEATABLE_MATERIALS);
            if (heatableMaterial != null || stack.isEmpty()) {
                if (!level.isClientSide) {
                    ((ForgeBlockEntity) blockEntity).setHeatingItem(stack.copyWithCount(1));
                    stack.shrink(1);
                }
                result = ItemInteractionResult.SUCCESS;
            }
        }

        return result;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ForgeBlockEntity && ((ForgeBlockEntity) blockEntity).isLit()) {
            level.playLocalSound(
                    (double) pos.getX() + 0.5,
                    (double) pos.getY() + 0.5,
                    (double) pos.getZ() + 0.5,
                    SoundEvents.FIRE_AMBIENT,
                    SoundSource.BLOCKS,
                    1.0F + random.nextFloat(),
                    random.nextFloat() * 0.7F + 0.3F,
                    false
            );
            if (random.nextInt(2) == 0)
                for (int i = 0; i < 3; i++) {
                    double d0 = (double) pos.getX() + random.nextDouble();
                    double d1 = (double) pos.getY() + random.nextDouble() * 0.5 + 0.5;
                    double d2 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0, 0.0, 0.0);
                }
            if (random.nextInt(5) == 0)
                for (int i = 0; i < random.nextInt(3); i++) {
                    double d0 = (double) pos.getX() + random.nextDouble();
                    double d1 = (double) pos.getY() + random.nextDouble() * 0.25 + 0.25;
                    double d2 = (double) pos.getZ() + random.nextDouble();
                    double speed = 0.25F;
                    double d3 = (random.nextDouble() * 2 - 1) * speed;
                    double d4 = (random.nextDouble() / 2 + 0.1) * speed;
                    double d5 = (random.nextDouble() * 2 - 1) * speed;
                    level.addParticle(ParticleTypes.SMALL_FLAME, d0, d1, d2, d3, d4, d5);
                }
        }
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.FORGE.get(), ForgeBlockEntity::tick);
    }
}

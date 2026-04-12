package grillo78.fantasy_beyond.mixin.entity;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.blocks.ModBlocks;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.data_map.ModDataMaps;
import grillo78.fantasy_beyond.data_map.forging.HeatableMaterial;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.items.components.TemperatureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.LayeredCauldronBlock.LEVEL;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    @Shadow
    public abstract ItemStack getItem();

    @Shadow
    public abstract void setItem(ItemStack stack);

    @Shadow
    private int age;

    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        if (!level().getBlockState(blockPosition()).is(ModBlocks.FORGE.get())) {
            if (getItem().has(ModDataComponents.TEMPERATURE_MANAGER)) {
                ItemStack item = getItem().copy();
                TemperatureManager temperatureManager = item.get(ModDataComponents.TEMPERATURE_MANAGER);
                if (temperatureManager.getTemperature() > TemperatureManager.AMBIENT_TEMP) {
                    temperatureManager.decreaseTemperature(item);
                    if (temperatureManager.getTemperature() > 100) {
                        if (isInWater())
                            temperatureManager.quench(level(), blockPosition(), item);
                        BlockState state = level().getBlockState(blockPosition());
                        if ((state.getBlock() == Blocks.WATER_CAULDRON || state.getBlock() == Blocks.POWDER_SNOW_CAULDRON) && ((LayeredCauldronBlock) state.getBlock()).isEntityInsideContent(state, blockPosition(), this)) {
                            if (state.getBlock() == Blocks.POWDER_SNOW_CAULDRON)
                                state = Blocks.WATER_CAULDRON.defaultBlockState().setValue(LEVEL, state.getValue(LEVEL));
                            LayeredCauldronBlock.lowerFillLevel(state, level(), blockPosition());
                            temperatureManager.quench(level(), blockPosition(), item);
                        }
                    }
                }
                setItem(item);
            }
        }
    }
}

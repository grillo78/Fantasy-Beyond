package grillo78.fantasy_beyond.mixin.item;

import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.items.components.TemperatureManager;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder {


    @Inject(method = "inventoryTick", at = @At("HEAD"))
    public void onInventoryTick(Level level, Entity entity, int inventorySlot, boolean isCurrentItem, CallbackInfo ci) {
        if (!level.isClientSide && has(ModDataComponents.TEMPERATURE_MANAGER)) {
            TemperatureManager temperatureManager = this.get(ModDataComponents.TEMPERATURE_MANAGER);
            temperatureManager.decreaseTemperature((ItemStack) (Object) this);
            if (temperatureManager.getTemperature() > 50) {
                entity.setRemainingFireTicks(20);
            }
        }
    }
}

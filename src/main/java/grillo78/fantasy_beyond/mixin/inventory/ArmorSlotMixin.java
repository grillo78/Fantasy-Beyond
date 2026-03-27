package grillo78.fantasy_beyond.mixin.inventory;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.customization.race.RaceType;
import grillo78.fantasy_beyond.items.RacistArmor;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.ArmorSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorSlot.class)
public class ArmorSlotMixin extends Slot {
    @Shadow
    @Final
    private LivingEntity owner;

    public ArmorSlotMixin(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
    public void mayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (owner.hasData(ModAttachments.CHARACTER_DATA.get())) {
            boolean shouldCancel = false;
            CharacterData data = owner.getData(ModAttachments.CHARACTER_DATA.get());
            if (stack.getItem() instanceof RacistArmor) {
                shouldCancel = !((RacistArmor) stack.getItem()).getRaces().contains(data.getPlayerCustomization().getRace().getType());
            } else {
                shouldCancel = data.getPlayerCustomization().getRace().getType() != RaceType.HUMAN;
            }
            if (shouldCancel)
                callbackInfoReturnable.setReturnValue(false);
        }
    }
}

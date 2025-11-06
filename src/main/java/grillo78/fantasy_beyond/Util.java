package grillo78.fantasy_beyond;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class Util {

    public static void setAttribute(LivingEntity entity, ResourceLocation id, Holder<Attribute> attribute, double amount, AttributeModifier.Operation operation) {
        AttributeInstance instance = entity.getAttribute(attribute);

        if (instance == null || entity.level().isClientSide) {
            return;
        }

        AttributeModifier modifier = instance.getModifier(id);

        if (amount == 0 || modifier != null && (modifier.amount() != amount || modifier.operation() != operation)) {
            instance.removeModifier(id);
            if(amount == 0)
                return;
        }

        modifier = instance.getModifier(id);

        if (modifier == null) {
            modifier = new AttributeModifier(id, amount, operation);
            instance.addTransientModifier(modifier);
        }
    }
}

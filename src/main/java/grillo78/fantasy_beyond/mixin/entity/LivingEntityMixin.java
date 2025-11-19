package grillo78.fantasy_beyond.mixin.entity;

import com.mojang.datafixers.util.Pair;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow @Nullable protected Stack<DamageContainer> damageContainers;

    @Shadow public abstract float getHealth();

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setHealth(F)V"))
    private void onActuallyHurt(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
        if (damageSource.getEntity() != null && damageSource.getEntity() instanceof Player player) {
            List<Pair<String, Float>> damageManager = new ArrayList<>();
            damageManager.addAll(getData(ModAttachments.DAMAGE_MANAGER));
            Pair<String, Float> pair = null;
            for (int i = 0; i < damageManager.size() && pair == null; i++) {
                Pair<String, Float> damage = damageManager.get(i);
                if (damage.getFirst().equals(player.getStringUUID()))
                    pair = damage;
            }
            if (pair != null)
                damageManager.remove(pair);
            pair = new Pair<>(player.getStringUUID(), Math.min(damageContainers.peek().getNewDamage(), getHealth()));
            damageManager.add(pair);
            setData(ModAttachments.DAMAGE_MANAGER, damageManager);
        }
    }
}

package grillo78.fantasy_beyond.character.classes.abilities;

import grillo78.fantasy_beyond.character.classes.PlayerClass;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;

import java.util.List;

public class SelfEffectAbility extends Ability {

    private int maxDuration;
    private int amplifier;
    private Holder<MobEffect> effect;

    public SelfEffectAbility(String name, List<Ability> parents, Vec2 mapPosition, PlayerClass playerClass, int requiredPoints, int maxDuration, Holder<MobEffect> effect, int amplifier) {
        super(name, parents, mapPosition, playerClass, requiredPoints);
        this.maxDuration = maxDuration;
        this.amplifier = amplifier;
        this.effect = effect;
        this.tick = maxDuration;
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (!entity.level().isClientSide && tick < maxDuration) {
            if (tick == 0) {
                entity.addEffect(new MobEffectInstance(effect, maxDuration, amplifier));
            }
            tick++;
            if (tick == maxDuration)
                active = false;
        }
    }

    @Override
    public boolean canActivate(Entity entity) {
        return !active && tick == maxDuration;
    }
}

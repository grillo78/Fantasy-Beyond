package grillo78.fantasy_beyond.character.classes.abilities;

import grillo78.fantasy_beyond.character.classes.PlayerClass;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;

import java.util.List;

public class EffectAbility extends Ability {

    private int maxDuration;
    private int amplifier;
    private Holder<MobEffect> effect;
    private boolean includeSelf;

    public EffectAbility(String name, List<Ability> parents, Vec2 mapPosition, PlayerClass playerClass, int requiredPoints, int maxDuration, Holder<MobEffect> effect, int amplifier) {
        this(name, parents, mapPosition, playerClass, requiredPoints, maxDuration, effect, amplifier, true);
        tick = maxDuration;
    }

    public EffectAbility(String name, List<Ability> parents, Vec2 mapPosition, PlayerClass playerClass, int requiredPoints, int maxDuration, Holder<MobEffect> effect, int amplifier, boolean includeSelf) {
        super(name, parents, mapPosition, playerClass, requiredPoints);
        this.maxDuration = maxDuration;
        this.amplifier = amplifier;
        this.effect = effect;
        this.includeSelf = includeSelf;
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (!entity.level().isClientSide && tick < maxDuration) {
            if (tick == 0) {
                List<Entity> entities = entity.level().getEntities(null, new AABB(entity.position().add(-1, -1, -1), entity.position().add(1, 1, 1)).inflate(5));
                for (int i = 0; i < entities.size(); i++) {
                    if (entities.get(i) instanceof LivingEntity livingEntity) {
                        if ((includeSelf && livingEntity.getClass() == entity.getClass()) || (!includeSelf && livingEntity != entity))
                            livingEntity.addEffect(new MobEffectInstance(effect, maxDuration, amplifier));
                    }
                }
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

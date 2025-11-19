package grillo78.fantasy_beyond.character.classes.abilities.tank;

import grillo78.fantasy_beyond.character.classes.PlayerClass;
import grillo78.fantasy_beyond.character.classes.abilities.Ability;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public class BattleRoarAbility extends Ability {

    private static final int DURATION = 2000;
    private int coolDownTick = 0;

    public BattleRoarAbility(String name, List<Ability> parents, Vec2 mapPosition, PlayerClass playerClass, int requiredPoints) {
        super(name, parents, mapPosition, playerClass, requiredPoints);
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (!entity.level().isClientSide && tick < DURATION) {
            if (tick == 0) {
                List<?> entities = entity.level().getNearbyEntities(entity.getClass(), TargetingConditions.forNonCombat(), null, new AABB(entity.position().add(-1, -1, -1), entity.position().add(1, 1, 1)).inflate(5));
                for (int i = 0; i < entities.size(); i++) {
                    ((LivingEntity) entities.get(i)).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, DURATION, 2));
                }
            }
            tick++;
            if (tick == DURATION)
                active = false;
        }
    }

    @Override
    public boolean canActivate(Entity entity) {
        return tick == DURATION;
    }
}

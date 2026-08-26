package grillo78.fantasy_beyond.entities.goals;

import grillo78.fantasy_beyond.entities.AnimatedAttackingMonster;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.pathfinder.Path;

public class CustomMeleeAttackGoal extends MeleeAttackGoal {

    private AnimatedAttackingMonster animatedAttackingMonster;
    private Path path;

    public CustomMeleeAttackGoal(AnimatedAttackingMonster animatedAttackingMonster) {
        super(animatedAttackingMonster, 1, false);
        this.animatedAttackingMonster = animatedAttackingMonster;
    }

    @Override
    public boolean canUse() {
        return !animatedAttackingMonster.isAttacking() && super.canUse();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if (this.canPerformAttack(target)) {
            animatedAttackingMonster.startAttack();
        }
    }
}

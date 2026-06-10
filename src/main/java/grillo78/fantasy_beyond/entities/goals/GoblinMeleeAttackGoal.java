package grillo78.fantasy_beyond.entities.goals;

import grillo78.fantasy_beyond.entities.Goblin;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.pathfinder.Path;

public class GoblinMeleeAttackGoal extends MeleeAttackGoal {

    private Goblin goblin;
    private Path path;

    public GoblinMeleeAttackGoal(Goblin goblin) {
        super(goblin, 1, false);
        this.goblin = goblin;
    }

    @Override
    public boolean canUse() {
        return !goblin.isAttacking() && super.canUse();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if (this.canPerformAttack(target)) {
            goblin.startAttack();
        }
        super.checkAndPerformAttack(target);
    }
}

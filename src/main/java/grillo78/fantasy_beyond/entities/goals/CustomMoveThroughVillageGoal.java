package grillo78.fantasy_beyond.entities.goals;

import grillo78.fantasy_beyond.entities.AnimatedAttackingMonster;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;

import java.util.function.BooleanSupplier;

public class CustomMoveThroughVillageGoal extends MoveThroughVillageGoal {

    private AnimatedAttackingMonster animatedAttackingMonster;

    public CustomMoveThroughVillageGoal(AnimatedAttackingMonster mob, double speedModifier, boolean onlyAtNight, int distanceToPoi, BooleanSupplier canDealWithDoors) {
        super(mob, speedModifier, onlyAtNight, distanceToPoi, canDealWithDoors);
        this.animatedAttackingMonster = mob;
    }

    @Override
    public boolean canUse() {
        return !animatedAttackingMonster.isAttacking() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !animatedAttackingMonster.isAttacking() && super.canContinueToUse();
    }
}

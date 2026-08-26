package grillo78.fantasy_beyond.entities;

import grillo78.fantasy_beyond.entities.goals.CustomMeleeAttackGoal;
import grillo78.fantasy_beyond.entities.goals.CustomMoveThroughVillageGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Direwolf extends AnimatedAttackingMonster implements GeoEntity {

    public static final RawAnimation BREATHING = RawAnimation.begin().then("breathing", Animation.LoopType.LOOP);
    public static final RawAnimation MOVING_RIGHT_EAR = RawAnimation.begin().then("moving_right_ear", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation MOVING_LEFT_EAR = RawAnimation.begin().then("moving_left_ear", Animation.LoopType.PLAY_ONCE);
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    protected Direwolf(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        triggerAnim("breathing", "breathing");
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public void tick() {
        super.tick();
        if(!level().isClientSide){
            if (random.nextInt(100) < 1) {
                if (random.nextBoolean())
                    triggerAnim("left_ear", "moving_left_ear");
                else
                    triggerAnim("right_ear", "moving_right_ear");
            }
        }
    }

    @Override
    public int triggerAnimationAndGetTimer() {

        return super.triggerAnimationAndGetTimer();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "breathing", 0, state -> PlayState.STOP)
                .triggerableAnim("breathing", BREATHING));
        controllers.add(new AnimationController<>(this, "left_ear", 0, state -> PlayState.STOP)
                .triggerableAnim("moving_left_ear", MOVING_LEFT_EAR));
        controllers.add(new AnimationController<>(this, "right_ear", 0, state -> PlayState.STOP)
                .triggerableAnim("moving_right_ear", MOVING_RIGHT_EAR));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}

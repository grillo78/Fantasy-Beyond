package grillo78.fantasy_beyond.client.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class GrimoireArmPoseTransformer implements IArmPoseTransformer {
    @Override
    public void applyTransform(HumanoidModel<?> model, LivingEntity entity, HumanoidArm arm) {
        if (arm == HumanoidArm.LEFT) {
            model.leftArm.xRot = (float) Math.toRadians(-80);
        }
        else {
            model.rightArm.xRot = (float) Math.toRadians(-80);
        }
    }
}

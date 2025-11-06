package grillo78.fantasy_beyond.client.entity.race;

import grillo78.fantasy_beyond.client.RenderUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public abstract class CustomizationModel<T extends Entity> extends EntityModel<T> {
    public abstract void setupModel(HumanoidModel bipedModel);

    public void setupModel(HumanoidModel bipedModel, LivingEntity player, float partialTick){
        setupModel(bipedModel);
    }
    public abstract ModelPart getRoot();

    public void setModelProperties(LivingEntity pLivingEntity) {
        if(RenderUtils.renderingFirstPersonModel)
            getHeadPart().visible = false;
    }

    public abstract ModelPart getHeadPart();

    public abstract ModelPart getArmPart(HumanoidArm arm);

    public void copyFrom(ModelPart newBone, ModelPart oldBone, boolean copyPos) {
        newBone.resetPose();
        newBone.xRot = oldBone.xRot;
        newBone.yRot = oldBone.yRot;
        newBone.zRot = oldBone.zRot;
        if(copyPos){
            float x = oldBone.x;
            float y = oldBone.y;
            float z = oldBone.z;
            oldBone.resetPose();
            x = x - oldBone.x;
            y = y - oldBone.y;
            z = z - oldBone.z;
            newBone.x += x;
            newBone.y += y;
            newBone.z += z;
            oldBone.x += x;
            oldBone.y += y;
            oldBone.z += z;
        }
        oldBone.xRot = newBone.xRot;
        oldBone.yRot = newBone.yRot;
        oldBone.zRot = newBone.zRot;
    }

    public void copyFrom(CustomizationModel model){}
}

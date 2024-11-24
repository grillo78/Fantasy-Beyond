package grillo78.fantasy_beyond.capabilities.customization.race.human;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

public class Human extends Race {
    public Human(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        this.getCharacteristics().add(new HumanCharacteristic(playerCustomization, "body"));
        this.getCharacteristics().add(new HumanColoreableCharacteristic(playerCustomization, "hair"));
    }

    @Override
    public void render(PlayerModel<Player> model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, Player player) {
        poseStack.pushPose();
        poseStack.translate(0,0,player.getPose() == Pose.SWIMMING && getPlayerCustomization().isYoung()? -0.2 : 0);
        super.render(model, poseStack, pBuffer, pPackedLight, player);
        poseStack.popPose();
    }

    @Override
    public EntityDimensions getNewSize(Pose pose, EntityDimensions newSize) {
        EntityDimensions size = newSize;
        if(getPlayerCustomization().isYoung()){
            switch (pose){
                default:
                    size = newSize.scale(0.75F);
                    break;
                case CROUCHING:
                    size = newSize.scale(0.66F);
                    break;
            }
        }
        return size;
    }

    @Override
    public float getNewEyeHeight(Pose pose, float oldEyeHeight) {
        float eyeHeight = oldEyeHeight;
        if(getPlayerCustomization().isYoung()){
            switch (pose){
                default:
                    eyeHeight *= 0.55F;
                    break;
                case CROUCHING:
                    eyeHeight *= 0.45F;
                    break;
            }
        }
        return eyeHeight;
    }
}

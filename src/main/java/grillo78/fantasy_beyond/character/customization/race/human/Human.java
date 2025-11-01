package grillo78.fantasy_beyond.character.customization.race.human;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Race;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.human.FemaleHumanModel;
import grillo78.fantasy_beyond.client.entity.race.human.MaleHumanModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLLoader;

public class Human extends Race {

    public Human(PlayerCustomization playerCustomization) {
        super(playerCustomization);
        this.getCharacteristics().add(new HumanCharacteristic(playerCustomization, "body",1));
        this.getCharacteristics().add(new HumanColoreableCharacteristic(playerCustomization, "hair",3));
        this.getCharacteristics().add(new HumanEyes(playerCustomization, "eyes",4));
    }

    @OnlyIn(Dist.CLIENT)
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

    @Override
    public void applyAttributes(Player player) {
        super.applyAttributes(player);
        setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID,"speed"), Attributes.MOVEMENT_SPEED, 0.025, AttributeModifier.Operation.ADD_VALUE);
        setAttribute(player, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID,"attack_speed"), Attributes.ATTACK_SPEED, 0.025, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public int getHUDViewerScale() {
        return 21;
    }
}

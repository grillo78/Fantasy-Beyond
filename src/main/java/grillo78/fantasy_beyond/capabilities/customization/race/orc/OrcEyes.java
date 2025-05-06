package grillo78.fantasy_beyond.capabilities.customization.race.orc;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Coloreable;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class OrcEyes extends OrcCharacteristic implements Coloreable {

    private boolean renderingIris = false;
    private Color color = new Color(105, 18, 18,255);

    public OrcEyes(PlayerCustomization playerCustomization, String name, int maxVariants) {
        super(playerCustomization,name, maxVariants);
    }

    @Override
    public void render(PlayerModel<Player> model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, Player player) {
        super.render(model, poseStack, pBuffer, pPackedLight, player);
        renderingIris = true;
        super.render(model, poseStack, pBuffer, pPackedLight, player);
        renderingIris = false;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return renderingIris? Color.WHITE : color;
    }

    @Override
    public ResourceLocation getTexture() {
        return renderingIris? new ResourceLocation(FantasyBeyond.MOD_ID, "textures/entity/customization/race/orc/iris/" + getVariant() + ".png"): new ResourceLocation(FantasyBeyond.MOD_ID, "textures/entity/customization/race/orc/eyes/" + getVariant() + ".png");
    }
}

package grillo78.fantasy_beyond.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.entities.Direwolf;
import grillo78.fantasy_beyond.entities.Goblin;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DirewolfRenderer extends GeoEntityRenderer<Direwolf> {
    public DirewolfRenderer(EntityRendererProvider.Context context) {
        super(context, new GeoModel<Direwolf>() {
            @Override
            public ResourceLocation getModelResource(Direwolf animatable) {
                return ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "geo/monsters/direwolf.geo.json");
            }

            @Override
            public ResourceLocation getTextureResource(Direwolf animatable) {
                return ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/entity/monsters/direwolf.png");
            }

            @Override
            public ResourceLocation getAnimationResource(Direwolf animatable) {
                return ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "animations/monsters/direwolf.animation.json");
            }
        });
    }

    @Override
    public void render(Direwolf entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

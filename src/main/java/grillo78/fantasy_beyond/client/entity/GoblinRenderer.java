package grillo78.fantasy_beyond.client.entity;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.entities.Goblin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GoblinRenderer<T extends Goblin> extends MobRenderer<T, GoblinModel<T>> {

    public GoblinRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GoblinModel<>(pContext.bakeLayer(ModModelLayers.GOBLIN)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Goblin pEntity) {
        return new ResourceLocation(FantasyBeyond.MOD_ID, "textures/entity/goblin.png");
    }
}

package grillo78.fantasy_beyond.client.entity.renderers;

import grillo78.fantasy_beyond.entities.DemonLord;
import grillo78.fantasy_beyond.entities.Goblin;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GoblinRenderer extends EntityRenderer<Goblin> {
    public GoblinRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Goblin entity) {
        return null;
    }
}

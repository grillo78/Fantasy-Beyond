package grillo78.fantasy_beyond.client.entity.renderers;

import grillo78.fantasy_beyond.entities.DemonLord;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DemonLordRenderer extends EntityRenderer<DemonLord> {
    public DemonLordRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(DemonLord entity) {
        return null;
    }
}

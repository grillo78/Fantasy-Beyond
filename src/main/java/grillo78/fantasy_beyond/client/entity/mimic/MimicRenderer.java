package grillo78.fantasy_beyond.client.entity.mimic;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.entities.Goblin;
import grillo78.fantasy_beyond.entities.Mimic;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MimicRenderer<T extends Mimic> extends MobRenderer<T, MimicModel<T>> {

    public MimicRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MimicModel<>(pContext.bakeLayer(ModModelLayers.MIMIC)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Mimic pEntity) {
        return new ResourceLocation(FantasyBeyond.MOD_ID, "textures/entity/goblin.png");
    }
}

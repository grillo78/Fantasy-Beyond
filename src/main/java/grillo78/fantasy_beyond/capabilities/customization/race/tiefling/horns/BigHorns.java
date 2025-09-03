package grillo78.fantasy_beyond.capabilities.customization.race.tiefling.horns;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.BigHornsModel;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class BigHorns extends Horn {

    public BigHorns() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->{
            setModel(new BigHornsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.BIG_HORNS)));
        });
    }
}

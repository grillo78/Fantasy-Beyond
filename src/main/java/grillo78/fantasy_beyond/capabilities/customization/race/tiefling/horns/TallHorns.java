package grillo78.fantasy_beyond.capabilities.customization.race.tiefling.horns;

import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.BigHornsModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.TallHornsModel;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class TallHorns extends Horn {

    public TallHorns() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->{
            setModel(new TallHornsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.TALL_HORNS)));
        });
    }
}

package grillo78.fantasy_beyond.capabilities.customization.race.tiefling.horns;

import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.BigHornsModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.MediumHornsModel;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class MediumHorns extends Horn {

    public MediumHorns() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->{
            setModel(new MediumHornsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MEDIUM_HORNS)));
        });
    }
}

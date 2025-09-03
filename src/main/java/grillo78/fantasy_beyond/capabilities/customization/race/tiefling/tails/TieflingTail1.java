package grillo78.fantasy_beyond.capabilities.customization.race.tiefling.tails;

import grillo78.fantasy_beyond.capabilities.customization.race.tiefling.horns.Horn;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.TallHornsModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail1Model;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class TieflingTail1 extends Tail {

    public TieflingTail1() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->{
            setModel(new TieflingTail1Model(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.TIEFLING_TAIL_1)));
        });
    }
}

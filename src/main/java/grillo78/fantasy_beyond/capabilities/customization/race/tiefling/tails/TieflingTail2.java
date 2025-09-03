package grillo78.fantasy_beyond.capabilities.customization.race.tiefling.tails;

import grillo78.fantasy_beyond.capabilities.customization.race.tiefling.horns.Horn;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail2Model;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class TieflingTail2 extends Tail {

    public TieflingTail2() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->{
            setModel(new TieflingTail2Model(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.TIEFLING_TAIL_2)));
        });
    }
}

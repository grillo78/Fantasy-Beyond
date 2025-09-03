package grillo78.fantasy_beyond.capabilities.customization.race.merfolk.tails;

import grillo78.fantasy_beyond.capabilities.customization.race.tiefling.horns.Horn;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.merfolk.tails.MerfolkTail1Model;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail1Model;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class MerfolkTail1 extends Tail {

    public MerfolkTail1() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->{
            setModel(new MerfolkTail1Model(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MERFOLK_TAIL_1)));
        });
    }
}

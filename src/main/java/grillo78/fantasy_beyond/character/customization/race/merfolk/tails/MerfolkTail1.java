package grillo78.fantasy_beyond.character.customization.race.merfolk.tails;

import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.merfolk.tails.MerfolkTail1Model;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;

public class MerfolkTail1 extends Tail {

    public MerfolkTail1() {
        if(FMLLoader.getDist() == Dist.CLIENT) {
            setModel(new MerfolkTail1Model(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MERFOLK_TAIL_1)));
        }
    }
}

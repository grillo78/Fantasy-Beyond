package grillo78.fantasy_beyond.character.customization.race.tiefling.tails;

import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail2Model;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;

public class TieflingTail2 extends Tail {

    public TieflingTail2() {
        if (FMLLoader.getDist() == Dist.CLIENT) {
            setModel(new TieflingTail2Model(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.TIEFLING_TAIL_2)));
        }
    }
}

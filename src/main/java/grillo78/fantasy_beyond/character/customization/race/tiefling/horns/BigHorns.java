package grillo78.fantasy_beyond.character.customization.race.tiefling.horns;

import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.BigHornsModel;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;

public class BigHorns extends Horn {

    public BigHorns() {
        if (FMLLoader.getDist() == Dist.CLIENT) {
            setModel(new BigHornsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.BIG_HORNS)));
        }
    }
}

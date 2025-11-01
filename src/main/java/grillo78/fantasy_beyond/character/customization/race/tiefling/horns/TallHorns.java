package grillo78.fantasy_beyond.character.customization.race.tiefling.horns;

import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.TallHornsModel;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;

public class TallHorns extends Horn {

    public TallHorns() {
        if (FMLLoader.getDist() == Dist.CLIENT) {
            setModel(new TallHornsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.TALL_HORNS)));
        }
    }
}

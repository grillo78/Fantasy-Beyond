package grillo78.fantasy_beyond.character.customization.race.tiefling.horns;

import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.MediumHornsModel;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;

public class MediumHorns extends Horn {

    public MediumHorns() {
        if (FMLLoader.getDist() == Dist.CLIENT) {
            setModel(new MediumHornsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MEDIUM_HORNS)));
        }
    }
}

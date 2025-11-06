package grillo78.fantasy_beyond.client.entity.race.tiefling.horns;

import grillo78.fantasy_beyond.character.customization.race.Characteristic;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import grillo78.fantasy_beyond.client.entity.race.RaceCharacteristicRenderer;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail1Model;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail2Model;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class TieflingHornsCharacteristicRenderer extends RaceCharacteristicRenderer {

    private List<CustomizationModel> variants = new ArrayList<>();

    public TieflingHornsCharacteristicRenderer() {
        super(null);
        variants.add(new BigHornsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.BIG_HORNS)));
        variants.add(new MediumHornsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MEDIUM_HORNS)));
        variants.add(new TallHornsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.TALL_HORNS)));
    }

    @Override
    public CustomizationModel getModel(Characteristic characteristic) {
        return variants.get(characteristic.getVariant());
    }
}

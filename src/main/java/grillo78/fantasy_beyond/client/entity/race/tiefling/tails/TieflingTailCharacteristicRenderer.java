package grillo78.fantasy_beyond.client.entity.race.tiefling.tails;

import grillo78.fantasy_beyond.character.customization.race.Characteristic;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import grillo78.fantasy_beyond.client.entity.race.RaceCharacteristicRenderer;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class TieflingTailCharacteristicRenderer extends RaceCharacteristicRenderer {

    private List<CustomizationModel> variants = new ArrayList<>();

    public TieflingTailCharacteristicRenderer() {
        super(null);
        variants.add(new TieflingTail1Model(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.TIEFLING_TAIL_1)));
        variants.add(new TieflingTail2Model(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.TIEFLING_TAIL_2)));
    }

    @Override
    public CustomizationModel getModel(Characteristic characteristic) {
        return variants.get(characteristic.getVariant());
    }
}

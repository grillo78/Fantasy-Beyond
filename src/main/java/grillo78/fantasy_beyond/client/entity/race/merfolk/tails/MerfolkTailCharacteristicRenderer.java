package grillo78.fantasy_beyond.client.entity.race.merfolk.tails;

import grillo78.fantasy_beyond.character.customization.race.Characteristic;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import grillo78.fantasy_beyond.client.entity.race.RaceCharacteristicRenderer;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail1Model;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail2Model;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class MerfolkTailCharacteristicRenderer extends RaceCharacteristicRenderer {

    private List<CustomizationModel> variants = new ArrayList<>();

    public MerfolkTailCharacteristicRenderer() {
        super(null);
        variants.add(new TieflingTail1Model(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.MERFOLK_TAIL_1)));
    }

    @Override
    public CustomizationModel getModel(Characteristic characteristic) {
        return variants.get(characteristic.getVariant());
    }
}

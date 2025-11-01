package grillo78.fantasy_beyond.character.customization.race.tiefling.horns;

import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class Horn {

    @OnlyIn(Dist.CLIENT)
    private CustomizationModel model;

    @OnlyIn(Dist.CLIENT)
    public CustomizationModel getModel() {
        return model;
    }

    @OnlyIn(Dist.CLIENT)
    public void setModel(CustomizationModel model) {
        this.model = model;
    }
}

package grillo78.fantasy_beyond.capabilities.customization.race.tiefling.horns;

import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.minecraft.client.model.EntityModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

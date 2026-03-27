package grillo78.fantasy_beyond.items;

import grillo78.fantasy_beyond.character.customization.race.RaceType;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import java.util.List;

public class RacistArmor extends ArmorItem {

    private List<RaceType> races;

    public RacistArmor(Holder<ArmorMaterial> material, Type type, Properties properties, List<RaceType> races) {
        super(material, type, properties);
        this.races = races;
    }

    public List<RaceType> getRaces() {
        return races;
    }
}

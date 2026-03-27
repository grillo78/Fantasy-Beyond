package grillo78.fantasy_beyond.data_map;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.data_map.forging.HeatableMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class ModDataMaps {
    public static final DataMapType<Item, HeatableMaterial> HEATABLE_MATERIALS = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "heatable_materials"),
            Registries.ITEM,
            HeatableMaterial.CODEC
    ).build();
}

package grillo78.fantasy_beyond.datagen;

import grillo78.fantasy_beyond.items.ModItems;
import grillo78.fantasy_beyond.items.RacistClothItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), modid, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        ModItems.ITEMS.getEntries().forEach(registryObject -> {
            Item item = registryObject.get();
//            if (!existingFileHelper.exists(new ResourceLocation(ClothesMod.MOD_ID, "item/" + BuiltInRegistries.ITEM.getKey(item).getPath()), ModelProvider.MODEL)) {
            if ((item instanceof RacistClothItem)) {
                ModelFile itemGenerated = getExistingFile(modLoc( "item/clothes/" + ((RacistClothItem) item).getBaseModel()));
                getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath()).parent(itemGenerated)
                        .texture("0", "entity/clothes/" + (((RacistClothItem)item).isForMale() ||((RacistClothItem)item).isGenderless() ? "male" : "female") + "/" + ForgeRegistries.ITEMS.getKey(item).getPath());
            }
//            }
        });
    }
}

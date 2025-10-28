package grillo78.fantasy_beyond.datagen;

import grillo78.fantasy_beyond.items.ModItems;
import grillo78.fantasy_beyond.items.RacistClothItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.commons.lang3.StringUtils;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }


//    @Override
//    protected void registerModels() {
//        ModItems.ITEMS.getEntries().forEach(registryObject -> {
//            Item item = registryObject.get();
////            if (!existingFileHelper.exists(new ResourceLocation(ClothesMod.MOD_ID, "item/" + BuiltInRegistries.ITEM.getKey(item).getPath()), ModelProvider.MODEL)) {
//            if ((item instanceof RacistClothItem)) {
//                ModelFile itemGenerated = getExistingFile(modLoc( "item/clothes/" + ((RacistClothItem) item).getBaseModel()));
//                getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath()).parent(itemGenerated)
//                        .texture("0", "entity/clothes/" + (((RacistClothItem)item).isForMale() ||((RacistClothItem)item).isGenderless() ? "male" : "female") + "/" + ForgeRegistries.ITEMS.getKey(item).getPath());
//            }
////            }
//        });
//    }

    @Override
    protected void addTranslations() {
        ModItems.ITEMS.getEntries().forEach(registryObject -> {
            if ((registryObject.get() instanceof RacistClothItem)) {

                this.addItem(registryObject, StringUtils.capitalize(BuiltInRegistries.ITEM.getKey(registryObject.get()).getPath().replace("multi_", "").replace("dwarf_", "").replace("human_", "").replace("_", " ")));
            }
        });
    }

}

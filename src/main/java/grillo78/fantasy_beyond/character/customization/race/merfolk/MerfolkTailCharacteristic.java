package grillo78.fantasy_beyond.character.customization.race.merfolk;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.merfolk.tails.MerfolkTail1;
import grillo78.fantasy_beyond.character.customization.race.merfolk.tails.Tail;
import grillo78.fantasy_beyond.character.customization.race.tiefling.TieflingCharacteristic;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class MerfolkTailCharacteristic extends TieflingCharacteristic {

    public MerfolkTailCharacteristic(PlayerCustomization playerCustomization, String name, int maxVariants) {
        super(playerCustomization,name, maxVariants);
    }

    @Override
    public ResourceLocation getTexture(LivingEntity player) {
        ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/entity/customization/race/merfolk/tails/" + getVariant() + ".png");

//        List<ResourceLocation> masks = new ArrayList<>();
//        AtomicReference<String> append = new AtomicReference<>("");
//        player.getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(clothes -> {
//            for (int i = 0; i < clothes.getInventory().getSlots(); i++) {
//                Item item = clothes.getInventory().getStackInSlot(i).getItem();
//                if (item instanceof Cloth) {
//                    masks.add(((Cloth) item).getAlphaMask(player));
//                    append.set(append.get() + ForgeRegistries.ITEMS.getKey(item).toString().replace(":", "_"));
//                }
//            }
//        });

//        return AlphaMaskTexture.getTexture(baseTexture, ResourceLocation.parse(ClothesMod.MOD_ID, baseTexture.getPath() + append), masks);
        return baseTexture;
    }
}

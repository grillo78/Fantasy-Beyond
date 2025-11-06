package grillo78.fantasy_beyond.character.customization.race.tiefling;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class TieflingTailCharacteristic extends TieflingCharacteristic {

    public TieflingTailCharacteristic(PlayerCustomization playerCustomization, String name, int maxVariant) {
        super(playerCustomization, name, maxVariant);
    }

    @Override
    public ResourceLocation getTexture(LivingEntity player) {
        ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/entity/customization/race/tiefling/tails/" + getVariant() + ".png");

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

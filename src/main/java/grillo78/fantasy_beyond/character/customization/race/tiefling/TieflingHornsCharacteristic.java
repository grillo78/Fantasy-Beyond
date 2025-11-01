package grillo78.fantasy_beyond.character.customization.race.tiefling;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.tiefling.horns.BigHorns;
import grillo78.fantasy_beyond.character.customization.race.tiefling.horns.Horn;
import grillo78.fantasy_beyond.character.customization.race.tiefling.horns.MediumHorns;
import grillo78.fantasy_beyond.character.customization.race.tiefling.horns.TallHorns;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TieflingHornsCharacteristic extends TieflingCharacteristic {

    private List<Horn> horns = new ArrayList<>();

    public TieflingHornsCharacteristic(PlayerCustomization playerCustomization, String name) {
        super(playerCustomization,name, 0);
        horns.add(new BigHorns());
        horns.add(new MediumHorns());
        horns.add(new TallHorns());

        setMaxVariant(horns.size());
    }

    @Override
    public CustomizationModel getModel() {
        return horns.get(getVariant()).getModel();
    }

    public ResourceLocation getTexture(Player player) {
        ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/entity/customization/race/tiefling/horns/" + getVariant() + ".png");

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

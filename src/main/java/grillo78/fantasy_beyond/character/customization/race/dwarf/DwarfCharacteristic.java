package grillo78.fantasy_beyond.character.customization.race.dwarf;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Characteristic;
import grillo78.fantasy_beyond.client.clothes.RacistClothItemRenderer;
import grillo78.fantasy_beyond.client.texture.AlphaMaskTexture;
import grillo78.fantasy_beyond.items.RacistClothItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DwarfCharacteristic extends Characteristic {

    public DwarfCharacteristic(PlayerCustomization playerCustomization, String name, int maxVariant) {
        super(playerCustomization, name, maxVariant);
    }

    @Override
    public ResourceLocation getTexture(LivingEntity player){
        ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/entity/customization/race/dwarf/" + name + "/" + (getPlayerCustomization().isMale() ? "male_" : "female_") + getVariant() + ".png");

        List<ResourceLocation> masks = new ArrayList<>();
        AtomicReference<String> append = new AtomicReference<>("");

        ICuriosItemHandler inventory = CuriosApi.getCuriosInventory(player).get();
        for (SlotResult slot : inventory.findCurios(itemStack -> itemStack.getItem() instanceof RacistClothItem)) {
            masks.add(RacistClothItemRenderer.getAlphaMask(player, (RacistClothItem) slot.stack().getItem()));
            append.set(append.get() + BuiltInRegistries.ITEM.getKey(slot.stack().getItem()).toString().replace(":", "_"));
        }

        return AlphaMaskTexture.getTexture(baseTexture, ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, baseTexture.getPath() + append), masks);
    }
}

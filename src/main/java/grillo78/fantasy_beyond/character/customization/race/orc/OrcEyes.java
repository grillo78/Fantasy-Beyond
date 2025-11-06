package grillo78.fantasy_beyond.character.customization.race.orc;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Coloreable;
import grillo78.fantasy_beyond.client.clothes.RacistClothItemRenderer;
import grillo78.fantasy_beyond.client.texture.AlphaMaskTexture;
import grillo78.fantasy_beyond.items.RacistClothItem;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class OrcEyes extends OrcCharacteristic implements Coloreable {


    private Color color = new Color(105, 18, 18, 255);

    public OrcEyes(PlayerCustomization playerCustomization, String name, int maxVariants) {
        super(playerCustomization, name, maxVariants);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return  color;
    }

    @Override
    public ResourceLocation getTexture(LivingEntity player, boolean renderingIris) {
        ResourceLocation baseTexture = renderingIris ? ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/entity/customization/race/orc/iris/" + getVariant() + ".png") : ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/entity/customization/race/orc/eyes/" + getVariant() + ".png");

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

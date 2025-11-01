package grillo78.fantasy_beyond.character.customization.race.elf;

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
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ElfEyes extends ElfCharacteristic implements Coloreable {

    private boolean renderingIris = false;
    private Color color = new Color(105, 18, 18,255);

    public ElfEyes(PlayerCustomization playerCustomization, String name, int maxVariants) {
        super(playerCustomization,name, maxVariants);
    }

    @Override
    public void render(PlayerModel<Player> model, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, Player player) {
        super.render(model, poseStack, pBuffer, pPackedLight, player);
        renderingIris = true;
        super.render(model, poseStack, pBuffer, pPackedLight, player);
        renderingIris = false;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return renderingIris? Color.WHITE : color;
    }

    @Override
    public ResourceLocation getTexture(Player player) {
        ResourceLocation baseTexture = renderingIris ? ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/entity/customization/race/common/iris/" + getVariant() + ".png") : ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/entity/customization/race/common/eyes/" + getVariant() + ".png");

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

package grillo78.fantasy_beyond.capabilities.customization.race.dwarf;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.client.texture.AlphaMaskTexture;
import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.items.Cloth;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.capabilities.customization.race.Coloreable;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DwarfEyes extends DwarfCharacteristic implements Coloreable {

    private boolean renderingIris = false;
    private Color color = new Color(105, 18, 18,255);

    public DwarfEyes(PlayerCustomization playerCustomization, String name, int maxVariants) {
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
        ResourceLocation baseTexture = renderingIris ? new ResourceLocation(FantasyBeyond.MOD_ID, "textures/entity/customization/race/common/iris/" + getVariant() + ".png") : new ResourceLocation(FantasyBeyond.MOD_ID, "textures/entity/customization/race/common/eyes/" + getVariant() + ".png");

        List<ResourceLocation> masks = new ArrayList<>();
        AtomicReference<String> append = new AtomicReference<>("");
        player.getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(clothes -> {
            for (int i = 0; i < clothes.getInventory().getSlots(); i++) {
                Item item = clothes.getInventory().getStackInSlot(i).getItem();
                if (item instanceof Cloth) {
                    masks.add(((Cloth) item).getAlphaMask(player));
                    append.set(append.get() + ForgeRegistries.ITEMS.getKey(item).toString().replace(":", "_"));
                }
            }
        });

        return AlphaMaskTexture.getTexture(baseTexture, new ResourceLocation(ClothesMod.MOD_ID, baseTexture.getPath() + append), masks);
    }
}

package grillo78.fantasy_beyond.character.customization.race.tiefling;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.customization.race.Coloreable;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class TieflingEyes extends TieflingCharacteristic implements Coloreable {

    private boolean renderingIris = false;
    private Color color = new Color(105, 18, 18,255);

    public TieflingEyes(PlayerCustomization playerCustomization, String name, int maxVariants) {
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
//
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
//
//        return AlphaMaskTexture.getTexture(baseTexture, ResourceLocation.parse(ClothesMod.MOD_ID, baseTexture.getPath() + append), masks);
        return baseTexture;
    }
}

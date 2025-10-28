package grillo78.fantasy_beyond.items;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.client.texture.AlphaMaskTexture;
import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.items.Cloth;
import grillo78.clothes_mod.common.items.ClothItem;
import grillo78.clothes_mod.common.items.ClothesSlot;
import grillo78.fantasy_beyond.capabilities.PlayerData;
import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import grillo78.fantasy_beyond.capabilities.customization.race.Race;
import grillo78.fantasy_beyond.capabilities.customization.race.RaceType;
import grillo78.fantasy_beyond.client.entity.race.CustomizationModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class RacistClothItem extends ClothItem {

    private List<RaceType> races;
    private boolean isGenderless;
    private boolean isForMale;
    public static List<RacistClothItem> CLOTHES = new ArrayList<>();
    private boolean hasMask;

    public RacistClothItem(Properties pProperties, ClothesSlot slot, List<RaceType> races) {
        this(pProperties, slot, races, true, false, false);
    }

    public RacistClothItem(Properties pProperties, ClothesSlot slot, List<RaceType> races, boolean hasMask) {
        this(pProperties, slot, races, true, false, hasMask);
    }

    public RacistClothItem(Properties pProperties, ClothesSlot slot, List<RaceType> races, boolean isGenderless, boolean isForMale, boolean hasMask) {
        super(pProperties, slot, hasMask, new ResourceLocation(""));
        this.races = races;
        this.isGenderless = isGenderless;
        this.isForMale = isForMale;
        this.hasMask = hasMask;
        CLOTHES.add(this);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        MutableComponent raceComp;
        raceComp = Component.translatable("fantasy_beyond.tooltip.races");
        raceComp.setStyle(raceComp.getStyle().withColor(Color.ORANGE.hashCode()));
        pTooltipComponents.add(raceComp);
        for (int i = 0; i < races.size(); i++) {
            ResourceLocation raceLoc = RaceType.RACE_TYPES_REGISTRY.get().getKey(races.get(i));
            raceComp = Component.literal("   " + Component.translatable("race.name." + raceLoc.getPath()).getString());
            raceComp.setStyle(raceComp.getStyle().withColor(Color.ORANGE.hashCode()));
            pTooltipComponents.add(raceComp);
        }
        raceComp = Component.translatable("fantasy_beyond.tooltip.gender", isGenderless ? Component.translatable("fantasy_beyond.tooltip.gender.genderless") : (isForMale ? Component.translatable("fantasy_beyond.tooltip.gender.male") : Component.translatable("fantasy_beyond.tooltip.gender.female")));
        raceComp.setStyle(raceComp.getStyle().withColor(Color.CYAN.hashCode()));
        pTooltipComponents.add(raceComp);
    }

    @Override
    public void renderCloth(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Player player, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, PlayerModel bipedModel) {
        player.getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
            Race race = data.getPlayerCustomization().getRace();
            CustomizationModel model = race.getModel();
            model.young = data.getPlayerCustomization().isYoung();
            model.setModelProperties(player);
            model.setupModel(bipedModel);
            model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTexture(data))), pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        });
    }

    @Override
    public boolean canPlace(ItemStack stack, ClothesSlot slot, Player player) {
        AtomicBoolean fitCustomization = new AtomicBoolean(true);
        player.getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
            fitCustomization.set(this.races.contains(data.getPlayerCustomization().getRace().getType()) && (isGenderless || data.getPlayerCustomization().isMale() == isForMale));
        });
        return super.canPlace(stack, slot, player) && fitCustomization.get();
    }

    @Override
    public ResourceLocation getAlphaMask(Player player) {
        PlayerData data = player.getCapability(PlayerDataProvider.DATA).orElse(null);

        ResourceLocation texture = getBaseTexture(data);

        return !this.hasMask? null: new ResourceLocation(texture.getNamespace(), texture.getPath().replace(".png", "_a.png"));
    }

    protected ResourceLocation getTexture(PlayerData data) {
        ResourceLocation baseTexture = getBaseTexture(data);

        List<ResourceLocation> masks = new ArrayList<>();
        AtomicReference<String> append = new AtomicReference<>("");
        data.getPlayer().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(clothes -> {
            switch (getSlot()){
                case SHIRT:
                    addMask(clothes.getInventory().getStackInSlot(ClothesSlot.HEAD.getID()).getItem(), append, masks, data.getPlayer());
                    addMask(clothes.getInventory().getStackInSlot(ClothesSlot.WRIST.getID()).getItem(), append, masks, data.getPlayer());
                    addMask(clothes.getInventory().getStackInSlot(ClothesSlot.PANTS.getID()).getItem(), append, masks, data.getPlayer());
                case PANTS:
                    addMask(clothes.getInventory().getStackInSlot(ClothesSlot.BELT.getID()).getItem(), append, masks, data.getPlayer());
                case WRIST:
                case HEAD:
                    addMask(clothes.getInventory().getStackInSlot(ClothesSlot.JACKET.getID()).getItem(), append, masks, data.getPlayer());
                    break;
            }
        });

        return AlphaMaskTexture.getTexture(baseTexture, new ResourceLocation(ClothesMod.MOD_ID, baseTexture.getPath() + append), masks);
    }

    public boolean isForMale() {
        return isForMale;
    }

    public boolean isGenderless() {
        return isGenderless;
    }

    private ResourceLocation getBaseTexture(PlayerData data) {
        boolean isForMale = this.isForMale;
        if (isGenderless) {
            isForMale = data.getPlayerCustomization().isMale();
        }
        return new ResourceLocation(ForgeRegistries.ITEMS.getKey(this).getNamespace(), "textures/entity/clothes/" + (isForMale ? "male" : "female") + "/" + ForgeRegistries.ITEMS.getKey(this).getPath() + ".png");
    }

    private void addMask(Item item, AtomicReference<String> append, List<ResourceLocation> masks, Player player){
        if (item instanceof Cloth) {
            masks.add(((Cloth) item).getAlphaMask(player));
            append.set(append.get() + ForgeRegistries.ITEMS.getKey(item).toString().replace(":", "_"));
        }
    }

    public String getBaseModel() {
        return (isGenderless || isForMale? "male_" : "female_")+ RaceType.RACE_TYPES_REGISTRY.get().getKey(races.get(0)).getPath();
    }
}

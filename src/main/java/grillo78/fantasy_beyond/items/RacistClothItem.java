package grillo78.fantasy_beyond.items;

import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RacistClothItem extends ClothItem {

    private RaceType race;
    private boolean isGenderless;
    private boolean isForMale;

    public RacistClothItem(Properties pProperties, ClothesSlot slot, RaceType race) {
        this(pProperties, slot, race, true, false);
    }
    public RacistClothItem(Properties pProperties, ClothesSlot slot, RaceType race, boolean isGenderless, boolean isForMale) {
        super(pProperties, slot);
        this.race = race;
        this.isGenderless = isGenderless;
        this.isForMale = isForMale;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        ResourceLocation raceLoc = RaceType.RACE_TYPES_REGISTRY.get().getKey(race);
        MutableComponent raceComp = Component.translatable("fantasy_beyond.tooltip.race", Component.translatable("race.name." + raceLoc.getPath()).getString());
        raceComp.setStyle(raceComp.getStyle().withColor(Color.ORANGE.hashCode()));
        pTooltipComponents.add(raceComp);
        raceComp = Component.translatable("fantasy_beyond.tooltip.gender", isGenderless? Component.translatable("fantasy_beyond.tooltip.gender.genderless") : (isForMale? Component.translatable("fantasy_beyond.tooltip.gender.male") : Component.translatable("fantasy_beyond.tooltip.gender.female")));
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
            fitCustomization.set(this.race == data.getPlayerCustomization().getRace().getType() && (isGenderless || data.getPlayerCustomization().isMale() == isForMale));
        });
        return super.canPlace(stack, slot, player) && fitCustomization.get();
    }

    protected ResourceLocation getTexture(PlayerData data) {
        AtomicBoolean isForMale = new AtomicBoolean(this.isForMale);
        if(isGenderless){
            isForMale.set(data.getPlayerCustomization().isMale());
        }
        return new ResourceLocation(ForgeRegistries.ITEMS.getKey(this).getNamespace(), "textures/entity/clothes/"+ (isForMale.get()? "male": "female") + "/" + ForgeRegistries.ITEMS.getKey(this).getPath() + ".png");
    }
}

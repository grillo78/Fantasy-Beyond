package grillo78.fantasy_beyond.items;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.customization.race.RaceType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RacistClothItem extends Item implements ICurioItem {

    private List<RaceType> races;
    private boolean isGenderless;
    private boolean isForMale;
    public static List<RacistClothItem> CLOTHES = new ArrayList<>();
    private boolean hasMask;

    public RacistClothItem(Item.Properties pProperties, List<RaceType> races) {
        this(pProperties, races, true, false, false);
    }

    public RacistClothItem(Item.Properties pProperties, List<RaceType> races, boolean hasMask) {
        this(pProperties, races, true, false, hasMask);
    }

    public RacistClothItem(Item.Properties pProperties, List<RaceType> races, boolean isGenderless, boolean isForMale, boolean hasMask) {
        super(pProperties);
        this.races = races;
        this.isGenderless = isGenderless;
        this.isForMale = isForMale;
        this.hasMask = hasMask;
        CLOTHES.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        MutableComponent raceComp;
        raceComp = Component.translatable("fantasy_beyond.tooltip.races");
        raceComp.setStyle(raceComp.getStyle().withColor(Color.ORANGE.hashCode()));
        tooltipComponents.add(raceComp);
        for (int i = 0; i < races.size(); i++) {
            ResourceLocation raceLoc = RaceType.RACE_TYPES_REGISTRY.getKey(races.get(i));
            raceComp = Component.literal("   " + Component.translatable("race.name." + raceLoc.getPath()).getString());
            raceComp.setStyle(raceComp.getStyle().withColor(Color.ORANGE.hashCode()));
            tooltipComponents.add(raceComp);
        }
        raceComp = Component.translatable("fantasy_beyond.tooltip.gender", isGenderless ? Component.translatable("fantasy_beyond.tooltip.gender.genderless") : (isForMale ? Component.translatable("fantasy_beyond.tooltip.gender.male") : Component.translatable("fantasy_beyond.tooltip.gender.female")));
        raceComp.setStyle(raceComp.getStyle().withColor(Color.CYAN.hashCode()));
        tooltipComponents.add(raceComp);
    }

//    @Override
//    public void renderCloth(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Player player, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, PlayerModel bipedModel) {
//        player.getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
//            Race race = data.getPlayerCustomization().getRace();
//            CustomizationModel model = race.getModel();
//            model.young = data.getPlayerCustomization().isYoung();
//            model.setModelProperties(player);
//            model.setupModel(bipedModel);
//            model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTexture(data))), pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
//        });
//    }


    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        AtomicBoolean fitCustomization = new AtomicBoolean(true);
        CharacterData data = slotContext.entity().getData(ModAttachments.CHARACTER_DATA);
        fitCustomization.set(this.races.contains(data.getPlayerCustomization().getRace().getType()) && (isGenderless || data.getPlayerCustomization().isMale() == isForMale));
        return ICurioItem.super.canEquip(slotContext, stack) && fitCustomization.get();
    }


    public boolean hasMask() {
        return hasMask;
    }
    public boolean isForMale() {
        return isForMale;
    }

    public boolean isGenderless() {
        return isGenderless;
    }


    public String getBaseModel() {
        return (isGenderless || isForMale ? "male_" : "female_") + RaceType.RACE_TYPES_REGISTRY.getKey(races.get(0)).getPath();
    }
}

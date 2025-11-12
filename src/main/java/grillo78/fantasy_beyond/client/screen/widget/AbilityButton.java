package grillo78.fantasy_beyond.client.screen.widget;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.level.classes.abilities.Ability;
import grillo78.fantasy_beyond.network.UnlockAbility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class AbilityButton extends ImageButton {
    public static WidgetSprites LOCKED = new WidgetSprites(
            ResourceLocation.withDefaultNamespace("advancements/goal_frame_unobtained"),
            ResourceLocation.withDefaultNamespace("advancements/task_frame_unobtained"),
            ResourceLocation.withDefaultNamespace("advancements/goal_frame_obtained"),
            ResourceLocation.withDefaultNamespace("advancements/task_frame_obtained")
    );
    public static WidgetSprites UNLOCKED = new WidgetSprites(
            ResourceLocation.withDefaultNamespace("advancements/challenge_frame_obtained"),
            ResourceLocation.withDefaultNamespace("advancements/challenge_frame_obtained"),
            ResourceLocation.withDefaultNamespace("advancements/challenge_frame_unobtained"),
            ResourceLocation.withDefaultNamespace("advancements/challenge_frame_unobtained")
    );

    private Ability ability;
    private ResourceLocation texture;

    public AbilityButton(int x, int y, int width, int height, ResourceLocation texture, Button.OnPress onPress, Ability ability) {
        super(x, y, width, height, new WidgetSprites(
                ResourceLocation.withDefaultNamespace("advancements/goal_frame_unobtained"),
                ResourceLocation.withDefaultNamespace("advancements/task_frame_unobtained"),
                ResourceLocation.withDefaultNamespace("advancements/goal_frame_obtained"),
                ResourceLocation.withDefaultNamespace("advancements/task_frame_obtained")
        ), onPress);
        this.ability = ability;
//        Tooltip tooltip = Tooltip.create(ability.getDisplayName());
//        setTooltip(tooltip);
        this.texture = texture;
        this.sprites = ability.isUnlocked() ? UNLOCKED : LOCKED;
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.onRelease(mouseX, mouseY);
        setFocused(false);
    }

    public void setSprites(WidgetSprites sprites){
        this.sprites = sprites;
    }

    @Override
    public boolean isActive() {
        return super.isActive() && ability.canBeUnlocked(Minecraft.getInstance().player) && !ability.isUnlocked();
    }

    public Ability getAbility() {
        return ability;
    }

    private List<FormattedCharSequence> getTitleTooltip(){
        List<FormattedCharSequence> list = new ArrayList<>();

        ability.getTitleTooltip().forEach(component -> list.addAll(Minecraft.getInstance().font.split(component, 170)));

        return list;
    }

    private List<FormattedCharSequence> getCustomTooltip(){
        List<FormattedCharSequence> list = new ArrayList<>();

        ability.getTooltip(Minecraft.getInstance().player).forEach(component -> {
            list.addAll(Minecraft.getInstance().font.split(component, 170));
        });

        return list;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(texture, getX()+3, getY()+3, 0, 0, width-6, height-6, width-6, height-6);
        if(isHovered()){
            guiGraphics.renderTooltip(Minecraft.getInstance().font, getCustomTooltip(), mouseX, mouseY);
            List<FormattedCharSequence> titleTooltip = getTitleTooltip();
            guiGraphics.renderTooltip(Minecraft.getInstance().font, titleTooltip, mouseX, mouseY - (Minecraft.getInstance().font.lineHeight * titleTooltip.size())-4);
        }
    }
}

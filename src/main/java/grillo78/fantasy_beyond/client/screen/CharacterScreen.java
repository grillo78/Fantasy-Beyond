package grillo78.fantasy_beyond.client.screen;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.customization.race.RaceType;
import grillo78.fantasy_beyond.client.screen.widget.CustomImageButton;
import grillo78.fantasy_beyond.client.screen.widget.StatsList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.awt.*;

public class CharacterScreen extends Screen {

    private static final ResourceLocation CHARACTER_BACKGROUND = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/stats/character_background.png");
    private static final ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("hud/experience_bar_background");
    private static final ResourceLocation EXPERIENCE_BAR_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("hud/experience_bar_progress");
    private static final WidgetSprites PAUSE_BUTTON_TEXTURE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/pause_button"),
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/pause_button_selected")
    );
    private static final WidgetSprites PLAY_BUTTON_TEXTURE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/play_button"),
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/play_button_selected")
    );
    private static final WidgetSprites RESET_BUTTON_TEXTURE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/reset_button"),
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/reset_button_selected")
    );
    private int angleO = 0;
    private int angle = 0;
    private boolean paused = true;

    public CharacterScreen() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        super.init();
        int width = 150;
        int x = this.width / 3 -width / 2;
        addRenderableWidget(new CustomImageButton(x + 1, height - 22, 16, 16, RESET_BUTTON_TEXTURE, (pButton -> {
            angleO = 0;
            angle = 0;
        })));
        addRenderableWidget(new CustomImageButton(x-17, height - 22, 16, 16, PLAY_BUTTON_TEXTURE, (pButton -> {
            paused = !paused;
            ((CustomImageButton) pButton).setSprites(paused ? PLAY_BUTTON_TEXTURE : PAUSE_BUTTON_TEXTURE);
            pButton.setFocused(false);
        })));
        CharacterData data = minecraft.player.getData(ModAttachments.CHARACTER_DATA);
        StatsList statsList = new StatsList(this,data);
        statsList.fillCharacteristics();
        addRenderableWidget(statsList);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        angleO = angle;
        if (!paused)
            angle--;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        CharacterData data = minecraft.player.getData(ModAttachments.CHARACTER_DATA);
        int y = 20;
        int width = 150;
        int x = this.width / 3 -width;
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/scrolls.png"), x, y, 0, 0, width, height - 40, width, height - 40);

        x = this.width/2-width/2;

        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/scrolls.png"), x, y, 0, 0, width, height - 40, width, height - 40);

        width = 300;
        guiGraphics.blitSprite(EXPERIENCE_BAR_BACKGROUND_SPRITE, this.width/2-width/2, 10, width, 7);
        guiGraphics.blitSprite(EXPERIENCE_BAR_PROGRESS_SPRITE, width, 7, 0, 0, this.width/2-width/2, 10, (int) (width*data.getLevel().getExperience()/data.getLevel().getExpToNextLevel()), 7);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int y = 20;
        int width = 144;
        int x = this.width / 3 -width-3;
        CharacterData data = minecraft.player.getData(ModAttachments.CHARACTER_DATA);
        pGuiGraphics.enableScissor(x, y, x + width, height - y);
        float oldYRot = minecraft.player.getYHeadRot();
        float oldXRot = minecraft.player.getXRot();
        float oldYBodyRot = minecraft.player.yBodyRot;
        minecraft.player.setYHeadRot(0);
        minecraft.player.setYBodyRot(0);
        minecraft.player.setXRot(0);
        InventoryScreen.renderEntityInInventory(pGuiGraphics, x + width / 2, height - 40, data.getPlayerCustomization().getRace().getHUDViewerScale() * 4.5F, Vec3.ZERO.toVector3f(), new Quaternionf().rotationX((float) Math.toRadians(180)).rotateY(Mth.lerp(pPartialTick, angleO, angle) * 0.025F), new Quaternionf(), minecraft.player);
        minecraft.player.setYHeadRot(oldYRot);
        minecraft.player.setXRot(oldXRot);
        minecraft.player.setYBodyRot(oldYBodyRot);
        pGuiGraphics.disableScissor();
        Component text = Component.literal(String.valueOf(data.getLevel().getLevel()));
        pGuiGraphics.drawString(font, text, this.width / 2 - font.width(text) / 2, 5, Color.WHITE.hashCode());
        y = 35;
        text = Component.translatable("fantasy_beyond.character.name", minecraft.player.getDisplayName().getString());
        pGuiGraphics.drawString(font, text, this.width / 3 + 10, y, Color.WHITE.hashCode());
        y += font.lineHeight + 5;
        text = Component.translatable("fantasy_beyond.character.gender").append( Component.literal((data.getPlayerCustomization().isMale()? "♂": "♀")).withColor(data.getPlayerCustomization().isMale()? Color.CYAN.hashCode() : Color.MAGENTA.hashCode()));
        pGuiGraphics.drawString(font, text, this.width / 3 + 10, y, Color.WHITE.hashCode());
        y += font.lineHeight + 5;
        text = Component.translatable("fantasy_beyond.character.race", Component.translatable("race.name." + RaceType.RACE_TYPES_REGISTRY.getKey(data.getPlayerCustomization().getRace().getType()).getPath()).getString());
        pGuiGraphics.drawString(font, text, this.width / 3 + 10, y, Color.WHITE.hashCode());
        y += font.lineHeight + 5;
        text = Component.translatable("fantasy_beyond.character.health", minecraft.player.getMaxHealth());
        pGuiGraphics.drawString(font, text, this.width / 3 + 10, y, Color.WHITE.hashCode());
        if(data.getPlayerClass() != null){
            y += font.lineHeight + 5;
            text = Component.translatable("fantasy_beyond.character.player_class", data.getPlayerClass().getDisplayName());
            pGuiGraphics.drawString(font, text, this.width / 3 + 10, y, Color.WHITE.hashCode());
            y += font.lineHeight + 5;
            text = Component.translatable("fantasy_beyond.character.player_class_level", data.getPlayerClass().getLevel().getLevel());
            pGuiGraphics.drawString(font, text, this.width / 3 + 20, y, Color.WHITE.hashCode());
        }
    }
}

package grillo78.fantasy_beyond.client.screen;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.client.screen.widget.CustomImageButton;
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

public class CharacterScreen extends Screen {

    private static final ResourceLocation CHARACTER_BACKGROUND = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/stats/character_background.png");
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
        addRenderableWidget(new CustomImageButton(72, height - 22, 16, 16, PLAY_BUTTON_TEXTURE, (pButton -> {
            paused = !paused;
            ((CustomImageButton) pButton).setSprites(paused ? PLAY_BUTTON_TEXTURE : PAUSE_BUTTON_TEXTURE);
            pButton.setFocused(false);
        })));
        addRenderableWidget(new CustomImageButton(92, height - 22, 16, 16, RESET_BUTTON_TEXTURE, (pButton -> {
            angleO = 0;
            angle = 0;
        })));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        angleO = angle;
        if(!paused)
            angle--;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        int x = 15;
        int y = 20;
        int width = 150;
        guiGraphics.blit(CHARACTER_BACKGROUND, x, y, 0, 0, width, height - 40, width, height - 40);
        x = this.width / 2;
        width = this.width / 2 - 15;
        guiGraphics.blit(CHARACTER_BACKGROUND, x, y, 0, 0, width, height - 40, width, height - 40);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int x = 15;
        int y = 20;
        int width = 150;
        CharacterData data = Minecraft.getInstance().player.getData(ModAttachments.CHARACTER_DATA);
        pGuiGraphics.enableScissor(x, y, x + width, height - y);
        float oldYRot = Minecraft.getInstance().player.getYHeadRot();
        float oldXRot = Minecraft.getInstance().player.getXRot();
        float oldYBodyRot = Minecraft.getInstance().player.yBodyRot;
        Minecraft.getInstance().player.setYHeadRot(0);
        Minecraft.getInstance().player.setYBodyRot(0);
        Minecraft.getInstance().player.setXRot(0);
        InventoryScreen.renderEntityInInventory(pGuiGraphics, x+width/2, height-40, data.getPlayerCustomization().getRace().getHUDViewerScale()*4.5F, Vec3.ZERO.toVector3f(), new Quaternionf().rotationX((float) Math.toRadians(180)).rotateY(Mth.lerp(pPartialTick, angleO, angle)*0.025F), new Quaternionf(), Minecraft.getInstance().player);
        Minecraft.getInstance().player.setYHeadRot(oldYRot);
        Minecraft.getInstance().player.setXRot(oldXRot);
        Minecraft.getInstance().player.setYBodyRot(oldYBodyRot);
        pGuiGraphics.disableScissor();
    }
}

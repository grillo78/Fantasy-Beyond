package grillo78.fantasy_beyond.client.screen;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.client.screen.widget.*;
import grillo78.fantasy_beyond.network.SyncCharacterData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Quaternionf;

public class CustomizationScreen extends Screen {
    private static final WidgetSprites MALE_TEXTURE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/male"),
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/male_disabled"),
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/male_selected"));
    private static final WidgetSprites FEMALE_TEXTURE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/female"),
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/female_disabled"),
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/female_selected"));
    private static final ResourceLocation GENDERS_BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/genders_background.png");
    private static final ResourceLocation SCROLLS_TEXTURE = ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/scrolls.png");
    public static final WidgetSprites SCROLL_BUTTON_TEXTURE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/scroll_entry"),
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/scroll_entry_selected"));
    private static final WidgetSprites PAUSE_BUTTON_TEXTURE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/pause_button"),
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/pause_button_selected")
    );
    private static final WidgetSprites PLAY_BUTTON_TEXTURE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/play_button"),
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/play_button_selected")
    );
    ;
    private int angle;
    private RacesList racesList;
    private CharacteristicsList characteristicsList;
    private Button maleButton;
    private Button femaleButton;
    private ColorPicker colorPicker;
    private boolean paused = true;

    public CustomizationScreen() {
        super(Component.empty());
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public void setColorPicker(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    @Override
    protected void init() {
        super.init();
        CharacterData data = Minecraft.getInstance().player.getData(ModAttachments.CHARACTER_DATA);
        characteristicsList = new CharacteristicsList(this);
        this.addRenderableWidget(characteristicsList);
        racesList = new RacesList(this, characteristicsList, data.getPlayerCustomization().getRace());
        this.addRenderableWidget(racesList);
        maleButton = new ImageButton(width / 6 - 23, height - 50, 16, 16, MALE_TEXTURE, (button) -> {
            angle = 0;
            button.active = false;
            femaleButton.active = true;
            data.getPlayerCustomization().setMale(true);
        });
        maleButton.active = false;
        addRenderableWidget(maleButton);
        femaleButton = new ImageButton(width / 6 + 10, height - 50, 16, 16, FEMALE_TEXTURE, (button) -> {
            angle = 0;
            button.active = false;
            maleButton.active = true;
            data.getPlayerCustomization().setMale(false);
        });
        addRenderableWidget(femaleButton);
        addRenderableWidget(new CustomImageButton(width / 6 - 8, height - 22, 16, 16, PLAY_BUTTON_TEXTURE, (pButton -> {
            paused = !paused;
            ((CustomImageButton) pButton).setSprites(paused ? PLAY_BUTTON_TEXTURE : PAUSE_BUTTON_TEXTURE);
            pButton.setFocused(false);
        })));
        addRenderableWidget(new ImageButtonWithText(2 * width / 3 + 5, height - 55, width / 3 - 30, 22, 0, 0, 22, SCROLL_BUTTON_TEXTURE, width / 3 - 30, 66, (pButton -> {
            data.getPlayerCustomization().finish();
            PacketDistributor.sendToServer(new SyncCharacterData(Minecraft.getInstance().player.getId(), data.serializeNBT(null)));
            Minecraft.getInstance().player.refreshDimensions();
            this.onClose();
        }), Component.translatable("gui.done")));
    }

    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidgetWrap(T widget) {
        return addRenderableWidget(widget);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {

        boolean canClick = colorPicker == null;
        if (!canClick && !colorPicker.isHovered()) {
            removeRenderable(colorPicker);
            colorPicker = null;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "textures/gui/customization/background.png"), 0, 0, 0, 0, width, height, width, height);
        guiGraphics.blit(GENDERS_BACKGROUND_TEXTURE, width / 6 - 63 / 2, height - 58, 0, 0, 64, 32, 128, 64);
        guiGraphics.blit(SCROLLS_TEXTURE, 2 * width / 3 - 5, 25, 0, 0, width / 3 + 1, height - 50, width / 3 + 1, height - 50);
    }

    @Override
    public void tick() {
        super.tick();
        if (!paused)
            angle++;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int scale = 70;
        float x = width / 6F;
        float y = scale * 0.9F + height / 2F;
        Minecraft.getInstance().player.setYHeadRot(0);
        Minecraft.getInstance().player.setYRot(0);
        InventoryScreen.renderEntityInInventory(pGuiGraphics, (int) x, (int) y, scale, Vec3.ZERO.toVector3f(), new Quaternionf().rotationX((float) Math.PI).rotateLocalY((float) Math.toRadians(paused ? angle : Mth.lerp(pPartialTick, angle, angle + 1))), new Quaternionf().rotationX((float) 0), Minecraft.getInstance().player);
    }

    public <T extends GuiEventListener & Renderable & NarratableEntry> void removeRenderable(T widget) {
        children().remove(widget);
        renderables.remove(widget);
    }
}

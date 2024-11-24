package grillo78.fantasy_beyond.client.screen;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import grillo78.fantasy_beyond.client.screen.widget.CharacteristicsList;
import grillo78.fantasy_beyond.client.screen.widget.RacesList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class CustomizationScreen extends Screen {
    private static final ResourceLocation GENDERS_TEXTURE = new ResourceLocation(FantasyBeyond.MOD_ID, "textures/screen/customization/genders.png");
    private int angle;
    private RacesList racesList;
    private CharacteristicsList characteristicsList;
    private Button maleButton;
    private Button femaleButton;

    public CustomizationScreen() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        super.init();
        Minecraft.getInstance().player.getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
            characteristicsList = new CharacteristicsList(this);
            this.addRenderableWidget(characteristicsList);
            racesList = new RacesList(this, characteristicsList, data.getPlayerCustomization().getRace());
            this.addRenderableWidget(racesList);
            maleButton = new ImageButton(width / 6 - 23, height - 50, 16, 16, 68, 1, 16, GENDERS_TEXTURE, 128, 64, (button) -> {
                angle = 0;
                button.active = false;
                femaleButton.active = true;
                data.getPlayerCustomization().setMale(true);
            });
            maleButton.active = false;
            addRenderableWidget(maleButton);
            femaleButton = new ImageButton(width / 6 + 10, height - 50, 16, 16, 87, 1, 16, GENDERS_TEXTURE, 128, 64, (button) -> {
                angle = 0;
                button.active = false;
                maleButton.active = true;
                data.getPlayerCustomization().setMale(false);
            });
            addRenderableWidget(femaleButton);
        });
    }

    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidgetWrap(T widget){
        return addRenderableWidget(widget);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        pGuiGraphics.blit(new ResourceLocation(FantasyBeyond.MOD_ID, "textures/screen/customization/background.png"), 0, 0, 0, 0, width, height, width, height);
    }

    @Override
    public void tick() {
        super.tick();
        angle++;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        pGuiGraphics.blit(GENDERS_TEXTURE, width / 6 - 63 / 2, height - 58, 0, 0, 64, 32, 128, 64);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int scale = 70;
        float x = width / 6F;
        float y = scale * 0.9F + height / 2F;
        Minecraft.getInstance().player.setYHeadRot(0);
        Minecraft.getInstance().player.setYRot(0);
        InventoryScreen.renderEntityInInventory(pGuiGraphics, (int) x, (int) y, scale, new Quaternionf().rotationX((float) Math.PI).rotateLocalY((float) Math.toRadians(Mth.lerp(pPartialTick, angle, angle + 1))), new Quaternionf().rotationX((float) 0), Minecraft.getInstance().player);
    }
}

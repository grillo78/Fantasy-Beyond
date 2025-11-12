package grillo78.fantasy_beyond.client.screen;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.level.classes.abilities.Ability;
import grillo78.fantasy_beyond.client.screen.widget.AbilityButton;
import grillo78.fantasy_beyond.client.screen.widget.ImageButtonWithText;
import grillo78.fantasy_beyond.network.UnlockAbility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector2i;

import java.awt.*;

public class AbilitiesScreen extends Screen {

    public static final WidgetSprites SCROLL_BUTTON_TEXTURE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/scroll_entry"),
            ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "customization/scroll_entry_selected"));
    private Screen parent;
    private Vector2i offset = new Vector2i(this.width / 2, this.height / 2);

    public AbilitiesScreen(Screen parent) {
        super(Component.empty());
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        offset = new Vector2i(this.width / 2, this.height / 2);
        CharacterData data = Minecraft.getInstance().player.getData(ModAttachments.CHARACTER_DATA);

        for (int i = 0; i < data.getPlayerClass().getAbilities().size(); i++) {
            Ability ability = data.getPlayerClass().getAbilities().get(i);
            addRenderableWidget(new AbilityButton(this.width / 2 + (int) (ability.getMapPosition().x * 50), height / 2 + (int) (ability.getMapPosition().y * 50), 22, 22, ability.getTexture(), button -> {
                if (button instanceof AbilityButton abilityButton && abilityButton.getAbility().canBeUnlocked(Minecraft.getInstance().player)) {
                    abilityButton.setSprites(AbilityButton.UNLOCKED);
                    PacketDistributor.sendToServer(new UnlockAbility(data.getPlayerClass().getAbilities().indexOf(abilityButton.getAbility()), Minecraft.getInstance().player.getId()));
                }
            }, ability));
        }

        addRenderableWidget(new ImageButtonWithText(2, 2, 150 * 2 / 3, 22 * 2 / 3, 0, 0, 22, SCROLL_BUTTON_TEXTURE, width / 3 - 30, 66, (pButton -> {
            this.onClose();
        }), Component.translatable("gui.back")));
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        CharacterData data = Minecraft.getInstance().player.getData(ModAttachments.CHARACTER_DATA);
        Component text = Component.translatable("fantasy_beyond.ability.points", data.getPlayerClass().getAbilityPoints()).withColor(Color.ORANGE.hashCode());
        guiGraphics.drawString(font, text, width / 2 - font.width(text) / 2, 10, Color.WHITE.hashCode());
        for (int i = 0; i < renderables.size(); i++) {
            Renderable renderable = renderables.get(i);
            if (renderable instanceof AbilityButton abilityButton && abilityButton.getAbility().getParents() != null) {
                for (int j = 0; j < abilityButton.getAbility().getParents().size(); j++) {
                    int x = offset.x + (int) (abilityButton.getAbility().getParents().get(j).getMapPosition().x * 50) + abilityButton.getWidth() / 2;
                    int y = offset.y + (int) (abilityButton.getAbility().getParents().get(j).getMapPosition().y * 50) + abilityButton.getHeight() / 2;
                    guiGraphics.hLine(abilityButton.getX() + abilityButton.getWidth() / 2, x, y, (abilityButton.getAbility().getParents().get(j).isUnlocked() ? Color.LIGHT_GRAY : Color.DARK_GRAY).hashCode());
                    guiGraphics.vLine(abilityButton.getX() + abilityButton.getWidth() / 2, y, abilityButton.getY() + abilityButton.getHeight() / 2, (abilityButton.getAbility().getParents().get(j).isUnlocked() ? Color.WHITE : Color.DARK_GRAY).hashCode());
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int scale = 50;
        int dragScale = 1;
        offset.add((int) (dragX * dragScale), (int) (dragY * dragScale));
        for (int i = 0; i < renderables.size(); i++) {
            if (renderables.get(i) instanceof AbilityButton abilityButton) {
                abilityButton.setPosition((int) (abilityButton.getAbility().getMapPosition().x * scale + offset.x), (int) (abilityButton.getAbility().getMapPosition().y * scale + offset.y));
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        super.onClose();
        minecraft.setScreen(parent);
    }
}

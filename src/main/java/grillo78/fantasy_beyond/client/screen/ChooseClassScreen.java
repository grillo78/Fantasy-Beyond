package grillo78.fantasy_beyond.client.screen;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.client.screen.widget.ClassList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ChooseClassScreen extends Screen {

    private Screen parent;

    public ChooseClassScreen(Screen parent) {
        super(Component.empty());
        this.parent = parent;
    }

    @Override
    public void onClose() {
        super.onClose();
        minecraft.setScreen(parent);
    }

    @Override
    protected void init() {
        super.init();
        CharacterData data = minecraft.player.getData(ModAttachments.CHARACTER_DATA);
        ClassList classList = new ClassList(this, data);
        classList.fillClasses();
        addRenderableWidget(classList);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

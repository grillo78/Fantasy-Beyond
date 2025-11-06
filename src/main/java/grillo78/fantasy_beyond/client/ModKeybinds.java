package grillo78.fantasy_beyond.client;

import com.mojang.blaze3d.platform.InputConstants;
import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.client.KeyMapping;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static final Lazy<KeyMapping> OPEN_CHARACTER_SCREEN = Lazy.of(() -> new KeyMapping(
            "key." + FantasyBeyond.MOD_ID + ".open_character_screen",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_I,
            "key.categories." + FantasyBeyond.MOD_ID
    ));
}

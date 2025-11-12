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
    public static final Lazy<KeyMapping> NEXT_QUIVER_INDEX = Lazy.of(() -> new KeyMapping(
            "key." + FantasyBeyond.MOD_ID + ".next_quiver_index",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UP,
            "key.categories." + FantasyBeyond.MOD_ID
    ));
    public static final Lazy<KeyMapping> PREVIOUS_QUIVER_INDEX = Lazy.of(() -> new KeyMapping(
            "key." + FantasyBeyond.MOD_ID + ".previous_quiver_index",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_DOWN,
            "key.categories." + FantasyBeyond.MOD_ID
    ));
    public static final Lazy<KeyMapping> NEXT_ABILITY_INDEX = Lazy.of(() -> new KeyMapping(
            "key." + FantasyBeyond.MOD_ID + ".next_ability_index",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT,
            "key.categories." + FantasyBeyond.MOD_ID
    ));
    public static final Lazy<KeyMapping> PREVIOUS_ABILITY_INDEX = Lazy.of(() -> new KeyMapping(
            "key." + FantasyBeyond.MOD_ID + ".previous_ability_index",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT,
            "key.categories." + FantasyBeyond.MOD_ID
    ));
    public static final Lazy<KeyMapping> ACTIVATE_ABILITY = Lazy.of(() -> new KeyMapping(
            "key." + FantasyBeyond.MOD_ID + ".activate_ability",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_GRAVE_ACCENT,
            "key.categories." + FantasyBeyond.MOD_ID
    ));
}

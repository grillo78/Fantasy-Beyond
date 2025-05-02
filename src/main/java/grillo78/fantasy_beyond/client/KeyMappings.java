package grillo78.fantasy_beyond.client;

import com.mojang.blaze3d.platform.InputConstants;
import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyMappings {

    public static final KeyMapping DODGE = new KeyMapping("key." + FantasyBeyond.MOD_ID + ".dodge", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.categories." + FantasyBeyond.MOD_ID);

}

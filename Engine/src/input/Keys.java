package input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.util.HashMap;
import java.util.Map;

import io.FileManager;

public class Keys {

	private static final String KEYBINDING_LOCATION = "keybinding.properties";
	private Map<String, Integer> keybinding = new HashMap<>();
	private Map<String, Integer> keyCodes = new HashMap<>();

	public Keys() {
		setKeyCodes();

		for (String keybind : FileManager.getProperties(KEYBINDING_LOCATION)) {
			int equalsLocation = keybind.indexOf('=');
			String action = keybind.substring(0, equalsLocation);
			int key = keyCodes.get(keybind.substring(equalsLocation + 1));
			keybinding.put(action, key);
		}
	}

	private void setKeyCodes() {
		keyCodes.put("GLFW_KEY_P", GLFW_KEY_P);
		keyCodes.put("GLFW_KEY_M", GLFW_KEY_M);
		keyCodes.put("GLFW_KEY_F", GLFW_KEY_F);
		keyCodes.put("GLFW_KEY_ESCAPE", GLFW_KEY_ESCAPE);
		keyCodes.put("GLFW_KEY_W", GLFW_KEY_W);
		keyCodes.put("GLFW_KEY_A", GLFW_KEY_A);
		keyCodes.put("GLFW_KEY_D", GLFW_KEY_D);
		keyCodes.put("GLFW_KEY_S", GLFW_KEY_S);
		keyCodes.put("GLFW_KEY_UP", GLFW_KEY_UP);
		keyCodes.put("GLFW_KEY_LEFT", GLFW_KEY_LEFT);
		keyCodes.put("GLFW_KEY_DOWN", GLFW_KEY_DOWN);
		keyCodes.put("GLFW_KEY_RIGHT", GLFW_KEY_RIGHT);
		keyCodes.put("GLFW_KEY_1", GLFW_KEY_1);
		keyCodes.put("GLFW_KEY_2", GLFW_KEY_2);
		keyCodes.put("GLFW_KEY_3", GLFW_KEY_3);
		keyCodes.put("GLFW_KEY_4", GLFW_KEY_4);
		keyCodes.put("GLFW_KEY_SPACE", GLFW_KEY_SPACE);
	}

	public int getKey(String action) {
		return keybinding.get(action);
	}
}

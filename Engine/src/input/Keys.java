package input;

import java.util.HashMap;
import java.util.Map;

import io.FileManager;

public class Keys {

	private static final String KEYBINDING_LOCATION = "keyboardbinding.properties";
	private static Map<String, Integer> keybinding = new HashMap<>();

	public static void init() {
		for (String keybind : FileManager.getProperties(KEYBINDING_LOCATION)) {
			int equalsLocation = keybind.indexOf('=');
			String action = keybind.substring(0, equalsLocation);
			int key = Keyboard.getKey(keybind.substring(equalsLocation + 1));
			keybinding.put(action, key);
		}
	}

	public static int getKey(String action) {
		return keybinding.get(action);
	}
}

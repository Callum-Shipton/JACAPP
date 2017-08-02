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
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard {

	private static GLFWKeyCallback keyCallback;
	private static int[] keys = new int[1024];
	private static Map<String, Integer> keyCodes = new HashMap<>();

	public static void destroy() {
		if (keyCallback != null) {
			keyCallback.free();
		}
	}

	public static int getKey(int key) {
		return keys[key];
	}

	public static void init(long window) {
		setKeyCodes();

		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {

			@Override
			// Polling key method, keypress calls callback which stores the most
			// recent event for each key.
			// allows simultaneous key presses and reduced need for references
			// for everything that
			// needs input handling.
			public void invoke(long window, int key, int scancode, int action, int mods) {

				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					glfwSetWindowShouldClose(window, true);

				if ((key >= 0) && (key < keys.length)) {
					if (action == GLFW_REPEAT) {
						keys[key] = 2;
					} else if (action == GLFW_PRESS) {
						keys[key] = 1;
					} else if (action == GLFW_RELEASE) {
						keys[key] = 0;
					}
				}
			}
		});
	}

	public static void setKey(int key) {
		keys[key] = 0;
	}

	public static void setKey(int key, boolean value) {
		if (value) {
			keys[key] = 1;
		} else {
			keys[key] = 0;
		}
	}

	public static void setKey(int key, int value) {
		keys[key] = value;
	}

	private static void setKeyCodes() {
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

	public static int getKey(String name) {
		return keyCodes.get(name);
	}
}

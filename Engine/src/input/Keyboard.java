package input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard {

	private static GLFWKeyCallback keyCallback;
	private static int keys[] = new int[1024];

	public static void destroy() {
		if (keyCallback != null) {
			keyCallback.free();
		}
	}

	public static int getKey(int key) {
		return keys[key];
	}

	public static void keyCheck(long window) {
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {

			@Override
			// Polling key method, keypress calls callback which stores the most
			// recent event for each key.
			// allows simultaneous key presses and reduced need for references
			// for everything that
			// needs input handling.
			public void invoke(long window, int key, int scancode, int action, int mods) {
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
}

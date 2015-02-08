import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {

	private static GLFWKeyCallback keyCallback;
	private static int keys[] = new int[1024];

	public static void keyCheck(long window) {
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action,
					int mods) {
				if (action == GLFW_REPEAT)
					keys[key] = 2;
				else if (action == GLFW_PRESS)
					keys[key] = 1;
				else if (action == GLFW_RELEASE)
					keys[key] = 0;
			}
		});
	}

	/*       

	 */

	public static int getKey(int key) {
		return keys[key];
	}

	public static void destroy() {
		keyCallback.release();

	}
}

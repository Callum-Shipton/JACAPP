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

public class Keys {
	public int pause = GLFW_KEY_P;
	public int mute = GLFW_KEY_M;
	public int fullscreen = GLFW_KEY_F;
	public int quit = GLFW_KEY_ESCAPE;

	public int moveUp = GLFW_KEY_W;
	public int moveLeft = GLFW_KEY_A;
	public int moveRight = GLFW_KEY_D;
	public int moveDown = GLFW_KEY_S;

	public int lookUp = GLFW_KEY_UP;
	public int lookLeft = GLFW_KEY_LEFT;
	public int lookDown = GLFW_KEY_DOWN;
	public int lookRight = GLFW_KEY_RIGHT;

	public int potion1 = GLFW_KEY_1;
	public int potion2 = GLFW_KEY_2;
	public int potion3 = GLFW_KEY_3;
	public int potion4 = GLFW_KEY_4;

	public int shoot = GLFW_KEY_SPACE;

	public Keys() {

	}
}

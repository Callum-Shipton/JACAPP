import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;

public class Player extends Entity {

	public Player() throws IOException {
		super(10, 10, 10, 10, Art.grass);
	}

	public Player(int posX, int posY, int speed, int health, String image) {
		super(posX, posY, speed, health, image);
	}

	// called every update
	public void update() {
		checkMove();
	}
	
	private void checkMove() {
		if (Keyboard.getKey(GLFW_KEY_W) == 1
				|| Keyboard.getKey(GLFW_KEY_W) == 2) {
			moveVertically(-1);
		}
		if (Keyboard.getKey(GLFW_KEY_A) == 1
				|| Keyboard.getKey(GLFW_KEY_A) == 2) {
			moveHorizontally(-1);
		}
		if (Keyboard.getKey(GLFW_KEY_S) == 1
				|| Keyboard.getKey(GLFW_KEY_S) == 2) {
			moveVertically(1);
		}
		if (Keyboard.getKey(GLFW_KEY_D) == 1
				|| Keyboard.getKey(GLFW_KEY_D) == 2) {
			moveHorizontally(1);
		}

	}

}

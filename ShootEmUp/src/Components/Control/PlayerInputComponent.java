package Components.Control;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import Components.Attack.BaseAttack;
import Components.Graphical.PlayerGraphics;
import Input.Keyboard;
import Math.Vector2;
import Object.Entity;
import Object.Weapon;

public class PlayerInputComponent implements InputComponent {
	
	PlayerGraphics PGC;
	BaseAttack BA;
	
	PlayerInputComponent(PlayerGraphics PGC, BaseAttack BA){
		this.PGC = PGC;
		this.BA = BA;
	}

	@Override
	public void update(Entity e) {
		Vector2 movement = new Vector2(0.0f, 0.0f);
		if (Keyboard.getKey(GLFW_KEY_W) == 1
				|| Keyboard.getKey(GLFW_KEY_W) == 2) {
			movement.add(0.0f, -1.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_A) == 1
				|| Keyboard.getKey(GLFW_KEY_A) == 2) {
			movement.add(-1.0f, 0.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_S) == 1
				|| Keyboard.getKey(GLFW_KEY_S) == 2) {
			movement.add(0.0f, 1.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_D) == 1
				|| Keyboard.getKey(GLFW_KEY_D) == 2) {
			movement.add(1.0f, 0.0f);
		}

		if (movement.length() > 0) {
			if (movement.length() > 1)
				movement.normalize();
			PGC.setAnimating(true);
			e.move(movement);
			PGC.scrollScreen(e);

		} else
			PGC.setAnimating(false);
		Vector2 dir = new Vector2(0.0f, 0.0f);
		if (Keyboard.getKey(GLFW_KEY_UP) == 1
				|| Keyboard.getKey(GLFW_KEY_UP) == 2) {
			dir.add(0.0f, -1.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_LEFT) == 1
				|| Keyboard.getKey(GLFW_KEY_LEFT) == 2) {
			dir.add(-1.0f, 0.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_DOWN) == 1
				|| Keyboard.getKey(GLFW_KEY_DOWN) == 2) {
			dir.add(0.0f, 1.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_RIGHT) == 1
				|| Keyboard.getKey(GLFW_KEY_RIGHT) == 2) {
			dir.add(1.0f, 0.0f);
		}
		if (dir.length() > 0) {
			if (dir.length() > 1)
				dir.normalize();
			PGC.setDirection((int) (Math.round(dir.Angle()) / 45));
		}

		if (Keyboard.getKey(GLFW_KEY_SPACE) == 1
				|| Keyboard.getKey(GLFW_KEY_SPACE) == 2) {
			BA.attack(e, PGC.getDirection());
		}

	}
	
}

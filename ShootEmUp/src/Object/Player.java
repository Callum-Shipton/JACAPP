package Object;

import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import Display.Art;
import Display.Image;
import Input.Keyboard;
import Main.ShootEmUp;
import Math.Matrix4;
import Math.Vector2;

public class Player extends NPC {

	private int lives;
	private Weapon weapon;
	private FloatBuffer matrix44Buffer;
	private Matrix4 viewMatrix;
	private int viewMatrixLocation;
	private int viewMatrixLocationInst;

	public Player() throws IOException {
		super(10.0f, 10.0f, 64.0f, 64.0f, 10, 0, Art.player);
	}

	public Player(float x, float y, float width, float height, int speed,
			int direction, Image image) {
		super(x, y, width, height, speed, direction, image);

		viewMatrix = new Matrix4();
		viewMatrix.clearToIdentity();
		viewMatrix.translate(-x + (ShootEmUp.WIDTH - width) / 2, -y
				+ (ShootEmUp.HEIGHT - height) / 2, 0);
		viewMatrix.transpose();
		matrix44Buffer = BufferUtils.createFloatBuffer(16);
		viewMatrixLocation = GL20.glGetUniformLocation(Art.ShaderBase,
				"viewMatrix");
		viewMatrixLocationInst = GL20.glGetUniformLocation(Art.ShaderInst,
				"viewMatrix");
		scrollScreen();
		health = 10;
		weapon = new Weapon(10, 10);
		team = 0;
	}

	// called every update
	public void update() {
		checkKeys();
		checkDead();
	}

	private void checkKeys() {
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
			move(movement);
			scrollScreen();

		}
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
			direction = (int) (Math.round(dir.Angle()) / 45);
		}

		if (Keyboard.getKey(GLFW_KEY_SPACE) == 1
				|| Keyboard.getKey(GLFW_KEY_SPACE) == 2) {
			shoot();
		}
	}

	private void scrollScreen() {
		viewMatrix.clearToIdentity();
		viewMatrix.translate(-posX + (ShootEmUp.WIDTH - width) / 2, -posY
				+ (ShootEmUp.HEIGHT - height) / 2, 0);
		matrix44Buffer.clear();
		matrix44Buffer = viewMatrix.toBuffer();
		GL20.glUseProgram(Art.ShaderBase);
		GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);
		GL20.glUseProgram(0);
		GL20.glUseProgram(Art.ShaderInst);
		GL20.glUniformMatrix4(viewMatrixLocationInst, false, matrix44Buffer);
		GL20.glUseProgram(0);
	}

	public void checkDead() {
		if (health <= 0) {
			respawn();
		}
	}

	private void respawn() {
		posX = ShootEmUp.currentLevel.spawn[0];
		posY = ShootEmUp.currentLevel.spawn[1];
		scrollScreen();
		health = 10;
	}

	private void shoot() {
		weapon.shoot(posX, posY, direction, team);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
}

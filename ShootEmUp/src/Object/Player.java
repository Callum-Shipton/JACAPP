package Object;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.FloatBuffer;
import java.util.HashSet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import Input.Keyboard;
import Main.ShootEmUp;
import Math.Matrix4;
import Math.Vector2;

public class Player extends Character {

	private int level;
	private int currentExp;
	private int expBound;
	private int mana;
	private int maxMana;
	private int manaRegen;
	private int healthRegen;
	private int lives;
	private int coins;

	private FloatBuffer matrix44Buffer;
	private Matrix4 viewMatrix;
	private int viewMatrixLocation;
	private int viewMatrixLocationInst;
	private int fireRate = 10;

	public Player(float x, float y) {
		super(x, y);
		canfly = false;
		setMaxHealth(18);
		health = maxHealth;
		width = 64.0f;
		height = 64.0f;
		speed = 5;
		direction = 0;
		image = Art.player;

		weapon = new Weapon(1, 10);

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

		team = 0;
		mana = 18;
		maxMana = 18;
		manaRegen = 50;
		healthRegen = 100;
		level = 1;
		currentExp = 0;
		expBound = 1;
		coins = 0;
	}

	// called every update
	public void update() {
		super.update();
		checkKeys();
		checkDead();
		if (manaRegen <= 0) {
			manaRegen = 50;
			if (mana < maxMana) {
				mana++;
			}
		}
		manaRegen--;

		if (healthRegen <= 0) {
			healthRegen = 100;
			if (health < maxHealth) {
				health++;
			}
		}
		healthRegen--;
		
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap
				.getEntites(gridPos);

		if (coins < 99) {
			for (Entity coin : entities) {
				if (coin instanceof Coin) {
					if (coin.doesCollide(posX, posY, width, height) != null) {
						((Coin) coin).remove();
						coins++;
					}
				}
			}
		}
		for (Entity exp : entities) {
			if (exp instanceof Exp) {
				if (exp.doesCollide(posX, posY, width, height) != null) {
					((Exp) exp).remove();
					currentExp++;
				}
			}
		}
		if (currentExp >= expBound) {
			currentExp = 0;
			if (level < 99) {
				level++;
			}
			if (expBound < 18) {
				expBound++;
			}
		}
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
			animating = true;
			move(movement);
			scrollScreen();

		} else
			animating = false;
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
			if ((fireRate <= 0) && (mana >= 1)) {
				weapon.shoot(posX, posY, direction, team);
				mana--;
				fireRate = 10;
			}
		}
		fireRate--;
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

	public boolean checkDead() {
		if (health <= 0) {
			respawn();
			return true;
		}
		return false;
	}

	private void respawn() {
		posX = ShootEmUp.currentLevel.spawn[0];
		posY = ShootEmUp.currentLevel.spawn[1];
		scrollScreen();
		health = getMaxHealth();
		mana = getMaxMana();
		currentExp = 0;
		expBound = 1;
		level = 1;
		coins = 0;
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

	public int getMaxMana() {
		return maxMana;
	}

	public int getMana() {
		return mana;
	}

	public int getCurrentExp() {
		return currentExp;
	}

	public int getExpBound() {
		return expBound;
	}

	public int getLevel() {
		return level;
	}

	public int getCoins() {
		return coins;
	}
}

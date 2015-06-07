package Object;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.FloatBuffer;
import java.util.HashSet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import Display.Art;
import Input.Keyboard;
import Main.ShootEmUp;
import Math.Matrix4;
import Math.Vector2;

public class Player extends Character {

	private int level;
	private int currentExp;
	private int expBound;

	private int lives;
	private int coins;



	public Player(float x, float y) {
		super(x, y);
		setTeam(0);

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
		
		
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap
				.getEntites(gridPos);

		if (coins < 99) {
			for (Entity coin : entities) {
				if (coin instanceof Coin) {
					if (coin.doesCollide(getPosX(), getPosY(), getWidth(), getHeight()) != null) {
						((Coin) coin).remove();
						coins++;
					}
				}
			}
		}
		
		for (Entity armour : entities) {
			if (armour instanceof Armour) {
				if (armour.doesCollide(getPosX(), getPosY(), getWidth(), getHeight()) != null) {
					((Armour) armour).remove();
					//add pickup to inventory
				}
			}
		}
		for (Entity item : entities) {
			if (item instanceof Item) {
				if (item.doesCollide(getPosX(), getPosY(), getWidth(), getHeight()) != null) {
					((Item) item).remove();
					//add pickup to inventory
				}
			}
		}
		for (Entity weapon : entities) {
			if (weapon instanceof Weapon) {
				if (weapon.doesCollide(getPosX(), getPosY(), getWidth(), getHeight()) != null) {
					((Weapon) weapon).remove();
					//add pickup to inventory
				}
			}
		}
		
		for (Entity exp : entities) {
			if (exp instanceof Exp) {
				if (exp.doesCollide(getPosX(), getPosY(), getWidth(), getHeight()) != null) {
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
		
	}

	public boolean checkDead() {
		if (health <= 0) {
			respawn();
			return true;
		}
		return false;
	}

	private void respawn() {

		scrollScreen();
		currentExp = 0;
		expBound = 1;
		level = 1;
		coins = 0;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
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

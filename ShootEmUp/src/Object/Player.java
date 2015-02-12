package Object;
import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;

import Display.Art;
import Input.Keyboard;
import Main.Level;

public class Player extends Entity {
	
	private int health;
	
	private int lives; 
	private Weapon weapon;
	
	public Player() throws IOException {
		super(10, 10, 10, 0, Art.grass);
	}

	public Player(int posX, int posY, int speed, int direction, String image) {
		super(posX, posY, speed, direction, image);
		health = 10;
		weapon = new Weapon();
	}

	// called every update
	public void update() {
		checkKeys();
		deathCheck();
	}
	
	private void checkKeys() {
		direction = 0;
		if (Keyboard.getKey(GLFW_KEY_W) == 1
				|| Keyboard.getKey(GLFW_KEY_W) == 2) {
			moveVertically(-1);

			direction += 4;
		}
		if (Keyboard.getKey(GLFW_KEY_A) == 1
				|| Keyboard.getKey(GLFW_KEY_A) == 2) {
			moveHorizontally(-1);
			direction += 2;
		}
		if (Keyboard.getKey(GLFW_KEY_S) == 1
				|| Keyboard.getKey(GLFW_KEY_S) == 2) {
			moveVertically(1);
			direction += 1;
		}
		if (Keyboard.getKey(GLFW_KEY_D) == 1
				|| Keyboard.getKey(GLFW_KEY_D) == 2) {
			moveHorizontally(1);
			direction += 8;
		}
		
		if (Keyboard.getKey(GLFW_KEY_SPACE) == 1
				|| Keyboard.getKey(GLFW_KEY_SPACE) == 2) {
			shoot();
		}
	}
	
	private void deathCheck(){
		if(health <= 0){
			respawn();
		}
	}

	private void respawn(){
		lives--;
		posX = 10;
		posY = 10;
	}
	
	private void shoot(){
		Level.p = new Particle(posX, posY, 10, direction, Art.particle, 1, 50);
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

	public Weapon getWeapon(){
		return weapon;
	}
	
	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
	}
}

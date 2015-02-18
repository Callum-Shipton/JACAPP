package Object;
import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;

import Display.Art;
import Input.Keyboard;
import Math.Vector2;

public class Player extends NPC {
	
	private int health;
	
	private int lives; 
	private Weapon weapon;
	
	public Player() throws IOException {
		super(10.0f, 10.0f, 64.0f, 64.0f, 10, 0, Art.playerID);
	}

	public Player(float x, float y, float width, float height, int speed, int direction, int image) {
		super(x, y, width, height, speed, direction, image);
		health = 10;
		weapon = new Weapon(10, 10);
	}

	// called every update
	public void update() {
		
		checkKeys();
		deathCheck();
	}
	
	private void checkKeys() {
		Vector2 movement = new Vector2(0.0f,0.0f);
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
		
		if(movement.length() > 0){
			if(movement.length() > 1) movement.normalize();
			move(movement);
		}
		Vector2 dir = new Vector2(0.0f,0.0f);
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
		if(dir.length() > 0){
		if(dir.length() > 1) dir.normalize();
		direction =  (int) (Math.round(dir.Angle()) / 45);
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
		posX = 10.0f;
		posY = 10.0f;
	}
	
	private void shoot(){
		weapon.shoot(posX, posY, direction);
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

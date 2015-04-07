package Object;

import Display.Image;
import Main.ShootEmUp;

public abstract class Character extends Entity {
	protected int health;
	private int maxHealth;
	protected Weapon weapon;

	public Character(float x, float y, float width, float height, int speed, int direction, Image image) {
		super(x, y, width, height, speed, direction, image);
		canfly = false;
		setMaxHealth(18);
		health = getMaxHealth();
		weapon = new Weapon(1, 10);
	}
	
	public void update(){
	}

	public void checkDead() {
		if (health <= 0) {
			ShootEmUp.currentLevel.characters.remove(this);
		}
	}

	public int getHealth() {
		return health;
	}

	public void damage(int damage) {
		this.health -= damage;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
}

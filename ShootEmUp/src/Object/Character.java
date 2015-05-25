package Object;

import Display.Image;
import Main.ShootEmUp;

public abstract class Character extends Entity {
	protected int health;
	protected int maxHealth;
	protected Weapon weapon;

	public Character(float x, float y) {
		super(x, y);
	}
	
	public void update(){
		super.update();
	}

	public boolean checkDead() {
		if (health <= 0) {
			destroy = true;
			ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
			return true;
		}
		return false;
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

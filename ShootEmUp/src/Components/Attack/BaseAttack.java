package Components.Attack;

import Object.Entity;
import Object.Weapon;

public abstract class BaseAttack implements AttackComponent {
	
	protected Weapon weapon;
	protected int fireRate;
	protected int health;
	protected int healthRegen;
	protected int maxHealth;
	
	@Override
	public abstract void attack(Entity e, int dir);

	@Override
	public abstract void update(Entity e);
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

}

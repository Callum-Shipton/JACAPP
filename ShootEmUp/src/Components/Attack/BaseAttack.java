package Components.Attack;

import Components.Component;
import Components.ComponentType;
import Object.Entity;
import Object.Weapon;

public abstract class BaseAttack extends Component implements AttackComponent {
	
	protected ComponentType type = ComponentType.ATTACK;
	
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
	
	public void damage(int damage) {
		this.health -= damage;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public ComponentType getType() {
		return type;
	}
	
}
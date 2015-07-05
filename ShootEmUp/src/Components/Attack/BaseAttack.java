package Components.Attack;

import Components.Component;
import Components.ComponentType;
import Components.Spawn.BaseSpawn;
import Object.Entity;
import Object.Weapon;

public abstract class BaseAttack extends Component implements AttackComponent {
	
	protected ComponentType type = ComponentType.ATTACK;
	protected BaseSpawn BS;
	
	protected Weapon weapon;
	protected int fireRate;
	protected int health;
	protected int healthRegen;
	protected int maxHealth;
	protected int maxHealthRegen;
	
	@Override
	public abstract void attack(Entity e, int dir);

	@Override
	public abstract void update(Entity e);
	
	public void destroy(Entity e){
		
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getHealthRegen() {
		return healthRegen;
	}

	public void setHealthRegen(int healthRegen) {
		this.healthRegen = healthRegen;
	}
	
	public int getMaxHealthRegen() {
		return maxHealthRegen;
	}

	public void setMaxHealthRegen(int maxHealthRegen) {
		this.maxHealthRegen = maxHealthRegen;
	}
	
	public void damage(int damage, Entity e) {
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
package components.attack;

import object.Entity;
import object.Weapon;
import components.Component;
import components.ComponentType;

public abstract class BaseAttack extends Component implements AttackComponent {

	protected ComponentType type = ComponentType.ATTACK;
	
	protected Weapon weapon;
	protected int health;
	protected int healthRegen;
	protected int maxHealth;
	protected int maxHealthRegen;
	protected int mana;
	protected int manaRegen;
	protected int maxMana;
	protected int maxManaRegen;
	private int fireCountdown;

	@Override
	public abstract void update(Entity e);
	
	public abstract void damage(int damage, Entity e);
	
	public void attack(Entity e, int dir) {
		if(fireCountdown <= 0){
			if(mana >= weapon.getManaCost()){
				weapon.attack(e, dir);
				mana -= weapon.getManaCost();
			}
			fireCountdown = weapon.getFireRate();
		}
		fireCountdown--;
	}
	
	public void healthRegen(){
		if (health < maxHealth) {
			if (healthRegen <= 0) {
				healthRegen = maxHealthRegen;
				health++;
			}
			healthRegen--;
		}
	}
	
	public void manaRegen(){
		if (mana < maxMana) {
			if (manaRegen <= 0) {
				manaRegen = maxManaRegen;
				mana++;
			}
			manaRegen--;
		}
	}
	
	public void addHealth(int i){
		health += i;
		if(health > maxHealth){
			health = maxHealth;
		}
	}
	
	public void addMana(int i){
		mana += i;
		if(mana > maxMana){
			mana = maxMana;
		}
	}
	
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
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getManaRegen() {
		return manaRegen;
	}

	public void setManaRegen(int manaRegen) {
		this.manaRegen = manaRegen;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getMaxManaRegen() {
		return maxManaRegen;
	}

	public void setMaxManaRegen(int maxManaRegen) {
		this.maxManaRegen = maxManaRegen;
	}

	public ComponentType getType() {
		return type;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
}
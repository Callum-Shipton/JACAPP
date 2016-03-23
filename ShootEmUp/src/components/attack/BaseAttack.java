package components.attack;

import object.Entity;
import object.Weapon;
import components.Component;
import components.TypeComponent;

public abstract class BaseAttack extends Component implements AttackComponent {

	protected TypeComponent type = TypeComponent.ATTACK;
	
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
	
	private boolean fire = false;
	private int fireCounter = 0;
	private int fireTime = 10;
	private boolean poison = false;
	private int poisonCounter = 0;
	private int poisonTime = 20;

	@Override
	public void update(Entity e){
		if(fire == true){
			damage(1, null);
			fireCounter++;
			if(fireCounter > fireTime){
				fire = false;
				fireCounter = 0;
			}
		}
		if(poison == true){
			damage(2, null);
			poisonCounter++;
			if(poisonCounter > poisonTime){
				poison = false;
				poisonCounter = 0;
			}
		}
	}

	public void damage(int damage, Entity e){
		this.health -= damage;
	}
	
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

	public TypeComponent getType() {
		return type;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public void setFire(boolean fire) {
		this.fire = fire;
	}

	public void setPoison(boolean poison) {
		this.poison = poison;
	}
}
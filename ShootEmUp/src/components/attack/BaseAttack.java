package components.attack;

import components.Component;
import components.Message;
import components.TypeComponent;
import entity.Entity;
import loop.Loop;
import object.Armour;
import object.Weapon;

public abstract class BaseAttack extends Component implements AttackComponent {

	protected TypeComponent componentType = TypeComponent.ATTACK;

	protected TypeAttack type;

	protected Weapon weapon;

	protected Armour boots = null;
	protected Armour legs = null;
	protected Armour chest = null;
	protected Armour helmet = null;
	protected int armourValue = 0;

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
	private final int FIRE_TIME = 1;
	private int fireStop = 0;
	private final int FIRE_HITS = 10;
	private boolean poison = false;
	private int poisonCounter = 0;
	private final int POISON_TIME = 2;

	public BaseAttack(TypeAttack type) {
		this.type = type;
		this.healthRegen = 100;
		this.manaRegen = 100;
	}

	public BaseAttack(TypeAttack type, int health, int mana, Weapon weapon) {

		this(type);

		this.weapon = weapon;
		this.health = health;
		this.maxHealth = health;
		this.maxHealthRegen = this.healthRegen;
		this.mana = mana;

		this.maxMana = mana;
		this.maxManaRegen = this.manaRegen;
	}

	public void addHealth(int i) {
		this.health += i;
		if (this.health > this.maxHealth) {
			this.health = this.maxHealth;
		}
	}

	public void addMana(int i) {
		this.mana += i;
		if (this.mana > this.maxMana) {
			this.mana = this.maxMana;
		}
	}

	@Override
	public void attack(Entity e, int dir) {
		if (this.fireCountdown <= 0) {
			if (this.mana >= this.weapon.getManaCost()) {
				this.weapon.attack(e, dir);
				this.mana -= this.weapon.getManaCost();
			}
			this.fireCountdown = this.weapon.getFireRate();
		}
		this.fireCountdown--;
	}

	public void damage(int damage, Entity e) {
		if (this.armourValue != 0) {
			damage = damage / this.armourValue;
		}
		this.health -= damage;
		if (this.health <= 0) {
			die(e);
		}
	}

	@Override
	public void destroy(Entity e) {

	}

	public abstract void die(Entity e);

	public void healthRegen() {
		if (health < maxHealth) {
			if (healthRegen <= 0) {
				healthRegen = maxHealthRegen;
				health++;
			}
			healthRegen--;
		}
	}

	public void manaRegen() {
		if (mana < maxMana) {
			if (manaRegen <= 0) {
				manaRegen = maxManaRegen;
				mana++;
			}
			manaRegen--;
		}
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

	protected void setArmourValue() {
		armourValue = 0;
		if (boots != null) {
			armourValue += boots.getDefence();
		} else if (legs != null) {
			armourValue += legs.getDefence();
		} else if (chest != null) {
			armourValue += chest.getDefence();
		} else if (helmet != null) {
			armourValue += helmet.getDefence();
		}
	}

	@Override
	public void update(Entity e) {

		healthRegen();
		manaRegen();

		if (this.fire == true) {
			this.fireCounter++;
			if (this.fireCounter >= Loop.ticks(this.FIRE_TIME)) {
				damage(1, e);
				this.fireCounter = 0;
				this.fireStop++;
				if (this.fireStop > this.FIRE_HITS) {
					this.fire = false;
					this.fireStop = 0;
				}
			}
		}
		if (this.poison == true) {
			this.poisonCounter++;
			if (this.poisonCounter > Loop.ticks(this.POISON_TIME)) {
				damage(2, e);
				this.poisonCounter = 0;
			}
		}
	}

	public void setBoots(Armour boots) {
		this.boots = boots;
		setArmourValue();
	}

	public void setChest(Armour chest) {
		this.chest = chest;
		setArmourValue();
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setHealthRegen(int healthRegen) {
		this.healthRegen = healthRegen;
	}

	public void setHelmet(Armour helmet) {
		this.helmet = helmet;
		setArmourValue();
	}

	public void setLegs(Armour legs) {
		this.legs = legs;
		setArmourValue();
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void setManaRegen(int manaRegen) {
		this.manaRegen = manaRegen;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setMaxHealthRegen(int maxHealthRegen) {
		this.maxHealthRegen = maxHealthRegen;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public void setMaxManaRegen(int maxManaRegen) {
		this.maxManaRegen = maxManaRegen;
	}

	public void setPoison(boolean poison) {
		this.poison = poison;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public boolean isFire() {
		return fire;
	}

	public boolean isPoison() {
		return poison;
	}

	public TypeAttack getAttackType() {
		return type;
	}

	public Armour getBoots() {
		return boots;
	}

	public Armour getChest() {
		return chest;
	}

	public int getHealth() {
		return health;
	}

	public int getHealthRegen() {
		return healthRegen;
	}

	public Armour getHelmet() {
		return helmet;
	}

	public Armour getLegs() {
		return legs;
	}

	public int getMana() {
		return mana;
	}

	public int getManaRegen() {
		return manaRegen;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getMaxHealthRegen() {
		return maxHealthRegen;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public int getMaxManaRegen() {
		return maxManaRegen;
	}

	@Override
	public TypeComponent getType() {
		return componentType;
	}

	public Weapon getWeapon() {
		return weapon;
	}
}
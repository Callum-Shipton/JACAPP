package components.attack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import components.Component;
import components.Message;
import components.MessageId;
import components.TypeComponent;
import entity.Entity;
import loop.Loop;
import object.Armour;
import object.Weapon;

public abstract class BaseAttack extends Component implements AttackComponent {
	protected TypeAttack type;
	
	protected Set<String> weaponTypes = new HashSet<>();
	
	protected static final String BOW = "Bow";
	protected static final String DAGGER = "Dagger";
	protected static final String ONE_HANDED = "OneHanded";
	protected static final String TWO_HANDED = "TwoHanded";
	protected static final String STAFF = "Staff";
	protected static final String CROSSBOW = "Crossbow";

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
	private static final int FIRE_TIME = 1;
	private int fireStop = 0;
	private static final int FIRE_HITS = 10;

	private boolean poison = false;
	private int poisonCounter = 0;
	private static final int POISON_TIME = 2;

	public BaseAttack(TypeAttack type) {
		this.type = type;
		
		switch (type) {
		case ARCHER:
			weaponTypes.add(BOW);
			weaponTypes.add(DAGGER);
			break;
		case MAGE:
			weaponTypes.add(STAFF);
			weaponTypes.add(DAGGER);
			break;
		case WARRIOR:
			weaponTypes.add(ONE_HANDED);
			weaponTypes.add(TWO_HANDED);
			break;
		case BATTLE_MAGE:
			weaponTypes.add(ONE_HANDED);
			weaponTypes.add(STAFF);
			break;
		case ROGUE:
			weaponTypes.add(CROSSBOW);
			weaponTypes.add(DAGGER);
			break;
		default:
			weaponTypes.add(ONE_HANDED);
			weaponTypes.add(TWO_HANDED);
		}
		
		healthRegen = 100;
		manaRegen = 100;
	}

	public BaseAttack(TypeAttack type, int health, int mana, Weapon weapon) {
		this(type);

		this.weapon = weapon;
		this.health = health;
		maxHealth = health;
		maxHealthRegen = healthRegen;
		this.mana = mana;

		maxMana = mana;
		maxManaRegen = manaRegen;
	}

	public void addHealth(int i) {
		health += i;
		if (health > maxHealth) {
			health = maxHealth;
		}
	}

	public void addMana(int i) {
		mana += i;
		if (mana > maxMana) {
			mana = maxMana;
		}
	}

	@Override
	public boolean attack(Entity e, int dir) {
		if (fireCountdown <= 0) {
			if (mana >= weapon.getManaCost()) {
				weapon.attack(e, dir);
				e.send(new Message(MessageId.SHOOT));
				mana -= weapon.getManaCost();
				fireCountdown = weapon.getFireRate();
				return true;
			}

		}
		fireCountdown--;
		return false;
	}

	public void damage(int damage, Entity e) {
		if (armourValue != 0) {
			damage = damage / armourValue;
		}
		health -= damage;
		if (health <= 0) {
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

		if (fire == true) {
			fireCounter++;
			if (fireCounter >= Loop.ticks(FIRE_TIME)) {
				damage(1, e);
				fireCounter = 0;
				fireStop++;
				if (fireStop > FIRE_HITS) {
					fire = false;
					fireStop = 0;
				}
			}
		}
		if (poison == true) {
			poisonCounter++;
			if (poisonCounter > Loop.ticks(POISON_TIME)) {
				damage(2, e);
				poisonCounter = 0;
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
		return TypeComponent.ATTACK;
	}

	public Weapon getWeapon() {
		return weapon;
	}
	
	public Set<String> getWeaponTypes(){
		return weaponTypes;
	}
}
package components.attack;

import java.util.HashSet;
import java.util.Set;

import component.interfaces.AttackComponent;
import components.Component;
import components.Message;
import components.MessageId;
import components.TypeComponent;
import entity.Entity;
import loop.Loop;
import object.Armour;
import object.Weapon;

public abstract class BaseAttack extends Component implements AttackComponent {
	protected Set<String> weaponTypes = new HashSet<>();

	protected transient Weapon weapon;
	protected String weaponId;
	protected int team;

	protected transient Armour boots = null;
	protected transient Armour legs = null;
	protected transient Armour chest = null;
	protected transient Armour helmet = null;
	
	protected String helmetId;
	protected String chestId;
	protected String legsId;
	protected String bootsId;
	
	protected transient int armourValue = 0;

	protected transient int health;
	protected transient int healthRegen;
	protected int maxHealth;
	protected transient int maxHealthRegen;

	protected transient int mana;
	protected transient int manaRegen;
	protected int maxMana;
	protected transient int maxManaRegen;
	private transient int fireCountdown;

	private transient boolean fire = false;
	private transient int fireCounter = 0;
	private transient static final int FIRE_TIME = 1;
	private transient int fireStop = 0;
	private transient static final int FIRE_HITS = 10;

	private transient boolean poison = false;
	private transient int poisonCounter = 0;
	private transient static final int POISON_TIME = 2;
	
	public BaseAttack() {
		
	}
	
	public BaseAttack(int health, int mana, String weaponId, int team, Set<String> weaponTypes) {

		
	}
	
	public BaseAttack(int health, int mana, String weaponId, int team, Set<String> weaponTypes, String helmetId, String chestId, String legsId, String bootsId) {
		healthRegen = 100;
		manaRegen = 100;
		
		this.health = health;
		maxHealth = health;
		maxHealthRegen = healthRegen;
		
		this.mana = mana;
		maxMana = mana;
		maxManaRegen = manaRegen;

		this.weaponId = weaponId;
		this.team = team;
		this.weaponTypes = weaponTypes;
		if(weaponId != null)weapon = new Weapon(weaponId, team);

		this.helmetId = helmetId;
		this.chestId = chestId;
		this.legsId = legsId;
		this.bootsId = bootsId;
		
		if(helmetId != null) helmet = new Armour(helmetId);
		if(chestId != null) chest = new Armour(chestId);
		if(legsId != null) legs = new Armour(legsId);
		if(bootsId != null) boots = new Armour(bootsId);
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
		if(weapon != null) {
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
		}
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
	public void destroy() {

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
	public void receive(Message m) {
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
	public void update() {

		healthRegen();
		manaRegen();

		if (fire) {
			fireCounter++;
			if (fireCounter >= Loop.ticks(FIRE_TIME)) {
				damage(1, getEntity());
				fireCounter = 0;
				fireStop++;
				if (fireStop > FIRE_HITS) {
					fire = false;
					fireStop = 0;
				}
			}
		}
		if (poison) {
			poisonCounter++;
			if (poisonCounter > Loop.ticks(POISON_TIME)) {
				damage(2, getEntity());
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

	public Set<String> getWeaponTypes() {
		return weaponTypes;
	}
}
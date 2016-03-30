  package components.attack;

import object.Armour;
import object.ArmourBuilder;
import object.Entity;
import object.Weapon;
import object.WeaponBuilder;
import save.CharacterSave;
import main.ShootEmUp;
import gui.menus.GameOverMenu;
import components.Message;
import components.inventory.SubTypeWeapon;
import display.Art;

public class PlayerAttack extends BaseAttack {
	protected TypeAttack type;
	
	protected int lives;
	
	private Armour boots = null;
	private Armour legs = null;
	private Armour chest = null;
	private Armour helmet = null;
	
	private int armourValue = 0;
	
	public PlayerAttack(TypeAttack type){
		this.type = type;
		
		healthRegen = 100;
		manaRegen = 100;
		lives = 3;
		
		switch(type){
		case WARRIOR:
			health = 5;
			mana = 3;
			weapon = WeaponBuilder.buildWeapon(SubTypeWeapon.SWORD, 0);
			break;
		case ARCHER:
			health = 4;
			mana = 4;
			weapon = WeaponBuilder.buildWeapon(SubTypeWeapon.LONGBOW, 0);
			break;
		case MAGE:
			health = 3;
			mana = 5;
			weapon = WeaponBuilder.buildWeapon(SubTypeWeapon.FIRE_STAFF, 0);
			break;
		case BATTLE_MAGE:
			health = 3;
			mana = 5;
			weapon = WeaponBuilder.buildWeapon(SubTypeWeapon.EARTH_STAFF, 0);
			break;
		case ROGUE:
			health = 3;
			mana = 5;
			weapon = WeaponBuilder.buildWeapon(SubTypeWeapon.CROSSBOW, 0);
			break;
		}
		
		maxHealth = health;
		maxHealthRegen = healthRegen;
		maxMana = mana;
		maxManaRegen = manaRegen;
	}
	
	public PlayerAttack(TypeAttack type, CharacterSave save){
		this.type = type;
		
		healthRegen = 100;
		manaRegen = 100;
		lives = save.getLives();
		
		
		health = save.getHealth();
		mana = save.getMana();
		weapon = WeaponBuilder.buildWeapon(save.getWeapon(), 0);
		if(save.getBoots() != null){
			boots = ArmourBuilder.buildArmour(save.getBoots());
			setArmourValue();
		}
		if(save.getLegs() != null){
			legs = ArmourBuilder.buildArmour(save.getLegs());
			setArmourValue();
		}
		if(save.getChest() != null){
			chest = ArmourBuilder.buildArmour(save.getChest());
			setArmourValue();
		}
		if(save.getHelmet() != null){
			helmet = ArmourBuilder.buildArmour(save.getHelmet());
			setArmourValue();
		}
		
		maxHealth = save.getMaxHealth();
		maxHealthRegen = save.getMaxHealthRegen();
		maxMana = save.getMaxMana();
		maxManaRegen = save.getMaxManaRegen();
	}
	
	public PlayerAttack(TypeAttack type, Weapon weapon, int health, int healthRegen, int maxHealth, int mana, int manaRegen, int maxMana, int lives){
		this.setWeapon(weapon);
		this.health = health;
		this.healthRegen = healthRegen;
		maxHealthRegen = healthRegen;
		this.maxHealth = maxHealth;
		this.mana = mana;
		this.manaRegen = manaRegen;
		this.maxMana = maxMana;
		maxManaRegen = manaRegen;
		this.lives = lives;
		this.type = type;
		
	}
	
	@Override
	public void update(Entity e) {
		healthRegen();
		manaRegen();
	}
	
	@Override
	public void damage(int damage, Entity e) {
		if(armourValue != 0){
			damage = damage / armourValue;
		}
		super.damage(damage, e);
		if(health <= 0) {
			health = maxHealth;
			mana = maxMana;
			removeLife();
			e.send(Message.ENTITY_DIED);
		}
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}
	
	public void removeLife() {
		if(lives == 1){
			ShootEmUp.paused = true;
			ShootEmUp.menuStack.add(new GameOverMenu(Art.gameOverScreen));
		} else {
			lives--;
		}
	}
	
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public TypeAttack getTypeAttack(){
		return type;
	}
	
	public Armour getBoots() {
		return boots;
	}

	public Armour getLegs() {
		return legs;
	}

	public Armour getChest() {
		return chest;
	}

	public Armour getHelmet() {
		return helmet;
	}
	
	public void setBoots(Armour boots) {
		this.boots = boots;
		setArmourValue();
	}

	public void setChest(Armour chest) {
		this.chest = chest;
		setArmourValue();
	}
	
	public void setLegs(Armour legs) {
		this.legs = legs;
		setArmourValue();
	}
	
	public void setHelmet(Armour helmet) {
		this.helmet = helmet;
		setArmourValue();
	}
		
	public void setArmourValue() {
		armourValue = 0;
		if(boots != null){
			armourValue += boots.getDefence();
		} else if(legs != null){
			armourValue += legs.getDefence();
		} else if(chest != null){
			armourValue += chest.getDefence();
		} else if(helmet != null){
			armourValue += helmet.getDefence();
		}
	}
}
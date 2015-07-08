  package components.attack;

import object.Entity;
import object.Weapon;
import object.WeaponBuilder;
import main.ShootEmUp;
import gui.menus.GameOverMenu;
import components.Message;
import components.inventory.TypeWeapon;
import display.Art;

public class PlayerAttack extends BaseAttack {
	protected TypeAttack type;
	
	protected int lives;
	
	public PlayerAttack(TypeAttack type){
		this.type = type;
		health = 3;
		maxHealth = health;
		healthRegen = 100;
		maxHealthRegen = healthRegen;
		mana = 3;
		maxMana = mana;
		manaRegen = 100;
		maxManaRegen = manaRegen;
		lives = 3;
		
		switch(type){
		case WARRIOR:
			weapon = WeaponBuilder.buildWeapon(TypeWeapon.SWORD, 0);
			break;
		case ARCHER:
			weapon = WeaponBuilder.buildWeapon(TypeWeapon.BOW, 0);
			break;
		case MAGE:
			weapon = WeaponBuilder.buildWeapon(TypeWeapon.EARTH_STAFF, 0);
		}
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
		this.health -= damage;
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
}
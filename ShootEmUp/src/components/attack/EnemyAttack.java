package components.attack;

import object.Entity;
import object.Weapon;
import object.WeaponBuilder;
import main.ShootEmUp;
import components.Message;
import components.inventory.TypeWeapon;

public class EnemyAttack extends BaseAttack implements AttackComponent {
	protected TypeAttack type;
	
	public EnemyAttack(TypeAttack type, int health){
		this.type = type;
		
		this.health = health;
		maxHealth = health;
		healthRegen = 100;
		maxHealthRegen = healthRegen;
		mana = 3;
		maxMana = mana;
		manaRegen = 100;
		maxManaRegen = manaRegen;
		
		switch(type){
		case WARRIOR:
			weapon = WeaponBuilder.buildWeapon(TypeWeapon.SWORD, 1);
			break;
		case ARCHER:
			weapon = WeaponBuilder.buildWeapon(TypeWeapon.BOW, 1);
			break;
		case MAGE:
			weapon = WeaponBuilder.buildWeapon(TypeWeapon.EARTH_STAFF, 1);
		}
	}
	
	public EnemyAttack(TypeAttack type, Weapon weapon, int health, int healthRegen, int maxHealth, int mana, int manaRegen, int maxMana){
		this.setWeapon(weapon);
		this.health = health;
		this.healthRegen = healthRegen;
		maxHealthRegen = healthRegen;
		this.maxHealth = maxHealth;
		this.mana = mana;
		this.manaRegen = manaRegen;
		this.maxMana = maxMana;
		maxManaRegen = manaRegen;
		this.type = type;
	}

	@Override
	public void update(Entity e) {
		healthRegen();
		manaRegen();
	}
	
	public void died(Entity e){
		e.destroy();
		ShootEmUp.currentLevel.spawner.removeEnemy();
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}
}

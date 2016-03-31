package components.attack;

import object.Armour;
import object.Entity;
import object.Weapon;
import main.ShootEmUp;
import components.Message;

public class EnemyAttack extends BaseAttack implements AttackComponent {
	
	public EnemyAttack(TypeAttack type, int health, Weapon weapon, Armour helmet, Armour chest, Armour legs, Armour boots){
		this.type = type;
		
		this.health = health;
		maxHealth = health;
		healthRegen = 100;
		maxHealthRegen = healthRegen;
		mana = 3;
		maxMana = mana;
		manaRegen = 100;
		maxManaRegen = manaRegen;
		
		this.weapon = weapon;
		this.helmet = helmet;
		this.chest = chest;
		this.legs = legs;
		this.boots = boots;
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
	
	@Override
	public void damage(int damage, Entity e) {
		super.damage(damage, e);
		if(health <= 0) {
			e.destroy();
			ShootEmUp.currentLevel.spawner.removeEnemy();
		}
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}
}

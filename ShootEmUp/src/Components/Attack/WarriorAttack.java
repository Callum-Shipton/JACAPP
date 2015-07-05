package Components.Attack;

import Components.Message;
import Components.Inventory.PlayerInventory;
import Components.Spawn.BaseSpawn;
import Object.Entity;
import Object.Weapon;

public class WarriorAttack extends PlayerAttack implements AttackComponent {

	public WarriorAttack(BaseSpawn BS, PlayerInventory PI, Weapon weapon, int health, int healthRegen, int maxHealth, int mana, int manaRegen, int maxMana){
		this.BS = BS;
		this.PI = PI;
		this.weapon = weapon;
		this.health = health;
		this.healthRegen = healthRegen;
		this.maxHealth = maxHealth;
		this.mana = mana;
		this.manaRegen = manaRegen;
		this.maxMana = maxMana;
		
	}
	
	@Override
	public void attack(Entity e, int dir) {
		if(fireRate <= 0){
			if(weapon.isMelee()){
				weapon.attack(e, dir);
			}
			else if(mana >= weapon.getManaCost()){
				weapon.attack(e, dir);
				mana-=weapon.getManaCost();
			}
			fireRate = weapon.getFireRate();
		}
	}
	
	@Override
	public void update(Entity e) {
		if(fireRate > 0)fireRate--;
		
		if(health <= 0) {
			health = maxHealth;
			mana = maxMana;
			BS.spawn(e);
			PI.removeLife();
		}
		
		if (healthRegen <= 0) {
			healthRegen = 100;
			if (health < maxHealth) {
				health++;
			}
		}
		healthRegen--;
		
		if (manaRegen <= 0) {
			manaRegen = 50;
			if (mana < maxMana) {
				mana++;
			}
		}
		manaRegen--;
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}
}

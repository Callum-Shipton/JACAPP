package Components.Attack;

import Components.Message;
import Components.Inventory.PlayerInventory;
import Components.Spawn.BaseSpawn;
import Object.Entity;
import Object.Weapon;

public class ArcherAttack extends PlayerAttack implements AttackComponent {

	public ArcherAttack(BaseSpawn BS, PlayerInventory PI, Weapon weapon, int health, int healthRegen, int maxHealth, int mana, int manaRegen, int maxMana){
		this.BS = BS;
		this.PI = PI;
		this.setWeapon(weapon);
		this.health = health;
		this.healthRegen = healthRegen;
		this.maxHealthRegen = healthRegen;
		this.maxHealth = maxHealth;
		this.mana = mana;
		this.manaRegen = manaRegen;
		this.maxMana = maxMana;
		this.maxManaRegen = manaRegen;
		
	}
	
	@Override
	public void attack(Entity e, int dir) {
		if(fireRate <= 0){
			if(getWeapon().isMelee()){
				getWeapon().attack(e, dir);
			}
			else if(mana >= getWeapon().getManaCost()){
				getWeapon().attack(e, dir);
				mana-=getWeapon().getManaCost();
			}
			fireRate = getWeapon().getFireRate();
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
		
		healthRegen();
		
		if (manaRegen <= 0) {
			manaRegen = maxManaRegen;
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

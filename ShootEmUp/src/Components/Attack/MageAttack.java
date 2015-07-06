package Components.Attack;

import Components.Message;
import Components.Spawn.BaseSpawn;
import Object.Entity;
import Object.Weapon;

public class MageAttack extends PlayerAttack implements AttackComponent {

	public MageAttack(BaseSpawn BS, Weapon weapon, int health, int healthRegen, int maxHealth, int mana, int manaRegen, int maxMana, int lives){
		this.BS = BS;
		this.setWeapon(weapon);
		this.health = health;
		this.healthRegen = healthRegen;
		this.maxHealth = maxHealth;
		this.maxHealthRegen = healthRegen;
		this.mana = mana;
		this.manaRegen = manaRegen;
		this.maxMana = maxMana;
		this.maxManaRegen = manaRegen;
		this.lives = lives;
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
			removeLife();
			BS.spawn(e);
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

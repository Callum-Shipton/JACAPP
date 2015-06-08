package Components.Attack;

import Components.Message;
import Components.Spawn.BaseSpawn;
import Object.Entity;
import Object.Weapon;

public class MageAttack extends BaseAttack implements AttackComponent {

	BaseSpawn BS;
	
	protected int mana;
	protected int manaRegen;
	protected int maxMana;
	
	public MageAttack(BaseSpawn BS, Weapon weapon, int fireRate, int health, int healthRegen, int maxHealth, int mana, int manaRegen, int maxMana){
		this.BS = BS;
		this.weapon = weapon;
		this.fireRate = fireRate;
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
			fireRate = weapon.getFirerate();
		}
	}
	
	@Override
	public void update(Entity e) {
		if(fireRate > 0)fireRate--;
		
		if(health <= 0) {
			health = maxHealth;
			mana = maxMana;
			BS.spawn(e);
		}
		
		if (healthRegen <= 0) {
			healthRegen = 100;
			if (health < maxHealth) {
				maxHealth++;
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

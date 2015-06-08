package Components.Attack;

import Components.Message;
import Components.Spawn.BaseSpawn;
import Object.Entity;
import Object.Weapon;

public class MeleeAttack extends BaseAttack implements AttackComponent {

	BaseSpawn BS;
	
	public MeleeAttack(BaseSpawn BS, Weapon weapon, int health, int healthRegen, int maxHealth){
		this.BS = BS;
		this.weapon = weapon;
		this.health = health;
		this.healthRegen = healthRegen;
		this.maxHealth = maxHealth;
	}
	
	@Override
	public void attack(Entity e, int dir) {
		if(fireRate <= 0){
			if(weapon.isMelee()){
				weapon.attack(e, dir);
			}
			fireRate = weapon.getFireRate();
		}
	}

	@Override
	public void update(Entity e) {
		if(fireRate > 0)fireRate--;
		
		if(health <= 0) {
			e.send(Message.ENTITY_DIED);
		}
		
		if (healthRegen <= 0) {
			healthRegen = 100;
			if (getHealth() < getMaxHealth()) {
				setHealth(getHealth() + 1);
			}
		}
		healthRegen--;
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}

}

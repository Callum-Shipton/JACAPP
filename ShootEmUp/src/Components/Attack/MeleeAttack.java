package Components.Attack;

import Components.Message;
import Components.Spawn.BaseSpawn;
import Object.Entity;

public class MeleeAttack extends BaseAttack implements AttackComponent {

	BaseSpawn BS;
	
	public MeleeAttack(BaseSpawn BS){
		this.BS = BS;
	}
	
	@Override
	public void attack(Entity e, int dir) {
		if(fireRate <= 0){
			if(weapon.isMelee()){
				weapon.attack(e, dir);
			}
			fireRate = weapon.getFirerate();
		}
	}

	@Override
	public void update(Entity e) {
		if(fireRate > 0)fireRate--;
		
		if(health <= 0) {
			health = maxHealth;
			BS.spawn(e);
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
	public void receive(Message m) {
		// TODO Auto-generated method stub
		
	}

}

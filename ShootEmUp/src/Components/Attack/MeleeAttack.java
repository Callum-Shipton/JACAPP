package Components.Attack;

import Object.Entity;

public class MeleeAttack extends BaseAttack implements AttackComponent {

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
		if (healthRegen <= 0) {
			healthRegen = 100;
			if (getHealth() < getMaxHealth()) {
				setHealth(getHealth() + 1);
			}
		}
		healthRegen--;
	}

}

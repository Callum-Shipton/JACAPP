package components.attack;

import main.ShootEmUp;
import object.Armour;
import object.Entity;
import object.Weapon;

public class EnemyAttack extends BaseAttack implements AttackComponent {

	public EnemyAttack(TypeAttack type, int health, int mana, Weapon weapon, Armour helmet, Armour chest, Armour legs,
			Armour boots) {
		super(type,health, mana, weapon);
		
		this.helmet = helmet;
		this.chest = chest;
		this.legs = legs;
		this.boots = boots;
	}

	@Override
	public void die(Entity e) {
		e.destroy();
		ShootEmUp.currentLevel.spawner.removeEnemy();
	}

}

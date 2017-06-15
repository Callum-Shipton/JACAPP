package components.attack;

import display.Art;
import gui.HudBar;
import main.ShootEmUp;
import object.Armour;
import object.Entity;
import object.Weapon;

public class EnemyAttack extends BaseAttack implements AttackComponent {

	private HudBar healthBar;

	public EnemyAttack(TypeAttack type, int health, int mana, Weapon weapon, Armour helmet, Armour chest, Armour legs,
			Armour boots) {
		super(type, health, mana, weapon);

		this.helmet = helmet;
		this.chest = chest;
		this.legs = legs;
		this.boots = boots;

		this.healthBar = new HudBar(10.0f, 10.0f, Art.getImage("BarHealth"), 0.25f);
	}

	@Override
	public void die(Entity e) {
		e.destroy();
		ShootEmUp.getCurrentLevel().getSpawner().removeEnemy();
	}

	@Override
	public void update(Entity e) {
		super.update(e);
		healthBar.update();
		healthBar.setValue(health);
		healthBar.setMaxValue(maxHealth);
	}

}

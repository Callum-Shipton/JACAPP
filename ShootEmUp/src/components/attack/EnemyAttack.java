package components.attack;

import display.ImageProcessor;
import entity.Entity;
import gui.HudBar;
import logging.Logger;
import logging.Logger.Category;
import main.ShootEmUp;
import object.Armour;
import object.Weapon;

public class EnemyAttack extends BaseAttack implements AttackComponent {

	private HudBar healthBar;

	public EnemyAttack(TypeAttack type, int health, int mana, Weapon weapon, Armour helmet, Armour chest, Armour legs,
			Armour boots, Entity entity) {
		super(type, health, mana, weapon, entity);

		this.helmet = helmet;
		this.chest = chest;
		this.legs = legs;
		this.boots = boots;

		healthBar = new HudBar(10.0f, 10.0f, ImageProcessor.getImage("Bars"), 1, 0.25f);
	}

	@Override
	public void die(Entity e) {
		Logger.debug("Enemy Died: " + e.getId(), Category.ENTITIES);
		e.destroy();
		ShootEmUp.getGame().getCurrentLevel().removeEnemy();
	}

	@Override
	public void update(Entity e) {
		super.update(e);
		healthBar.update();
		healthBar.setValue(health);
		healthBar.setMaxValue(maxHealth);
	}
}

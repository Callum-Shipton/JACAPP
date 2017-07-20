package components.attack;

import java.util.HashSet;
import java.util.Set;

import display.ImageProcessor;
import entity.Entity;
import gui.HudBar;
import logging.Logger;
import logging.Logger.Category;
import main.ShootEmUp;
import object.Armour;

public class EnemyAttack extends BaseAttack implements AttackComponent {

	private HudBar healthBar;

	public EnemyAttack(int health, int mana, String weaponId, Armour helmet, Armour chest, Armour legs, Armour boots,
			Set<String> weaponTypes) {
		super(health, mana, weaponId, 0, weaponTypes);

		this.helmet = helmet;
		this.chest = chest;
		this.legs = legs;
		this.boots = boots;

		healthBar = new HudBar(10.0f, 10.0f, ImageProcessor.getImage("Bars"), 1, 0.25f);
	}

	public EnemyAttack(EnemyAttack enemyAttack) {
		this(enemyAttack.maxHealth, enemyAttack.maxMana, enemyAttack.weaponId, null, null, null, null,
				new HashSet<String>(enemyAttack.weaponTypes));
	}

	@Override
	public void die(Entity e) {
		Logger.debug("Enemy Died: " + e.getId(), Category.ENTITIES);
		e.destroy();
		ShootEmUp.getGame().getCurrentLevel().removeEnemy();
	}

	@Override
	public void update() {
		super.update();
		healthBar.update();
		healthBar.setValue(health);
		healthBar.setMaxValue(maxHealth);
	}
}
